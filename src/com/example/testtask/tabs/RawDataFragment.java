package com.example.testtask.tabs;

import org.json.JSONObject;

public class RawDataFragment extends ResultFragment {
	
	@Override
	protected JSONObject getData() {
		return notifier.getRawData();
	}

	@Override
	public void run() {
		if(notifier != null) {
			for(;;) {
				try {
					final JSONObject result = notifier.waitForRawData();
					if(result != null) {
						setDataInMainThread(result);
					}
				} catch(InterruptedException e) {
				}
			}
		}
		
	}

}
