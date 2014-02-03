"use strict";

/* Controllers */

var userController = angular.module("userController", []);
var accountController = angular.module("accountController", []);

userController.controller("UserListCtrl", ['$scope', 'Users',
    function ($scope, Users) {

        $scope.userList = Users.query();
        $scope.userList.$promise.then(function (data) {
            console.log(data)
        });

        $scope.usersDataSource = new kendo.data.DataSource({
            dataSource: {
                data: $scope.userList,
                pageSize: 20
//                serverPaging: true,
//                transport: {
//                    read: Users.query()
//                    update: User.update(),
//                    create: User.save()
//                }
            },
            schema: {
                data: "content",
//                data: function(response) {
//                    console.log("The content of the response is: " + response.content);
//                    return response.content;
//                },
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
//                ,
//                total: "totalPages"
            }
        })
    }]);

userController.controller("UserDetailCtrl", ["$scope", "$routeParams", "User",
    function ($scope, $routeParams, User) {
        $scope.user = User.get({userId: $routeParams.userId}, function (user) {
        });
    }]);
