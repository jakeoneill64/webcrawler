package com.hyperglance.crawler.support;

import lombok.Getter;

@Getter
public class Tuple <S, T>{

    private final S one;
    private final T two;

    public Tuple(S one, T two){
        this.one = one;
        this.two = two;
    }

}
