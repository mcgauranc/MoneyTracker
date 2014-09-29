"use strict";

moneyApp.config(["$routeProvider",
    function ($routeProvider) {
        $routeProvider.
            when("/", {
                templateUrl: "partials/login.html",
                controller: "AuthController"
            }).
            when("/users", {
                templateUrl: "partials/user/user-list.html",
                controller: "UserController"
            }).
            when("/dataUploads", {
                templateUrl: "partials/dataUpload/dataUpload.html",
                controller: "DataUploadController"
            }).
            otherwise({
                redirectTo: "/"
            });
    }]);
