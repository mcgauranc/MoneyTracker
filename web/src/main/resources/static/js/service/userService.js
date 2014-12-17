"use strict";

/**
 * This service facilitates all of the user functionality, and interaction with the server.
 *
 */

moneyApp.factory("mnyUserService", ['Restangular', '$http', 'mnyAddressService', function (Restangular, $http, mnyAddressService) {
    return {
        getAllUsers: function () {
            return Restangular.all("users");
        },
        userExists: function (userName) {
            return $http.get("api/users/search/existsByUserName?userName=" + userName);
        },
        save: function (user, address) {
            return Restangular.all("users").post(user);
        },
        associate: function (userlocation, relatedLocation) {
            return $http.put(userlocation, relatedLocation, {"Content-Type": "text/uri-list"})
        }
    }
}]);
