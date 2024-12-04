package com.example.listviewwithsearchview;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    SearchView searchView;
    NewsAdapter adapter;
    List<NewsItem> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        searchView = findViewById(R.id.searchView);

        newsList = new ArrayList<>();
        newsList.add(new NewsItem("Sport News","This is a sport."));
        newsList.add(new NewsItem("Entertainment","Entertainment news"));
        newsList.add(new NewsItem("Movies","New released movies"));
        newsList.add(new NewsItem("India's Best News","Description of news"));
        newsList.add(new NewsItem("TV9 News","TV9"));

        adapter = new NewsAdapter(this,newsList);
        listView.setAdapter(adapter);

        //set search view
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
    }
}