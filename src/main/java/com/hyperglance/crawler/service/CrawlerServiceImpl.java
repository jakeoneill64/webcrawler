package com.hyperglance.crawler.service;

import com.hyperglance.crawler.model.CrawlRequest;
import com.hyperglance.crawler.repository.CachedMapRepository;
import com.hyperglance.crawler.support.CrawlException;
import com.hyperglance.crawler.support.Parser;
import com.hyperglance.crawler.support.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.*;
import java.io.IOException;

@RequiredArgsConstructor
@Service
@Slf4j
public class CrawlerServiceImpl implements CrawlerService{

    private final CachedMapRepository<String, List<String>> crawlerRepository;
    private final Parser<Optional<String>, String> urlStripper;
    private final Parser<Optional<String>, Tuple<String, String>> urlConstructor;

    @Override
    public void crawl(CrawlRequest crawlRequest) {
        try {
            crawl(crawlRequest, new TreeSet<>());
        }catch(CrawlException e){
            log.error("could not perform crawl request for {}", crawlRequest.getUrl());
        }
    }

    private void crawl(CrawlRequest crawlRequest, Set<String> visitedUrls) throws CrawlException {
        String stripped = urlStripper.parse(crawlRequest.getUrl()).orElseThrow(CrawlException::new);
        if(visitedUrls.contains(stripped))
            return;
        List<String> imageUrls = new LinkedList<>();

        Document page = fetchDocument(stripped).orElseThrow(CrawlException::new);
        Elements images = page.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
        images.forEach(image -> imageUrls.add(image.attr("src")));
        crawlerRepository.put(crawlRequest.getUrl(), imageUrls);

        if(crawlRequest.getDepth() < 1)
            return;
        Elements links = page.select("a[href]");
        links.forEach(link ->{
            String url = urlConstructor.parse(
                    new Tuple<>(stripped, link.attr("href"))
            ).get();
            crawl(CrawlRequest.builder()
                    .depth(crawlRequest.getDepth() - 1)
                    .url(url)
                    .build());}
        );
    }

    private Optional<Document> fetchDocument(String url){
        try {
            return Optional.of(Jsoup.connect(url).get());
        } catch (IOException e) {
            log.error("There was an issue fetching the page at {}", url);
            return Optional.empty();
        }

    }

}
