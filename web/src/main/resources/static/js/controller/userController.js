"use strict";

/* Controllers */

moneyApp.controller("UserController", ["$scope", "AuthRestangular", "$location", "focus",
    function ($scope, Restangular, $location, focus) {

        var userController = $scope.userController = {};

        userController.all = Restangular.all("users");
        userController.currentPage = 0;
        userController.pages = 0;
        userController.current = {};
        userController.location = $location;
        userController.pageSize = 10;

        userController.refresh = function () {
            userController.all.getList({"size": userController.pageSize, "page": userController.currentPage, "sort": ""}).then(function (users) {
                $scope.users = users;
                userController.pages = users.page.totalPages;
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
            if ('route' in userController.current) {
                userController.current.put().then(function (result) {
                    userController.current = {};
                    userController.refresh();
                });
            } else {
                userController.current.dateOfBirth = new Date(userController.current.dateOfBirth).toISOString();
                userController.all.post(userController.current).then(function (result) {
                    userController.current = {};
                    userController.refresh();
                });
            }
        };

        userController.newUser = function () {
            userController.currentPage = 0;
            userController.pageSize = 4;
            userController.refresh();
            userController.current.userName = '';
            userController.current.password = '';
            userController.current.firstName = '';
            userController.current.lastName = '';
            userController.current.dateOfBirth = '';
            focus('startEdit');
        };

        userController.cancel = function () {
            userController.pageSize = 5;
            userController.current = {};
            userController.refresh();
        };

        userController.edit = function (user, event) {
            userController.current = user;
            focus('startEdit');
        };

        userController.remove = function (user) {
            users.remove().then(function (result) {
                userController.refresh();
            });
        };

        userController.refresh();
    }]);