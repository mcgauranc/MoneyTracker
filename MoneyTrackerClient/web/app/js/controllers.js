"use strict";

/* Controllers */

var userController = angular.module("userController", []);
var accountController = angular.module("accountController", []);

userController.controller("UserListCtrl", ["$scope", "User",
    function ($scope, User) {
        $scope.userData = {
            dataSource: {
                transport: {
                    read: User.query(),
//                    update: User.update(),
                    create: User.save()
                }
            }
        }
    }]);

//    function ($scope, User) {
//        $scope.users = User.query();
//    }]);

userController.controller("UserDetailCtrl", ["$scope", "$routeParams", "User",
    function ($scope, $routeParams, User) {
        $scope.user = User.get({userId: $routeParams.userId}, function (user) {
        });
    }]);
