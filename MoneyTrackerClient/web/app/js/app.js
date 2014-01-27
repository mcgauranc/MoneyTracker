"use strict";

/* App Module */

var moneyApp = angular.module("moneyApp", [
    "ngRoute",
    "userController",
    "userService",
    "accountController",
    "accountService",
    "kendo.directives"
]);

moneyApp.config(["$routeProvider",
    function ($routeProvider) {
        $routeProvider.
            when("/user", {
                templateUrl: "partials/user/user-list.html",
                controller: "UserListCtrl"
            }).
            when("/user/:userId", {
                templateUrl: "partials/user/user-detail.html",
                controller: "UserDetailCtrl"
            }).
            when("/account", {
                templateUrl: "partials/account/account-list.html",
                controller: "AccountListCtrl"
            }).
            when("/account/:accountId", {
                templateUrl: "partials/account/account-detail.html",
                controller: "AccountDetailCtrl"
            }).
            otherwise({
                redirectTo: "/user"
            });
    }]);
