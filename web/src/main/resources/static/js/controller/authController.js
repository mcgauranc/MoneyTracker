moneyApp.controller("AuthController", ["$scope", "mnyAuthService", "$location",
    function ($scope, authService, $location) {

        var authController = $scope.authController = {};

        authController.username = "";
        authController.password = "";

        authController.authService = authService;

        authController.login = function () {
            authController.authService.login(authController.username, authController.password)
                .then(function (response) {
                    $location.path("/landingPage");
                }, function (reject) {
                    console.log("Login error");
                });
            authController.password = "";
        };

        authController.logout = function () {
            authController.authService.logout();
            authController.username = "";
            authController.password = "";
            authController.location.path('/');
        };
    }]);