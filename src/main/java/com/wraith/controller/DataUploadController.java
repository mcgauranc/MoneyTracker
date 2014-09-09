package com.wraith.controller;

import com.wraith.service.DataUploadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * This controller manages the data upload request functionality.
 * <p>
 * User: rowan.massey
 * Date: 26/08/2014
 * Time: 21:02
 */
@Controller
public class DataUploadController {

    @Inject
    DataUploadService dataUploadService;

    @RequestMapping(value = "/$service/dataUpload", method = RequestMethod.GET)
    public String getUploadedData(Model model) {
        return "dataupload";
    }

    @RequestMapping(value = "/$service/dataUpload", method = RequestMethod.POST)
    public void uploadData(Model model) {

    }
}
