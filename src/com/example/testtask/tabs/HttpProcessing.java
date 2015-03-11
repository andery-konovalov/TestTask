package com.example.testtask.tabs;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.testtask.DataNotifier;
import com.example.testtask.download.HttpSolver;
import com.example.testtask.download.HttpsChallenge;
import com.example.testtask.processor.DataProcessor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class HttpProcessing extends Fragment implements View.OnClickListener{

	private static final int CHALLENGE_ID = 4;
	private static final int HIDE_RESULT_ID = 3;
	private static final int TAB_LAYOUT_ID =10;
	
	private JSONObject rawData = null;
	private JSONObject sortData = null;
	
	private EditText hostField;
	private EditText challengeUriField;
	private EditText solveUriField;
	
	private LinearLayout resultLayout;
	private int resultViewState = View.INVISIBLE; 

	private DataNotifier notifier;
	public void onAttach(Activity a) {
	    super.onAttach(a);
	    notifier = (DataNotifier)a;
	}

	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("resultViewState", resultViewState);
	}
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState != null) {
			resultViewState = savedInstanceState.getInt("resultViewState", View.INVISIBLE);
		}
	}
	
	private View makeEditHostFields() {
		
		LinearLayout edLayout = new LinearLayout(getActivity().getApplicationContext());
		edLayout.setOrientation(LinearLayout.HORIZONTAL);
		edLayout.setWeightSum(1);
		
		hostField = new EditText(getActivity().getApplicationContext());
		edLayout.addView(hostField, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
		hostField.setTextColor(Color.BLACK);
		hostField.setText("https://46.10.208.40");
/*
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = 10;
		EditText portEd = new EditText(getActivity().getApplicationContext());
		portEd.setText("8080");
		portEd.setTextColor(Color.BLACK);
		edLayout.addView(portEd, lp);
*/
		return edLayout;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d("MainTab", "here");
		LinearLayout mainLayout = new LinearLayout(getActivity().getApplicationContext());
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		
		mainLayout.addView(makeEditHostFields());
		
		//making requst uri
		String uriArr[] = new String[]{"/AndroidBackendServices/services/getChallenge", "/AndroidBackendServices/services/solveChallenge"};
		EditText edArr[] = new EditText[]{(challengeUriField = new EditText(getActivity().getApplicationContext())), (solveUriField = new EditText(getActivity().getApplicationContext()))};
		for(int i = 0;i < 2;i ++) {
			edArr[i].setText(uriArr[i]);
			edArr[i].setTextColor(Color.BLACK);
			mainLayout.addView(edArr[i], new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		}
		
		
		String buttonNames[] = new String[]{"Get Challenge", "Hide Result"};
		int ids[] = new int[]{CHALLENGE_ID, HIDE_RESULT_ID};
		
		for(int i = 0; i < 2;i ++) {
			Button b = new Button(getActivity().getApplicationContext());
			b.setText(buttonNames[i]);
			b.setId(ids[i]);
			b.setOnClickListener(this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			mainLayout.addView(b, lp);
		}
		
		resultLayout = new LinearLayout(getActivity().getApplicationContext());
		resultLayout.setOrientation(LinearLayout.VERTICAL);
		
		FrameLayout fl = new FrameLayout(getActivity());
		fl.setId(android.R.id.tabcontent);
		resultLayout.addView(fl);
		
		
		
		
		FragmentTabHost tabHost = new FragmentTabHost(getActivity());
        tabHost.setup(getActivity(), getChildFragmentManager(), TAB_LAYOUT_ID);
        
        tabHost.addTab(tabHost.newTabSpec("request").setIndicator("Request"), RawDataFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("result").setIndicator("Result"), ResultFragment.class, null);
        resultLayout.addView(tabHost);

		fl = new FrameLayout(getActivity());
		fl.setId(TAB_LAYOUT_ID);
		resultLayout.addView(fl);
		
		resultLayout.setVisibility(resultViewState);
		
		mainLayout.addView(resultLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		
		return mainLayout;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case CHALLENGE_ID:{
				try {
					final ProgressDialog dialog = new ProgressDialog(getActivity().getApplicationContext());
					dialog.setMessage("Processing ...");
					URL url = new URL(hostField.getText().toString() + challengeUriField.getText().toString());
					if(url.getProtocol().equals("https") == true) {
						//Getting data
						new HttpsChallenge(url) {
							
							@Override
							public void onDownloadingComplete(final JSONObject jResult, int responseCode) {
								//checking response code !!!
								Log.d("", jResult.toString());
								rawData = jResult;
								//processing data
								new DataProcessor() {
									@Override
									protected void onProcessingComplete(JSONObject result) {
										try {
											URL url = new URL(hostField.getText().toString() + solveUriField.getText().toString());
											dialog.setMessage("Uploading the result");
											sortData = result; 
											//notify tabs
											notifier.onDataNotifier(rawData, sortData);
											if(resultLayout.getVisibility() == View.INVISIBLE) {
												resultLayout.setVisibility(View.VISIBLE);
												resultViewState = View.VISIBLE;
											}
											//sending response
											new HttpSolver(url, result) {
												
												@Override
												public void onDownloadingComplete(JSONObject jResult, int responseCode) {
													//check response code
													dialog.dismiss();
													AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
													if(jResult != null) {
														String title;
														try {
															title = jResult.getString("result");
														} catch(JSONException e) {
															title = "Ex";
														}
														builder.setMessage(jResult.toString()).setTitle(title);
														Log.d("", jResult.toString());
													} else {
														builder.setMessage("Response error: " + Integer.toString(responseCode)).setTitle("Error");
													}
													builder.setPositiveButton("Close", null);
													builder.create().show();
													
												}
											}.execute((Void)null);
										} catch(MalformedURLException e) {
										}
									}
								}.execute(jResult);
							}
						}.execute((Void)null);
					}
				} catch(MalformedURLException ex) {
				}
			}
			break;
			case HIDE_RESULT_ID:
				resultLayout.setVisibility(View.INVISIBLE);
				resultViewState = View.INVISIBLE;
				break;
		}
	}
	

}
