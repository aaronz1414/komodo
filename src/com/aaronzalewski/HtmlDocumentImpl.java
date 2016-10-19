package com.aaronzalewski;

import com.google.common.collect.ImmutableList;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlDocumentImpl implements HtmlDocument {

    private static final String EMAIL_REGEX = "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+";

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
    public List<String> getEmailAddresses() {
        ImmutableList.Builder<String> emails = ImmutableList.builder();

        Matcher matcher = Pattern.compile(EMAIL_REGEX).matcher(document.text());
        while(matcher.find()) {
            emails.add(matcher.group());
        }

        return emails.build();
    }

    @Override
    public List<String> getUrlsInDomain() {
        ImmutableList.Builder<String> links = ImmutableList.builder();
        document.select("a[href*=" + domain + "]").stream()
                .filter(link -> (hasDocumentDomain(link)))
                .forEach(link -> links.add(link.attr("href")));

        return links.build();
    }

    private String getUrlDomain(String url) {
        try {
            return (new URI(url)).getHost();
        } catch (URISyntaxException e) {
            return "";
        }
    }

    private boolean hasDocumentDomain(Element link) {
        return domain.equals(getUrlDomain(link.attr("href"))) ||
                ("www." + domain).equals(getUrlDomain(link.attr("href")));
    }

    private static class EmptyHtmlDocumentImpl implements HtmlDocument {

        @Override
        public List<String> getEmailAddresses() {
            return ImmutableList.of();
        }

        @Override
        public List<String> getUrlsInDomain() {
            return ImmutableList.of();
        }
    }

}
