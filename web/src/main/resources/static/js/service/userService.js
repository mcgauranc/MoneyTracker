"use strict";

/**
 * This service facilitates all of the user functionality, and interaction with the server.
 */

moneyApp.service("mnyUserService", ['$http', '$q', function ($http, $q) {

    var userService = this;

    /**
     * This method checks to see if a user, with the given username exists in the database.
     *
     * @param userName userName The name of the user to
     * @returns {promise.promise|jQuery.promise|jQuery.ready.promise} Returns true, if the user exists in the database.
     */
    userService.userExists = function (userName) {
        //This deferred object will contain the promise that we'll return. The called should ALWAYS expect a promise.
        //I could use "success", and "error" as opposed to "then" - the latter however is standard.
        var deferred = $q.defer();
        $http.get("api/users/search/existsByUserName?userName=" + userName).then(function (data) {
            //On success we want to resolve the. We could use a caching mechanism here by assigning the value to a self.exists
            //variable, and resolve to that if it's populated. e.g  . if (self.name != null) { deferred.resolve(self.name); }
            deferred.resolve(data);
        }, function (error) {
            //Reject the promise if there is a failure.
            console.log("There was an error determining if the username was unique.");
            deferred.reject(error);
        });
        return deferred.promise;
    };

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

    /**
     *
     * @returns {promise.promise|jQuery.promise|jQuery.ready.promise}
     */
    userService.getAllUsers = function () {
        var deferred = $q.defer();
        $http.get("api/users").then(function (data) {
            deferred.resolve(data.data._embedded.users);
        }, function (error) {
            console.log("There was an error saving the user." + error);
            deferred.reject(error);
        });
        return deferred.promise;
    }
}]);
