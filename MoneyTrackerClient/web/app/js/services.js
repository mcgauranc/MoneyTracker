"use strict";

/* Services */

var userService = angular.module("userService", ["ngResource"]);
var accountService = angular.module("accountService", ["ngResource"]);

userService.factory("Users", ["$resource",
    function ($resource) {
        return $resource("/server/userss/", {}, {
            get: {
                method: 'GET', isArray: false}
        });
    }]);
