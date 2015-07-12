(function () {
    'use strict';

    /**
     * This controller handles everything to do with the creation of an account screen.
     *
     * User: rowan.massey
     * Date: 10/06/2015
     */

    moneyApp.controller("AccountCreateController", ["$scope", "$location", "mnyAccountService", "mnyCurrencyService",
        function ($scope, $location, mnyAccountService, mnyCurrencyService) {

            var vm = this;

            vm.account = {};
            vm.accountLocation = "";

            /**
             * This method saves a new account.
             */
            vm.addAccount = function () {
                var account = vm.getAccountDto(vm.account);
                mnyAccountService.save(account).then(function (accountData) {
                    vm.accountLocation = accountData.headers().location;
                    //TODO: Need to save the currency and type here if they're populated.
                    $location.path("accounts");
                });
            };

            /**
             * This method cancels the current operation, and redirects to the accounts page.
             */
            vm.cancel = function () {
                $location.path("accounts");
            };

            /**
             * This method converts the account object defined in the controller, into an account object that will be posted
             * to the server.
             *
             * @param account The account object defined in the controller.
             * @returns {} account object with only relevant account object information populated.
             */
            vm.getAccountDto = function (account) {
                var result = {};

                result.name = account.name;
                result.number = account.number;
                result.type = account.type;
                result.openingBalance = account.openingBalance;
                result.balance = account.balance;
                result.openingDate = account.openingDate;
                //result.currency = getCurrencyLocationFromId(account.currency);

                return result;
            };

            /**
             * This method searches for the typed in currency.
             *
             * @param currency The name of the currency which needs to be searched for.
             */
            vm.searchCurrency = function (currency) {
                return mnyCurrencyService.searchCurrency(currency);
            };
        }
    ]);
})();