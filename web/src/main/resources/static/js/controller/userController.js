"use strict";

/* Controllers */

moneyApp.controller("UserController", ["$scope", "AuthRestangular", "$location", "focus", "mnyUserService",
    function ($scope, Restangular, $location, focus, mnyUserService) {

        var userController = $scope.userController = {};

        userController.user = {};
        userController.location = $location;
        userController.userLocation = "";

        userController.newUser = false;


        /**
         * This method returns true if we're dealing with a new user.
         *
         * @returns {boolean|*|Function}
         */
        userController.isNewUser = function () {
            return userController.newUser;
        };

        /**
         * This method saves a new, or updates and existing user.
         */
        userController.save = function () {
            userController.user.dateOfBirth = new Date(userController.user.dateOfBirth).toISOString();
            userController.user.confirmPassword = "";
            mnyUserService.save(userController.user).then(function (userResult) {
                userController.userLocation = userResult.headers().location;
                userController.user = {};
            });
        };

        /**
         * This method creates a new user object, to be referenced in code.
         */
        userController.newUser = function () {
            userController.newUser = true;

            userController.user.userName = "";
            userController.user.password = "";
            userController.user.confirmPassword = "";
            userController.user.firstName = "";
            userController.user.lastName = "";
            userController.user.dateOfBirth = "";

            userController.user.address1 = "";
            userController.user.address2 = "";
            userController.user.address3 = "";
            userController.user.address4 = "";
            userController.user.city = "";
            userController.user.county = "";
        };
    }]);