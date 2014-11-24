package pl.zeromskiego.androidapp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class dodaj extends Activity implements OnClickListener {

	EditText etNazwa, etOpis, etMiejsce, etAdres, etData;
	RadioGroup rG;
	private Spinner idRow;
	CheckBox cbAlarm;
	Button bDodaj, bUsun, bInfo, bEdit;
	RadioButton rBiznesowe, rTowarzyskie, rPodroz;
	String Nazwa, Opis, Alarm, Typ, Miejsce, Adres, Data, sRow1;
	boolean didItWork;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_dodaj);
		inizialize();
		try {
			addItemsOnSpinner();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bDodaj:
			dodaj();
			break;
		case R.id.bUsun:
			Usun();
			break;
		case R.id.bGetinfo:
			try {
				String s = String.valueOf(idRow.getSelectedItem());
				long l = Long.parseLong(s);
				mapa.bz.open();
				String retnazwa = mapa.bz.getNazwa(l);
				String retopis = mapa.bz.getOpis(l);
				String retMiejsce = mapa.bz.getMiejsce(l);
				String retAdres = mapa.bz.getAdres(l);
				String retData = mapa.bz.getD(l);
				mapa.bz.close();
				etNazwa.setText(retnazwa);
				etOpis.setText(retopis);
				etMiejsce.setText(retMiejsce);
				etAdres.setText(retAdres);
				etData.setText(retData);
			} catch (Exception e) {
				String error = e.toString();
				Dialog d = new Dialog(this);
				d.setTitle("Error");
				TextView tv = new TextView(this);
				tv.setText(error);
				d.setContentView(tv);
				d.show();
			}
			break;
		case R.id.bEdit:
			Edit();
			break;
		}
	}
	
	private void Edit() {
		// TODO Auto-generated method stub
		try {
			Nazwa = etNazwa.getText().toString();
			Opis = etOpis.getText().toString();
			Miejsce = etMiejsce.getText().toString();
			Adres = etAdres.getText().toString();
			Data = etData.getText().toString();
			String sRow = String.valueOf(idRow.getSelectedItem());
			Alarm = "";
			Typ = "";
			if (cbAlarm.isChecked()) {
				Alarm = "ture";
			} else {
				Alarm = "false";
			}
			if (rBiznesowe.isChecked()) {
				Typ = "Biznesowe";
			} else if (rTowarzyskie.isChecked()) {
				Typ = "Towarzyskie";
			} else if (rPodroz.isChecked()) {
				Typ = "Podroz";
			}else{
				Typ = "Nie okreslony";
			}

			long lRow = Long.parseLong(sRow);
		
			mapa.bz.open();
			mapa.bz.updateSpotkanie(lRow, Nazwa, Opis, Miejsce, Adres,
					Data, Typ, Alarm);
			mapa.bz.close();
		} catch (Exception e) {
			String error = e.toString();
			Dialog d = new Dialog(this);
			d.setTitle("Error");
			TextView tv = new TextView(this);
			tv.setText(error);
			d.setContentView(tv);
			d.show();
		} finally {
			Dialog d = new Dialog(this);
			d.setTitle("ooo yea!:>");
			TextView tv = new TextView(this);
			tv.setText("Success");
			d.setContentView(tv);
			d.show();
		}
	}
	


	private void Usun() {
		// TODO Auto-generated method stub
		try {
			sRow1 = String.valueOf(idRow.getSelectedItem());	
		} catch (Exception e) {
			String error = e.toString();
			Dialog d = new Dialog(this);
			d.setTitle("Error");
			TextView tv = new TextView(this);
			tv.setText(error);
			d.setContentView(tv);
			d.show();
		} finally {
			try {
				mapa.bz.open();
				mapa.bz.deleteSpotkanie(sRow1);
				mapa.bz.close();
				Dialog d = new Dialog(this);
				d.setTitle("ooo yea!:>");
				TextView tv = new TextView(this);
				tv.setText("Success");
				d.setContentView(tv);
				d.show();
				try {
					addItemsOnSpinner();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		
			
		}
	}


	private void dodaj() {
		// TODO Auto-generated method stub
		didItWork = true;
		try {
			Alarm = "";
			Typ = "";
			Nazwa = etNazwa.getText().toString();
			if(Nazwa.equals("")){
				didItWork = false;
				Dialog d = new Dialog(this);
				d.setTitle("Nie poda³es nazwy");
				TextView tv = new TextView(this);
				d.setContentView(tv);
				d.show();
			}else{
			sprawdzNazwe();
			}
			Opis = etOpis.getText().toString();
			Miejsce = etMiejsce.getText().toString();
			Adres = etAdres.getText().toString();
			Data = etData.getText().toString();
			if (cbAlarm.isChecked()) {
				Alarm = "ture";
			} else {
				Alarm = "false";
			}
			if (rBiznesowe.isChecked()) {
				Typ = "Biznesowe";
			} else if (rTowarzyskie.isChecked()) {
				Typ = "Towarzyskie";
			} else if (rPodroz.isChecked()) {
				Typ = "Podroz";
			}else{
				Typ = "Nie okreslony";
			}
	
		} catch (Exception e) {
			didItWork = false;
			String error = e.toString();
			Dialog d = new Dialog(this);
			d.setTitle("Error");
			TextView tv = new TextView(this);
			tv.setText(error);
			d.setContentView(tv);
			d.show();
		} finally {
			if (didItWork) {
				
				try {
					mapa.bz.open();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mapa.bz.createEntry(Nazwa, Opis, Miejsce, Adres, Data,
						Typ, Alarm);
				mapa.bz.close();
				Dialog d = new Dialog(this);
				d.setTitle("ooo yea!:>");
				TextView tv = new TextView(this);
				tv.setText("Success");
				d.setContentView(tv);
				d.show();
				try {
					addItemsOnSpinner();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}


	public void addItemsOnSpinner() throws SQLException {
		
		BazaSpotkan bz = new BazaSpotkan(this);
		List<String> list = new ArrayList<String>();
		bz.open();
		String[] idRowhelp=null;
		String id = bz.pobierzIDROW();
		idRowhelp = id.split(" ");
		for (int i = 0; i < idRowhelp.length; i++) {
		list.add(idRowhelp[i]);
		}
		bz.close();
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		idRow.setAdapter(dataAdapter);
	  }
	private void inizialize() {
		// TODO Auto-generated method stub
		etNazwa = (EditText) findViewById(R.id.etNazwa);
		etOpis = (EditText) findViewById(R.id.etOpis);
		etMiejsce = (EditText) findViewById(R.id.etMiejsce);
		etAdres = (EditText) findViewById(R.id.etAdres);
		etData = (EditText) findViewById(R.id.etData);
		rG = (RadioGroup) findViewById(R.id.rG);
		cbAlarm = (CheckBox) findViewById(R.id.cbAlarm);
		bDodaj = (Button) findViewById(R.id.bDodaj);
		rBiznesowe = (RadioButton) findViewById(R.id.rBiznesowe);
		rTowarzyskie = (RadioButton) findViewById(R.id.rTowarzyskie);
		rPodroz = (RadioButton) findViewById(R.id.rPodroz);
		bUsun = (Button) findViewById(R.id.bUsun);
		bInfo = (Button) findViewById(R.id.bGetinfo);
		bEdit = (Button) findViewById(R.id.bEdit);
		idRow = (Spinner) findViewById(R.id.idRow);
		etAdres.setText(mapa.ulicahelper);
		etMiejsce.setText(mapa.miastohelper);
		bDodaj.setOnClickListener(this);
		bUsun.setOnClickListener(this);
		bInfo.setOnClickListener(this);
		bEdit.setOnClickListener(this);
		mapa.ulicahelper="";
		mapa.miastohelper="";
	

	}
	private void sprawdzNazwe() throws SQLException {
		// TODO Auto-generated method stub
		BazaSpotkan bz = new BazaSpotkan(this);
		bz.open();
		String[] idRow = null;
		String id = bz.pobierzIDROW();
		idRow = id.split(" ");
		for (int i = 0; i < idRow.length; i++) {
			int IdHelper = Integer.parseInt(idRow[i]);
			String wynik1[] = null;
			String Info = bz.getNazwa(IdHelper) + ";";
			wynik1 = Info.split(";");
			for (int a = 0; a < wynik1.length; a++) {
				String nazwa1 = wynik1[a];
				if(Nazwa.equals(nazwa1)){
					didItWork = false;
					Dialog d = new Dialog(this);
					d.setTitle("Taka nazwa ju¿ istnieje");
					TextView tv = new TextView(this);
					d.setContentView(tv);
					d.show();
				}
			}
			for (int a = 0; a < wynik1.length; a++) {
				wynik1[a] = null;
			}
		}
		bz.close();
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

