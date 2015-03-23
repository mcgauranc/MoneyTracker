/**
 * This controller handles everything to do with the edit functionality for a given user.
 *
 * User: rowan.massey
 * Date: 03/01/2015
 */

(function () {
    'use strict';

    moneyApp.controller("UserEditController", ["$scope", "$location", "$stateParams", "mnyUserService", "user", "mnyNotificationService",
        function ($scope, $location, $stateParams, mnyUserService, user, mnyNotificationService) {

            var vm = this;
            vm.user = user;

            /**
             * This method updates the user details, changed in the scope object.
             */
            vm.update = function () {
                mnyUserService.updateUser($stateParams.location, vm.user).then(function () {
                    $location.path("users");
                });
            };

            /**
             * This method cancels the current operation.
             */
            vm.cancel = function () {
                $location.path("users");
            };

            vm.notify = function (message, type) {
                throw new Error("This is a bloody error");
            }
        }]);
})();