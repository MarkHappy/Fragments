package com.example.android.fragments;

import java.util.Arrays;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainMenuFragment extends Fragment {
	private static final String TAG = MainMenuFragment.class.getSimpleName();
	private UiLifecycleHelper uiHelper;
	FacebookLoggedInListener fbCallback;
	
	public interface FacebookLoggedInListener {
		public void isFbLoggedIn (boolean state);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

    	View view = inflater.inflate(R.layout.main_menu, container, false);
    	LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
    	loginButton.setFragment(this);
    	loginButton.setReadPermissions(Arrays.asList("user_likes"));
    	
        return view;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
    }
    
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
        	Log.w(TAG, "Session.StatusCallback was called.");
            onSessionStateChange(session, state, exception);
        }
    };
    
    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
    	Log.w(TAG, "DeclinedPermissions: " + session.getDeclinedPermissions());
    	Log.w(TAG, "Permissions: " + session.getPermissions());
        if (state.isOpened()) {
        	fbCallback.isFbLoggedIn(true);
            Log.w(TAG, "Logged in...");
        } else if (state.isClosed()) {
        	fbCallback.isFbLoggedIn(false);
            Log.w(TAG, "Logged out...");
        }
    }
    
    @Override
    public void onAttach(Activity activity) {
    	Log.w(TAG, "onAttach");
    	super.onAttach(activity);
    	try {
    		fbCallback = (FacebookLoggedInListener) activity;
    	} catch (ClassCastException e) {
    		throw new ClassCastException(activity.toString()
                    + " must implement FacebookLoggedInListener");
    	}
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	uiHelper.onResume();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	uiHelper.onActivityResult(requestCode, resultCode, data);
    	Log.w(TAG, "onActivityResult");
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	uiHelper.onPause();
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	uiHelper.onDestroy();
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	uiHelper.onSaveInstanceState(outState);
    }
}
