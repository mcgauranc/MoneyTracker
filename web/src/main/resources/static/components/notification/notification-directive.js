/**
 * This directive will display a notification to the user, depending on the type.
 *
 * User: rowan.massey
 * Date: 22/03/2015
 */

(function () {
    "use strict";

    var mnyNotification = function ($timeout) {
        return {
            restrict: "E",
            templateUrl: "/components/notification/notification.html",
            replace: true,
            scope: {},
            link: function ($scope, element) {
                var type;
                var message;
                $scope.$on("mnyNotificationEvent", function (notificationObject, notificationContent) {
                    if (notificationContent) {
                        type = notificationContent.notificationType;
                        message = notificationContent.notificationMessage;
                        element.addClass(type);
                    }
                    element.show();
                });
            }
        };
    };

    moneyApp.directive("mnyNotification", mnyNotification);
})();