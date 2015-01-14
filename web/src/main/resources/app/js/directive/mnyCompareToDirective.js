/**
 * This directive compares the values of two fields.
 *
 * User: rowan.massey
 * Date: 10/12/2014
 */
var mnyCompareTo = function () {
    return {
        require: "ngModel",
        scope: {
            mnyCompareTo: "="
        },
        link: function (scope, element, attributes, ngModel) {
            scope.$watch(function () {
                var combined;

                if (scope.mnyCompareTo || ngModel.$viewValue) {
                    combined = scope.mnyCompareTo + '_' + ngModel.$viewValue;
                }
                return combined;
            }, function (value) {
                if (value) {
                    ngModel.$parsers.unshift(function (viewValue) {
                        var origin = scope.mnyCompareTo;
                        if (origin !== viewValue) {
                            ngModel.$setValidity("mnyCompareTo", false);
                            return undefined;
                        } else {
                            ngModel.$setValidity("mnyCompareTo", true);
                            return viewValue;
                        }
                    });
                }
            });
        }
    };
};

moneyApp.directive("mnyCompareTo", mnyCompareTo);

