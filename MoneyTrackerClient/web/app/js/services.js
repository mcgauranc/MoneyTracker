"use strict";

/* Services */

var userService = angular.module("userService", ["ngResource"]);
var accountService = angular.module("accountService", ["ngResource"]);

userService.factory("Users", ["$resource",
    function ($resource) {
        return $resource("/server/users/", {}, {
            get: {
                method: 'GET'},
            update: {
                method: "PUT"},
            remove: {
                method: "DELETE"
            }
        });
    }]);
