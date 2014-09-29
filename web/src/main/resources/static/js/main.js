var BASE_URL = 'http://localhost:8080/api';
var EMBEDDED_TAG = '_embedded';
var HREF_TAG = 'href';
var LINKS_TAG = 'links';
var GETLIST_OP = 'getList';

var moneyApp = angular.module("moneyApp", ["restangular", "ngRoute", "ngStorage"]);

moneyApp.directive('focusOn', function () {
    return function (scope, elem, attr) {
        scope.$on('focusOn', function (e, name) {
            if (name === attr.focusOn) {
                elem[0].focus();
            }
        });
    };
});

moneyApp.factory('focus', function ($rootScope, $timeout) {
    return function (name) {
        $timeout(function () {
            $rootScope.$broadcast('focusOn', name);
        });
    }
});

moneyApp.directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if (event.which === 13) {
                scope.$apply(function () {
                    scope.$eval(attrs.ngEnter);
                });
                event.preventDefault();
            }
        });
    };
});