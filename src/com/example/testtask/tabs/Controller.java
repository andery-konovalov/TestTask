package com.example.testtask.tabs;

import java.net.MalformedURLException;
import java.net.URL;


import com.example.testtask.data.holder.DataNotifierInterface;
import com.example.testtask.tabs.processor.AbstractProcessor;
import com.example.testtask.tabs.processor.ProcessorFactory;

import android.view.View;
import android.view.View.OnClickListener;

class Controller implements View.OnClickListener {

	public enum VisibleStates {
		Hide(true),
		Show(false);
		
		private final boolean state;
		VisibleStates(boolean isHide) {
			state = isHide;
		}
		
		public boolean getState() {
			return state;
		}
		
		public static VisibleStates getNextState(VisibleStates vs) {
			VisibleStates result;
			if(vs == Hide) {
				result = Show;
			} else {
				result = Hide;
			}
			return result;
		}
	};
	
	private VisibleStates currentState = VisibleStates.Hide;
	private static Controller instance;
	
	private final ViewControlsInterface controls;
	private final DataNotifierInterface dataNotifier;
	
	private Controller(DataNotifierInterface dataNotifier, ViewControlsInterface controls) {
		this.controls = controls;
		this.dataNotifier = dataNotifier;
	}
	
	public static OnClickListener getInstance(DataNotifierInterface notifier, ViewControlsInterface controls) {
		Controller localController = instance;
			if(localController == null) {
				synchronized (Controller.class) {
					if(instance == null) {
						instance = new Controller(notifier, controls);
					}
					localController = instance;
				}
			}
		return localController;
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case ViewControlsInterface.CHALLENGE_ID:{
				try {
					URL url = new URL(controls.getUrl() + controls.getChallengeServiceUri());
					if(url.getProtocol().equals("https") == true) {
						AbstractProcessor challenger = ProcessorFactory.getChallengeProcessor(url);
						challenger.startProcessing(new ProcessComplete(dataNotifier, controls));
					}
				} catch(MalformedURLException ex) {
				}
			}
			break;
			case ViewControlsInterface.HIDE_RESULT_ID:
				controls.hideShowTabs(currentState.state);
				currentState = VisibleStates.getNextState(currentState);
			break;
		}
	}


}
