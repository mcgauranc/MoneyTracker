"use strict";

/* Services */

var userServices = angular.module("userServices", ["ngResource"]);

userServices.factory("User", ["$resource",
    function ($resource) {
        return $resource("/server/userss/:userId", {}, {
            query: {method: "GET", params: {userId: ""}, isArray: false}
        });
    }]);
