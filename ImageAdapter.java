package com.example.android.remindme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by akshaymiglani on 29/03/17.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private boolean[] arrGrid;
    public ImageAdapter(Context c) {
        mContext = c;
        arrGrid = new boolean[mThumbIds.length];

        for (int i = 0; i < arrGrid.length; i++) {
            arrGrid[i] = false;
        }

        // set the following positions blank for example

    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

     public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_image_view, parent, false);
            imageView = (ImageView) convertView;
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.recommend1,
            R.drawable.flight1,
            R.drawable.hotel1};  };
