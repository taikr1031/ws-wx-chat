angular.module('wechat.chatController', [])

    .controller('chatCtrl', function ($scope, $state, $ionicPopup, localStorageService, messageService) {
      // $scope.chats = messageService.getChats();
      // console.log($scope.chats);
      $scope.$on("$ionicView.beforeEnter", function () {
        // console.log($scope.chats);
        //messageService.setFriendSessionInfo(OWN_OPEN_ID);
        $scope.chats = messageService.getChats();
        $scope.popup = {
          isPopup: false,
          index: 0
        };
      });
      $scope.onSwipeLeft = function () {
        $state.go("tab.friends");
      };
      $scope.popupMessageOpthins = function (message) {
        $scope.popup.index = $scope.chats.indexOf(message);
        $scope.popup.optionsPopup = $ionicPopup.show({
          templateUrl: "templates/popup.html",
          scope: $scope,
        });
        $scope.popup.isPopup = true;
      };
      // 好友列表中好友头像右上方未读信息条数提示
      $scope.markMessage = function () {
        var index = $scope.popup.index;
        var message = $scope.chats[index];
        if (message.showHints) {
          message.showHints = false;
          message.noReadMessages = 0;
        } else {
          message.showHints = true;
          message.noReadMessages = 1;
        }
        $scope.popup.optionsPopup.close();
        $scope.popup.isPopup = false;
        messageService.updateChat(message);
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