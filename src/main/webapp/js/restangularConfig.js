moneyApp.config(function (RestangularProvider) {

    RestangularProvider.setBaseUrl(BASE_URL);

    RestangularProvider.setResponseExtractor(function (data, operation, route, url, response, deferred) {
        var returnData = data;
        if (operation == GETLIST_OP && EMBEDDED_TAG in data) {
            for (var i = 0; i < data[EMBEDDED_TAG].length; i++) {
                data[EMBEDDED_TAG][i][HREF_TAG] = data[EMBEDDED_TAG][i][LINKS_TAG][0][HREF_TAG];
                delete data[EMBEDDED_TAG][i][LINKS_TAG];
            }
            returnData = data[EMBEDDED_TAG][route];
            delete data[EMBEDDED_TAG];
            for (var key in data) {
                returnData[key] = data[key];
            }
        }
        return returnData;
    });
});

moneyApp.factory('AuthRestangular', ['Restangular', 'AuthService', function (Restangular, authService) {
    var authService = authService;
    return Restangular.withConfig(function (RestangularConfigurer) {
        RestangularConfigurer.setFullRequestInterceptor(function (elems, operation, what, url, headers, params) {
            var auth = authService.getAuthToken();
            if (auth != null) {
                if (headers == null)
                    headers = {};
                headers.Authorization = auth;
            }
            return {
                element: elems,
                params: params,
                headers: headers
            }
        });
    });
}]);