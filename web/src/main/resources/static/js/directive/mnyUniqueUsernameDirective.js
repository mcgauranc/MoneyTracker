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
            element.bind("blur", function () {
                ngModel.$loading = true;
                mnyUserService.userExists(element.val()).then(function(exists){
                    var userExists = exists.data;
                    ngModel.$setValidity("mnyUsernameUnique", !userExists);
                }, function(result){
                    console.log("There was an error processing if the user existed: " + result);
                    ngModel.$setValidity("mnyUsernameUnique", false);
                });
            });
        }
    };
};

moneyApp.directive("mnyUsernameUnique", mnyUsernameUnique);