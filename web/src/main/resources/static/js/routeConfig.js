"use strict";

moneyApp.config(["$routeProvider",
    function ($routeProvider) {
        $routeProvider.
            when("/", {
                templateUrl: "partials/home.html"
            }).
            when("/login", {
                templateUrl: "partials/login/login.html",
                controller: "AuthController"
            }).
            when("/users", {
                templateUrl: "partials/user/users.html",
                controller: "UserController"
            }).
            when("/landingPage", {
                templateUrl: "partials/landingPage/landingPage.html"
            }).
            when("/dataUploads", {
                templateUrl: "partials/dataUpload/dataUpload.html",
                controller: "DataUploadController"
            }).
            otherwise({
                redirectTo: "/"
            });
    }]);
