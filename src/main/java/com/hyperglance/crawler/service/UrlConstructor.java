package com.hyperglance.crawler.service;

import com.hyperglance.crawler.support.Parser;
import com.hyperglance.crawler.support.Tuple;

import java.util.Optional;

public class UrlConstructor implements Parser<Optional<String>, Tuple<String, String>> {

    @Override
    public Optional<String> parse(Tuple<String, String> raw) {
        String toTransform = raw.getTwo();
        if(toTransform.startsWith("http"))
            return Optional.of(toTransform);
        if(toTransform.startsWith("//"))
            return Optional.of("http:" +  toTransform);
        if(toTransform.startsWith("/"))
            return Optional.of(raw.getOne().concat(toTransform));
        return Optional.empty();
    }


}
