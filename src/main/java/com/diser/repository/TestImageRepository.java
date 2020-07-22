package com.diser.repository;


import com.diser.entity.Test;
import com.diser.entity.TestImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestImageRepository extends JpaRepository<TestImage, Long> {

    List<TestImage> findAllByTest(Test test);
}
