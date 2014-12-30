/**
 * This service facilitates all of the address functionality, and interaction with the server.
 *
 */

moneyApp.service("mnyAddressService", ["$http", "$q", function ($http, $q) {

    var addressService = this;

    /**
     * This method saves an address record, and returns the location of the saved resource.
     *
     * @param address The address object, containing all the relevant information.
     * @returns {promise.promise|jQuery.promise|jQuery.ready.promise}
     */
    addressService.save = function (address) {
        var deferred = $q.defer();
        $http.post("api/addresses", address).then(function (data) {
            deferred.resolve(data);
        }, function (error) {
            console.log("There was an error saving the address." + error);
            deferred.reject(error);
        });
        return deferred.promise;
    };
}]);

