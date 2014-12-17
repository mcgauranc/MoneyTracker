/**
 * This service facilitates all of the address functionality, and interaction with the server.
 *
 */

moneyApp.factory("mnyAddressService", ['Restangular', '$http', function (Restangular) {
    return {
        save: function (address) {
            return Restangular.all("addresses").post(address);
        }
    }
}]);

