angular.module('chat.settingService', [])
    .factory('settingService',  ['$http', '$q', 'localStorageService', 'dateService',
      function ($http, $q, localStorageService, dateService) {
        return {
          getAllUser: function () {
            var url = 'http://' + IP + ':' + PORT + '/user/getAllUser.json';
            return $http.get(url).then(function (response) {
              return response.data;
            });
          },

          loginChat: function (name, password) {
            var url = 'http://' + IP + ':' + PORT + '/login/login/' + name + '/' + password;
            $http.get(url).then(function (response) {
              console.log(response.data.user.name + '-=' + response.data.user.code + ' loginChat success!');
            });
          },
          isLoginSession: function (ownOpenId) {
            var url = 'http://' + IP + ':' + PORT + '/login/isLoginSession/' + ownOpenId;
            console.log('setFriendSessionInfo: ' + url);
            $http.get(url).then(function (response) {
              if (response.data == false) {
                return response.data;

              }
            });
          }
        };
      }
    ]);