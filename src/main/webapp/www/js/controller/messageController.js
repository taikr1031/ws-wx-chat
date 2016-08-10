angular.module('chat.messageController', [])

    .config(function ($httpProvider) {
      $httpProvider.defaults.transformRequest = function (obj) {
        var str = [];
        for (var p in obj) {
          str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
        }
        return str.join("&");
      };
      $httpProvider.defaults.headers.post = {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })

    .controller('messageCtrl', ['$rootScope', '$scope', '$stateParams',
      '$ionicScrollDelegate', '$ionicActionSheet', '$timeout', '$ionicLoading', 'messageService',
      function ($rootScope, $scope, $stateParams, $ionicScrollDelegate, $ionicActionSheet, $timeout, $ionicLoading, messageService) {
        var viewScroll = $ionicScrollDelegate.$getByHandle('messageDetailsScroll');
        var beginDate;
        // 聊天界面中录音按钮开启（down）和停止（up）事件绑定
        $('#voiceBtn').bind('touchstart', function () {
          console.log('touchstart');
          beginDate = new Date();
          wxjs.run(function () {
            console.log('startRecord');
            wx.startRecord();
          });
        }).bind('touchend', function () {
          console.log('touchend');
          var endDate = new Date();
          wxjs.run(function () {
            wx.stopRecord({
              success: function (res) {
                var localId = res.localId;
                $scope.localId = localId;
                $scope.msg = localId;
                var intervalNum = Math.round((endDate.getTime() - beginDate.getTime()) / 1000);
                sendVoice(localId, intervalNum);
              }
            });
          });
        });

        // 页面载入事件
        $scope.$on("$ionicView.beforeEnter", function () {
          var chatIndex = parseInt($stateParams.chatIndex);
          var userIds = $stateParams.chatId.split('-');
          var friendId = (userIds[0] == $stateParams.ownId) ? userIds[1] : userIds[0];
          $scope.userModel = {
            chatId: $stateParams.chatId,
            ownId: $stateParams.ownId,
            ownName: $stateParams.ownName,
            friendId: friendId
          };
          var promiseChat = messageService.queryMessage($stateParams.chatId); // 同步调用，获得承诺接口
          promiseChat.then(function(data) { // 调用承诺API获取数据 .resolve
            $scope.messages = data.messageList;
          }, function(data) { // 处理错误 .reject
            console.log('queryMessage error!');
          });
          // 将该聊天信息的未读条数清除和未读状态
          //if($scope.userId == userIds[0]) {
          if($rootScope.chatList[chatIndex].auserId == friendId) {
            $rootScope.chatList[chatIndex].auserNoReadNum = 0;
            $rootScope.chatList[chatIndex].auserShowHints = false;
          } else {
            $rootScope.chatList[chatIndex].buserNoReadNum = 0;
            $rootScope.chatList[chatIndex].buserShowHints = false;
          }
          // 设置聊天对象的openid
          if($rootScope.chatList[chatIndex].auserId == friendId) {
            $scope.userModel.ownPic = $rootScope.chatList[chatIndex].buserPic;
            $scope.userModel.friendCode = $rootScope.chatList[chatIndex].auserCode;
          } else {
            $scope.userModel.ownPic = $rootScope.chatList[chatIndex].auserPic;
            $scope.userModel.friendCode = $rootScope.chatList[chatIndex].buserCode;
          }
          //messageService.updateChat($scope.chat);
          $scope.messageNum = 10;
          //$scope.messages = messageService.getAmountMessageById($scope.messageNum, $stateParams.chatId);
          $timeout(function () {
            viewScroll.scrollBottom();
          }, 0);
          connect();
        });

        $scope.isInputText = true;
        $scope.msg = "";
        $scope.doRefresh = function () {
          $scope.messageNum += 5;
          $timeout(function () {
            // duplicate
            var promiseChat = messageService.queryMessage($stateParams.chatId); // 同步调用，获得承诺接口
            promiseChat.then(function(data) { // 调用承诺API获取数据 .resolve
              $scope.messages = data.messageList;
            }, function(data) { // 处理错误 .reject
              console.log('queryMessage error!');
            });

            $scope.$broadcast('scroll.refreshComplete');
          }, 1);
        };

        var ws = null;
        var url = null;
        var transports = [];

        // 连接到spring的websocket
        var connect = function () {
          ws = new WebSocket('ws://' + IP + ':' + PORT + '/ws');
          ws.onopen = function () {
            setConnected(true);
            log('Info: connection opened.');
          };
          ws.onmessage = function (event) {
            log('Received: ' + event.data);
            var ownId = ($scope.userModel.chatId.split('-')[0] == $scope.userModel.ownId) ? $scope.userModel.chatId.split('-')[1] : $scope.userModel.chatId.split('-')[0];
            var data = generateMessage(event.data, ownId, $scope.userModel.ownPic, 'TEXT');
            $scope.messages.push(data);
            $timeout(function () {
              viewScroll.scrollBottom();
            }, 0);
          };
          ws.onclose = function (event) {
            setConnected(false);
            log('Info: connection closed.');
            log(event);
          };
        };

        var disconnect = function () {
          if (ws != null) {
            ws.close();
            ws = null;
          }
          setConnected(false);
        };

        // 用户发送微信消息后，同时通过websocket发给服务器，服务器通过websocket给收信人推送一条消息，收信人的ws.onmessage事件回调函数将该消息自动显示在聊天界面最下方，
        var sendMessage = function (message) {
          if (ws != null) {
            log('sendMessage: ' + message);
            ws.send(message);
          } else {
            alert('connection not established, please connect.');
          }
        };

        var log = function (message) {
          console.log(message);
        };

        var setConnected = function (connected) {
        };

        /* LOCATION*/
        var location = {
          latitude: 0, longitude: 0, speed: 0, accuracy: 0
        };

        var getLocation = function () {
          wxjs.run(function () {
            wx.getLocation({
              type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
              success: function (res) {
                location.latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                location.longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                location.speed = res.speed; // 速度，以米/每秒计
                location.accuracy = res.accuracy; // 位置精度
                openLocation();
              },
              cancel: function () {
                alert('用户拒绝授权获取地理位置');
              }
            });
          })
        };

        var openLocation = function () {
          alert('东经：' + parseFloat(location.longitude) + '， 北纬：' + parseFloat(location.latitude));
          wx.openLocation({
            latitude: parseFloat(location.latitude), // 纬度，浮点数，范围为90 ~ -90
            longitude: parseFloat(location.longitude), // 经度，浮点数，范围为180 ~ -180。
            name: '楚烟', // 位置名
            address: '武汉市硚口区1号', // 地址详情说明
            scale: 16, // 地图缩放级别,整形值,范围从1~28。默认为最大
            infoUrl: 'http://www.whcyit.com/whcyit/index.xhtml' // 在查看位置界面底部显示的超链接,可点击跳转
          });
        };
        /* LOCATION*/

        /* IMAGE */
        var images = {
          localId: [],
          serverId: [],
          downloadId: []
        };
        var uploadedCount = 0;

        var chooseImage = function () {
          images.localId.length = 0;
          images.serverId.length = 0;
          images.downloadId.length = 0;
          uploadedCount = 0;

          wxjs.run(function () {
            wx.chooseImage({
              count: 2, // 默认9
              sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
              sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
              success: function (res) {
                images.localId = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                uploadImage(images.localId);
              }
            });
          });
        };

        var uploadImage = function (localIds) {
          $('body,html').animate({scrollTop: 0}, 500);
          $ionicLoading.show({
            template: '图片上传中  ...'
          });
          var localIdsClone = localIds.slice();
          var localId = localIdsClone.pop();
          wx.uploadImage({
            localId: localId,
            isShowProgressTips: 0,
            success: function (res) {
              $ionicLoading.show({
                template: '图片上传中 ' + (uploadedCount + 1) + '/' + localIds.length + ' ...'
              });
              images.serverId.push(res.serverId);
              uploadedCount++;
              if (localIdsClone.length > 0) {
                uploadImage(localIdsClone);
              } else {
                images.downloadId = downloadImage(images.serverId);
                $timeout(function () {
                  for (var i = 0; i < images.serverId.length; i++) {
                    sendImages(images.serverId[i], images.downloadId[i]);
                  }
                }, 100);
              }
              $ionicLoading.hide();
            }, fail: function (res) {
              $ionicLoading.hide();
              alert('uploadImage error: ' + JSON.stringify(res));
            }
          })
        };

        var downloadImage = function (serverIds) {
          var serverIdsClone = serverIds.slice();
          var serverId = serverIdsClone.pop();
          wx.downloadImage({
            serverId: serverId, // 需要下载的图片的服务器端ID，由uploadImage接口获得
            isShowProgressTips: 0, // 默认为1，显示进度提示
            success: function (res) {
              images.downloadId.push(res.localId); // 返回图片下载后的本地ID
              if (serverIdsClone.length > 0) {
                downloadImage(serverIdsClone);
              }
            }, fail: function (res) {
              alert('downloadImage error: ' + JSON.stringify(res));
            }
          });
          return images.downloadId;
        };

        var sendImages = function (serverId, downloadId) {
          messageService.sendImage($scope.message.openid, serverId);
          var data = {};
          data.content = downloadId;
          data.fromeMe = true;
          data.time = new Date();
          data.type = 'IMAGE';
          data.mediaId = serverId;
          $scope.messages.push(data);
          $scope.msg = '';
          $timeout(function () {
            document.getElementById(downloadId).src = downloadId;
            viewScroll.scrollBottom();
          }, 0);
        };

        $scope.previewImage = function (downloadId) {
          wxjs.run(function () {
            wx.previewImage({
              current: downloadId,
              urls: images.downloadId
            });
          });
        };
        /* IMAGE */


        /* VOICE */
        $scope.palyAudio = function (mediaId) {
          wxjs.run(function () {
            wx.playVoice({
              localId: mediaId// 需要播放的音频的本地ID，由stopRecord接口获得
            });
          });
        };

        sendVoice = function (mediaId, intervalNum) {
          var data = {};
          data.content = ' ' + intervalNum + '秒';
          data.fromeMe = true;
          data.time = new Date();
          data.type = 'VOICE';
          data.mediaId = mediaId;
          $scope.messages.push(data);
          messageService.sendVoice($scope.message.openid, mediaId);
          $scope.msg = '';
          viewScroll.scrollBottom();
        };
        /* VOICE */

        var generateMessage = function (msg, ownId, ownPic, type) {
          var data = {};
          if (msg.indexOf('___') != -1) {
            data.content = msg.split('___')[0];
          } else {
            data.content = msg;
          }
          data.userId = ownId;
          data.time = new Date();
          data.type = type;
          data.pic = ownPic;
          data.mediaId = null;
          return data;
        };

        /* TEXT */
        $scope.sendText = function () {
          sendMessage($scope.msg + '___' + $scope.userModel.friendId);
          var data = generateMessage($scope.msg, $scope.userModel.ownId, $scope.userModel.ownPic, 'TEXT');
          $scope.messages.push(data);
          messageService.sendText($scope.userModel.chatId, $scope.userModel.ownId, $scope.userModel.friendCode, $scope.userModel.ownPic, $scope.msg, 'TEXT');
          $scope.msg = '';
          viewScroll.scrollBottom();
        };

        $scope.toggleInput = function (isInputText) {
          $scope.isInputText = !isInputText;
        };
        /* TEXT */

        // 点击“加号”展开其他功能菜单
        $scope.show = function () {
          // Show the action sheet
          var hideSheet = $ionicActionSheet.show({
            buttons: [
              {text: '<i class="ion-ios-camera icon-button icon-action" ></i>    <span class="tab-action"></span>     <i class="text-action">照片</i> '},
              {text: '<i class="ion-social-instagram icon-button icon-action" ></i>   <span class="tab-action"></span>        <i class="text-width">小视频</i> '},
              {text: '<i class="ion-ios-videocam icon-button icon-action" ></i>   <span class="tab-action"></span>        <i class="text-width">视频聊天</i> '},
              {text: '<i class="ion-ios-location icon-button icon-action" ></i>    <span class="tab-action"></span>        <i class="text-width">位置</i> '},
              {text: '<i class="ion-ios-eye icon-button icon-action" ></i>    <span class="tab-action"></span>        <i class="text-width">收藏</i> '},
              //{ text: '<i class="ion-more icon-button icon-action" ></i>               <span class="tab-action"></span>        <i class="text-width">More</i> ' },
            ],
            //destructiveText: 'Delete',
            //titleText: 'Modify your album',
            //cssClass: 'social-actionsheet',
            //cancelText: 'Cancel',
            //cancel: function() {
            //},
            buttonClicked: function (index) {
              if (index == '0') {
                chooseImage();
              } else if (index == '3') {
                getLocation();
              }
              return true;
            }
          });
          // For example's sake, hide the sheet after two seconds
          //me.$timeout(function() {
          //  hideSheet();
          //}, 2000);
        };

        window.addEventListener("native.keyboardshow", function (e) {
          viewScroll.scrollBottom();
        });
      }
    ])
