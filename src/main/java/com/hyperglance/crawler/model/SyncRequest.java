package com.hyperglance.crawler.model;

import org.springframework.context.ApplicationEvent;

import java.time.Instant;

public class SyncRequest extends ApplicationEvent {

    private String ip;
    private Instant timestamp;

    public SyncRequest(
            String ip,
            Instant timestamp) {
        super(ip);
    }

}
