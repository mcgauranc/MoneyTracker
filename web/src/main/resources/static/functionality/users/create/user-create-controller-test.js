describe('UserCreateController', function () {
    beforeEach(module('moneyApp'));

    var controller, mnyUserService;

    beforeEach(inject(function ($controller, MnyUserService) {
        spyOn(MnyUserService, 'save').andCallThrough();
        mnyUserService = MnyUserService;
        controller = $controller(UserCreateController);
    }));

    it('should have called save user', function () {
        expect(mnyUserService.save).toHaveBeenCalled();
    })
});