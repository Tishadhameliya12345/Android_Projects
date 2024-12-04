package com.example.listviewwithsearchview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends BaseAdapter {

    Context context;
    List<NewsItem> originalList;
    List<NewsItem> filteredList;

    public NewsAdapter(Context context, List<NewsItem> newsList) {
        this.context = context;
        this.originalList = new ArrayList<>(newsList);
        this.filteredList = newsList;
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_news,parent,false);
        }

        TextView txtTitle = convertView.findViewById(R.id.txtTitle);
        TextView txtDescription = convertView.findViewById(R.id.txtDescription);

        NewsItem newsItem = filteredList.get(position);

        txtTitle.setText(newsItem.getTitle());
        txtDescription.setText(newsItem.getDescription());

        return convertView;
    }

    public void filter(String text){
        filteredList.clear();
        if(text.isEmpty()){
            filteredList.addAll(originalList);
        }else {
            text = text.toLowerCase();
            for(NewsItem item : originalList){
                if(item.getTitle().toLowerCase().contains(text) || item.getDescription().toLowerCase().contains(text)){
                    filteredList.add(item);
                }
            }
        }

        notifyDataSetChanged();
    }
}
