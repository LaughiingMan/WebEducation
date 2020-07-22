package com.diser.service.impl;

import com.diser.entity.Test;
import com.diser.entity.TestAudio;
import com.diser.entity.TestImage;
import com.diser.repository.TestAudioRepository;
import com.diser.repository.TestImageRepository;
import com.diser.repository.TestRepository;
import com.diser.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final TestImageRepository testImageRepository;
    private final TestAudioRepository testAudioRepository;

    @Autowired
    public TestServiceImpl(TestRepository testRepository, TestImageRepository testImageRepository, TestAudioRepository testAudioRepository) {
        this.testRepository = testRepository;
        this.testImageRepository = testImageRepository;
        this.testAudioRepository = testAudioRepository;
    }

    @Override
    public List<Test> getAll() {
        return testRepository.findAll();
    }

    @Override
    public Optional<Test> findTestById(Long id) { return testRepository.findById(id); }

    @Override
    public void saveTest(Test test) {
        testRepository.save(test);
    }

    @Override
    public void saveImages(Collection<TestImage> images) {
        testImageRepository.saveAll(images);
    }

    @Override
    public void saveAudios(Collection<TestAudio> audios) {
        testAudioRepository.saveAll(audios);
    }

    @Override
    @Transactional
    public List<TestImage> findImagesInTest(Test test) { return testImageRepository.findAllByTest(test); }

    @Override
    @Transactional
    public List<TestAudio> findAudiosInTest(Test test) { return testAudioRepository.findAllByTest(test); }
}
