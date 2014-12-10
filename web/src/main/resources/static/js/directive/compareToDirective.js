/**
 * This directive compares the values of two fields.
 *
 * User: rowan.massey
 * Date: 10/12/2014
 */
var compareTo = function () {
    return {
        require: "ngModel",
        scope: {
            compareTo: "="
        },
        link: function (scope, element, attributes, ngModel) {
            scope.$watch(function () {
                var combined;

                if (scope.compareTo || ngModel.$viewValue) {
                    combined = scope.compareTo + '_' + ngModel.$viewValue;
                }
                return combined;
            }, function (value) {
                if (value) {
                    ngModel.$parsers.unshift(function (viewValue) {
                        var origin = scope.compareTo;
                        if (origin !== viewValue) {
                            ngModel.$setValidity("compareTo", false);
                            return undefined;
                        } else {
                            ngModel.$setValidity("compareTo", true);
                            return viewValue;
                        }
                    });
                }
            });
        }
    };
};

moneyApp.directive("compareTo", compareTo);

