package com.example.testtask.tabs.view;

import org.json.JSONObject;

import com.example.testtask.NotifyBridgeInterface;
import com.example.testtask.data.holder.DataNotifierInterface;
import com.example.testtask.tabs.view.updater.ResultUpdater;
import com.example.testtask.tabs.view.updater.UpdaterInterface;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ResultFragment extends Fragment implements FragmentViewUpdaterInterface {

	protected TextView outField;
	
	private UpdaterInterface updater;
	
	
	@Override
	public void onAttach(Activity a) {
	    super.onAttach(a);
	    updater = getUpdater(((NotifyBridgeInterface)a).getNotifier());
	}

	protected UpdaterInterface getUpdater(DataNotifierInterface notifier) {
		return new ResultUpdater(this, notifier);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		updater.start();
		
	}
	
	@Override
	public void onStop() {
		super.onStop();
		updater.stop();
	}
	
	@Override
	synchronized public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		outField = new TextView(getActivity().getApplicationContext());
		outField.setTextColor(Color.BLACK);
		return outField;
	}
	
	@Override
	public void updateData(JSONObject data) {
		if(data != null) {
			outField.setText(data.toString());
		}
	}
	
}
