/**
 * This service deals with the setting up of relationships between two entities.
 *
 */

moneyApp.service("mnyRelationshipService", ["$http", "$q", function ($http, $q) {

    var relationshipService = this;

    /**
     * This method associates the given child location, with the given parent location to allow for relationship definition.
     *
     * @param parentLocation The location of the saved parent record.
     * @param childEntity The actual name of the child entity, this is used to build up the PUT request.
     * @param childLocation The location of the saved child record.
     * @returns {*|promise}
     */
    relationshipService.associate = function (parentLocation, childEntity, childLocation) {
        //TODO: Determine if there is a way to infer the child entity name from the child location.
        var deferred = $q.defer();
        var request = {
            method: "PUT",
            url: parentLocation + "/" + childEntity,
            headers: {
                "Content-Type": "text/uri-list"
            },
            data: childLocation
        };
        $http(request).then(function (data) {
            deferred.resolve(data);
        }, function (error) {
            deferred.reject(error);
            throw new Error("There was an error associating child record " + childLocation + " with parent record " + parentLocation + ": " + error.message);
        });
        return deferred.promise;
    };
}]);

