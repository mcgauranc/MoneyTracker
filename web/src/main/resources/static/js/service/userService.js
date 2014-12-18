"use strict";

/**
 * This service facilitates all of the user functionality, and interaction with the server.
 */

moneyApp.service("mnyUserService", ['$http', 'mnyAddressService', function ($http, mnyAddressService) {
    var userService = this;


    /**
     * This method checks to see if a user, with the given username exists in the database.
     *
     * @param userName userName The name of the user to
     * @returns {boolean} Returns true, if the user exists in the database.
     */
    userService.userExists = function (userName) {
        var result = false;
        $http.get("api/users/search/existsByUserName?userName=" + userName).success(function (data) {
            result = data;
        });
        return result;
    };

    /**
     *
     * @param user
     * @returns {string}
     */
    userService.save = function (user) {
        var result = "";
        $http.post("api/users", user).success(function (data, status, headers) {
            result = headers("location");
            var address = getAddress(user);

        }).error(function () {
            console.log("There was an error saving the user.")
        });

        //Restangular.all("users").post(user).then(function (userResult, headers) {
        //    result = true;
        //    var userLocation = userResult.headers().location;
        //    var address = getAddress(user);
            //mnyAddressService.save(address).success(function(data, status, headers) {
            //    var addressLocation = headers.getHeader("Location");
            //    userService.associate(userLocation, addressLocation);
            //}).error(function(){
            //    deferred(status);
            //});
        //});
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
