package com.hyperglance.crawler.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CrawlRequest {

    private String url;
    private int depth;

}
