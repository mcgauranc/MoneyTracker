/**
 * This controller facilitates access to the address entity.
 *
 * User: rowan.massey
 * Date: 15/12/2014
 *
 */

moneyApp.controller("AddressController", ["$scope", "$location",
    function ($scope, $location) {

        var addressController = $scope.addressController = {};

        addressController.address = {};
        addressController.addressLocation = "";

        /**
         * This method saves a new instance of an address to the database.
         */
        addressController.save = function () {
        };

        /**
         * This method creates a new address object, to be referenced in code.
         */
        addressController.newAddress = function () {
            addressController.address.address1 = "";
            addressController.address.address2 = "";
            addressController.address.address3 = "";
            addressController.address.address4 = "";
            addressController.address.county = "";
            addressController.address.city = "";
        };
    }]);