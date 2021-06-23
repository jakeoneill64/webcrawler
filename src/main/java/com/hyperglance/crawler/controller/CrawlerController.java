package com.hyperglance.crawler.controller;

import com.hyperglance.crawler.model.CrawlRequest;
import com.hyperglance.crawler.repository.CrawlerRepository;
import com.hyperglance.crawler.service.CrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@RestController
@RequiredArgsConstructor
public class CrawlerController {

    private final CrawlerRepository crawlerRepository;
    private final CrawlerService crawlerService;
    private final ExecutorService executorService;

    @PostMapping("crawl")
    public void crawl(@RequestBody @Validated CrawlRequest request){
        executorService.submit(() -> crawlerService.crawl(request));
    }

    @GetMapping("inventory")
    public Map<String, List<String>> inventory(){
        return crawlerRepository.get();
    }

}
