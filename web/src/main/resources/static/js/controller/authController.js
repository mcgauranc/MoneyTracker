moneyApp.controller("AuthController", ["$scope", "AuthService", "$http", "AuthRestangular", "$location",
    function ($scope, authService, Restangular, $location) {

        var authController = $scope.authController = {};

        authController.username = "";
        authController.password = "";

        authController.authService = authService;
        authController.location = $location;

        authController.login = function () {
            authController.authService.login(authController.username, authController.password)
                .then(function (response) {
                    authController.location.path("/users");
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