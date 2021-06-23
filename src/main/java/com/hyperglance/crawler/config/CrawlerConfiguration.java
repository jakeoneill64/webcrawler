package com.hyperglance.crawler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class CrawlerConfiguration {

    @Bean
    public ExecutorService executorService(@Value("${thread-count}") int threads){
        return Executors.newFixedThreadPool(threads);
    }


}
