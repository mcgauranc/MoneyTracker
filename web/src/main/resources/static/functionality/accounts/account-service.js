/**
 * This service facilitates all of the account functionality, and interaction with the server. Only ONE instance of a
 * service is created for the application. So it can be reused.
 *
 * NOTE: Could possibly implement AngularJS $resource functionality to replace items below.
 */

(function () {
    'use strict';

    moneyApp.service("mnyAccountService", ["$http", "$q", "mnyBaseService", function ($http, $q, mnyBaseService) {

        var accountService = this;
        var PATH = "api/accounts";

        /**
         * This method saves a new instance of a account to the database.
         *
         * @param account The account object that the user wants saved to the database.
         * @returns {*|promise}
         */
        accountService.save = function (account) {
            return mnyBaseService.saveRecord(PATH, account);
        };

        /**
         * This method removes the record at the given location
         *
         * @param location The location of the record.
         * @returns {promise.promise|jQuery.promise|jQuery.ready.promise}
         */
        accountService.remove = function (location) {
            return mnyBaseService.remove(location);
        };

        /**
         * This method returns a list of all of the accounts defined in the database.
         *
         * @returns {*|promise}
         */
        accountService.getAllAccounts = function () {
            return mnyBaseService.getAllRecords(PATH, "accounts");
        };

        /**
         * This method retrieves the account record for the given location.
         *
         * @param location The location of the required record.
         * @returns {*|promise}
         */
        accountService.getAccount = function (location) {
            return mnyBaseService.getRecord(location);
        };

        /**
         * This method updates the account, for the given location.
         *
         * @returns {promise.promise|jQuery.promise|jQuery.ready.promise}
         */
        accountService.updateAccount = function (location, data) {
            return mnyBaseService.updateRecord(location, data);
        };
    }]);
})();