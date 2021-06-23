package com.hyperglance.crawler.repository;

import java.util.Map;

public interface CachedMapRepository<S, T>{

    Map<S, T> get();
    void put(S key, T value);
    void sync();

}
