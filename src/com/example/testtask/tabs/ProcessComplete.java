package com.example.testtask.tabs;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

import com.example.testtask.data.holder.DataNotifierInterface;
import com.example.testtask.data.processor.DataProcessor;
import com.example.testtask.tabs.Controller.VisibleStates;
import com.example.testtask.tabs.processor.AbstractProcessor;
import com.example.testtask.tabs.processor.ProcessorFactory;

class ProcessComplete implements AbstractProcessor.CompleteListener{

	private final ViewControlsInterface controls;
	private final DataNotifierInterface dataNotifier;
	
	public ProcessComplete(DataNotifierInterface dataNotifier, ViewControlsInterface controls) {
		this.controls = controls;
		this.dataNotifier = dataNotifier;
	}

	@Override
	public void onProcessComplete(final JSONObject rawData, int responseCode) {

		new DataProcessor() {

			@Override
			protected void onProcessingComplete(JSONObject sortedData) {
				
				dataNotifier.dataArrivedNotify(rawData, sortedData);
				controls.hideShowTabs(VisibleStates.Show.getState());
				
				try {
					URL url = new URL(controls.getUrl()
							+ controls.getSolveServiceUri());
					AbstractProcessor solver = ProcessorFactory.getSolveProcessor(url, sortedData);
					solver.startProcessing(controls);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}.execute(rawData);

	}

}
