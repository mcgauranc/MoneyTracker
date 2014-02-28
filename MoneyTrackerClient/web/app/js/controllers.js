"use strict";

/* Controllers */

var userController = angular.module("userController", []);
var accountController = angular.module("accountController", []);

userController.controller("UserListCtrl", ['$scope', 'Users',
    function ($scope, Users) {

        $scope.userDataSource = new kendo.data.DataSource({
            pageSize: 20,
            transport: {
                read: function (options) {
                    Users.get(function (data) {
                        options.success(data.content);
                    });
                },
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
            }});
    }]);

userController.controller("UserDetailCtrl", ["$scope", "$routeParams", "User",
    function ($scope, $routeParams, User) {
        $scope.user = User.get({userId: $routeParams.userId}, function (user) {
        });
    }]);
