package com.example.android.booklisting3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BookListingAdapter extends ArrayAdapter<BookListingQ> {


    public BookListingAdapter(MainActivity earthquakes, ArrayList<BookListingQ> earthquakes1) {

        super(earthquakes, 0,earthquakes1);

    }



    @Override

    public View getView(int position, View view, ViewGroup parent) {



        BookListingQ book = getItem(position);



        if (view == null){

            view = LayoutInflater.from(getContext()).inflate(

                    R.layout.booklistingrow, parent, false);

        }



        TextView title = (TextView) view.findViewById(R.id.title);

        TextView author = (TextView) view.findViewById(R.id.author);

        title.setText(book.getTitle());

        author.setText(book.getAuthor());



        return view;

    }

}

