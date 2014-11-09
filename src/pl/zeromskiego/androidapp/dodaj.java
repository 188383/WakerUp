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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bDodaj:
			boolean didItWork = true;
			try {
				String Alarm = "";
				String Typ = "";
				String Nazwa = etNazwa.getText().toString();
				String Opis = etOpis.getText().toString();
				String Miejsce = etMiejsce.getText().toString();
				String Adres = etAdres.getText().toString();
				String Data = etData.getText().toString();
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

				BazaSpotkan entry = new BazaSpotkan(dodaj.this);
				entry.open();
				entry.createEntry(Nazwa, Opis, Miejsce, Adres, Data,
						Typ, Alarm);
				entry.close();

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

			break;
		case R.id.bUsun:
			try {
				String sRow1 = String.valueOf(idRow.getSelectedItem());
				BazaSpotkan bz2 = new BazaSpotkan(this);
				bz2.open();
				bz2.deleteSpotkanie(sRow1);
				bz2.close();
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
				try {
					addItemsOnSpinner();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case R.id.bGetinfo:
			try {
				String s = String.valueOf(idRow.getSelectedItem());
				long l = Long.parseLong(s);
				BazaSpotkan bz = new BazaSpotkan(this);
				bz.open();
				String retnazwa = bz.getNazwa(l);
				String retopis = bz.getOpis(l);
				String retMiejsce = bz.getMiejsce(l);
				String retAdres = bz.getAdres(l);
				String retData = bz.getD(l);
				bz.close();
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
			try {
				String Nazwa = etNazwa.getText().toString();
				String Opis = etOpis.getText().toString();
				String Miejsce = etMiejsce.getText().toString();
				String Adres = etAdres.getText().toString();
				String Data = etData.getText().toString();
				String sRow = String.valueOf(idRow.getSelectedItem());
				String Alarm = "";
				String Typ = "";
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
				BazaSpotkan bz1 = new BazaSpotkan(this);
				bz1.open();
				bz1.updateSpotkanie(lRow, Nazwa, Opis, Miejsce, Adres,
						Data, Typ, Alarm);
				bz1.close();
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
			break;
		}
		

	}
}

