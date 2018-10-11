package com.example.android.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        News currentNews = getItem(position);

        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        titleView.setText(currentNews.getTitle());
        TextView authorView = (TextView) listItemView.findViewById(R.id.author);
        authorView.setText(currentNews.getAuthor());
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        String date = currentNews.getDate();
        String splitDate = "T";
        if(date.contains(splitDate)){
            String[] section = date.split(splitDate);
            date = section[0];
        }
        dateView.setText(date);

        TextView sectionView = (TextView) listItemView.findViewById(R.id.section);
        sectionView.setText(currentNews.getSection());

        return listItemView;
    }
}