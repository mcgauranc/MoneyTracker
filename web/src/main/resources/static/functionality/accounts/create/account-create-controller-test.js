describe('Controller: AccountCreateController', function () {
    beforeEach(module('moneyApp'));

    var $rootScope,
        $scope,
        $location,
        $injector,
        $q,
        accountCreateController,
        mnyAccountService,
        vm,
        deferred;

    beforeEach(inject(function (_$rootScope_, $controller, _$q_, _$location_, _$injector_) {

        $rootScope = _$rootScope_;
        $scope = $rootScope.$new();
        $location = _$location_;
        $injector = _$injector_;
        $q = _$q_;

        deferred = $q.defer();

        mnyAccountService = $injector.get("mnyAccountService");
        accountCreateController = $controller;

        vm = accountCreateController('AccountCreateController', {
            $scope: $scope,
            $location: $location,
            mnyAccountService: mnyAccountService
        });

        vm.account = {
            "name": "Current",
            "number": "12345",
            "balance": "2000.00",
            "openingDate": "2015-03-17T18:49:41Z"
        };
    }));

    it('Should add a new account', function () {
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
        spyOn(mnyAccountService, 'save').and.returnValue(deferred.promise);
        deferred.resolve(result);

        vm.addAccount();

        //Need this to actually set the relevant values.
        $rootScope.$digest();

        expect(vm.accountLocation).toBe("http://localhost/test");
        expect($location.$$path).toBe("/accounts");
    });

    it('Should cancel the request', function () {
        vm.cancel();
        expect($location.$$path).toBe("/accounts");
    });

    it('Should create an accountDto object', function () {
        var result = vm.getAccountDto(vm.account);
        expect(result.name).toBe("Current");
        expect(result.number).toBe("12345");
        expect(result.balance).toBe("2000.00");
        expect(result.openingDate).toBe("2015-03-17T18:49:41Z");
    });
});
