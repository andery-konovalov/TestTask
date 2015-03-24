package com.example.testtask.tabs;

import com.example.testtask.tabs.processor.AbstractProcessor.CompleteListener;

interface ViewControlsInterface extends CompleteListener {
	
	public static final int CHALLENGE_ID = 4;
	public static final int HIDE_RESULT_ID = 3;
	public static final int TAB_LAYOUT_ID =10;

	public String getUrl();
	public String getChallengeServiceUri();
	public String getSolveServiceUri();
	
	public void hideShowTabs(boolean hide);
}
