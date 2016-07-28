angular.module('wechat.settingController', [])
    .controller('settingCtrl', function ($rootScope, $scope, $state, $timeout, messageService) {
      $scope.$on("$ionicView.beforeEnter", function () {
        //$scope.friends = [{openid: 'oMPxav8gQa7VgRFjILtzRX_lhymE', name: 'ZM'}, {openid: 'TOM123', name: 'TOM'}];
        var data = messageService.getAllUser();
        $timeout(function() {
          //console.log(data.$$state.value.userList[0].name);
          console.log(data);
          $scope.friends = data;
        }, 0);

        //if ($rootScope.OWN_OPEN_ID != null && $rootScope.$rootScope != '') {
        //  messageService.setFriendSessionInfo($rootScope.OWN_OPEN_ID);
        //  $state.go('/tab/message');
        //}
      });
      $scope.loginChat = function (name, password) {
        messageService.loginChat(name, password);
        $state.go('tab.message');
      }
    });