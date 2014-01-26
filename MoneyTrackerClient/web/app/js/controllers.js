"use strict";

/* Controllers */

var userController = angular.module("userController", []);

userController.controller("UserListCtrl", ["$scope", "User",
    function ($scope, User) {
        $scope.users = User.query();
    }]);

userController.controller("UserDetailCtrl", ["$scope", "$routeParams", "User",
    function ($scope, $routeParams, User) {
        $scope.user = User.get({userId: $routeParams.userId}, function (user) {
        });
    }]);
