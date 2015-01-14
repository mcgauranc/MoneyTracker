"use strict";

/**
 *
 * User: rowan.massey
 * Date: 03/01/2015
 */

moneyApp.controller("UserListController", ["$scope", "$location", "mnyUserService", "users",
    function ($scope, $location, mnyUserService, users) {

        var userListController = $scope.userListController = {};

        userListController.users = users;

        /**
         * This method gets all of the users from the database.
         */
        userListController.getUsers = function () {
            mnyUserService.getAllUsers().then(function (userData) {
                userListController.users = userData;
            }, function (error) {
                console.log("There was an error getting all the users: " + error);
            })
        };

        /**
         * This method removes the given user location from the database.
         */
        userListController.remove = function (location) {
            mnyUserService.remove(location).then(function () {
                userListController.getUsers();
            }, function (error) {
                console.log("There was an error deleting the user from the database: " + error);
            });
        };
    }]);