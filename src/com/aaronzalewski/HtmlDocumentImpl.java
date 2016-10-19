package com.aaronzalewski;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HtmlDocumentImpl implements HtmlDocument {

    // Email regex from http://emailregex.com
    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)" +
        "*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])" +
        "*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|" +
        "\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|" +
        "[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|" +
        "\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    private String domain;
    private Document document;

    public static HtmlDocument createHtmlDocument(String url) {
        try {
            return new HtmlDocumentImpl(url);
        } catch (IOException | IllegalArgumentException e) {
            return new EmptyHtmlDocumentImpl();
        }
    }

    private HtmlDocumentImpl(String url) throws IOException {
        Connection connection = Jsoup.connect(url);
        this.document = connection.get();
        this.domain = getUrlDomain(url);
    }

    @Override
    public Set<String> getEmailAddresses() {
        ImmutableSet.Builder<String> emails = ImmutableSet.builder();

        Matcher matcher = Pattern.compile(EMAIL_REGEX).matcher(document.text().toLowerCase());
        while(matcher.find()) {
            emails.add(matcher.group());
        }

        return emails.build();
    }

    @Override
    public Set<String> getUrlsInDomain() {
        ImmutableSet.Builder<String> urls = ImmutableSet.builder();

        List<Element> linksInDomain =
                document.select("a[href*=" + domain + "]").stream()
                    .filter(link -> (hasDocumentDomain(link))).collect(Collectors.toList());

        for (Element link : linksInDomain) {
            String cleanedUrl = cleanUrlBeginning(link.attr("href"));
            if (UrlValidator.getInstance().isValid(cleanedUrl)) {
                urls.add(cleanedUrl);
            }
        }

        return urls.build();
    }

    private String getUrlDomain(String url) {
        try {
            return (new URI(url)).getHost();
        } catch (URISyntaxException e) {
            return "";
        }
    }

    private boolean hasDocumentDomain(Element link) {
        return domain.equals(getUrlDomain(link.attr("href")));
    }

    private String cleanUrlBeginning(String url) {
        if (url.indexOf("http://") == 0) {
            return url;
        }

        String urlEnd = url.substring(url.lastIndexOf(domain) + domain.length());
        return "http://" + domain + urlEnd;
    }

    private static class EmptyHtmlDocumentImpl implements HtmlDocument {

        @Override
        public Set<String> getEmailAddresses() {
            return ImmutableSet.of();
        }

        @Override
        public Set<String> getUrlsInDomain() {
            return ImmutableSet.of();
        }
    }

}
