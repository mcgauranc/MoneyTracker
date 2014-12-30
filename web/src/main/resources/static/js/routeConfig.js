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
            when("/landingPage", {
                templateUrl: "partials/landingPage/landingPage.html"
            }).
            when("/user", {
                templateUrl: "partials/user/user.html",
                controller: "UserController"
            }).
            when("/userList", {
                templateUrl: "partials/user/userList.html",
                controller: "UserController"
            }).
            when("/transactionList", {
                templateUrl: "partials/transaction/transactionList.html",
                controller: "TransactionController"
            }).
            when("/dataUploads", {
                templateUrl: "partials/dataUpload/dataUpload.html",
                controller: "DataUploadController"
            }).
            otherwise({
                redirectTo: "/"
            });
    }]);
