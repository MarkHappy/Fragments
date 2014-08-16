package com.example.android.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class ItemListAdapter extends ArrayAdapter<PostItem>{
    private static final String TAG = ItemListAdapter.class.getSimpleName();
    private Context adapterContext;
    private ArrayList<PostItem> items;
    ImageView iv;

    public ItemListAdapter(Context context, ArrayList<PostItem> items) {
        super(context, R.layout.list_items, items);
        adapterContext = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        PostItem pi = items.get(position);

        if (v == null) {
            LayoutInflater li = (LayoutInflater) adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.list_items, null);
        }

        iv = (ImageView) v.findViewById(R.id.imageView);
        TextView tv1 = (TextView) v.findViewById(R.id.textView);
        TextView tv2 = (TextView) v.findViewById(R.id.textView2);
        TextView tv3 = (TextView) v.findViewById(R.id.textView3);

        if (pi.getType().equals("photo") || pi.getType().equals("video")) {
            cacheImage(pi.getPicture());
        }

        if (pi.getType().contains("status")) {
            iv.setVisibility(View.GONE);
        }

//        tv1.setText(pi.getStatus_type());
        tv2.setText(pi.getMessage());
        tv3.setText(pi.getType());
        return v;
    }

    private void cacheImage(String url) {
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                iv.setImageBitmap(bitmap);
            }

            @Override
            public void onPrepareLoad(Drawable drawable) {

            }

            @Override
            public void onBitmapFailed(Drawable drawable) {

            }
        };
        Picasso.with(getContext()).load(url).into(target);
    }
}
