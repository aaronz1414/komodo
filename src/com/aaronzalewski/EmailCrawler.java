package com.aaronzalewski;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class EmailCrawler {

    private static Set<String> emailAddresses;

    public static void main(String[] args) {
        emailAddresses = new HashSet<>();

        breadthFirstSearchCrawl("http://" + args[0]);

        emailAddresses.forEach(System.out::println);
    }

    private static void breadthFirstSearchCrawl(String startUrl) {
        Queue<String> urlsToCrawl = new LinkedList<>();
        Set<String> crawledUrls = new HashSet<>();

        urlsToCrawl.add(startUrl);

        while (urlsToCrawl.size() > 0) {
            String currentUrl = urlsToCrawl.poll();
            if (!crawledUrls.contains(currentUrl)) {
                crawledUrls.add(currentUrl);
                HtmlDocument document = HtmlDocumentImpl.createHtmlDocument(currentUrl);
                urlsToCrawl.addAll(document.getUrlsInDomain());
                document.getEmailAddresses()
                        .forEach(email -> emailAddresses.add(email));
            }
        }
    }
}
