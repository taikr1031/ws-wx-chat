angular.module('wechat.settingController', [])
    .controller('settingCtrl', function ($rootScope, $scope, $state, $timeout, messageService) {
      $scope.$on("$ionicView.beforeEnter", function () {
        //$scope.friends = [{openid: 'oMPxav8gQa7VgRFjILtzRX_lhymE', name: 'ZM'}, {openid: 'TOM123', name: 'TOM'}];
        var data = messageService.getAllUser();
        $timeout(function() {
          console.log(data);
          $scope.friends = data;
        }, 0);
      });
      $scope.loginChat = function (name, password) {
        messageService.loginChat(name, password);
        $state.go('tab.chat');
      }
    });