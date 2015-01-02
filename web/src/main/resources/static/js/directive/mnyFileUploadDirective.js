/**
 * This directive facilitates the uploading of a file to the server.
 *
 * User: rowan.massey
 * Date: 02/01/2015
 */
var mnyFileUpload = function () {
    return {
        restrict: "E",
        //require: "ngModel",
        controller: ["$scope", function ($scope) {

        }],
        link: function (scope, element, attributes, ngModel) {
        },
        templateUrl: "../../partials/template/fileUpload.html"
    };
};

moneyApp.directive("mnyFileUpload", mnyFileUpload);

