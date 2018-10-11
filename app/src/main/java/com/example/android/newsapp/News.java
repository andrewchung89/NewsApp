package com.example.android.newsapp;

public class News {
    private String nTitle;
    private String nAuthor;
    private String nDate;
    private String nSection;
    private String nUrl;

    public News(String title, String author, String date, String section, String url) {
        nTitle = title;
        nAuthor = author;
        nDate = date;
        nSection = section;
        nUrl = url;
    }

    public String getTitle() {
        return nTitle;
    }

    public String getAuthor() {
        return nAuthor;
    }



    public String getDate() {
        return nDate;
    }


    public String getSection() {
        return nSection;
    }

    public String getUrl() {return nUrl;}

}