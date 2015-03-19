/**
 *
 * User: rowan.massey
 * Date: 17/03/2015
 * Time: 16:14
 */

describe('UserService', function () {
    beforeEach(module('moneyApp'));

    var $injector,
        $httpBackend,
        $q,
        deferred,
        mnyUserService,
        user,
        result;


    beforeEach(inject(function (_$injector_, _$httpBackend_, _$q_) {
        $injector = _$injector_;
        $httpBackend = _$httpBackend_;
        $q = _$q_;

        deferred = $q.defer();

        mnyUserService = $injector.get("mnyUserService");

        user = {
            "userName": "Admin",
            "password": "Passw0rd",
            "firstName": "Admin",
            "lastName": "User",
            "dateOfBirth": "01/01/2001"
        };
    }));

    it('Should see if a User Exists', function () {
        $httpBackend.whenGET("api/users/search/existsByUserName?userName=Admin").respond({
            "_embedded": {
                "users": [{
                    "userName": "Admin",
                    "password": "Passw0rd",
                    "firstName": "Admin",
                    "lastName": "User",
                    "dateOfBirth": "2015-03-17T17:32:13Z",
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/users/1"
                        },
                        "address": {
                            "href": "http://localhost:8080/api/users/1/address"
                        },
                        "groups": {
                            "href": "http://localhost:8080/api/users/1/groups"
                        },
                        "accounts": {
                            "href": "http://localhost:8080/api/users/1/accounts"
                        }
                    }
                }]
            }
        });
        mnyUserService.userExists("Admin").then(function (data) {
            result = data;
        });
        $httpBackend.flush();

        expect(result).toBeDefined();
        expect(result.data._embedded.users[0].userName).toBe("Admin");
        expect(result.data._embedded.users[0].password).toBe("Passw0rd");
        expect(result.data._embedded.users[0].firstName).toBe("Admin");
        expect(result.data._embedded.users[0].lastName).toBe("User");
        expect(result.data._embedded.users[0].dateOfBirth).toBe("2015-03-17T17:32:13Z");
    });

    it('Should Save a New User', function () {
        $httpBackend.whenPOST("api/users").respond({
            data: {
                headers: function () {
                    return {
                        location: "http://localhost/user/1",
                        expires: 0
                    };
                }
            }
        });
        mnyUserService.save(user).then(function (data) {
            result = data;
        });
        $httpBackend.flush();

        expect(result).toBeDefined();
        expect(result.data.data.headers().location).toBe("http://localhost/user/1");
    });

    it('Should remove an Existing User', function () {
        $httpBackend.whenDELETE("api/users/1").respond({
            data: {
                status: "201"
            }
        });

        mnyUserService.remove("api/users/1").then(function (data) {
            result = data;
        });
        $httpBackend.flush();

        expect(result).toBeDefined();
        expect(result.config.method).toBe("DELETE");
        expect(result.status).toBe(200);
    });

    it('Should Get All Users', function () {
        $httpBackend.whenGET("api/users").respond({
            "_links": {
                "self": {
                    "href": "http://localhost:8080/api/users{?page,size,sort}",
                    "templated": true
                },
                "search": {
                    "href": "http://localhost:8080/api/users/search"
                }
            },
            "_embedded": {
                "users": [{
                    "userName": "Admin",
                    "password": "Passw0rd",
                    "firstName": "Admin",
                    "lastName": "User",
                    "dateOfBirth": "2015-03-17T18:49:41Z",
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/users/1"
                        },
                        "accounts": {
                            "href": "http://localhost:8080/api/users/1/accounts"
                        },
                        "address": {
                            "href": "http://localhost:8080/api/users/1/address"
                        },
                        "groups": {
                            "href": "http://localhost:8080/api/users/1/groups"
                        }
                    }
                },
                    {
                        "userName": "Admin1",
                        "password": "Passw0rd",
                        "firstName": "Admin1",
                        "lastName": "User",
                        "dateOfBirth": "2015-03-17T18:49:41Z",
                        "_links": {
                            "self": {
                                "href": "http://localhost:8080/api/users/2"
                            },
                            "accounts": {
                                "href": "http://localhost:8080/api/users/2/accounts"
                            },
                            "address": {
                                "href": "http://localhost:8080/api/users/2/address"
                            },
                            "groups": {
                                "href": "http://localhost:8080/api/users/2/groups"
                            }
                        }
                    }]
            },
            "page": {
                "size": 20,
                "totalElements": 2,
                "totalPages": 1,
                "number": 0
            }
        });

        mnyUserService.getAllUsers("api/users").then(function (data) {
            result = data;
        });
        $httpBackend.flush();

        expect(result).toBeDefined();
        expect(result.length).toBeGreaterThan(1);
        expect(result[0].firstName).toBe("Admin");
        expect(result[0].lastName).toBe("User");
        expect(result[0].password).toBe("Passw0rd");
        expect(result[0].userName).toBe("Admin");
        expect(result[0]._links.accounts.href).toBe("http://localhost:8080/api/users/1/accounts");
        expect(result[0]._links.address.href).toBe("http://localhost:8080/api/users/1/address");
        expect(result[0]._links.groups.href).toBe("http://localhost:8080/api/users/1/groups");

        expect(result[1].firstName).toBe("Admin1");
        expect(result[1].lastName).toBe("User");
        expect(result[1].password).toBe("Passw0rd");
        expect(result[1].userName).toBe("Admin1");
        expect(result[1]._links.accounts.href).toBe("http://localhost:8080/api/users/2/accounts");
        expect(result[1]._links.address.href).toBe("http://localhost:8080/api/users/2/address");
        expect(result[1]._links.groups.href).toBe("http://localhost:8080/api/users/2/groups");
    });

    it('Should Get a User', function () {
        $httpBackend.whenGET("api/users/2").respond({
            "userName": "Admin",
            "password": "Passw0rd",
            "firstName": "Admin",
            "lastName": "User",
            "dateOfBirth": "2015-03-19T15:47:55Z",
            "_links": {
                "self": {
                    "href": "http://localhost:8080/api/users/1"
                },
                "address": {
                    "href": "http://localhost:8080/api/users/1/address"
                },
                "groups": {
                    "href": "http://localhost:8080/api/users/1/groups"
                },
                "accounts": {
                    "href": "http://localhost:8080/api/users/1/accounts"
                }
            }
        });
        mnyUserService.getUser("api/users/2").then(function (data) {
            result = data;
        });
        $httpBackend.flush();

        expect(result).toBeDefined();
        expect(result.userName).toBe("Admin");
        expect(result.password).toBe("Passw0rd");
        expect(result.firstName).toBe("Admin");
        expect(result.lastName).toBe("User");
        expect(result.dateOfBirth.toString()).toBe("Thu Mar 19 2015 15:47:55 GMT+0000 (GMT Standard Time)");
    });

});

