package com.example.testtask.tabs.view.updater;

import java.lang.Thread.State;

import org.json.JSONObject;

import android.os.Handler;
import android.os.Looper;

import com.example.testtask.data.holder.DataNotifierInterface;
import com.example.testtask.tabs.view.FragmentViewUpdaterInterface;

	
public class DataUpdater implements Runnable, UpdaterInterface {
	
	protected final DataNotifierInterface notifier;
	private final FragmentViewUpdaterInterface viewUpdater;
	private Thread processThread;
	private boolean shouldFinish = true;
	
	public DataUpdater(FragmentViewUpdaterInterface updater, DataNotifierInterface notifier) {
		this.viewUpdater = updater;
		this.notifier = notifier;
		processThread = new Thread(this);
		
	}
	@Override 
	public void start() {
		synchronized (this) {
			shouldFinish = false;
		}
		if(processThread.getState() != State.NEW) {
			processThread = new Thread(this);
		}
		processThread.start();
	}
	
	@Override
	public void stop() {
		synchronized (this) {
			shouldFinish = true;
		}
		processThread.interrupt();
	}
	
	protected void performInMainThred(final JSONObject obj) {
		new Handler(Looper.getMainLooper()).post(new Runnable() {

			@Override
			public void run() {
				synchronized(DataUpdater.this) {
					viewUpdater.updateData(obj);
				}
			}
		});
	}
	
	protected JSONObject waitForData() throws InterruptedException {
		return notifier.waitForRawData();
	}
	
	protected JSONObject checkForData() {
		return notifier.checkRawData();
	}
	
	@Override
	public void run() {
		if(notifier != null) {
			JSONObject result = checkForData();
			for(boolean terminate = shouldFinish;terminate == false;) {
				try {
					if(result != null && !Thread.interrupted()) {
						performInMainThred(result);
					}
					result = waitForData();
					
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (this) {
					terminate = shouldFinish;
				}
			}
		}
		
	}


}
