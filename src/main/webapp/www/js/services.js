angular.module('wechat.services', [])

    .factory('localStorageService', [function () {
    return {
      get: function localStorageServiceGet(key, defaultValue) {
        var stored = localStorage.getItem(key);
        try {
          stored = angular.fromJson(stored);
        } catch (error) {
          stored = null;
        }
        if (defaultValue && stored === null) {
          stored = defaultValue;
        }
        return stored;
      },
      update: function localStorageServiceUpdate(key, value) {
        if (value) {
          localStorage.setItem(key, angular.toJson(value));
        }
      },
      clear: function localStorageServiceClear(key) {
        localStorage.removeItem(key);
      }
    };
  }])

  .factory('dateService', ['$filter', function ($filter) {
    return {
      handleChatDate: function (chats) {
        var i = 0,
            length = 0,
            chatDate = {},
            nowDate = {},
            weekArray = ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
            diffWeekValue = 0;
        if (chats) {
          nowDate = this.getNowDate();
          length = chats.length;
          for (i = 0; i < length; i++) {
            chatDate = this.getChatDate(chats[i]);
            if (!chatDate) {
              return null;
            }
            if (nowDate.year - chatDate.year > 0) {
              chats[i].lastMessage.time = chatDate.year + "";
              continue;
            }
            if (nowDate.month - chatDate.month >= 0 ||
                nowDate.day - chatDate.day > nowDate.week) {
              chats[i].lastMessage.time = chatDate.month +
                  "月" + chatDate.day + "日";
              continue;
            }
            if (nowDate.day - chatDate.day <= nowDate.week &&
                nowDate.day - chatDate.day > 1) {
              diffWeekValue = nowDate.week - (nowDate.day - chatDate.day);
              chats[i].lastMessage.time = weekArray[diffWeekValue];
              continue;
            }
            if (nowDate.day - chatDate.day === 1) {
              chats[i].lastMessage.time = "昨天";
              continue;
            }
            if (nowDate.day - chatDate.day === 0) {
              chats[i].lastMessage.time = chatDate.hour + ":" + chatDate.minute;
              continue;
            }
          }
          // console.log(chats);
          // return chats;
        } else {
          console.log("chats is null");
          return null;
        }

      },
      getNowDate: function () {
        var nowDate = {};
        var date = new Date();
        nowDate.year = date.getFullYear();
        nowDate.month = date.getMonth();
        nowDate.day = date.getDate();
        nowDate.week = date.getDay();
        nowDate.hour = date.getHours();
        nowDate.minute = date.getMinutes();
        nowDate.second = date.getSeconds();
        return nowDate;
      },
      getChatDate: function (chat) {
        var chatDate = {};
        var chatTime = "";
        //2015-10-12 15:34:55
        var reg = /(^\d{4})-(\d{1,2})-(\d{1,2})\s(\d{1,2}):(\d{1,2}):(\d{1,2})/g;
        var result = new Array();
        if (chat) {
          //chatTime = chat.originalTime;
          chatTime = $filter("date")(chat.originalTime, "yyyy-MM-dd HH:mm:ss");

          result = reg.exec(chatTime);
          if (!result) {
            console.log("result is null");
            return null;
          }
          chatDate.year = parseInt(result[1]);
          chatDate.month = parseInt(result[2]);
          chatDate.day = parseInt(result[3]);
          chatDate.hour = parseInt(result[4]);
          chatDate.minute = parseInt(result[5]);
          chatDate.second = parseInt(result[6]);
          // console.log(chatDate);
          return chatDate;
        } else {
          console.log("chat is null");
          return null;
        }
      }
    };
  }])

  .factory('messageService', ['$http', 'localStorageService', 'dateService',
    function ($http, localStorageService, dateService) {
      return {
        init: function (chats) {
          var i = 0;
          var length = 0;
          var chatID = new Array();
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
              chatID[i] = {
                id: chats[i].id
              };
            }
            localStorageService.update("chatID", chatID);
            for (i = 0; i < length; i++) {
              localStorageService.update("chat_" + chats[i].id, chats[i]);
            }
          }
        },

        getAllUser: function() {
          var url = 'http://' + IP + ':' + PORT + '/user/getAllUser.json';
          return $http.get(url).then(function(response) {
            return response.data;
          });
        },

        loginChat: function(name, password) {
          var url = 'http://' + IP + ':' + PORT + '/login/login/' + name + '/' + password;
          console.log('loginChat URL: ' + url);
          $http.get(url).then(function(response) {
            console.log(response.data.user.name + '-=' + response.data.user.code + ' loginChat success!');
          });
        },
        isLoginSession: function(ownOpenId) {
          var url = 'http://' + IP + ':' + PORT + '/login/isLoginSession/' + ownOpenId;
          console.log('setFriendSessionInfo: ' + url);
          $http.get(url).then(function(response) {
            if(response.data == false) {
              return response.data;

            }
          });
        },
        sendText: function (chatId, openid, msg) {
          var url = 'http://' + IP + ':' + PORT + '/wxServlet?type=TEXT&openid=' + openid + '&content=' + msg;
          return $http.get(url).then(function (response) {
            console.log('TEXT success');
            //this.saveMessage(openid, msg);
            var url = 'http://' + IP + ':' + PORT + '/chat/save';
            alert(url);
            var data = {
              chatId: chatId,
              openid: openid,
              msg: msg
            };
            console.log('saveMessage' + data);
            $http({
                method: 'POST',
                url:url,
                data: data,
                headers:{'Content-Type': 'application/x-www-form-urlencoded'},
                transformRequest: function(obj) {
                  var str = [];
                  for(var p in obj){
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                  }
                  return str.join("&");
                }}).then(function(response) {
                  console.log(response.data);
            })
          });
        },

        sendImage: function (openid, msg) {
          var url = 'http://' + IP + ':' + PORT + '/wxServlet?type=IMAGE&openid=' + openid + '&content=' + msg;
          return $http.get(url).then(function (response) {
            console.log('IMAGE success');
          });
        },

        sendVoice: function (openid, mediaId) {
          var url = 'http://' + IP + ':' + PORT + '/wxServlet?type=VOICE&openid=' + openid + '&content=' + mediaId;
          return $http.get(url).then(function (response) {
            console.log('VOICE success');
          });
        },

        getChats: function () {
          var chats = new Array();
          var i = 0;
          var chatID = localStorageService.get("chatID");
          var length = 0;
          var chat = null;
          if (chatID) {
            length = chatID.length;

            for (; i < length; i++) {
              chat = localStorageService.get("chat_" + chatID[i].id);
              if (chat) {
                chats.push(chat);
              }
            }
            dateService.handleChatDate(chats);
            return chats;
          }
          return null;
        },
        getChatById: function (id) {
          return localStorageService.get("chat_" + id);
        },
        getAmountMessageById: function (num, id) {
          var chats = [];
          var chat = localStorageService.get("chat_" + id).messages;
          var length = 0;
          if (num < 0 || !chat) return;
          length = chat.length;
          if (num < length) {
            chats = chat.splice(length - num, length);
            return chats;
          } else {
            return chat;
          }
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
  ])