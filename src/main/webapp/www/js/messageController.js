angular.module('wechat.messageController', [])

    .controller('messageCtrl', function ($scope, $state, $ionicPopup, localStorageService, messageService) {
      // $scope.messages = messageService.getAllMessages();
      // console.log($scope.messages);
      $scope.$on("$ionicView.beforeEnter", function () {
        // console.log($scope.messages);
        //messageService.setFriendSessionInfo(OWN_OPEN_ID);
        $scope.messages = messageService.getAllMessages();
        $scope.popup = {
          isPopup: false,
          index: 0
        };
      });
      $scope.onSwipeLeft = function () {
        $state.go("tab.friends");
      };
      $scope.popupMessageOpthins = function (message) {
        $scope.popup.index = $scope.messages.indexOf(message);
        $scope.popup.optionsPopup = $ionicPopup.show({
          templateUrl: "templates/popup.html",
          scope: $scope,
        });
        $scope.popup.isPopup = true;
      };
      // 好友列表中好友头像右上方未读信息条数提示
      $scope.markMessage = function () {
        var index = $scope.popup.index;
        var message = $scope.messages[index];
        if (message.showHints) {
          message.showHints = false;
          message.noReadMessages = 0;
        } else {
          message.showHints = true;
          message.noReadMessages = 1;
        }
        $scope.popup.optionsPopup.close();
        $scope.popup.isPopup = false;
        messageService.updateMessage(message);
      };
      // 好友列表页面中删除好友
      $scope.deleteMessage = function () {
        var index = $scope.popup.index;
        var message = $scope.messages[index];
        $scope.messages.splice(index, 1);
        $scope.popup.optionsPopup.close();
        $scope.popup.isPopup = false;
        messageService.deleteMessageId(message.id);
        messageService.clearMessage(message);
      };
      // 好友列表页面设置好友置顶
      $scope.topMessage = function () {
        var index = $scope.popup.index;
        var message = $scope.messages[index];
        if (message.isTop) {
          message.isTop = 0;
        } else {
          message.isTop = new Date().getTime();
        }
        $scope.popup.optionsPopup.close();
        $scope.popup.isPopup = false;
        messageService.updateMessage(message);
      };
      $scope.messageDetails = function (message) {
        console.log(message.id);
        $state.go("messageDetail", {
          "messageId": message.id
        });
      };
    })
