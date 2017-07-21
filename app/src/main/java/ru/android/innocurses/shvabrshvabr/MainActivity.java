package ru.android.innocurses.shvabrshvabr;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;


public class MainActivity extends Activity {
    public static RssItem selectedRssItem;
    String feedUrl = "https://habrahabr.ru/rss/hubs/all/";
    ListView rssListView;
    ArrayList<RssItem> rssItems = new ArrayList<>();
    ArrayAdapter<RssItem> arrayAdapter;
    private SwipeRefreshLayout swipeContainer;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        rssListView = (ListView) findViewById(R.id.rssListView);
        rssListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View view, int index,
                                    long arg3) {
                selectedRssItem = rssItems.get(index);

                Intent intent = new Intent(view.getContext(), RssItemDisplayer.class);
                startActivity(intent);
            }
        });

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });

        arrayAdapter = new ArrayAdapter<>(this, R.layout.list_item, rssItems);
        rssListView.setAdapter(arrayAdapter);
        refreshList();
    }

    private void refreshList() {

        ArrayList<RssItem> newItems = RssItem.getRssItems(feedUrl);
        rssItems.clear();
        rssItems.addAll(newItems);
        arrayAdapter.notifyDataSetChanged();
        swipeContainer.setRefreshing(false);

    }

}