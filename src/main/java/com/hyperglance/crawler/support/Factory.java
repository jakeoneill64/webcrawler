package com.hyperglance.crawler.support;

public interface Factory <S, T>{

    S get(T qualifier);

}
