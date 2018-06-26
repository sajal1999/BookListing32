package com.example.android.booklisting3;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class BookListingLoader
extends AsyncTaskLoader<List<BookListingQ>>

    {

        /**
         * Tag for log messages
         */
        private static final String LOG_TAG = BookListingLoader.class.getName();

        /**
         * Query URL
         */
        private String mUrl;

        /**
         * Constructs a new {@link BookListingQ}.
         *
         * @param context of the activity
         * @param url     to load data from
         */
    public BookListingLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

        @Override
        protected void onStartLoading() {
        forceLoad();
    }

        /**
         * This is on a background thread.
         */
        @Override
        public List<BookListingQ> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<BookListingQ> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
        return earthquakes;
    }
    }
