"use strict";

moneyApp.config(["$stateProvider",
    function ($stateProvider) {
        $stateProvider.
            state("home", {
                url: "/",
                templateUrl: "../functionality/home.html"
            }).
            state("login", {
                url: "/login",
                templateUrl: "../functionality/login/login.html",
                controller: "AuthController"
            }).
            state("landingPage", {
                url: "/landingPage",
                templateUrl: "../functionality/landingpage/landing-page.html"
            }).
            state("accountList", {
                url: "/accountList",
                controller: "AccountListController",
                templateUrl: "../functionality/accounts/list/account-list.html"
            }).
            state("users", {
                url: "/users",
                templateUrl: "../functionality/users/list/user-list.html",
                controller: "UserListController",
                resolve: {
                    users: function (mnyUserService) {
                        return mnyUserService.getAllUsers();
                    }
                }
            }).
            state("newUser", {
                url: "/users/new",
                templateUrl: "../functionality/users/create/user-add.html",
                controller: "UserCreateController"
            }).
            state("editUser", {
                url: "/users/edit/{location}",
                templateUrl: "../functionality/users/edit/user-edit.html",
                controller: "UserEditController",
                resolve: {
                    user: function (mnyUserService, $stateParams) {
                        return mnyUserService.getUser($stateParams.location);
                    }
                }
            }).
            state("transactionList", {
                url: "/transactionList",
                templateUrl: "../functionality/transactions/list/transaction-list.html",
                controller: "TransactionController"
            }).
            state("dataUploads", {
                url: "/dataUploads",
                templateUrl: "../functionality/dataupload/data-upload.html",
                controller: "DataUploadController"
            });
        //.
        //otherwise("/");
    }]);
