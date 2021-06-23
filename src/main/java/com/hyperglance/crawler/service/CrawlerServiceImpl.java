package com.hyperglance.crawler.service;

import com.hyperglance.crawler.model.CrawlRequest;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class CrawlerServiceImpl implements CrawlerService{

    @Override
    public void crawl(CrawlRequest crawlRequest) {
        Document page;
        try {
            page = Jsoup.connect(crawlRequest.getUrl()).get();
        } catch (IOException e) {
            log.error("There was an issue fetching the page at {}", crawlRequest.getUrl());
            return;
        }
        Elements images =
                page.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
        for(Element element: images)
        System.out.println(element.attr("src"));
    }

}
