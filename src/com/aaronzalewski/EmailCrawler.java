package com.aaronzalewski;

import java.util.List;
import java.util.Queue;
import java.util.Set;

public class EmailCrawler {

    private static String domain;
    private static HtmlDocument document;

    private static List<String> emailAddresses;
    private static Queue<String> urlsToCrawl;
    private static Set<String> crawledUrls;

    public static void main(String[] args) {
        domain = args[0];
        document = new HtmlDocument("http://" + domain, domain);
    }

    public static String getDomain() {
        return domain;
    }
}
