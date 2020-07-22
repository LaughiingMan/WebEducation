package com.diser.controller;

import com.diser.entity.*;
import com.diser.service.StatisticsService;
import com.diser.service.TestService;
import com.diser.service.impl.UserServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("profile")
public class ProfileController {

    private final UserServiceImpl userService;
    private final TestService testService;
    private final StatisticsService statisticsService;

    @Autowired
    public ProfileController(UserServiceImpl userService, TestService testService, StatisticsService statisticsService) {
        this.userService = userService;
        this.testService = testService;
        this.statisticsService = statisticsService;
    }

    @GetMapping("/all_users")
    @JsonView(Views.UserInfo.class)
    public List<User> findAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/all_tests")
    @JsonView(Views.UserInfo.class)
    public List<Test> findAllTests() {
        return testService.getAll();
    }

    @PostMapping("/id_test")
    @JsonView(Views.UserInfo.class)
    public Test findTest(@RequestBody Long id) {
        return testService.findTestById(id).orElse(null);
    }

    @PostMapping("/id_image")
    @JsonView(Views.UserInfo.class)
    public List<TestImage> findImages(@RequestBody Test test) {
        return testService.findImagesInTest(test);
    }

    @PostMapping("/id_audio")
    @JsonView(Views.UserInfo.class)
    public List<TestAudio> findAudios(@RequestBody Test test) {
        return testService.findAudiosInTest(test);
    }

    @GetMapping("/all_statistics")
    @JsonView(Views.UserInfo.class)
    public List<Statistics> findStatistics() {
        return statisticsService.findAll();
    }

    @PostMapping("/save_tests")
    public void saveTest(@RequestBody ResultTest resultTest, @AuthenticationPrincipal User user) {
        Test test = resultTest.getTest();
        List<TestImage> image = resultTest.getImage();
        List<TestAudio> audio = resultTest.getAudio();

        test.setAuthor(user);
        testService.saveTest(test);

        if (image != null && !image.isEmpty()) {
            image.forEach(item -> item.setTest(test));
            testService.saveImages(image);
        }

        if (audio != null && !audio.isEmpty()) {
            audio.forEach(item -> item.setTest(test));
            testService.saveAudios(audio);
        }
    }

    @PostMapping("/save_statistics")
    public void saveStatistics(@RequestBody Statistics statistics, @AuthenticationPrincipal User user) {
        statistics.setUser(user);
        statisticsService.saveStatistics(statistics);
    }
}
