describe('Controller: UserCreateController', function () {
    beforeEach(module('moneyApp'));

    var $rootScope,
        $scope,
        $location,
        $injector,
        $q,
        userCreateController,
        mnyUserService,
        mnyAddressService,
        mnyBaseService,
        vm,
        deferred;

    beforeEach(inject(function (_$rootScope_, $controller, _$q_, _$location_, _$injector_) {

        $rootScope = _$rootScope_;
        $scope = $rootScope.$new();
        $location = _$location_;
        $injector = _$injector_;
        $q = _$q_;

        deferred = $q.defer();

        mnyUserService = $injector.get("mnyUserService");
        mnyAddressService = $injector.get("mnyAddressService");
        mnyBaseService = $injector.get("mnyBaseService");
        userCreateController = $controller;

        vm = userCreateController('UserCreateController', {
            $scope: $scope,
            $location: $location,
            mnyUserService: mnyUserService,
            mnyAddressService: mnyAddressService,
            mnyBaseService: mnyBaseService
        });

        vm.user = {
            "userName": "Admin",
            "password": "Passw0rd",
            "firstName": "Admin",
            "lastName": "User",
            "dateOfBirth": "01/01/2001",
            "address1": "address 1",
            "address2": "address 2",
            "address3": "address 3",
            "address4": "address 4",
            "city": "city",
            "county": "county"
        };
    }));

    it('Should add a new user', function () {
        //A dummy response value that gets returned when the Save() method for a service gets called.
        var result = {
            headers: function () {
                return {
                    location: "http://localhost/test",
                    expires: 0
                };
            }
        };

        //Create spies for the relevant service functions that are called in the addUser function.
        spyOn(mnyUserService, 'save').and.returnValue(deferred.promise);
        deferred.resolve(result);
        spyOn(mnyAddressService, 'save').and.returnValue(deferred.promise);
        deferred.resolve(result);
        spyOn(mnyBaseService, 'relate').and.returnValue(deferred.promise);
        deferred.resolve(result);

        vm.addUser();

        //Need this to actually set the relevant values.
        $rootScope.$digest();

        expect(vm.userLocation).toBe("http://localhost/test");
        expect(vm.addressLocation).toBe("http://localhost/test");
        expect($location.$$path).toBe("/users");
    });

    it('Should cancel the request', function () {
        vm.cancel();
        expect($location.$$path).toBe("/users");
    });

    it('Should create a userDto object', function () {
        var result = vm.getUserDto(vm.user);
        expect(result.userName).toBe("Admin");
        expect(result.password).toBe("Passw0rd");
        expect(result.firstName).toBe("Admin");
        expect(result.lastName).toBe("User");
        expect(result.dateOfBirth).toBe("2001-01-01T00:00:00.000Z");
    });

    it('Should create an addressDto object', function () {
        var result = vm.getAddressDto(vm.user);
        expect(result.address1).toBe("address 1");
        expect(result.address2).toBe("address 2");
        expect(result.address3).toBe("address 3");
        expect(result.address4).toBe("address 4");
        expect(result.city).toBe("city");
        expect(result.county).toBe("county");
    });
});
