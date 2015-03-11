package com.example.testtask.tabs;

import org.json.JSONObject;

import com.example.testtask.DataNotifier;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ResultFragment extends Fragment implements Runnable {

	DataNotifier notifier;
	@Override
	public void onAttach(Activity a) {
	    super.onAttach(a);
	    notifier = (DataNotifier)a;
	    new Thread(this).start();
	}

	
	protected TextView outField;
	@Override
	synchronized public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		outField = new TextView(getActivity().getApplicationContext());
		outField.setTextColor(Color.BLACK);
		JSONObject data = getData();
		if(data != null) {
			outField.setText(data.toString());
		}
		return outField;
	}
	
	protected JSONObject getData() {
		return notifier.getResultData();
	}
	
	
	protected void setDataInMainThread(final JSONObject obj) {
		new Handler(Looper.getMainLooper()).post(new Runnable() {

			@Override
			public void run() {
				synchronized (ResultFragment.this) {
					outField.setText(obj.toString());
				}
			}
		});
	}
	
	@Override
	public void run() {
		if(notifier != null) {
			for(;;) {
				try {
					final JSONObject result = notifier.waitForResultData();
					if(result != null) {
						setDataInMainThread(result);
					}
				} catch(InterruptedException e) {
				}
			}
		}
		
	}

}
