package com.diser.repository;

import com.diser.entity.Test;
import com.diser.entity.TestAudio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestAudioRepository extends JpaRepository<TestAudio, Long> {

    List<TestAudio> findAllByTest(Test test);
}
