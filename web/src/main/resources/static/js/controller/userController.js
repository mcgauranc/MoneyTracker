"use strict";

/* Controllers */

moneyApp.controller("UserController", ["$scope", "AuthRestangular", "$location", "focus",
    function ($scope, Restangular, $location, focus) {

        var userController = $scope.userController = {};

        userController.usersAll = Restangular.all("users");

        userController.user = {};
        userController.location = $location;
        userController.userLocation = "";

        userController.newUser = false;

        //userController.setRelatedInformation = function (parentLocation, childLocation) {
        //    $http({
        //        url: parentLocation,
        //        method: "PUT",
        //        headers: {
        //            "Content-Type": "text/uri-list"
        //        },
        //        data: childLocation
        //    }).success(function (response) {
        //        console.log("Record successfully associated with parent. ", +response);
        //    }).error(function (error) {
        //        console.log("There was an error associating the record to the parent. " + error);
        //    });
        //};

        /**
         * This method returns true if we're dealing with a new user.
         *
         * @returns {boolean|*|Function}
         */
        userController.isNewUser = function () {
            return userController.newUser;
        };

        /**
         * This method refreshed the users list.
         */
        userController.refresh = function () {
            userController.usersAll.getList({
                "size": userController.pageSize,
                "page": userController.currentPage,
                "sort": ""
            }).then(function (users) {
                $scope.users = users;
                userController.pages = users.data.page.totalPages;
            }, function (error) {
                userController.location.path('/');
            });
        };

        /**
         * This method saves a new, or updates and existing user.
         */
        userController.save = function () {
            if ('route' in userController.user) {
                userController.user.put().then(function (result) {
                    userController.user = {};
                    userController.refresh();
                });
            } else {
                userController.user.dateOfBirth = new Date(userController.user.dateOfBirth).toISOString();
                userController.user.confirmPassword = "";
                userController.usersAll.post(userController.user).then(function (userResult) {
                    userController.userLocation = userResult.headers().location;
                    userController.user = {};
                    userController.refresh();
                });
            }
        };

        /**
         * This method creates a new user object, to be referenced in code.
         */
        userController.newUser = function () {
            userController.newUser = true;

            userController.refresh();
            userController.user.userName = "";
            userController.user.password = "";
            userController.user.confirmPassword = "";
            userController.user.firstName = "";
            userController.user.lastName = "";
            userController.user.dateOfBirth = "";

        };

        userController.cancel = function () {
            userController.user = {};
            userController.refresh();
        };

        userController.edit = function (user) {
            userController.user = user;
            focus('startEdit');
        };

        userController.remove = function (user) {
            user.remove().then(function () {
                userController.refresh();
            });
        };

        userController.refresh();
    }]);