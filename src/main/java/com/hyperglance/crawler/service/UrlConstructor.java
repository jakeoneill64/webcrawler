package com.hyperglance.crawler.service;

import com.hyperglance.crawler.support.Parser;
import com.hyperglance.crawler.support.Tuple;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UrlConstructor implements Parser<Optional<String>, Tuple<String, String>> {

    private final String URL_BASE_SCRAPPER = "(https?:\\/\\/.+?)\\/";


    /**Create a full url from the base url
     * and the partial url from href / src tags
     *
     * @param raw
     * @return
     */
    @Override
    public Optional<String> parse(Tuple<String, String> raw) {
        Matcher baseMatcher = Pattern.compile(URL_BASE_SCRAPPER).matcher(raw.getOne());
        if(!baseMatcher.find())
            return Optional.empty();
        String toTransform = raw.getTwo();
        if(toTransform.startsWith("http"))
            return Optional.of(toTransform);
        if(toTransform.startsWith("//"))
            return Optional.of("http:" +  toTransform);
        if(toTransform.startsWith("/"))
            return Optional.of(baseMatcher.group(1).concat(toTransform));
        return Optional.empty();
    }


}
