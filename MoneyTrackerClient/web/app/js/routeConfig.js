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
            otherwise({
                redirectTo: "/users"
            });
    }]);
