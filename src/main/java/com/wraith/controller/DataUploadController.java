package com.wraith.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This controller manages the data upload request functionality.
 * <p>
 * User: rowan.massey
 * Date: 26/08/2014
 * Time: 21:02
 */
@Controller
public class DataUploadController {

    @RequestMapping(value = "/$service/dataUpload", method = RequestMethod.GET)
    public String getUploadedData(Model model) {
        return "Data Uploaded";
    }

    @RequestMapping(value = "/$service/dataUpload", method = RequestMethod.POST)
    public void uploadData(Model model) {

    }
}
