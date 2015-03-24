package com.example.testtask.tabs;


import org.json.JSONException;
import org.json.JSONObject;

import com.example.testtask.NotifyBridgeInterface;
import com.example.testtask.data.holder.DataNotifierInterface;
import com.example.testtask.tabs.view.RawDataFragment;
import com.example.testtask.tabs.view.ResultFragment;

import android.app.Activity;
import android.app.AlertDialog;
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

public class MainFragment extends Fragment implements ViewControlsInterface{

	
	
	private EditText hostField;
	private EditText challengeUriField;
	private EditText solveUriField;
	
	private LinearLayout resultLayout;
	private int resultViewState = View.INVISIBLE; 
	
	
	private Button hideButton;

	private DataNotifierInterface notifier;
	public void onAttach(Activity a) {
	    super.onAttach(a);
	    notifier = ((NotifyBridgeInterface)a).getNotifier();
	}

	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("resultViewState", resultViewState);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
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
		hostField.setBackgroundColor(Color.WHITE);
		hostField.setText("https://46.10.208.40");
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
			edArr[i].setBackgroundColor(Color.WHITE);
			mainLayout.addView(edArr[i], new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		}
		
		
		String buttonNames[] = new String[]{"Get Challenge", "Hide Result"};
		int ids[] = new int[]{CHALLENGE_ID, HIDE_RESULT_ID};
		
		for(int i = 0; i < 2;i ++) {
			Button b = new Button(getActivity().getApplicationContext());
			b.setText(buttonNames[i]);
			b.setId(ids[i]);
			b.setOnClickListener(Controller.getInstance(notifier, this));
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			mainLayout.addView(b, lp);
		}
		hideButton = (Button)mainLayout.findViewById(HIDE_RESULT_ID);
		hideButton.setVisibility(View.INVISIBLE);
		
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
		
		resultLayout.setVisibility(View.INVISIBLE);
		
		mainLayout.addView(resultLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		
		return mainLayout;
	}

	@Override
	public String getUrl() {
		return hostField.getText().toString();
	}

	@Override
	public String getChallengeServiceUri() {
		// TODO Auto-generated method stub
		return challengeUriField.getText().toString();
	}

	@Override
	public void onProcessComplete(JSONObject result, int responseCode) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		if(result != null) {
			String title;
			try {
				title = result.getString("result");
			} catch(JSONException e) {
				title = "Ex";
			}
			builder.setMessage(result.toString()).setTitle(title);
			Log.d("", result.toString());
		} else {
			builder.setMessage("Response error: " + Integer.toString(responseCode)).setTitle("Error");
		}
		builder.setPositiveButton("Close", null);
		builder.create().show();
	}

	@Override
	public String getSolveServiceUri() {
		// TODO Auto-generated method stub
		return solveUriField.getText().toString();
	}

	@Override
	public void hideShowTabs(boolean hide) {
		if(hide) {
			resultLayout.setVisibility(View.INVISIBLE);
			hideButton.setVisibility(View.INVISIBLE);
		} else {
			resultLayout.setVisibility(View.VISIBLE);
			hideButton.setVisibility(View.VISIBLE);
		}
	}
}
