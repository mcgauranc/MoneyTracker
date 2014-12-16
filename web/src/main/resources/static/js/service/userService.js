"use strict";

/**
 * This service facilitates the user functionality.
 *
 */

moneyApp.factory("mnyUserService", ['Restangular', '$http', function (Restangular, $http) {
    return {
        getAllUsers: function () {
            return Restangular.all("users");
        },
        userExists: function (userName) {
            return $http.get("api/users/search/existsByUserName?userName=" + userName);
        }
    }
}]);
