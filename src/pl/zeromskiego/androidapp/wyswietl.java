package pl.zeromskiego.androidapp;

import java.sql.SQLException;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

public class wyswietl extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_wyswietl);
		TextView tv = (TextView)findViewById(R.id.info);
		
		try {
			mapa.bz.open();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			TextView tv1 = new TextView(this);
			boolean didItWork = false;
			String error = e.toString();
			Dialog d = new Dialog(this);
			d.setTitle("Lose!");
			tv.setText(error);
			d.setContentView(tv);
			d.show();
		}
		String data = mapa.bz.getData();
		mapa.bz.close();
		tv.setText(data);
	}
	
	

}
