package com.diser.service.impl;

import com.diser.entity.Statistics;
import com.diser.repository.StatisticsRepository;
import com.diser.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepository statisticsRepository;

    @Autowired
    public StatisticsServiceImpl(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    @Override
    public void saveStatistics(Statistics statistics) {
        statisticsRepository.save(statistics);
    }

    @Override
    public List<Statistics> findAll() {
        return statisticsRepository.findAll();
    }
}
