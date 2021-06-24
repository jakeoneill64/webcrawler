package com.hyperglance.crawler.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CrawlRequest {

    private final String url;
    private final int depth;

}
