angular.module('wechat.chatController', [])

    .controller('chatCtrl', function ($scope, $state, $ionicPopup, $timeout, localStorageService, messageService) {

      $scope.$on("$ionicView.beforeEnter", function () {
        var promiseChat = messageService.queryChat(); // 同步调用，获得承诺接口
        promiseChat.then(function(data) { // 调用承诺API获取数据 .resolve
          $scope.chats = data.chatList;
        }, function(data) { // 处理错误 .reject
          console.log('queryChat error!');
        });
        console.log($scope.chats);

        //var promiseUser = messageService.getUserId();
        //promiseUser.then(function(data) {
        //  console.log(data);
        //  $scope.userId = data;
        //}, function(data) {
        //  console.log('getUserId error!');
        //});

        var userId = messageService.getUserId();
        $scope.userId = userId;
        console.log('beforeEnter userId: ' + $scope.userId);

        $scope.popup = {
          isPopup: false,
          index: 0
        };
      });
      $scope.onSwipeLeft = function () {
        $state.go("tab.friends");
      };
      $scope.popupMessageOpthins = function (chat) {
        $scope.popup.index = $scope.chats.indexOf(chat);
        $scope.popup.optionsPopup = $ionicPopup.show({
          templateUrl: "templates/popup.html",
          scope: $scope
        });
        $scope.popup.isPopup = true;
      };
      // 好友列表中好友头像右上方未读信息条数提示
      $scope.markMessage = function () {
        var userId = messageService.getUserId();
        alert('markMessage ' + userId);
        var index = $scope.popup.index;
        var chat = $scope.chats[index];
        if (chat.showHints) {
          chat.showHints = false;
          chat.noReadMessages = 0;
        } else {
          chat.showHints = true;
          chat.noReadMessages = 1;
        }
        $scope.popup.optionsPopup.close();
        $scope.popup.isPopup = false;
        messageService.updateChat(chat);
      };
      // 好友列表页面中删除好友
      $scope.deleteMessage = function () {
        var index = $scope.popup.index;
        var message = $scope.chats[index];
        $scope.chats.splice(index, 1);
        $scope.popup.optionsPopup.close();
        $scope.popup.isPopup = false;
        messageService.deleteChatId(message.id);
        messageService.clearChat(message);
      };
      // 好友列表页面设置好友置顶
      $scope.topMessage = function () {
        var index = $scope.popup.index;
        var message = $scope.chats[index];
        if (message.isTop) {
          message.isTop = 0;
        } else {
          message.isTop = new Date().getTime();
        }
        $scope.popup.optionsPopup.close();
        $scope.popup.isPopup = false;
        messageService.updateChat(message);
      };
      $scope.toMessage = function (chat) {
        console.log(chat.id);
        $state.go("message", {
          "chatId": chat.id
        });
      };
    });
