package com.kbbukopin.projectdash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kbbukopin.projectdash.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

}
