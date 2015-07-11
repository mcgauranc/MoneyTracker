/**
 * This service facilitates all of the currency functionality, and interaction with the server. Only ONE instance of a
 * service is created for the application. So it can be reused.
 *
 * NOTE: Could possibly implement AngularJS $resource functionality to replace items below.
 */

(function () {
    'use strict';

    moneyApp.service("mnyCurrencyService", ['$http', '$q', function ($http, $q) {

        var currencyService = this;

        /**
         * This method saves a new instance of a currency to the database.
         *
         * @param currency The currency object that the user wants saved to the database.
         * @returns {*|promise}
         */
        currencyService.save = function (currency) {
            var deferred = $q.defer();
            $http.post("api/currencies", currency).then(function (data) {
                deferred.resolve(data);
            }, function (error) {
                deferred.reject(error);
                throw new Error("There was an error saving the currency: " + error.data.message);
            });
            return deferred.promise;
        };

        /**
         * This method deletes the currency for the given location.
         *
         * @param location The location of the currency which needs to be deleted.
         * @returns {promise.promise|jQuery.promise|jQuery.ready.promise}
         */
            //TODO: Could make this a general function
        currencyService.remove = function (location) {
            var deferred = $q.defer();
            $http.delete(location).then(function (data) {
                deferred.resolve(data);
            }, function (error) {
                deferred.reject(error);
                throw new Error("There was an error deleting the currency: " + error.data.message);
            });
            return deferred.promise;
        };

        /**
         * This method returns a list of all of the currencies defined in the database.
         *
         * @returns {*|promise}
         */
        currencyService.getAllCurrencies = function () {
            var deferred = $q.defer();
            $http.get("api/currencies").then(function (data) {
                if (data.data._embedded) {
                    deferred.resolve(data.data._embedded.currencies);
                } else {
                    //TODO: Not too sure if this is right. Should I resolve if nothing is found?
                    deferred.resolve();
                }
            }, function (error) {
                deferred.reject(error);
                throw new Error("There was an error retrieving all of the currencies: " + error.data.message);
            });
            return deferred.promise;
        };

        /**
         * This method returns a specific currency for the given location.
         *
         * @param location The location of the currency which will be retrieved.
         * @returns {*|promise}
         */
        currencyService.getCurrency = function (location) {
            var deferred = $q.defer();
            $http.get(location).then(function (data) {
                deferred.resolve(data.data);
            }, function (error) {
                deferred.reject(error);
                throw new Error("There was an error getting the currency: " + error.data.message);
            });
            return deferred.promise;
        };

        /**
         * This method updates the currency, with the given payload
         *
         * @param data The payload of the currency which will be updated.
         * @returns {promise.promise|jQuery.promise|jQuery.ready.promise}
         */
            //TODO: Could make this a general function
        currencyService.updateCurrency = function (data) {
            var deferred = $q.defer();
            $http.put(data).then(function (data) {
                deferred.resolve(data.data);
            }, function (error) {
                deferred.reject(error);
                throw new Error("There was an error updating the currency: " + error.data.message);
            });
            return deferred.promise;
        };

        /**
         * This method searches for a list of currencies that matches the name passed in.
         *
         * @param currencyValue The name of the currency for whom you are searching.
         * @returns {*}
         */
        currencyService.searchCurrency = function (currencyValue) {
            if (currencyValue) {
                currencyValue = encodeURIComponent('%' + currencyValue + '%');
            }
            var deferred = $q.defer();
            $http.get("api/currencies/search/findByName?name=" + currencyValue).then(function (data) {
                if (data.data._embedded) {
                    deferred.resolve(data.data._embedded.currencies);
                } else {
                    deferred.resolve(undefined);
                }
            }, function (error) {
                deferred.reject(error);
                throw new Error("There was an error searching for currency: " + error.message);
            });
            return deferred.promise;

        };

    }]);
})();