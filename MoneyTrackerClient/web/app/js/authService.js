moneyApp.service('AuthService', ['$localStorage', '$q', '$http' , function ($localStorage, $q, $http) {
    var authService = this;
    authService.authToken = null;
    authService.storage = $localStorage;
    if (authService.storage.authService == null)
        authService.storage.authService = {};
    authService.storage = authService.storage.authService;

    authService.login = function (username, password) {
//        authService.authToken = 'Basic ' + CryptoJS.enc.Utf8.parse(username + ':' + CryptoJS.SHA256(password).toString(CryptoJS.enc.Base64)).toString(CryptoJS.enc.Base64);
        authService.authToken = "Basic " + CryptoJS.enc.Utf8.parse(username + ':' + password).toString(CryptoJS.enc.Base64);
        return authService.isAuthenticated(username, password);
    };

    authService.isAuthenticated = function (username, password) {
        var deferred = $q.defer();

        $http.post(BASE_URL + "/login", {'j_username': username, 'j_password': password}, {headers: {"Authorization": authService.authToken, "x-ajax-call": "true"}}).
            success(function (data, status, headers, config) {
                authService.storage.authToken = authService.authToken;
                deferred.resolve();
            }).
            error(function (data, status, headers, config) {
                authService.authToken = null;
                deferred(status);
            });

        return deferred.promise;
    };

    authService.getAuthToken = function () {
        if (authService.storage.authToken != null) {
            authService.authToken = authService.storage.authToken;
        }
        return authService.authToken;
    };

    authService.logout = function () {
        authService.authToken = null;
        delete authService.storage.authToken;
    }
}]);