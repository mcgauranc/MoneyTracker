moneyApp.controller('AuthController', ['$scope', 'AuthService', 'AuthRestangular', '$location', '$http',
    function ($scope, authService, Restangular, $location, $http) {

        var authController = $scope.authController = {};

        authController.username = '';
        authController.password = '';

        authController.authService = authService;
        authController.location = $location;

        authController.login = function () {
            authController.authService.login(authController.username, authController.password)
                .then(function (response) {
                    authController.location.path('/auth');
                }, function (reject) {
                    console.log('Login error');
                });
            authController.username = '';
            authController.password = '';
        };

        authController.logout = function () {
            authController.authService.logout();
            authController.username = '';
            authController.password = '';
            authController.location.path('/');
        };
    }]);