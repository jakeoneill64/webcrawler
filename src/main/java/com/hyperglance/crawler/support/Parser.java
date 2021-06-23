package com.hyperglance.crawler.support;

public interface Parser <S, T>{

    S parse(T raw);

}
