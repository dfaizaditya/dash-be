package com.kbbukopin.projectdash.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kbbukopin.projectdash.services.BatchService;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

    @Autowired
    private BatchService batchService;

    


    
    
}
