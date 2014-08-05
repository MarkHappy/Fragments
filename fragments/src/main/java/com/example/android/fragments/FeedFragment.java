package com.example.android.fragments;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

public class FeedFragment extends Fragment{
	private static final String TAG = FeedFragment.class.getSimpleName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		testRequest();
		pageRequest("260689792175"); //260689792175 status tweet
	}
	
	private void testRequest(){
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
		StringBuilder sb = new StringBuilder("/");
		sb.append(page);
		sb.append("/posts");
		
		new Request(session, sb.toString(), null, HttpMethod.GET, new Request.Callback() {
			
			@Override
			public void onCompleted(Response response) {
				PostItem pi = new PostItem();
//				Log.w(TAG, "" + response.toString());
				try {
					JSONArray jarry = new JSONArray();
					jarry = response.getGraphObject().getInnerJSONObject().getJSONArray("data");
//					for (int i = 0; i < jarry.length(); i++) {
						Log.w(TAG, "" + jarry.getJSONObject(0).toString());
//						Log.e(TAG, "");
						Log.w(TAG, "" + jarry.getJSONObject(0).getString("message"));
						pi.setFrom_id(jarry.getJSONObject(0).getJSONObject("from").getString("id"));
//					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	).executeAsync();
	}

}
