/**
 * This directive checks to see if the provided username is unique in the database, if so the validity of the
 * control to true, false otherwise.
 *
 * User: rowan.massey
 * Date: 12/12/2014
 */

var mnyUsernameUnique = function ($http, mnyUniqueUserService) {
    return {
        restrict: "A",
        require: "ngModel",
        link: function (scope, element, attrs, ngModel) {
            element.bind("blur", function () {
                ngModel.$loading = true;
                mnyUniqueUserService.userExists(element.val()).then(function (exists) {
                    var userExists = exists.data == 1;
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