"use strict";

/* Controllers */

var userController = angular.module("userController", []);
var accountController = angular.module("accountController", []);

userController.controller("UserListCtrl", ['$scope', 'Users',
    function ($scope, Users) {

        var userList = Users.query();
        userList.$promise.then(function (data) {
            populateDataSource(data)
        }, function (status) {
            console.log(status)
        });

        $scope.users = {};
        $scope.users.dataSource = new kendo.data.DataSource();

        var populateDataSource = function (data) {
            var dataSource = new kendo.data.DataSource({
                data: data,
                pageSize: 20,
                schema: {
                    data: "content",
                    model: {
                        fields: {
                            userName: {
                                editable: true,
                                nullable: false
                            },
                            firstName: {
                                editable: true,
                                nullable: false
                            },
                            lastName: {
                                editable: true,
                                nullable: false
                            },
                            enabled: {
                                type: "boolean",
                                editable: true,
                                nullable: false
                            }
                        }
                    }
                }
            });
            dataSource.read();
            $scope.users.dataSource = dataSource;
        }
    }]);

userController.controller("UserDetailCtrl", ["$scope", "$routeParams", "User",
    function ($scope, $routeParams, User) {
        $scope.user = User.get({userId: $routeParams.userId}, function (user) {
        });
    }]);
