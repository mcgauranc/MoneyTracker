"use strict";

/**
 * This service facilitates all of the user functionality, and interaction with the server.
 */

moneyApp.service("mnyUserService", ['Restangular', '$http', '$q', 'mnyAddressService', function (Restangular, $http, $q) {
    var userService = this;

    /**
     *
     * @returns {*}
     */
    userService.getAllUsers = function () {
        return Restangular.all("users");
    };

    /**
     *
     * @param userName
     * @returns {*}
     */
    userService.userExists = function (userName) {
        return $http.get("api/users/search/existsByUserName?userName=" + userName);
    };

    /**
     *
     * @param user
     * @returns {*}
     */
    userService.save = function (user) {
        var result = false;
        Restangular.all("users").post(user).then(function (userResult) {
            result = true;
            var userLocation = userResult.headers().location;
            var address = getAddress(user);
            //mnyAddressService.save(address).success(function(data, status, headers) {
            //    var addressLocation = headers.getHeader("Location");
            //    userService.associate(userLocation, addressLocation);
            //}).error(function(){
            //    deferred(status);
            //});
        });
        return result;
    };

    /**
     *
     * @param userlocation
     * @param relatedLocation
     * @returns {*}
     */
    userService.associate = function (userlocation, relatedLocation) {
        return $http.put(userlocation, relatedLocation, {"Content-Type": "text/uri-list"})
    };

    function getAddress(user) {
        var address = {};
        if (!user.address1 == '') {
            address.address1 = user.address1;
            address.address2 = user.address2;
            address.address3 = user.address3;
            address.address4 = user.address4;
            address.city = user.city;
            address.county = user.county;
        }
        return address;
    }
}]);
