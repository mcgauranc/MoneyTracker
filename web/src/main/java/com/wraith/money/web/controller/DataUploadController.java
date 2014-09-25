package com.wraith.money.web.controller;

import com.wraith.money.data.DataUpload;
import com.wraith.money.data.DataUploadMapping;
import com.wraith.money.web.service.DataUploadService;
import org.springframework.batch.core.BatchStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;

/**
 * This controller manages the data upload request functionality.
 * <p/>
 * User: rowan.massey
 * Date: 26/08/2014
 * Time: 21:02
 */
@Controller
public class DataUploadController {

    @Inject
    private DataUploadService dataUploadService;

    @RequestMapping(value = "/$service/dataUpload", method = RequestMethod.POST)
    public void saveDataUpload(@RequestBody DataUpload dataUpload) {
    }

    @RequestMapping(value = "/$service/dataUpload/{dataUploadId}", method = RequestMethod.GET)
    public void getDataUpload(@PathVariable("dataUploadId") String dataUploadId) {
    }

    @RequestMapping(value = "/$service/dataUploads", method = RequestMethod.GET)
    public void getDataUploads() {
    }

    @RequestMapping(value = "/$service/dataUploadMapping", method = RequestMethod.POST)
    public void saveDataUploadMapping(@RequestBody DataUploadMapping dataUploadMapping) {
    }

    @RequestMapping(value = "/$service/dataUploadMapping/{dataUploadMappingId}", method = RequestMethod.GET)
    public void getDataUploadMapping(@PathVariable("dataUploadMappingId") String dataUploadMappingId) {
    }

    @RequestMapping(value = "/$service/performDataUpload", method = RequestMethod.POST)
    @ResponseBody
    public BatchStatus performDataUpload(@RequestParam("file") MultipartFile file, @RequestParam("uploadType") String uploadType) {
        return dataUploadService.performUploadData(file, uploadType).getStatus();
    }
}
