"use strict";

moneyApp.config(["$routeProvider",
    function ($routeProvider) {
        $routeProvider.
            when("/users", {
                templateUrl: "partials/user/user-list.html",
                controller: "UserListCtrl"
            }).
            when("/user/:userId", {
                templateUrl: "partials/user/user-detail.html",
                controller: "UserDetailCtrl"
            }).
            when("/accounts", {
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
