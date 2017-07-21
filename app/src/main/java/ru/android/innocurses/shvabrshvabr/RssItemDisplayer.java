package ru.android.innocurses.shvabrshvabr;


import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.widget.*;
import java.text.*;

public class RssItemDisplayer extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss_item_displayer);

        RssItem selectedRssItem = MainActivity.selectedRssItem;

        TextView titleTv = (TextView)findViewById(R.id.titleTextView);
        TextView contentTv = (TextView)findViewById(R.id.contentTextView);

        String title = "";
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd - hh:mm:ss");
        title = "\n" + selectedRssItem.getTitle() + " )\n";

        String content = "";
        content += selectedRssItem.getDescription() + "\n"
                + selectedRssItem.getLink()  + "\n"
                + selectedRssItem.getCategory()  + "\n\n"
                + sdf.format(selectedRssItem.getPubDate());

        titleTv.setText(title);
        contentTv.setText(Html.fromHtml(content));

    }
}