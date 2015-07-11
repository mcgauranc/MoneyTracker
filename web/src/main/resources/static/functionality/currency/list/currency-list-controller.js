/**
 * This controller handles all of the currency list requests made to the server, and facilitates their access from
 * the front-end.
 *
 * User: rowan.massey
 * Date: 11/07/2015
 */

(function () {
    'use strict';

    moneyApp.controller("CurrencyListController", ["$scope", "$location", "mnyCurrencyService", "currencies",
        function ($scope, $location, mnyCurrencyService, currencies) {

            var vm = this;

            vm.currencies = currencies;

            /**
             * This method gets all of the currencies from the database.
             */
            vm.getCurrencies = function () {
                mnyCurrencyService.getAllCurrencies().then(function (currencyData) {
                    vm.currencies = currencyData;
                });
            };

            /**
             * This method removes the given currency from the database, for the given .
             */
            vm.remove = function (location) {
                mnyCurrencyService.remove(location).then(function () {
                    vm.getCurrencies();
                });
            };
        }]);
})();