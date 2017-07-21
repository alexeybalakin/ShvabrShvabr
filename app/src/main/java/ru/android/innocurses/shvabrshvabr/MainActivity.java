package ru.android.innocurses.shvabrshvabr;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;


public class MainActivity extends Activity {
    public static RssItem selectedRssItem;
    String feedUrl = "";
    ListView rssListView;
    ArrayList<RssItem> rssItems = new ArrayList<>();
    ArrayAdapter<RssItem> arrayAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        final TextView rssURLTV = (TextView) findViewById(R.id.rssURL);

        Button fetchRss = (Button) findViewById(R.id.fetchRss);

        fetchRss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                feedUrl = rssURLTV.getText().toString();
                arrayAdapter.notifyDataSetChanged();
                refreshRssList();
            }
        });


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

        arrayAdapter = new ArrayAdapter<>(this, R.layout.list_item, rssItems);
        rssListView.setAdapter(arrayAdapter);
        feedUrl = rssURLTV.getText().toString();
        refreshRssList();
    }

    private void refreshRssList() {

        ArrayList<RssItem> newItems = RssItem.getRssItems(feedUrl);

        rssItems.clear();
        rssItems.addAll(newItems);
        arrayAdapter.notifyDataSetChanged();
    }

}