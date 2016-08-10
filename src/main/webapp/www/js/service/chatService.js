angular.module('chat.chatService', [])
    .factory('chatService', ['$http', '$q', 'localStorageService', 'dateService',
      function ($http, $q, localStorageService, dateService) {
        return {
          init: function (chats) {
            var i = 0;
            var length = 0;
            //var chatID = new Array();
            var date = null;
            var chatDate = null;
            if (chats) {
              length = chats.length;
              for (; i < length; i++) {
                chatDate = dateService.getChatDate(chats[i]);
                if (!chatDate) {
                  return null;
                }
                date = new Date(chatDate.year, chatDate.month,
                    chatDate.day, chatDate.hour, chatDate.minute,
                    chatDate.second);
                chats[i].timeFrome1970 = date.getTime();
                //chatID[i] = {
                //  id: chats[i].id
                //};
              }
              //localStorageService.update("chatID", chatID);
              //for (i = 0; i < length; i++) {
              //  localStorageService.update("chat_" + chats[i].id, chats[i]);
              //}
            }
          },

          queryChat: function () {
            var url = 'http://' + IP + ':' + PORT + '/chat/queryChat.json';
            var deferred = $q.defer(); // 声明延后执行，表示要去监控后面的执行
            $http({method: 'GET', url: url}).success(function (data, status, headers, config) {
              deferred.resolve(data);
            }).error(function (data, status, headers, config) {
              deferred.reject(data);
            });
            return deferred.promise;
          },

          getUserId: function () {
            var url = 'http://' + IP + ':' + PORT + '/user/getUserId.json';
            var userId;
            $.ajax({
              async: false,
              type: 'GET',
              url: url,
              dataType: 'json',
              success: function (data) {
                userId = data.stringList[0];
              }
            });
            return userId;
          },

          updateChat: function (chat) {
            var id = 0;
            if (chat) {
              id = chat.id;
              localStorageService.update("chat_" + id, chat);
            }
          },
          deleteChatId: function (id) {
            var chatId = localStorageService.get("chatID");
            var length = 0;
            var i = 0;
            if (!chatId) {
              return null;
            }
            length = chatId.length;
            for (; i < length; i++) {
              if (chatId[i].id === id) {
                chatId.splice(i, 1);
                break;
              }
            }
            localStorageService.update("chatID", chatId);
          },
          clearChat: function (chat) {
            var id = 0;
            if (chat) {
              id = chat.id;
              localStorageService.clear("chat_" + id);
            }
          }
        };
      }
    ]);