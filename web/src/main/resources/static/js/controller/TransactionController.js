"use strict";

/* Controllers */

moneyApp.controller("TransactionController", ["$scope", "$location",
    function ($scope, $location) {

        var transactionController = $scope.transactionController = {};

        transactionController.transactionList = {};

    }]);