package com.example.testtask;

import com.example.testtask.data.holder.DataNotifier;
import com.example.testtask.data.holder.DataNotifierInterface;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.os.Parcel;

public class MainActivity extends FragmentActivity implements NotifyBridgeInterface {

	private static String DATA_CONTAINER_KEY = "dataKey";
	
	FragmentTabHost tabHost;

	DataNotifierInterface dataNotifier;

	@Override
	protected void onSaveInstanceState (Bundle outState) {
		super.onSaveInstanceState(outState);
		if(dataNotifier != null) {
			outState.putParcelable(DATA_CONTAINER_KEY, dataNotifier);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(savedInstanceState != null) {
			Parcel parcel;
			if((parcel = savedInstanceState.getParcelable(DATA_CONTAINER_KEY)) != null) {
				dataNotifier = DataNotifier.makeFromParcel(parcel);
			}
		} else {
			dataNotifier = DataNotifier.makeInstance();
		}
		setContentView(R.layout.activity_main);

		tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        tabHost.addTab(tabHost.newTabSpec("http").setIndicator("Requests"), com.example.testtask.tabs.MainFragment.class, null);
	}

	@Override
	public DataNotifierInterface getNotifier() {
		return dataNotifier;
	}

}
