package com.example.android.quakereportwithhttp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.example.android.quakereportwithhttp.R.id.date1;
import static com.example.android.quakereportwithhttp.R.id.time1;

/**
 * Created by 118168 on 6/14/2017.
 */

public class eqAdapter extends ArrayAdapter<Location> {
    public eqAdapter(Activity con, ArrayList<Location> earthquakes) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(con, 0, earthquakes);

        this.con = con;
    }

    static MediaPlayer mPlayer;
    Activity con;
    String magcolors;

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }


        // Get the {@link AndroidFlavor} object located at this position in the list
        final Location curLoc = getItem(position);

        // Find the short description
        TextView magTextView = (TextView) listItemView.findViewById(R.id.mag1);
        magTextView.setText(String.valueOf(curLoc.getMagnitude()));

        GradientDrawable gd =  (GradientDrawable) magTextView.getBackground();



        switch ((int) curLoc.getMagnitude()) {
            case 1:
                gd.setColor(ContextCompat.getColor(getContext(), R.color.magnitude1));

                break;
            case 2:
                gd.setColor(ContextCompat.getColor(getContext(), R.color.magnitude2));

                break;
            case 3:
                gd.setColor(ContextCompat.getColor(getContext(), R.color.magnitude3));
                break;
            case 4:
                gd.setColor(ContextCompat.getColor(getContext(), R.color.magnitude4));
                break;
            case 5:
                gd.setColor(ContextCompat.getColor(getContext(), R.color.magnitude5));
                break;
            case 6:
                gd.setColor(ContextCompat.getColor(getContext(), R.color.magnitude6));


                break;
            case 7:
                gd.setColor(ContextCompat.getColor(getContext(), R.color.magnitude7));
            case 8:
                gd.setColor(ContextCompat.getColor(getContext(), R.color.magnitude8));
                break;
            case 9:
                gd.setColor(ContextCompat.getColor(getContext(), R.color.magnitude9));
                break;
        }

        // Find the short description
        TextView shortTextView = (TextView) listItemView.findViewById(R.id.text1    );
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView

        TextView longTextView = (TextView) listItemView.findViewById(R.id.text2    );
        Boolean split= curLoc.getLocation().contains("of");
        if (split == false) {
            shortTextView.setText("Near to");
            longTextView.setText(curLoc.getLocation());
        }
         else if (split == true){
         String[] secondString = curLoc.getLocation().split("of");
            shortTextView.setText(secondString[0] );
            longTextView.setText(secondString[1] );
        }
        TextView dateTextView = (TextView) listItemView.findViewById(date1   );
        java.util.Date date=new java.util.Date( curLoc.getDate() );
        DateFormat eDate = DateFormat.getDateInstance();
        dateTextView.setText(String.valueOf(eDate.format(curLoc.getDate())));

        TextView timeTextView = (TextView) listItemView.findViewById(time1   );
        DateFormat eTime = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.ENGLISH);


        timeTextView.setText(String.valueOf(eTime.format(curLoc.getDate())));

        listItemView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(curLoc.getUrl()));

                con.startActivity(browserIntent);

            }
        });
        return listItemView;
/* On click listener to call the URL intent */


    }










    public void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mPlayer = null;
        }
    }
}



