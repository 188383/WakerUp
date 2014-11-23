package pl.zeromskiego.androidapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import java.util.HashMap;

public class powiadom extends Activity implements OnClickListener {

	Spinner odleglosc, dzwonek;
	Button zapisz, anuluj;
	CheckBox alarm;
	public static HashMap al = new HashMap ();
	public static HashMap odl = new HashMap();
	public static HashMap poz = new HashMap();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_powiadom);
		inizialize();
	}

	private void inizialize() {
		// TODO Auto-generated method stub
		odleglosc = (Spinner) findViewById(R.id.odleglosc);
		dzwonek = (Spinner) findViewById(R.id.dzwonek);
		zapisz = (Button) findViewById(R.id.zapisz);
		anuluj = (Button) findViewById(R.id.anuluj);
		alarm = (CheckBox) findViewById(R.id.alarm);
		zapisz.setOnClickListener(this);
		anuluj.setOnClickListener(this);

		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i <= 5000; i = i + 100) {
			list.add(i);
		}
		ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		odleglosc.setAdapter(dataAdapter);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.zapisz:
			if(alarm.isChecked())
			{
				int odle = (Integer) odleglosc.getSelectedItem();
				al.put(mapa.ida, "true");
				odl.put(mapa.ida, odle);
				poz.put(mapa.ida, mapa.MarkerPosition);
				finish();
				
			}else{
				poz.put(mapa.ida, mapa.MarkerPosition);
				al.put(mapa.ida, "false");
				finish();
			}				
			break;
		case R.id.anuluj:
			finish();
			break;
		}

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
