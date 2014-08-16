package com.example.android.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FeedFragment extends ListFragment {

    private static final String TAG = FeedFragment.class.getSimpleName();
    ItemListAdapter adapter;
    ArrayList<PostItem> piList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_fragment, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        piList = new ArrayList<PostItem>();
        piList.clear();

//		testRequest();
        pageRequest("260689792175"); //260689792175 status tweet
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void testRequest() {
        Session session = Session.getActiveSession();
        Bundle searchParams = new Bundle();
        searchParams.putString("fields", "name,id");

        new Request(session, "/me/likes", searchParams, HttpMethod.GET, new Request.Callback() {
            public void onCompleted(Response response) {
                try {
                    JSONArray jarry = new JSONArray();
                    jarry = response.getGraphObject().getInnerJSONObject().getJSONArray("data");
                    for (int i = 0; i < jarry.length(); i++) {
                        Log.w(TAG, "name: " + jarry.getJSONObject(i).getString("name"));
                        Log.w(TAG, "id: " + jarry.getJSONObject(i).getString("id"));
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        ).executeAsync();
    }

    private void pageRequest(String page) {
        Session session = Session.getActiveSession();
        Bundle searchParams = new Bundle();
        searchParams.putString("fields", "name,id,to,from");
        StringBuilder endPoint = new StringBuilder("/");
        endPoint.append(page);
        endPoint.append("/posts");

        new Request(session, endPoint.toString(), null, HttpMethod.GET, new Request.Callback() {

            @Override
            public void onCompleted(Response response) {
                if (response == null) {
                    Log.e(TAG, "response is null");
                    return;
                }

                parseResponse(response);
                setupAdapter();
            }
        }
        ).executeAsync();
    }

    private void printItem(PostItem pi) {
        Log.wtf(TAG, "Object ID: " + pi.getObject_id());
        Log.wtf(TAG, "Link: " + pi.getLink());
        Log.wtf(TAG, "From: " + pi.getFrom_id());
        Log.wtf(TAG, "Type: " + pi.getType());
        Log.wtf(TAG, "Picture: " + pi.getPicture());
        Log.wtf(TAG, "Message: " + pi.getMessage());
        Log.wtf(TAG, "Status type: " + pi.getStatus_type());
    }

    private void setupAdapter() {
        adapter = new ItemListAdapter(getActivity(), piList);
        setListAdapter(adapter);

        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.wtf(TAG, "Item clicked");
            }
        });
    }

    private void parseResponse (Response response) {
        JSONObject jObject = null;

        try {
            JSONArray jArray = response.getGraphObject().getInnerJSONObject().getJSONArray("data");
            for (int i = 0; i < jArray.length(); i++) {
                PostItem pi = new PostItem();
                jObject = jArray.getJSONObject(i);

                pi.setType(jObject.getString("type"));
                if (pi.getType().equals("photo")) {
                    pi.setObject_id(jObject.getString("object_id"));
                    pi.setLink(jObject.getString("link"));
                    pi.setPicture(jObject.getString("picture"));
                }

                if (pi.getType().equals("video")) {
                    pi.setObject_id(jObject.getString("object_id"));
                    pi.setLink(jObject.getString("link"));
                    pi.setPicture(jObject.getString("picture"));
                    pi.setSource(jObject.getString("source"));
                }

                pi.setFrom_id(jObject.getJSONObject("from").getString("id"));
                if (jObject.toString().contains("message")) {
                    pi.setMessage(clearNameFromMessage(jObject.getString("message")));
                } else {
                    pi.setMessage("");
                }

                if (jObject.toString().contains("status_type")) {
                    pi.setStatus_type(jObject.getString("status_type"));
                } else {
                    pi.setStatus_type("");
                }

                piList.add(pi);
//                Log.w(TAG, jObject.toString());
                Log.wtf(TAG, "----------------------");
            }
        } catch (JSONException e) {
            Log.wtf(TAG, "" + e);
            Log.wtf(TAG, "" + jObject.toString());
        }
    }

    private String clearNameFromMessage(String message) {
        int i = message.indexOf("\n\n");
        if (i > 0) {
            return message.substring(0, i);
        }
        Log.w(TAG, "index of n is: " + i);
        Log.w(TAG, "Message is: " + message);
        return message;
    }

}
