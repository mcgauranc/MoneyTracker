/**
 *
 * User: rowan.massey
 * Date: 02/01/2015
 *
 */
moneyApp.service("mnyDataUploadService", ['$http', '$q', function ($http, $q) {

    var dataUploadService = this;

    /**
     * This method saves a new instance of a data upload to the database.
     *
     * @param dataUpload The data upload object that the user wants saved to the database.
     * @returns {promise.promise|jQuery.promise|jQuery.ready.promise}
     */
    dataUploadService.save = function (dataUpload) {
        var deferred = $q.defer();
        $http.post("api/dataUpload", dataUpload).then(function (data) {
            deferred.resolve(data);
        }, function (error) {
            console.log("There was an error saving the data upload." + error);
            deferred.reject(error);
        });
        return deferred.promise;
    };

    /**
     * This method deletes the record, for the given location.
     *
     * @param location
     * @returns {promise.promise|jQuery.promise|jQuery.ready.promise}
     */
    dataUploadService.remove = function (location) {
        var deferred = $q.defer();
        $http.delete(location).then(function (data) {
            deferred.resolve(data);
        }, function (error) {
            console.log("There was an error deleting the data upload." + error);
            deferred.reject(error);
        });
        return deferred.promise;

    };

    /**
     * This method returns all of the data upload records from the database.
     *
     * @returns {promise.promise|jQuery.promise|jQuery.ready.promise}
     */
    userService.getAllDataUploads = function () {
        var deferred = $q.defer();
        $http.get("api/dataUpload").then(function (data) {
            deferred.resolve(data.data._embedded.dataUpload);
        }, function (error) {
            console.log("There was an error getting all the data uploads." + error);
            deferred.reject(error);
        });
        return deferred.promise;
    }
}]);
