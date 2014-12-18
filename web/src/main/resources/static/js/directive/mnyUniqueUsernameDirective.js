/**
 *
 * User: rowan.massey
 * Date: 12/12/2014
 * Time: 23:38
 *
 */

var mnyUsernameUnique = function ($http, mnyUserService) {
    return {
        restrict: "A",
        require: "ngModel",
        link: function (scope, element, attrs, ngModel) {
            element.bind("blur", function (e) {
                ngModel.$loading = true;
                ngModel.$setValidity("mnyUsernameUnique", !mnyUserService.userExists(element.val()));
            });
        }
    };
};

moneyApp.directive("mnyUsernameUnique", mnyUsernameUnique);