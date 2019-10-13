package com.example.stackoverflow.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlUilts {
    public static String stripHtml(String content) {
        content = content.replaceAll("\r\n", "").replaceAll("\n", "").replaceAll("\r","");
        Document doc = Jsoup.parse(content);
        Elements code = doc.getElementsByTag("code");
        Elements links = doc.select("a[href]");
        for (Element e:code){
            content = content.replace(e.text(), "");
        }
        for (Element link:links){
            content = content.replace(link.text(), "");
        }
        content = content.replaceAll("\\<.*?>", "");
        System.out.println(content);
        return content;
    }
}
