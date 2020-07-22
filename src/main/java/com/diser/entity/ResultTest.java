package com.diser.entity;

import java.util.List;

public class ResultTest {
    private Test test;
    private List<TestImage> image;
    private List<TestAudio> audio;

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public List<TestImage> getImage() {
        return image;
    }

    public void setImage(List<TestImage> image) {
        this.image = image;
    }

    public List<TestAudio> getAudio() {
        return audio;
    }

    public void setAudio(List<TestAudio> audio) {
        this.audio = audio;
    }
}
