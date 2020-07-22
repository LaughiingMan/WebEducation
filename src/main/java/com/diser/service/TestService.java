package com.diser.service;

import com.diser.entity.Test;
import com.diser.entity.TestAudio;
import com.diser.entity.TestImage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TestService {
    List<Test> getAll();

    Optional<Test> findTestById(Long id);

    void saveTest(Test test);

    void saveImages(Collection<TestImage> images);

    void saveAudios(Collection<TestAudio> audios);

    List<TestImage> findImagesInTest(Test test);

    List<TestAudio> findAudiosInTest(Test test);
}
