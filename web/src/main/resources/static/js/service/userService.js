"use strict";

/**
 * This service facilitates all of the user functionality, and interaction with the server.
 */

moneyApp.service("mnyUserService", ['$http', '$q', function ($http, $q) {
    var userService = this;

    /**
     * This method saves a new instance of a user to the database.
     *
     * @param user The user object that the user wants saved to the database.
     * @returns {promise.promise|jQuery.promise|jQuery.ready.promise}
     */
    userService.save = function (user) {
        var deferred = $q.defer();
        $http.post("api/users", user).then(function (data) {
            deferred.resolve(data);
        }, function (error) {
            console.log("There was an error saving the user." + error);
            deferred.reject(error);
        });
        return deferred.promise;
    };
}]);
