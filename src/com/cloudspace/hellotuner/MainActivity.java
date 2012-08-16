package com.cloudspace.hellotuner;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity
extends Activity
implements OnClickListener {

	private SoundListener listener = new SoundListener();
	private boolean running = false;
	Handler decibelUpdateHandler;
	Runnable decibelUpdateCallback;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button start = (Button)findViewById(R.id.start);
		start.setOnClickListener(this);
		decibelUpdateHandler = new Handler(Looper.getMainLooper());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onClick(View v) {
		switch(v.getId())  {
		case R.id.start:
			toggleSoundListening(v);
			break;
		}		
	}   

	private void toggleSoundListening(View v)  {
		if(running)  {
			stopSoundListening(v);
		} else {
			startSoundListening(v);
		}
		running = !running;
	}
	private void stopSoundListening(View v)  {
		listener.stop();

		Button button = (Button) v;
		button.setText(getResources().getString(R.string.start));

		decibelUpdateHandler.removeCallbacks(decibelUpdateCallback);
	}

	private void startSoundListening(View v)  {
		listener.start();
		Button button = (Button) v;
		button.setText(getResources().getString(R.string.stop));
		
		decibelUpdateCallback = new Runnable() {
	    public void run()
	    {
				TextView dBCurrent = (TextView)findViewById(R.id.dBText);
				dBCurrent.setText(String.valueOf(listener.getCurrentDecibels()));
	    }
	};
		
		decibelUpdateHandler.post(decibelUpdateCallback);

	}
}
