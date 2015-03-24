package com.example.testtask.tabs.view.updater;

import org.json.JSONObject;

import com.example.testtask.data.holder.DataNotifierInterface;
import com.example.testtask.tabs.view.FragmentViewUpdaterInterface;

public class ResultUpdater extends DataUpdater {

	public ResultUpdater(FragmentViewUpdaterInterface updater, DataNotifierInterface notifier) {
		super(updater, notifier);
	}
	
	@Override
	protected JSONObject waitForData() throws InterruptedException {
		return notifier.waitForResultData();
	}

	@Override
	protected JSONObject checkForData() {
		return notifier.checkResultData();
	}

}
