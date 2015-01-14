"use strict";

moneyApp.config(["$stateProvider",
    function ($stateProvider) {
        $stateProvider.
            state("home", {
                url: "/",
                templateUrl: "partials/home.html"
            }).
            state("login", {
                url: "/login",
                templateUrl: "partials/login/login.html",
                controller: "AuthController"
            }).
            state("landingPage", {
                url: "/landingPage",
                templateUrl: "partials/landingPage/landingPage.html"
            }).
            state("accountList", {
                url: "/accountList",
                controller: "AccountListController",
                templateUrl: "partials/account/account-list.html"
            }).
            state("users", {
                url: "/users",
                templateUrl: "../partials/user/user-list.html",
                controller: "UserListController",
                resolve: {
                    users: function (mnyUserService) {
                        return mnyUserService.getAllUsers();
                    }
                }
            }).
            state("newUser", {
                url: "/users/new",
                templateUrl: "../partials/user/user-add.html",
                controller: "UserCreateController"
            }).
            state("editUser", {
                url: "/users/edit/{location}",
                templateUrl: "partials/user/user-edit.html",
                controller: "UserEditController",
                resolve: {
                    user: function (mnyUserService, $stateParams) {
                        return mnyUserService.getUser($stateParams.location);
                    }
                }
            }).
            state("transactionList", {
                url: "/transactionList",
                templateUrl: "partials/transaction/transactionList.html",
                controller: "TransactionController"
            }).
            state("dataUploads", {
                url: "/dataUploads",
                templateUrl: "partials/dataUpload/dataUpload.html",
                controller: "DataUploadController"
            });
        //.
        //otherwise("/");
    }]);
