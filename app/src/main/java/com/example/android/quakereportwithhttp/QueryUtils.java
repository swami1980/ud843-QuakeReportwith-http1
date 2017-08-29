package com.example.android.quakereportwithhttp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    public  static ArrayList<Location> curLocs = new ArrayList<Location>();
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */


    /* Make the HTTP connection */
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        int responseCode = 0;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            responseCode = urlConnection.getResponseCode();
            if (responseCode == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {

                jsonResponse = null;
            }

        } catch (IOException e) {
            Log.e("Connection exception", String.valueOf(responseCode));
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /* convert stream data to string data */

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
/* Build list of Location objects from the JSON Response received from USGS site */
    public static ArrayList<Location> extractJsonResponse(String JsonResponse){
        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject eJson = new JSONObject(JsonResponse);
            JSONArray eArray = eJson.optJSONArray("features");
            for (int i = 0; i < eArray.length(); i++) {
                JSONObject listJson = eArray.optJSONObject(i);
                JSONObject propJson = listJson.optJSONObject("properties");
                String magnitude = (propJson.optString("mag").toString());
                String place = propJson.optString("place").toString();
                String date = propJson.optString("time").toString();
                String url = propJson.optString("url").toString();
                // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
                // build up a list of Earthquake objects with the corresponding data.
                curLocs.add(new Location(Float.parseFloat(magnitude), place, Long.parseLong(date), url));
                Log.v("earth", magnitude+ place+ date);
            }
        }catch(JSONException e){
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        Log.v("list", String.valueOf(curLocs.size()));
        // Return the list of earthquakes
        return curLocs;
    }



        }

