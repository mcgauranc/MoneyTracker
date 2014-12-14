/**
 *
 * User: rowan.massey
 * Date: 12/12/2014
 * Time: 23:38
 *
 */

var mnyUsernameUnique = function ($http) {
    return {
        restrict: "A",
        require: "ngModel",
        link: function (scope, element, attrs, ngModel) {
            element.bind("blur", function (e) {
                ngModel.$loading = true;

                //TODO: Check can I use restangular here.
                $http.get("api/users/search/existsByUserName?userName=" + element.val()).success(function (data) {
                    ngModel.$loading = false;
                    ngModel.$setValidity("mnyUsernameUnique", !data);
                });
            });
        }
    };
};

moneyApp.directive("mnyUsernameUnique", mnyUsernameUnique);