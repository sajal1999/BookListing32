package com.example.android.booklisting3;

import android.text.TextUtils;


import android.util.Log;





import org.json.JSONArray;


import org.json.JSONException;


import org.json.JSONObject;





import java.io.BufferedReader;


import java.io.IOException;


import java.io.InputStream;


import java.io.InputStreamReader;


import java.net.HttpURLConnection;


import java.net.MalformedURLException;


import java.net.URL;


import java.nio.charset.Charset;


import java.util.ArrayList;


import java.util.List;
public final class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();


    private QueryUtils() {



    }

    public static List<BookListingQ> fetchEarthquakeData(String requestUrl) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Create URL object


        URL url = createUrl(requestUrl);


        String jsonResponse = null;


        try {


            jsonResponse = makeHttpRequest(url);


        } catch (IOException e) {


            Log.e(LOG_TAG, "Problem making the HTTP request.", e);


        }


        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s


        List<BookListingQ> earthquakes = extractFeatureFromJson(jsonResponse);


        // Return the list of {@link Earthquake}s


        return earthquakes;


    }


    private static URL createUrl(String stringUrl) {


        URL url = null;


        try {


            url = new URL(stringUrl);


        } catch (MalformedURLException e) {


            Log.e(LOG_TAG, "Problem building the URL ", e);


        }


        return url;


    }


    private static String makeHttpRequest(URL url) throws IOException {


        String jsonResponse = "";


        // If the URL is null, then return early.


        if (url == null) {


            return jsonResponse;


        }


        HttpURLConnection urlConnection = null;


        InputStream inputStream = null;


        try {


            urlConnection = (HttpURLConnection) url.openConnection();


            urlConnection.setReadTimeout(10000 /* milliseconds */);


            urlConnection.setConnectTimeout(15000 /* milliseconds */);


            urlConnection.setRequestMethod("GET");


            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {


                inputStream = urlConnection.getInputStream();

                jsonResponse = readFromStream(inputStream);


            } else {


                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());


            }


        } catch (IOException e) {


            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);


        } finally {


            if (urlConnection != null) {


                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }


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



    public static String formatListOfAuthors(JSONArray authorsList) throws JSONException {



        String authorsListInString = null;



        if (authorsList.length() == 0) {

            return null;

        }



        for (int i = 0; i < authorsList.length(); i++){

            if (i == 0) {

                authorsListInString = authorsList.getString(0);

            } else {

                authorsListInString += ", " + authorsList.getString(i);

            }

        }



        return authorsListInString;

    }

    /**
     * Return a list of {@link BookListingQ} objects that has been built up from
     * parsing the given JSON response.
     */
    public static List<BookListingQ> extractFeatureFromJson(String booksJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(booksJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<BookListingQ> books = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject jsonResponse = new JSONObject(booksJSON);



            if (jsonResponse.getInt("totalItems") == 0) {

                return books;

            }

            JSONArray jsonArray = jsonResponse.getJSONArray("items");



            for (int i = 0; i < jsonArray.length(); i++){

                JSONObject bookObject = jsonArray.getJSONObject(i);



                JSONObject bookInfo = bookObject.getJSONObject("volumeInfo");



                String title = bookInfo.getString("title");

                JSONArray authorsArray = bookInfo.getJSONArray("authors");

                String authors = formatListOfAuthors(authorsArray);

                String url = bookInfo.getString("url");

                BookListingQ book = new BookListingQ(authors, title,url);

                books.add(book);

            }

        } catch (JSONException e) {

            e.printStackTrace();

        }



        return books;

    }

}



