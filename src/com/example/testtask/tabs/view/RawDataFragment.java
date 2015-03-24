package com.example.testtask.tabs.view;


import com.example.testtask.data.holder.DataNotifierInterface;
import com.example.testtask.tabs.view.updater.DataUpdater;
import com.example.testtask.tabs.view.updater.UpdaterInterface;

public class RawDataFragment extends ResultFragment {

	@Override
	protected UpdaterInterface getUpdater(DataNotifierInterface notifier) {
		return new DataUpdater(this, notifier);
	}

}
