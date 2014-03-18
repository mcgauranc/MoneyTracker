"use strict";

/* Controllers */

var accountController = angular.module("accountController", []);

var userController = angular.module("userController", []);
userController.controller("UserListCtrl", ['$scope', 'Users',
    function ($scope, Users) {

        $scope.filterOptions = {
            filterText: "",
            useExternalFilter: true
        };

        $scope.pagingOptions = {
            pageSizes: [10, 50, 100],
            pageSize: 10,
            currentPage: 1
        };

        $scope.setPagingData = function (data, page, pageSize) {
            $scope.myData = data.slice((page - 1) * pageSize, page * pageSize);
            $scope.totalServerItems = data.length;
            if (!$scope.$$phase) {
                $scope.$apply();
            }
        };

        $scope.gridOptions = {
            data: 'myData',
            enablePaging: true,
            showFooter: true,
            totalServerItems: 'totalServerItems',
            pagingOptions: $scope.pagingOptions,
            filterOptions: $scope.filterOptions
        };

        function getPagedDataAsync() {
            console.log('in get data');  //this get logged twice
            userService.get().then(function (data) {
                $scope.setPagingData(data);
            });
        }
    }]);

userController.controller("UserDetailCtrl", ["$scope", "$routeParams", "User",
    function ($scope, $routeParams, User) {
        $scope.user = User.get({userId: $routeParams.userId}, function (user) {
        });
    }]);
