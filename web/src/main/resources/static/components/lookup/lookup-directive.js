/**
 * This directive will facilitate the lookups values for entities on the server.
 *
 * User: rowan.massey
 * Date: 07/04/2015
 */

(function () {
    "use strict";

    var mnyLookup = function () {
        return {
            restrict: "E",
            replace: true,
            scope: {
                searchLabel: "@",
                searchFunction: "&"
            },
            templateUrl: "components/lookup/lookup.html",
            controller: function ($scope, $q) {
                $scope.foundUsers = [];
                $scope.searchTerm = "";

                $scope.search = function () {
                    var defer = $q.defer();
                    defer.resolve($scope.searchFunction({userName: $scope.searchTerm}));
                    defer.promise.then(function (searchResults) {
                        $scope.foundUsers = searchResults;
                    });
                };
            },
            link: function ($scope) {
            }
        };
    };
    moneyApp.directive("mnyLookup", mnyLookup);
})();