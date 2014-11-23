package pl.zeromskiego.androidapp;

import android.app.Activity;
import android.os.Bundle;

public class onas extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_onas);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

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

}
