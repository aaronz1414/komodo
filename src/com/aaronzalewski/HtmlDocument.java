package com.aaronzalewski;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

public class HtmlDocument {

    private String url;
    private String domain;
    private Document document;

    public HtmlDocument(String url, String domain) {
        this.url = url;
        this.domain = domain;
        try {
            this.document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getEmailAddresses() {
        return null;
    }

    public List<String> getUrlsInDomain() {
        List<Element> links = document.select("a[href*=" + domain + "]");

        return null;
    }

}
