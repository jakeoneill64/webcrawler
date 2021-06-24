package com.hyperglance.crawler.service;

import com.hyperglance.crawler.support.Parser;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
public class UrlStripper implements Parser<Optional<String>, String> {

    private final String URL_WITH_QUERY_PATTERN = "(https?:\\/\\/.+?)([?#])";
    private final String URL_BASIC_PATTERN = "(https?:\\/\\/.+)";

    @Override
    public Optional<String> parse(String raw) {
        Matcher urlWithQueryMatcher =
                Pattern.compile(URL_WITH_QUERY_PATTERN).matcher(raw);

        if (urlWithQueryMatcher.find())
            return Optional.of(urlWithQueryMatcher.group(1));

        Matcher urlBasic =
                Pattern.compile(URL_BASIC_PATTERN).matcher(raw);
        if (urlBasic.find())
            return Optional.of(urlBasic.group());
        return Optional.empty();
    }
}
