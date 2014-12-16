moneyApp.service('AuthService', ['$localStorage', '$q', '$http', 'mnyUserService', function ($localStorage, $q, $http) {

    var authService = this;

    authService.userName = "";
    authService.authToken = null;
    //authService.firstName = "";
    //authService.lastName = "";

    authService.storage = $localStorage;

    if (authService.storage.authService == null)
        authService.storage.authService = {};

    authService.storage = authService.storage.authService;

    authService.login = function (username, password) {
//        authService.authToken = 'Basic ' + CryptoJS.enc.Utf8.parse(username + ':' + CryptoJS.SHA256(password).toString(CryptoJS.enc.Base64)).toString(CryptoJS.enc.Base64);
        authService.username = username;
        authService.authToken = "Basic " + CryptoJS.enc.Utf8.parse(username + ':' + password).toString(CryptoJS.enc.Base64);
        return authService.isAuthenticated();
    };

    authService.isAuthenticated = function () {
        var deferred = $q.defer();

        $http.get(BASE_URL + "/users/search/existsByUserName?userName=" + authService.userName, {headers: {"Authorization": authService.authToken}}).
            success(function (data, status, headers, config) {
                authService.storage.authToken = authService.authToken;
                //authService.firstName = data._embedded.users[0].firstName;
                //authService.lastName = data._embedded.users[0].lastName;
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

    authService.getFullName = function () {
        return authService.firstName + " " + authService.lastName;
    };

    authService.logout = function () {
        authService.authToken = null;
        authService.firstName = "";
        authService.lastName = "";
        delete authService.storage.authToken;
        delete authService.storage.username;
    }
}]);