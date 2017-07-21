package ru.android.innocurses.shvabrshvabr;


import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class RssItem {
    private String title;
    private String description;
    private Date pubDate;
    private String link;
    private String category;


    public RssItem(String title, String description, Date pubDate, String link, String category) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.link = link;
        this.category = category;

    }

    public String getCategory() {
        return category;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getLink()
    {
        return this.link;
    }

    public String getDescription()
    {
        return this.description;
    }

    public Date getPubDate()
    {
        return this.pubDate;
    }

    @Override
    public String toString() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy - hh:mm");

        String result = getTitle()  + "\n\n"
                + this.getCategory()  + "\n"
                + sdf.format(this.getPubDate());
        return result;
    }

    public static ArrayList<RssItem> getRssItems(String feedUrl) {

        ArrayList<RssItem> rssItems = new ArrayList<>();

        try {

            URL url = new URL(feedUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document document = db.parse(is);
                Element element = document.getDocumentElement();

                NodeList nodeList = element.getElementsByTagName("item");
                if (nodeList.getLength() > 0) {
                    for (int i = 0; i < nodeList.getLength(); i++) {

                        Element entry = (Element) nodeList.item(i);

                        Element eTitle = (Element) entry.getElementsByTagName("title").item(0);
                        Element eDescription = (Element) entry.getElementsByTagName("description").item(0);
                        Element ePubDate = (Element) entry.getElementsByTagName("pubDate").item(0);
                        Element eLink = (Element) entry.getElementsByTagName("link").item(0);
                        NodeList nl = entry.getElementsByTagName("category");

                        String _title = eTitle.getFirstChild().getNodeValue();
                        String _description =  eDescription.getTextContent();
                        Date _pubDate = new Date(ePubDate.getFirstChild().getNodeValue());
                        String _link = eLink.getFirstChild().getNodeValue();
                        String _category ="";
                        for(int j = 0; j < nl.getLength();  j++){
                            Element elm = (Element) nl.item(j);
                            _category += "#"+elm.getFirstChild().getNodeValue() + " ";
                        }

                        RssItem rssItem = new RssItem(_title, _description,
                                _pubDate, _link, _category);

                        rssItems.add(rssItem);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rssItems;
    }

}