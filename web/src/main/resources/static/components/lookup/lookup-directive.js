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
                $scope.foundRecords = [];
                $scope.searchTerm = "";
                $scope.displayField = [{searchField: 'firstName'}, {searchField: 'lastName'}];

                $scope.displays = processDisplayFields($scope.displayField);

                $scope.search = function () {
                    var defer = $q.defer();
                    defer.resolve($scope.searchFunction({searchValue: $scope.searchTerm}));
                    defer.promise.then(function (searchResults) {
                        $scope.foundRecords = searchResults;
                    });
                };

                function processDisplayFields(displayFields) {
                    var show = "";
                    for (var i in displayFields) {
                        show += "item." + displayFields[i].searchField + " ";
                    }
                    return show;
                }
            },
            link: function ($scope) {
            }
        };
    };
    moneyApp.directive("mnyLookup", mnyLookup);
})();