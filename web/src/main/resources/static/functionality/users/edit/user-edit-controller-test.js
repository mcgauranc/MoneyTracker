describe('Controller: UserEditController', function () {
    beforeEach(module('moneyApp'));

    var $rootScope,
        $scope,
        $location,
        $injector,
        $q,
        $stateParams,
        userCreateController,
        mnyUserService,
        vm,
        deferred;

    beforeEach(inject(function (_$rootScope_, $controller, _$q_, _$location_, _$injector_) {

        $rootScope = _$rootScope_;
        $scope = $rootScope.$new();
        $location = _$location_;
        $injector = _$injector_;
        $q = _$q_;
        $stateParams = {location: "http://localhost/test"};

        deferred = $q.defer();

        mnyUserService = $injector.get("mnyUserService");
        userCreateController = $controller;

        var testUser = {
            "userName": "Admin",
            "password": "Passw0rd",
            "firstName": "Admin",
            "lastName": "User",
            "dateOfBirth": "01/01/2001"
        };

        vm = userCreateController("UserEditController", {
            $scope: $scope,
            $location: $location,
            $stateParams: $stateParams,
            mnyUserService: mnyUserService,
            user: testUser
        });
    }));

    it('Should update an existing user', function () {
        //A dummy response value that gets returned when the Save() method for a service gets called.
        var result = {
            location: "http://localhost/test",
            expires: 0
        };

        //Create spies for the relevant service functions that are called in the addUser function.
        spyOn(mnyUserService, "updateUser").and.returnValue(deferred.promise);
        deferred.resolve(result);

        vm.update();

        //Need this to actually set the relevant values.
        $rootScope.$digest();

        expect($location.$$path).toBe("/users");
    });

    it('Should cancel the request', function () {
        vm.cancel();
        expect($location.$$path).toBe("/users");
    });
});
