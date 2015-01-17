"use strict";

/**
 *
 * User: rowan.massey
 * Date: 03/01/2015
 */

moneyApp.controller("UserListController", ["$scope", "$location", "mnyUserService", "users",
    function ($scope, $location, mnyUserService, users) {

        var vm = this;

        vm.users = users;

        /**
         * This method gets all of the users from the database.
         */
        vm.getUsers = function () {
            mnyUserService.getAllUsers().then(function (userData) {
                vm.users = userData;
            }, function (error) {
                console.log("There was an error getting all the users: " + error);
            })
        };

        /**
         * This method removes the given user location from the database.
         */
        vm.remove = function (location) {
            mnyUserService.remove(location).then(function () {
                vm.getUsers();
            }, function (error) {
                console.log("There was an error deleting the user from the database: " + error);
            });
        };
    }]);