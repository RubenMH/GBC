package com.bostbidev.gearbikecalculator;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private final String TAG = "GearBikeCalculator";
	private float mWheel;
	private float mCrankset;
	private float mCassette;
	private float mResult;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Spinner 	spinCrankset = (Spinner)findViewById(R.id.spinCrankset);
        final Spinner 	spinCassette = (Spinner)findViewById(R.id.spinCassette);
        final EditText 	editTextWheel = (EditText)findViewById(R.id.etWheel);
        final TextView	textViewResult = (TextView)findViewById(R.id.tvResult);
        
        SharedPreferences prefs = getPreferences(MODE_PRIVATE); 
        final String restoredWheelValue = prefs.getString("wheel", null);
        Log.d(TAG, "Restored value: " + restoredWheelValue);
        if (restoredWheelValue != null) {
        	editTextWheel.setText(restoredWheelValue);
        }
        
        final Button button = (Button)findViewById(R.id.btnCalculate);
        button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if (editTextWheel.getText().toString().trim().length() > 0) {
					mWheel = Float.valueOf(editTextWheel.getText().toString());
					mCrankset = Float.valueOf(spinCrankset.getSelectedItem().toString());
					mCassette = Float.valueOf(spinCassette.getSelectedItem().toString());
					mResult = (mWheel * mCrankset) / (mCassette * 1000);
					getValuesDDMS();
					textViewResult.setText(String.valueOf(String.format("%.2f", mResult)) + " m");
					SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
					editor.putString("wheel", String.valueOf(mWheel));
					editor.commit();
				}
				else
				{
					textViewResult.setText("0 m");
					Toast.makeText(getApplicationContext(), R.string.error_dividir_0, Toast.LENGTH_LONG).show();
				}
			}
		});
        
        button.setOnLongClickListener(new OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				finish();
				return false;
			}
		});
        
    }
	private void getValuesDDMS(){
		Log.d(TAG, "mWheel: " + String.valueOf(mWheel));
		Log.d(TAG, "mCrankset: " + String.valueOf(mCrankset));
		Log.d(TAG, "mCassette: " + String.valueOf(mCassette));
		Log.d(TAG, "mResult: " + String.valueOf(mResult));
	}
}
