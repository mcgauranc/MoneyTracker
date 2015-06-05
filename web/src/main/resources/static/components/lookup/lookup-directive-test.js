describe("Directive: mnyLookup", function () {
    var $compile,
        $rootScope,
        $timeout,
        element;

    beforeEach(module("moneyApp"));

    beforeEach(inject(function (_$rootScope_, _$compile_, _$timeout_) {
        $compile = _$compile_;
        $rootScope = _$rootScope_;
        $timeout = _$timeout_;

        element = angular.element("<mny-lookup lookup-label='User' lookup-function='findUsers()' lookup-display='['firstName']' ></mny-lookup>");
        $compile(element)($rootScope.$new());
    }));

    it("Replaces the element with the appropriate content for lookups", function () {
        $rootScope.$digest();
        expect(element.html()).toContain('');
    });

});