package pl.zeromskiego.androidapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import java.util.HashMap;

public class powiadom extends Activity implements OnClickListener {

	Spinner odleglosc, dzwonek, kontakty;
	Button zapisz, anuluj, dodaj;
	CheckBox alarm, powiadom;
	public static String muzyka, listaNum;
	public static HashMap al = new HashMap();
	public static HashMap odl = new HashMap();
	public static HashMap poz = new HashMap();
	public static HashMap pow = new HashMap();	
	public static HashMap tel = new HashMap();
	String num;
	List<String> list1 = new ArrayList<String>();;

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
		dodaj = (Button) findViewById(R.id.dodaj);
		kontakty = (Spinner) findViewById(R.id.kontakty);
		powiadom = (CheckBox) findViewById(R.id.Powiadomic);

		zapisz.setOnClickListener(this);
		anuluj.setOnClickListener(this);
		dodaj.setOnClickListener(this);

		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i <= 5000; i = i + 100) {
			list.add(i);
		}
		ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		odleglosc.setAdapter(dataAdapter);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.dzwonek, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dzwonek.setAdapter(adapter);

		ContentResolver cr = getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String id = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				Log.i("Names", name);
				if (Integer
						.parseInt(cur.getString(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					// Query phone here. Covered next
					Cursor phones = getContentResolver().query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = " + id, null, null);
					while (phones.moveToNext()) {
						String phoneNumber = phones
								.getString(phones
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						Log.i("Number", phoneNumber);
						list1.add(phoneNumber); // mozna dopisac name bedzie
												// sciagac nazwy
					}
					phones.close();
				}
			}
		}
		ArrayAdapter<String> numer = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list1);
		numer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		kontakty.setAdapter(numer);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.zapisz:
			if (alarm.isChecked()) {
				muzyka = (String) dzwonek.getSelectedItem();
				int odle = (Integer) odleglosc.getSelectedItem();
				al.put(mapa.ida, "true");
				odl.put(mapa.ida, odle);
				poz.put(mapa.ida, mapa.MarkerPosition);
				finish();
			}else {
				poz.put(mapa.ida, mapa.MarkerPosition);
				al.put(mapa.ida, "false");
				finish();
			}
			if (powiadom.isChecked()) {
				int odle = (Integer) odleglosc.getSelectedItem();
				pow.put(mapa.ida, "true");
				odl.put(mapa.ida, odle);
				poz.put(mapa.ida, mapa.MarkerPosition);
				tel.put(mapa.ida, listaNum);
				listaNum = "";
			}else {
				poz.put(mapa.ida, mapa.MarkerPosition);
				pow.put(mapa.ida, "false");
				finish();
			}
			
			break;
		case R.id.anuluj:
			finish();
			break;
		case R.id.dodaj:
			num = (String) kontakty.getSelectedItem();
			listaNum += num + ";";
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
	public void onDestroy() {
		// RUN SUPER | REGISTER ACTIVITY AS NULL IN APP CLAS
		super.onDestroy();

	}

}
