package ru.iac.hakaton.neirostorm.util;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleImageSearch {


    public static String getImageURLByQuery(String query)  {
        String url = "https://yandex.ru/images/search?text=" + query;

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();

        }
        Elements images = doc.select("img.serp-item__thumb");

        for (Element image : images) {
            String imageUrl = image.attr("src");
            return imageUrl;
            // System.out.println(imageUrl);
        }

        return null;
    }
}