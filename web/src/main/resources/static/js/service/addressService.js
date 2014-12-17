/**
 * This service facilitates all of the address functionality, and interaction with the server.
 *
 */

moneyApp.service("mnyAddressService", ['Restangular', '$http', function (Restangular) {
    return {
        save: function (address) {
            var deferred = $q.defer();
            Restangular.all("addresses").post(address).success(function (data, status, headers) {
                deferred.resolve();
            }).error(function (data, status, headers) {
                deferred(status);
            });
            return deferred.promise();
        }
    }
}]);

