"use strict";

/* Controllers */

var accountController = angular.module("accountController", []);

var userController = angular.module("userController", []);
userController.controller("UserListCtrl", ['$scope', 'Users',
    function ($scope, Users) {

        $scope.users = Users.get();

//        $scope.filterOptions = {
//            filterText: "",
//            useExternalFilter: true
//        };
//
//        $scope.pagingOptions = {
//            pageSizes: [10, 50, 100],
//            pageSize: 10,
//            currentPage: 1
//        };
//
//        $scope.setPagingData = function (data, page, pageSize) {
//            $scope.myData = data.slice((page - 1) * pageSize, page * pageSize);
//            $scope.totalServerItems = data.length;
//            if (!$scope.$$phase) {
//                $scope.$apply();
//            }
//        };
//
//        $scope.gridOptions = {
//            data: 'myData',
//            enablePaging: true,
//            showFooter: true,
//            totalServerItems: 'totalServerItems',
//            pagingOptions: $scope.pagingOptions,
//            filterOptions: $scope.filterOptions
//        };
    }]);

userController.controller("UserDetailCtrl", ["$scope", "$routeParams", "User",
    function ($scope, $routeParams, User) {
        $scope.userId = $routeParams.userId;
    }]);
