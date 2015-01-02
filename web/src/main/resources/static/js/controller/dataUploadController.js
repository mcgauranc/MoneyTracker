/**
 *
 * User: rowan.massey
 * Date: 26/09/2014
 */

"use strict";

/* Controllers */

moneyApp.controller("DataUploadController", ["$scope", "mnyDataUploadService",
    function ($scope, mnyDataUploadService) {

        var dataUploadController = $scope.dataUploadController = {};

        dataUploadController.dataUpload = {};
        dataUploadController.listOfEntities = [];
        dataUploadController.selectedEntity = "";
        dataUploadController.listOfFieldsForEntity = [];

        dataUploadController.save = function () {
        };

        dataUploadController.getListOfEntities = function () {
            mnyDataUploadService.getListOfEntities().then(function (entityData) {
                dataUploadController.listOfEntities = entityData;
            }, function (error) {
                console.log("There was an error getting all the entities: " + error);
            })
        };

        dataUploadController.getListOfFieldsForEntity = function () {
            if (dataUploadController.selectedEntity != "") {
                mnyDataUploadService.getEntitySchema(dataUploadController.selectedEntity).then(function (fieldData) {
                    dataUploadController.listOfFieldsForEntity = fieldData
                }, function (error) {
                    console.log("There was an error getting all the entities: " + error);
                })
            }
        };

        dataUploadController.refresh = function () {
            this.getListOfEntities();
        };
        ////dataUploadController.all = Restangular.all('dataUploads');
        //dataUploadController.currentPage = 0;
        //dataUploadController.pages = 0;
        //dataUploadController.current = {};
        //dataUploadController.location = $location;
        //dataUploadController.pageSize = 5;
        //
        //dataUploadController.refresh = function () {
        //    //dataUploadController.all.getList({"size": dataUploadController.pageSize, "page": dataUploadController.currentPage, "sort": ""}).then(function (dataUploads) {
        //    //    $scope.dataUploads = dataUploads;
        //    //    dataUploadController.pages = dataUploads.page.totalPages;
        //    //}, function (error) {
        //    //    dataUploadController.location.path('/');
        //    //});
        //};
        //
        //dataUploadController.nextPage = function () {
        //    //if (dataUploadController.currentPage + 1 < dataUploadController.pages) {
        //    //    dataUploadController.currentPage = dataUploadController.currentPage + 1;
        //    //    dataUploadController.refresh();
        //    //}
        //};
        //
        //dataUploadController.previousPage = function () {
        //    //if (dataUploadController.currentPage > 0) {
        //    //    dataUploadController.currentPage = dataUploadController.currentPage - 1;
        //    //    dataUploadController.refresh();
        //    //}
        //};
        //
        //dataUploadController.save = function () {
        //    //dataUploadController.pageSize = 5;
        //    //dataUploadController.all.post(dataUploadController.current).then(function (result) {
        //    //    dataUploadController.current = {};
        //    //    dataUploadController.refresh();
        //    //});
        //};
        //
        //dataUploadController.newDataUpload = function () {
        //    dataUploadController.currentPage = 0;
        //    dataUploadController.pageSize = 4;
        //    dataUploadController.refresh();
        //    dataUploadController.current.dataupload_description = '';
        //};
        //
        //dataUploadController.cancel = function () {
        //    dataUploadController.pageSize = 5;
        //    dataUploadController.current = {};
        //    dataUploadController.refresh();
        //};
        //
        //dataUploadController.edit = function (dataUpload, event) {
        //    dataUploadController.current = dataUpload;
        //};
        //
        //dataUploadController.remove = function (dataUpload) {
        //    //dataUploads.remove().then(function (result) {
        //    //    dataUploadController.refresh();
        //    //});
        //};
        //
        //dataUploadController.refresh();
    }]);