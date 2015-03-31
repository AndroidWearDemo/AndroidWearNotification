package com.daijiale.notificationwear;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.support.v4.app.RemoteInput;
import android.view.Menu;

public class OtherActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other);
	}
	
	private CharSequence getMessageText(Intent intent) {
		Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
		if (remoteInput != null) {
		return remoteInput.getCharSequence(MainActivity.EXTRA_VOICE_REPLY);
		 }
		
		return null;
		}

}
