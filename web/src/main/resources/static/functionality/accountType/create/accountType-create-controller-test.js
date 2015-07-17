/**
 *
 * User: rowan.massey
 * Date: 11/07/2015
 *
 */
describe('Controller: AccountTypeCreateController', function () {
    beforeEach(module('moneyApp'));

    var $rootScope,
        $scope,
        $location,
        $injector,
        $q,
        accountTypeCreateController,
        mnyAccountTypeService,
        vm,
        deferred;

    beforeEach(inject(function (_$rootScope_, $controller, _$q_, _$location_, _$injector_) {

        $rootScope = _$rootScope_;
        $scope = $rootScope.$new();
        $location = _$location_;
        $injector = _$injector_;
        $q = _$q_;

        deferred = $q.defer();

        mnyAccountTypeService = $injector.get("mnyAccountTypeService");

        accountTypeCreateController = $controller;

        vm = accountTypeCreateController('AccountTypeCreateController', {
            $scope: $scope,
            $location: $location,
            mnyCurrencyService: mnyAccountTypeService
        });

        vm.currency = {
            "id": 1,
            "name": "Current",
            "_links": {
                "self": {
                    "href": "http://localhost:8080/api/accountTypes/1"
                }
            }
        };
    }));

    it('Should add a new account type', function () {
        //A dummy response value that gets returned when the Save() method for a service gets called.
        var result = {
            headers: function () {
                return {
                    location: "http://localhost:8080/api/accountTypes/1",
                    expires: 0
                };
            }
        };

        //Create spies for the relevant service functions that are called in the addUser function.
        spyOn(mnyAccountTypeService, 'save').and.returnValue(deferred.promise);
        deferred.resolve(result);

        vm.addAccountType();

        //Need this to actually set the relevant values.
        $rootScope.$digest();

        expect(vm.accountTypeLocation).toBe("http://localhost:8080/api/accountTypes/1");
        expect($location.$$path).toBe("/accountTypes");
    });

    it('Should cancel the request', function () {
        vm.cancel();
        expect($location.$$path).toBe("/accountTypes");
    });

    it('Should create an AccountTypeDto object', function () {
        var result = vm.getAccountTypeDto(vm.currency);
        expect(result.name).toBe("Current");
    });
});
