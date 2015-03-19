(function () {
    'use strict';

    /**
     * This controller handles everything to do with the edit functionality for a given user.
     *
     * User: rowan.massey
     * Date: 03/01/2015
     */

    moneyApp.controller("UserEditController", ["$scope", "$location", "mnyUserService", "user",
        function ($scope, $location, mnyUserService, user) {

            var vm = this;
            vm.user = user;

            /**
             * This method updates the user details, changed in the scope object.
             */
            vm.update = function () {
                mnyUserService.updateUser($stateParams.location, vm.user).then(function () {
                    $location.path("users");
                }, function (error) {
                    console.log("There was an error updating the user on the database: " + error);
                });
            };

            /**
             * This method cancels the current operation.
             */
            vm.cancel = function () {
                $location.path("users");
            };
        }]);
})();