"use strict";

/* Controllers */

moneyApp.controller("UserController", ["$scope", "$location", "mnyUserService",
    "mnyAddressService", "mnyRelationshipService",
    function ($scope, $location, mnyUserService, mnyAddressService, mnyRelationshipService) {

        var userController = $scope.userController = {};

        userController.user = {};

        userController.userLocation = "";
        userController.addressLocation = "";
        userController.newUser = false;

        userController.allUsers = [];


        /**
         * This method returns true if we're dealing with a new user.
         *
         * @returns {boolean|*|Function}
         */
        userController.isNewUser = function () {
            return userController.newUser;
        };

        /**
         * This method saves a new user.
         */
        userController.save = function () {
            var user = getUserDto(userController.user);
            mnyUserService.save(user).then(function (userData) {
                userController.userLocation = userData.headers().location;
                //mnyAuthService.login(user.userName, user.password);
                var address = getAddressDto(userController.user);
                mnyAddressService.save(address).then(function (addressData) {
                    userController.addressLocation = addressData.headers().location;
                    mnyRelationshipService.associate(userController.userLocation, "address", userController.addressLocation).then(function () {
                        userController.user = {};
                    })
                });
            }, function (error) {
                console.log("There was an error processing the user: " + error);
            })
        };

        /**
         *
         */
        userController.delete = function (location) {
            mnyUserService.delete(location).then(function (data) {
                userController.refresh();
            }, function (error) {

            });
        };

        /**
         *
         */
        userController.refresh = function () {
            mnyUserService.getAllUsers().then(function (userData) {
                userController.allUsers = userData;
            }, function (error) {
                console.log("There was an error getting all the users: " + error);
            })
        };

        /**
         * This method creates a new user object, to be referenced in the view.
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

        /**
         * This method converts the user object defined in the controller, into a user object that will be posted
         * to the server.
         *
         * @param user The user object defined in the controller.
         * @returns {} user object with only relevant user object information populated.
         */
        function getUserDto(user) {
            var result = {};

            result.userName = user.userName;
            result.password = user.password;
            result.firstName = user.firstName;
            result.lastName = user.lastName;
            result.dateOfBirth = new Date(user.dateOfBirth).toISOString();

            return result;
        }

        /**
         * This method converts the user object defined in the controller, into an address object that will be posted
         * to the server.
         *
         * @param user The user object defined in the controller with address information.
         * @returns An address object, with only address relevant information populated.
         */
        function getAddressDto(user) {
            var result = {};

            result.address1 = user.address1;
            result.address2 = user.address2;
            result.address3 = user.address3;
            result.address4 = user.address4;
            result.city = user.city;
            result.county = user.county;

            return result;
        }

        userController.redirect = function (path) {
            userController.isNewUser = true;
            $location.path(path);
        }
    }
])
;