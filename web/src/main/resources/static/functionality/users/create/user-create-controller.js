"use strict";

/**
 *
 * User: rowan.massey
 * Date: 03/01/2015
 */

moneyApp.controller("UserCreateController", ["$scope", "$location", "mnyUserService",
    "mnyAddressService", "mnyRelationshipService",
    function ($scope, $location, mnyUserService, mnyAddressService, mnyRelationshipService) {

        var vm = this;

        vm.user = {};
        vm.errorMessage = "";

        /**
         * This method saves a new user.
         */
        vm.addUser = function () {
            var user = getUserDto(vm.user);
            mnyUserService.save(user).then(function (userData) {
                vm.userLocation = userData.headers().location;
                //mnyAuthService.login(user.userName, user.password);
                var address = getAddressDto(vm.user);
                mnyAddressService.save(address).then(function (addressData) {
                    vm.addressLocation = addressData.headers().location;
                    mnyRelationshipService.associate(vm.userLocation, "address", vm.addressLocation).then(function () {
                        vm.user = {};
                    })
                });
                $location.path("users")
            }, function (error) {
                vm.errorMessage = "There was an error processing the user: " + error;
            })
        };

        /**
         * This method converts the user object defined in the controller, into a user object that will be posted
         * to the server.
         *
         * @param user The user object defined in the controller.
         * @returns {} user object with only relevant user object information populated.
         */
        function getUserDto(user) {
            var result = {};

            result.userName = user.userName;
            result.password = user.password;
            result.firstName = user.firstName;
            result.lastName = user.lastName;
            result.dateOfBirth = new Date(user.dateOfBirth).toISOString();

            return result;
        }

        /**
         * This method converts the user object defined in the controller, into an address object that will be posted
         * to the server.
         *
         * @param user The user object defined in the controller with address information.
         * @returns An address object, with only address relevant information populated.
         */
        function getAddressDto(user) {
            var result = {};

            result.address1 = user.address1;
            result.address2 = user.address2;
            result.address3 = user.address3;
            result.address4 = user.address4;
            result.city = user.city;
            result.county = user.county;

            return result;
        }
    }
])
;