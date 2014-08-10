package com.example.android.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ItemListAdapter extends ArrayAdapter<PostItem>{
    private static final String TAG = ItemListAdapter.class.getSimpleName();
    private Context adapterContext;
    private ArrayList<PostItem> items;

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

        ImageView iv = (ImageView) v.findViewById(R.id.imageView);
        TextView tv1 = (TextView) v.findViewById(R.id.textView);
        TextView tv2 = (TextView) v.findViewById(R.id.textView2);
        TextView tv3 = (TextView) v.findViewById(R.id.textView3);

        if (pi.getType().equals("photo")) {
            Log.w(TAG, "picture");
            URL url = null;
            try {
                url = new URL(pi.getPicture());
                Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                iv.setImageBitmap(bitmap);
            } catch (MalformedURLException urlE) {
                Log.e(TAG, "url issue");
            } catch (IOException ioE) {
                Log.e(TAG, "IO issue");
            }
        }

        tv1.setText(pi.getStatus_type());
        tv2.setText(pi.getMessage());
        tv3.setText(pi.getType());
        Log.w(TAG, "tv3 is:" + tv3.getText());
        Log.w(TAG, "pi is:" + pi.getType());

        return v;
    }
}
