package com.hyperglance.crawler.service;

import com.hyperglance.crawler.model.CrawlRequest;
import com.hyperglance.crawler.repository.CachedMapRepository;
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
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
@Service
@Slf4j
public class CrawlerServiceImpl implements CrawlerService{

    private final CachedMapRepository<String, List<String>> crawlerRepository;
    private final Parser<Optional<String>, String> urlStripper;
    private final Parser<Optional<String>, Tuple<String, String>> urlConstructor;
    private final ExecutorService executorService;


    /**Recursively search for image and url tags
     * Do not search the same page twice -
     * for simplicity this class does not search the same page
     * if it has different query parameters ie. "?user=.."
     * @param crawlRequest
     */
    @Override
    public void crawl(CrawlRequest crawlRequest) {
            crawl(crawlRequest, new TreeSet<>());
            log.info("Full crawl completed for {}", crawlRequest.getUrl());
    }

    private void crawl(CrawlRequest crawlRequest, Set<String> visitedUrls){
        Optional<String> stripped = urlStripper.parse(crawlRequest.getUrl());
        if(stripped.isEmpty() || visitedUrls.contains(stripped.get()))
            return;
        List<String> imageUrls = new LinkedList<>();
        Optional<Document> page = fetchDocument(crawlRequest.getUrl());
        if(page.isEmpty())
            return;
        Elements images = page.get().select("img[src~=(?i)\\.(png|jpe?g|gif)]");
        images.forEach(image -> {
                    Optional<String> constructedImgUrl = urlConstructor
                            .parse(new Tuple<>(stripped.get(), image.attr("src")));
                    constructedImgUrl.ifPresent(imageUrls::add);
                });
        crawlerRepository.put(crawlRequest.getUrl(), imageUrls);
        visitedUrls.add(stripped.get());
        if(crawlRequest.getDepth() < 1)
            return;
        Elements links = page.get().select("a[href]");
        links.forEach(link ->{
            Optional<String> url = urlConstructor.parse(
                    new Tuple<>(crawlRequest.getUrl(), link.attr("href"))
            );
            if(url.isEmpty())
                return;
            CrawlRequest request = CrawlRequest.builder()
                    .depth(crawlRequest.getDepth() - 1)
                    .url(url.get())
                    .build();
            executorService.submit(() -> crawl(request, visitedUrls));
            log.info("completed crawl for {} at depth {}", request.getUrl(), crawlRequest.getDepth());
         }
        );
    }

    private Optional<Document> fetchDocument(String url){
        try {
            return Optional.of(Jsoup.connect(url).get());
        } catch (IOException e) {
            log.error("There was an issue reading the page at {}", url);
            return Optional.empty();
        }

    }

}
