package com.hyperglance.crawler.repository;

import com.hyperglance.crawler.model.SyncRequest;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CrawlerRepository implements CachedMapRepository<String, List<String>>{

    private final Map<String, List<String>> cache =
            Collections.synchronizedMap(new HashMap<>());

    @Override
    public Map<String, List<String>> get() {
        return cache;
    }

    @Override
    public void put(String key, List<String> value) {
        cache.put(key, value);
    }

    @EventListener(SyncRequest.class)
    private void sync(){
        int x = 0;
    }
}
