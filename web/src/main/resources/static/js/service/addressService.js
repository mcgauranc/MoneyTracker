/**
 * This service facilitates all of the address functionality, and interaction with the server.
 *
 */

moneyApp.service("mnyAddressService", ['Restangular', '$http', function (Restangular) {

    var addressService = this;

    /**
     * This method saves an address record, and returns the location of the saved resource.
     *
     * @param address The address object to save.
     * @returns {string} The saved address's location.
     */
    addressService.save = function (address) {
        var result = "";
        Restangular.all("addresses").post(address).then(function (addressResult) {
            result = addressResult.headers().location;
        });
        return result;
    }
}]);

