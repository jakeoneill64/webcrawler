package com.hyperglance.crawler.controller;

import com.hyperglance.crawler.model.CrawlRequest;
import com.hyperglance.crawler.model.SyncRequest;
import com.hyperglance.crawler.repository.CrawlerRepository;
import com.hyperglance.crawler.service.CrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@RestController
@RequiredArgsConstructor
public class CrawlerController {

    private final CrawlerRepository crawlerRepository;
    private final CrawlerService crawlerService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @PostMapping("crawl")
    public void crawl(@RequestBody @Validated CrawlRequest request){
        crawlerService.crawl(request);
    }

    @GetMapping("inventory")
    public Map<String, List<String>> inventory(){
        return crawlerRepository.get();
    }

    @GetMapping("sync")
    public void sync(HttpServletRequest httpServletRequest){
        applicationEventPublisher.publishEvent(
                new SyncRequest(
                        httpServletRequest.getRemoteAddr(),
                        Instant.now()
                )
        );
    }

}
