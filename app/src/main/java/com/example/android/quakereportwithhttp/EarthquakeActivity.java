/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereportwithhttp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    String jsonResponse = "";
    ArrayList<Location> curLoc = new ArrayList<Location>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

       /* Subclass to hold worker thread functionality */

        EQAsyncTask eqActivity = new EQAsyncTask();
        eqActivity.execute();
    }

    private void updateUi(ArrayList<Location> curLoc) {
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        eqAdapter testadapter = new eqAdapter(
                this,  curLoc);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(testadapter);
    }
   private class EQAsyncTask extends AsyncTask<URL, Void, ArrayList<Location>> {

        @Override
        protected ArrayList<Location> doInBackground(URL... urls) {
            // Create URL object
            URL url = null;
            try {
                url = new URL(USGS_REQUEST_URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            // Perform HTTP request to the URL and receive a JSON response back

            try {
                jsonResponse = QueryUtils.makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
            }

            curLoc = QueryUtils.extractJsonResponse(jsonResponse);
            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
            return curLoc;
        }

        @Override
        protected void onPostExecute(ArrayList<Location> locations) {
            // Find a reference to the {@link ListView} in the layout
            updateUi(locations);
        }


    }
}
