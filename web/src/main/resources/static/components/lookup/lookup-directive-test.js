describe("Directive: mnyLookup", function () {
    var $compile,
        $rootScope,
        $timeout,
        $httpBackend,
        $q,
        element;

    beforeEach(module("moneyApp"));

    beforeEach(module("templates"));

    beforeEach(inject(function (_$rootScope_, _$compile_, _$timeout_, _$httpBackend_, _$q_) {
        $compile = _$compile_;
        $rootScope = _$rootScope_;
        $timeout = _$timeout_;
        $httpBackend = _$httpBackend_;
        $q = _$q_;

        $rootScope.findUsers = function () {
            return [{
                "firstName": "First",
                "lastName": "User"
            }, {
                "firstName": "Second",
                "lastName": "User"
            }];
        };

        element = angular.element('<mny-lookup lookup-datasource="findUsers()" lookup-value="[\'firstName\', \'lastName\']" lookup-id="id"></mny-lookup>');
        $compile(element)($rootScope.$new());
    }));

    it("Contains the relevant isolated scope variables, which have been correctly set", function () {
        $rootScope.$digest();
        expect(element.isolateScope().lookupDatasource).toBeDefined();
        expect(element.isolateScope().lookupValue.length).toBe(2);
        expect(element.isolateScope().lookupValue[0]).toBe("firstName");
        expect(element.isolateScope().lookupValue[1]).toBe("lastName");
        expect(element.isolateScope().lookupId).toBe("id");
    });

    it("Replaces the element with the appropriate text for lookups", function () {
        $rootScope.$digest();
        expect(element.find("input").attr("type")).toEqual('text');
    });

    it("Contains the relevant model attribute", function () {
        $rootScope.$digest();
        expect(element.find("input").attr("ng-model")).toEqual('searchTerm');
    });

    it("Contains the relevant keyup attribute", function () {
        $rootScope.$digest();
        expect(element.find("input").attr("ng-keyup")).toEqual('search()');
    });

    it("Contains the relevant angular class attribute values", function () {
        $rootScope.$digest();
        expect(element.find("input").attr("class")).toEqual('ng-pristine ng-untouched ng-valid');
    });

    it("Performs search on key up", function () {
        $rootScope.$digest();
        element.find("input").triggerHandler("keyup");
        expect(element.find("li").length).toEqual(2);
    });

});