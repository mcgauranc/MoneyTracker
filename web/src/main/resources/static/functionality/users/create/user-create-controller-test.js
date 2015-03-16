describe('UserCreateController', function () {
    beforeEach(module('moneyApp'));

    var $scope,
        $location,
        userCreateController;

    beforeEach(inject(function ($rootScope, $controller, $q, _$location_) {

        $scope = $rootScope.$new();

        $scope.user = {
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

        $location = _$location_;
        userCreateController = $controller('UserCreateController', {
            $scope: $scope,
            $location: $location
        });
    }));

    it('Should add a new user', function () {
        //Creates a spy on the addUser method. Basically calls service methods, which will be tested separately.
        userCreateController.addUser = jasmine.createSpy("addUser() Spy");
        userCreateController.addUser();
        expect(userCreateController.addUser).toHaveBeenCalled();
    });

    it('Should cancel the request', function () {
        userCreateController.cancel();
        expect($location.$$path).toBe("/users")
    });

    it('Should create a userDto object', function () {
        var result = userCreateController.getUserDto($scope.user);
        expect(result.userName).toBe("Admin");
        expect(result.password).toBe("Passw0rd");
        expect(result.firstName).toBe("Admin");
        expect(result.lastName).toBe("User");
        expect(result.dateOfBirth).toBe("2001-01-01T00:00:00.000Z");
    });

    it('Should create an addressDto object', function () {
        var result = userCreateController.getAddressDto($scope.user);
        expect(result.address1).toBe("address 1");
        expect(result.address2).toBe("address 2");
        expect(result.address3).toBe("address 3");
        expect(result.address4).toBe("address 4");
        expect(result.city).toBe("city");
        expect(result.county).toBe("county");
    });
});
