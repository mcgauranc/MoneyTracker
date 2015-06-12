/**
 * This service facilitates all of the account functionality, and interaction with the server. Only ONE instance of a
 * service is created for the application. So it can be reused.
 *
 * NOTE: Could possibly implement AngularJS $resource functionality to replace items below.
 */

(function () {
    'use strict';

    moneyApp.service("mnyAccountService", ['$http', '$q', function ($http, $q) {

        var accountService = this;

        /**
         * This method saves a new instance of a account to the database.
         *
         * @param account The account object that the user wants saved to the database.
         * @returns {*|promise}
         */
        accountService.save = function (account) {
            var deferred = $q.defer();
            $http.post("api/accounts", account).then(function (data) {
                deferred.resolve(data);
            }, function (error) {
                deferred.reject(error);
                throw new Error("There was an error saving the account: " + error.data.message);
            });
            return deferred.promise;
        };

        /**
         *
         * @param location
         * @returns {promise.promise|jQuery.promise|jQuery.ready.promise}
         */
            //TODO: Could make this a general function
        accountService.remove = function (location) {
            var deferred = $q.defer();
            $http.delete(location).then(function (data) {
                deferred.resolve(data);
            }, function (error) {
                deferred.reject(error);
                throw new Error("There was an error deleting the account: " + error.data.message);
            });
            return deferred.promise;
        };

        /**
         * This method returns a list of all of the accounts defined in the database.
         *
         * @returns {*|promise}
         */
        accountService.getAllAccounts = function () {
            var deferred = $q.defer();
            $http.get("api/accounts").then(function (data) {
                if (data.data._embedded) {
                    deferred.resolve(data.data._embedded.accounts);
                } else {
                    //TODO: Not too sure if this is right. Should I resolve if nothing is found?
                    deferred.resolve();
                }
            }, function (error) {
                deferred.reject(error);
                throw new Error("There was an error retrieving all of the accounts: " + error.data.message);
            });
            return deferred.promise;
        };

        /**
         *
         * @returns {*|promise}
         */
        accountService.getAccount = function (location) {
            var deferred = $q.defer();
            $http.get(location).then(function (data) {
                deferred.resolve(data.data);
            }, function (error) {
                deferred.reject(error);
                throw new Error("There was an error getting the account: " + error.data.message);
            });
            return deferred.promise;
        };

        /**
         *
         * @returns {promise.promise|jQuery.promise|jQuery.ready.promise}
         */
            //TODO: Could make this a general function
        accountService.updateAccount = function (data) {
            var deferred = $q.defer();
            $http.put(data).then(function (data) {
                deferred.resolve(data.data);
            }, function (error) {
                deferred.reject(error);
                throw new Error("There was an error updating the account: " + error.data.message);
            });
            return deferred.promise;
        };
    }]);
})();