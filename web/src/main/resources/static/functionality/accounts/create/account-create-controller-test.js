describe('Controller: AccountCreateController', function () {
    beforeEach(module('moneyApp'));

    var $rootScope,
        $scope,
        $location,
        $injector,
        $q,
        accountCreateController,
        mnyAccountService,
        mnyCurrencyService,
        vm,
        deferred,
        allCurrencies;

    beforeEach(inject(function (_$rootScope_, $controller, _$q_, _$location_, _$injector_) {

        $rootScope = _$rootScope_;
        $scope = $rootScope.$new();
        $location = _$location_;
        $injector = _$injector_;
        $q = _$q_;

        deferred = $q.defer();

        mnyAccountService = $injector.get("mnyAccountService");
        mnyCurrencyService = $injector.get("mnyCurrencyService");

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

        allCurrencies = {
            "currencies": [{
                "id": 1,
                "name": "Euro",
                "iso": "EUR",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/currencies/1"
                    }
                }
            }, {
                "id": 2,
                "name": "British Pound",
                "iso": "GBP",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/currencies/2"
                    }
                }
            }]
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

    it('Should get all currencies', function () {
        spyOn(mnyCurrencyService, "searchCurrency").and.returnValue(deferred.promise);
        deferred.resolve(allCurrencies);
        var currencies = [];
        vm.searchCurrency().then(function (data) {
            currencies = data;
        });

        //Need this to actually set the relevant values.
        $rootScope.$digest();

        expect(currencies.currencies[0].name).toBe("Euro");
        expect(currencies.currencies[1].name).toBe("British Pound");
    });
});
