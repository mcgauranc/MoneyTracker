"use strict";

/* Controllers */

moneyApp.controller("UserController", ["$scope", "AuthRestangular", "$location", "focus",
    function ($scope, Restangular, $location, focus) {

        var userController = $scope.userController = {};

        userController.usersAll = Restangular.all("users");

        userController.currentPage = 0;
        userController.pages = 0;
        userController.user = {};
        userController.address = {};
        userController.location = $location;
        userController.pageSize = 10;
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

        userController.isNewUser = function () {
            return userController.newUser;
        };

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

        userController.nextPage = function () {
            if (userController.currentPage + 1 < userController.pages) {
                userController.currentPage = userController.currentPage + 1;
                userController.refresh();
            }
        };

        userController.previousPage = function () {
            if (userController.currentPage > 0) {
                userController.currentPage = userController.currentPage - 1;
                userController.refresh();
            }
        };

        userController.save = function () {
            userController.pageSize = 5;
            if ('route' in userController.user) {
                userController.user.put().then(function (result) {
                    userController.user = {};
                    userController.refresh();
                });
            } else {
                userController.user.dateOfBirth = new Date(userController.user.dateOfBirth).toISOString();
                userController.usersAll.post(userController.user).then(function (userResult) {
                    userController.userLocation = userResult.headers().location;
                    userController.user = {};
                    userController.address = {};
                    userController.refresh();
                });
            }
        };

        userController.newUser = function () {
            userController.newUser = true;

            userController.currentPage = 0;
            userController.pageSize = 4;
            userController.refresh();
            userController.user.userName = "";
            userController.user.password = "";
            userController.user.firstName = "";
            userController.user.lastName = "";
            userController.user.dateOfBirth = "";

            userController.address.address1 = "";
            userController.address.address2 = "";
            userController.address.address3 = "";
            userController.address.address4 = "";
            userController.address.county = "";
            userController.address.city = "";
            focus('startEdit');
        };

        userController.cancel = function () {
            userController.pageSize = 5;
            userController.user = {};
            userController.refresh();
        };

        userController.edit = function (user, event) {
            userController.user = user;
            focus('startEdit');
        };

        userController.remove = function (user) {
            users.remove().then(function (result) {
                userController.refresh();
            });
        };

        userController.refresh();
    }]);