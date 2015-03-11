package com.example.testtask;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;

public class MainActivity extends FragmentActivity implements DataNotifier {

	FragmentTabHost tabHost;
	private JSONObject rawData;
	private JSONObject resultData;


	@Override
	protected void onSaveInstanceState (Bundle outState) {
		super.onSaveInstanceState(outState);
		if(rawData != null) {
			outState.putString("rawData", rawData.toString());
		}
		if(resultData != null) {
			outState.putString("resultData", resultData.toString());
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(savedInstanceState != null) {
			String tmpStr;
			try {
				if((tmpStr = savedInstanceState.getString("rawData")) != null) {
					rawData = new JSONObject(tmpStr);
				}
				if((tmpStr = savedInstanceState.getString("resultData")) != null) {
					resultData = new JSONObject(tmpStr);
				}
			} catch(JSONException e) {
			}
		}
		setContentView(R.layout.activity_main);

		tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        tabHost.addTab(tabHost.newTabSpec("http").setIndicator("Requests"), com.example.testtask.tabs.HttpProcessing.class, null);
	}


	@Override
	public JSONObject waitForRawData() throws InterruptedException {
		synchronized (this) {
			wait();
		}
		return rawData;
	}

	@Override
	public JSONObject waitForResultData() throws InterruptedException {
		synchronized (this) {
			wait();
		}
		return resultData;
	}

	@Override
	public void onDataNotifier(JSONObject rawData, JSONObject resultData) {
		synchronized(this) {
			this.rawData = rawData;
			this.resultData = resultData;
			notifyAll();
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject getRawData() {
		return rawData;
	}

	@Override
	public JSONObject getResultData() {
		return resultData;
	}

}
