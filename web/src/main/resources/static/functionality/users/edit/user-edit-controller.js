"use strict";

/**
 *
 * User: rowan.massey
 * Date: 03/01/2015
 */

moneyApp.controller("UserEditController", ["$scope", "$location", "mnyUserService", "user",
    function ($scope, $location, mnyUserService, user) {

        var vm = this;

        vm.user = user;

        /**
         * This method removes the given user location from the database.
         */
        vm.update = function (user) {
            mnyUserService.updateUser(user).then(function () {
                $location.path("users")
            }, function (error) {
                console.log("There was an error deleting the user from the database: " + error);
            });
        };

        /**
         *
         * @param location
         */
        vm.get = function (location) {
            mnyUserService.get(location).then(function (data) {
                $scope.user = data;
            })
        };
    }]);