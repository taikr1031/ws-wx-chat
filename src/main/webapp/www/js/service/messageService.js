angular.module('chat.messageService', [])
    .factory('messageService', ['$http', '$q', 'localStorageService', 'dateService',
      function ($http, $q, localStorageService, dateService) {
        return {

          __queryChat: function () {
            var url = 'http://' + IP + ':' + PORT + '/chat/queryChat.json';
            var deferred = $q.defer(); // 声明延后执行，表示要去监控后面的执行
            $http({method: 'GET', url: url}).success(function (data, status, headers, config) {
              deferred.resolve(data);
            }).error(function (data, status, headers, config) {
              deferred.reject(data);
            });
            return deferred.promise;
          },
          queryMessage: function (chatId) {
            var url = 'http://' + IP + ':' + PORT + '/message/queryMessage/' + chatId;
            var deferred = $q.defer(); // 声明延后执行，表示要去监控后面的执行
            $http({method: 'GET', url: url}).success(function (data, status, headers, config) {
              deferred.resolve(data);
            }).error(function (data, status, headers, config) {
              deferred.reject(data);
            });
            return deferred.promise;
          },

          sendText: function (chatId, openid, msg) {
            var url = 'http://' + IP + ':' + PORT + '/wxServlet?type=TEXT&openid=' + openid + '&content=' + msg;
            return $http.get(url).then(function (res) {
              console.log('TEXT success');
              //this.saveMessage(openid, msg);
              var url = SITE + '/chat/save';
              var data = {
                //chatId: chatId,
                //openid: openid,
                //msg: msg
              };
              console.log('saveMessage' + data);
              $http({
                method: 'POST',
                url: url,
                data: data,
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                transformRequest: function (obj) {
                  var str = [];
                  for (var p in obj) {
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                  }
                  return str.join("&");
                }
              }).then(function (res) {
                console.log(res.data);
              })
            });
          },

          sendImage: function (openid, msg) {
            var url = SITE + '/wxServlet?type=IMAGE&openid=' + openid + '&content=' + msg;
            return $http.get(url).then(function (res) {
              console.log('IMAGE success');
            });
          },

          sendVoice: function (openid, mediaId) {
            var url = SITE + '/wxServlet?type=VOICE&openid=' + openid + '&content=' + mediaId;
            return $http.get(url).then(function (res) {
              console.log('VOICE success');
            });
          },

          //getAmountMessageById: function (num, id) {
          //  var chats = [];
          //  var chat = localStorageService.get("chat_" + id).messages;
          //  var length = 0;
          //  if (num < 0 || !chat) return;
          //  length = chat.length;
          //  if (num < length) {
          //    chats = chat.splice(length - num, length);
          //    return chats;
          //  } else {
          //    return chat;
          //  }
          //}
        };
      }
    ]);