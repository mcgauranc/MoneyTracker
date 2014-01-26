"use strict";

/* App Module */

var userApp = angular.module("userApp", [
    "ngRoute",
//    'usercatAnimations',
    "userController",
//    'usercatFilters',
    "userServices"
]);

var accountApp = angular.module("accountApp", [
    "ngRoute",
    "accountControllers",
    "accountServices"
]);

userApp.config(["$routeProvider",
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
            otherwise({
                redirectTo: "/user"
            });
    }]);

accountApp.config(["$routeProvider",
    function ($routeProvider) {
        $routeProvider.
            when("/account", {
                templateUrl: "partials/account/account-list.html",
                controller: "AccountListCtrl"
            }).
            when("/account/:accountId", {
                templateUrl: "partials/account/account-detail.html",
                controller: "AccountDetailCtrl"
            }).
            otherwise({
                redirectTo: "/account"
            });
    }])