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
            transclude: true,
            scope: {
                lookupDatasource: "&",
                lookupTextField: "=",
                lookupValueField: "@"
            },
            templateUrl: "components/lookup/lookup.html",
            link: function ($scope, element, attrs) {
            },
            controller: function ($scope, $q) {
                $scope.foundRecords = [];
                $scope.searchTerm = "";
                $scope.search = function () {
                    var defer = $q.defer();
                    defer.resolve($scope.lookupDatasource({searchValue: $scope.searchTerm}));
                    defer.promise.then(function (searchResults) {
                        $scope.foundRecords = searchResults;
                    });
                };
            }
        };
    };
    moneyApp.directive("mnyLookup", mnyLookup);
})();