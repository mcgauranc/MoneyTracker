(function () {
    'use strict';

    /**
     * This controller handles everything to do with the creation of an account screen.
     *
     * User: rowan.massey
     * Date: 11/07/2015
     */

    moneyApp.controller("CurrencyCreateController", ["$scope", "$location", "mnyCurrencyService",
        function ($scope, $location, mnyCurrencyService) {

            var vm = this;

            vm.currency = {};
            vm.currencyLocation = "";

            /**
             * This method saves a new currency.
             */
            vm.addCurrency = function () {
                var currency = vm.getCurrencyDto(vm.currency);
                mnyCurrencyService.save(currency).then(function (currencyData) {
                    vm.currencyLocation = currencyData.headers().location;
                    $location.path("currency");
                });
            };

            /**
             * This method cancels the current operation, and redirects to the currency list page.
             */
            vm.cancel = function () {
                $location.path("currency");
            };

            /**
             * This method converts the currency object defined in the controller, into a currency object that will be posted
             * to the server.
             *
             * @param currency The currency object defined in the controller.
             * @returns {} currency object with only relevant currency object information populated.
             */
            vm.getCurrencyDto = function (currency) {
                var result = {};

                result.name = currency.name;
                result.iso = currency.iso;

                return result;
            };
        }
    ]);
})();