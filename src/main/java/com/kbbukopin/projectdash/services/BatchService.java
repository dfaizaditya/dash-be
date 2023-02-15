package com.kbbukopin.projectdash.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kbbukopin.projectdash.repository.ProjectRepository;

@Service
public class BatchService {

    @Autowired
    private ProjectRepository projectRepository;
}
