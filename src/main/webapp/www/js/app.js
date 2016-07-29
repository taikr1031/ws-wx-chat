var IP = '192.168.1.8';
var PORT = '8080';
var OWN_OPEN_ID = 'oMPxav8gQa7VgRFjILtzRX_lhymE';

angular.module('wechat', ['ionic', 'wechat.controllers', 'wechat.chatController',
      'wechat.messageController', 'wechat.settingController', 'wechat.routes',
      'wechat.services', 'wechat.directives', 'monospaced.elastic'
    ])

    .config(['$ionicConfigProvider', function ($rootScope, $ionicConfigProvider) {
      $rootScope.OWN_OPEN_ID = '';
      //$ionicConfigProvider.tabs.position('bottom'); // other values: top
    }])

    .run(function ($ionicPlatform, $http, messageService, dateService) {
      var url = 'http://' + IP + ':' + PORT + '/chat/queryChat.json';
      if (ionic.Platform.isAndroid()) {
        url = "/android_asset/www/";
      }

      $http.get(url).then(function (response) {
        // localStorageService.update("messages", response.data.messages);
        messageService.init(response.data.chatList);
      });
      //$http.get(url + "data/json/friends.json").then(function (response) {
      //  console.log(response.data.results);
      //});
      $ionicPlatform.ready(function () {
        if (window.cordova && window.cordova.plugins.Keyboard) {
          cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
        }
        if (window.StatusBar) {
          StatusBar.styleDefault();
        }
      });
    });