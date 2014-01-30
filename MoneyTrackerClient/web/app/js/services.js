"use strict";

/* Services */

var userService = angular.module("userService", ["ngResource"]);
var accountService = angular.module("accountService", ["ngResource"]);

//userService.factory("User", ["$resource",
//    function ($resource) {
//        return $resource("/server/userss/:userId", {}, {
//            query: {
//                method: "GET", params: {
//                    userId: ""},
//                isArray: false}
//        });
//    }]);

userService.factory("User", ["$resource",
    function ($resource) {
        return $resource("/server/userss/:userId", {}, {
            update: {
                method: "PUT" }
        });
    }]);
