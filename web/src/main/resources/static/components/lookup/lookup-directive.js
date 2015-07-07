/**
 * This directive will facilitate the lookups values for entities on the server.
 *
 * User: rowan.massey
 * Date: 07/04/2015
 */

(function () {
    "use strict";

    var mnyLookup = function ($q) {
        return {
            restrict: "E",
            replace: true,
            scope: {
                lookupDatasource: "&",
                lookupTextField: "@",
                lookupValueField: "@"
            },
            templateUrl: "components/lookup/lookup.html",
            link: function ($scope, element) {
                $scope.onItemSelect = function(item) {
                    element.find("input").val(item[$scope.lookupTextField]);
                    $scope.foundRecords = [];
                };

                $scope.search = function () {
                    var defer = $q.defer();
                    defer.resolve($scope.lookupDatasource({searchValue: $scope.searchTerm}));
                    defer.promise.then(function (searchResults) {
                        $scope.foundRecords = searchResults;
                    });
                };
            },
            controller: function ($scope, $q) {
            }
        };
    };
    moneyApp.directive("mnyLookup", mnyLookup);
})();