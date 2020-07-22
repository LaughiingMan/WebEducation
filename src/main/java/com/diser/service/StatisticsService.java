package com.diser.service;

import com.diser.entity.Statistics;

import java.util.List;

public interface StatisticsService {

    void saveStatistics(Statistics statistics);

    List<Statistics> findAll();
}
