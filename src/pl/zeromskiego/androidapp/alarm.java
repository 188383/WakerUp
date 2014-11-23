package pl.zeromskiego.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class alarm extends Activity implements OnClickListener {

	Button OK;
	MediaPlayer alarm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm);
		OK = (Button) findViewById(R.id.ok);
		OK.setOnClickListener(this);
		sprawdzA();
		

	
	}

	private void sprawdzA() {
		
		if(powiadom.muzyka.equals("a1"))
		{
			alarm = MediaPlayer.create(alarm.this, R.raw.a1);
			alarm.start();
		}else if(powiadom.muzyka.equals("a2"))
		{
			alarm = MediaPlayer.create(alarm.this, R.raw.a2);
			alarm.start();
		}else if(powiadom.muzyka.equals("a3"))
		{
			alarm = MediaPlayer.create(alarm.this, R.raw.a3);
			alarm.start();
		}else if(powiadom.muzyka.equals("a4"))
		{
			alarm = MediaPlayer.create(alarm.this, R.raw.a4);
			alarm.start();
		}else if(powiadom.muzyka.equals("a5"))
		{
			alarm = MediaPlayer.create(alarm.this, R.raw.a5);
			alarm.start();
		}else if(powiadom.muzyka.equals("a6"))
		{
			alarm = MediaPlayer.create(alarm.this, R.raw.a6);
			alarm.start();
		}
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		alarm.release();
		finish();
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ok:
			alarm.stop();
			alarm.release();
			finish();
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy()
	{
	    // RUN SUPER | REGISTER ACTIVITY AS NULL IN APP CLAS
	        super.onDestroy();

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		return super.onOptionsItemSelected(item);
	}

}
