package com.hyperglance.crawler.controller;

import com.hyperglance.crawler.model.CrawlRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
public class CrawlerController {

    private final

    @PostMapping("crawl")
    public void crawl(@RequestBody @Validated CrawlRequest request){

    }

    @GetMapping("inventory")
    public Map<String, List<String>> inventory(){
        return null;
    }

}
