"use strict";

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
         * This method updates a given user on the database.
         *
         * @param user The user object which will contain the information which will be updated on the database.
         */
        vm.update = function (user) {
            mnyUserService.updateUser(user).then(function () {
                $location.path("users")
            }, function (error) {
                console.log("There was an error updating the user on the database: " + error);
            });
        };

        /**
         * This method retrieves the user for the given location.
         *
         * @param location The URL where the user information can be found.
         */
        vm.get = function (location) {
            mnyUserService.get(location).then(function (data) {
                vm.user = data;
            })
        };
    }]);