package pl.zeromskiego.androidapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewConfiguration;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class mapa extends Activity implements OnMapLongClickListener,
		OnMarkerClickListener, OnClickListener,
		android.location.LocationListener {

	private static final String TAG = null;
	public GoogleMap googleMap;
	public double OnLongClicklat;
	public double OnLongClicklng;
	public static LatLng MarkerPosition;
	BazaSpotkan bz;
	LatLng Wroclaw = new LatLng(51.107885, 17.038538);
	public static String ulicahelper;
	public static String miastohelper;
	long start;
	long stop;
	MapView map;
	List<LatLng> polyz;
	JSONArray array;
	ProgressDialog pDialog;
	Polyline line;
	public static String ida;
	public String markery;
	Circle circle1;
    LocationManager lm;
    Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getApplicationInfo().targetSdkVersion = 10;

		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa);
		bz = new BazaSpotkan(this);
		try {
			// Loading map
			initilizeMap();
			CreateMap();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
		googleMap.setMyLocationEnabled(true);
		googleMap.getMyLocation();
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Wroclaw, 13));
		googleMap.getUiSettings().setZoomControlsEnabled(true);
		googleMap.getUiSettings().setZoomGesturesEnabled(true);
		googleMap.getUiSettings().setCompassEnabled(true);
		googleMap.getUiSettings().setMyLocationButtonEnabled(true);
		googleMap.getUiSettings().setRotateGesturesEnabled(true);
		googleMap.setOnMapLongClickListener(this);
		googleMap.setOnMarkerClickListener(this);
		lm = (LocationManager)this.getSystemService(LOCATION_SERVICE); 
		location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER); //<5>
	    if (location != null) {
	      Log.d(TAG, location.toString());
	      this.onLocationChanged(location); //<6>
	    }
	  
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.wyslij) {
			return true;
		}
		if (item.getItemId() == R.id.dodaj) {
			Intent i = new Intent("pl.zeromskiego.androidapp.DODAJ");
			startActivity(i);
			return true;
		}
		if (item.getItemId() == R.id.wyswietl) {
			Intent i = new Intent("pl.zeromskiego.androidapp.WYSWIETL");
			startActivity(i);
			return true;
		}
		if (item.getItemId() == R.id.odswiez) {
			googleMap.clear();
			new Load().execute();
			CreateMap();
			DrawCircle();
			return true;

		}
		if (item.getItemId() == R.id.onas) {
			Intent i = new Intent("pl.zeromskiego.androidapp.ONAS");
			startActivity(i);
			return true;
		}
		if (item.getItemId() == R.id.exit) {
			onDestroy();
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void DrawCircle() {
		String[] Idmarkery = markery.split(";");
	
		for (int i = 0; i < Idmarkery.length; i++) {
			String element1 = (String) powiadom.al.get(Idmarkery[i]);
			if ("true".equals(element1)) {	
				int element2 = (Integer) powiadom.odl.get(Idmarkery[i]);
				LatLng element3 = (LatLng) powiadom.poz.get(Idmarkery[i]);
				double elat = element3.latitude;
				double elng = element3.longitude;
				CircleOptions circleOptions1 = new CircleOptions().center(
				new LatLng(elat, elng)).radius(element2).zIndex(i);
				circle1 = googleMap.addCircle(circleOptions1);
				Log.v(TAG,"nazwa kolo" + circle1.getZIndex());
			}else if ("false".equals(element1)) {
				LatLng srodekk=circle1.getCenter();
				LatLng element3 = (LatLng) powiadom.poz.get(Idmarkery[i]);
				if(element3 == srodekk)
				circle1.remove();
			}
				
			
		}
	}

	private void CreateMap() {
		// TODO Auto-generated method stub
		double lat = 0.0;
		double lng = 0.0;
		markery = "";


		Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
		try {
			bz.open();
			String[] idRow = null;
			String id = bz.pobierzIDROW();
			idRow = id.split(" ");
			for (int i = 0; i < idRow.length; i++) {
				int IdHelper = Integer.parseInt(idRow[i]);
				String wynik1[] = null;
				String Info = bz.DoMarker(IdHelper);
				wynik1 = Info.split(";");

				String Addres = wynik1[4] + " " + wynik1[3];
				if (Addres != null && !Addres.isEmpty()) {

					List<Address> addressList = geoCoder.getFromLocationName(
							Addres, 1);
					if (addressList != null && addressList.size() > 0) {
						lat = addressList.get(0).getLatitude();
						lng = addressList.get(0).getLongitude();
					}
					if (addressList.size() > 0) {
						addressList.clear();
					}

				}
				Marker m = googleMap.addMarker(new MarkerOptions()
						.title("[" + wynik1[0] + "]" + "<" + wynik1[6] + ">"
								+ " " + wynik1[1])
						.snippet(wynik1[2] + " " + wynik1[5])
						.position(new LatLng(lat, lng)));
				markery += m.getTitle() + ";";
				Addres = null;
				lat = 0.0;
				lng = 0.0;
				for (int a = 0; a < wynik1.length; a++) {
					wynik1[a] = null;
				}
			}
			for (int a = 0; a < idRow.length; a++) {
				idRow[a] = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bz.close();
	}



	@Override
	protected void onResume() {
		super.onResume();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, this);
		googleMap.clear();
		CreateMap();
		DrawCircle();
	}
	@Override
	public void onDestroy()
	{
	    // RUN SUPER | REGISTER ACTIVITY AS NULL IN APP CLAS
	        super.onDestroy();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	public void onMapLongClick(LatLng arg0) {
		OnLongClicklat = arg0.latitude;
		OnLongClicklng = arg0.longitude;
		pokazadres(OnLongClicklat, OnLongClicklng);

	}

	public void pokazadres(double lat, double lng) {

		Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
		try {
			List<Address> address = geoCoder.getFromLocation(OnLongClicklat,
					OnLongClicklng, 1);
			if (address.size() > 0) {
				String addresshelp = "";
				for (int i = 0; i < address.get(0).getMaxAddressLineIndex(); i++) {
					addresshelp += address.get(0).getAddressLine(i) + ";";
				}
				Toast tadres = Toast.makeText(this, addresshelp,
						Toast.LENGTH_LONG);
				tadres.show();
				String wynikadres[] = addresshelp.split(";");
				ulicahelper = wynikadres[0];
				miastohelper = wynikadres[1];
				for (int a = 0; a < wynikadres.length; a++) {
					wynikadres[a] = null;
				}
				Intent i = new Intent("pl.zeromskiego.androidapp.DODAJ");
				startActivity(i);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onMarkerClick(Marker arg0) {

		arg0.hideInfoWindow();
		MarkerPosition = arg0.getPosition();
		ida = arg0.getTitle();
		String title = arg0.getTitle();
		final Location start = googleMap.getMyLocation();
		Log.i(TAG, ida + " " + title);
		AlertDialog MOpcje = new AlertDialog.Builder(mapa.this).create();
		MOpcje.setTitle(title);
		MOpcje.setMessage(arg0.getSnippet());
		MOpcje.setButton("Prowadz do", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String urlTopass = makeURL(MarkerPosition.latitude,
						MarkerPosition.longitude, start.getLatitude(),
						start.getLongitude());
				new connectAsyncTask(urlTopass).execute();

			}
		});
		MOpcje.setButton2("Edytuj", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
		MOpcje.setButton3("Powiadom", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent i = new Intent("pl.zeromskiego.androidapp.POWIADOM");
				startActivity(i);

			}

		});
		MOpcje.show();
		return true;
	}

	private class connectAsyncTask extends AsyncTask<Void, Void, String> {
		private ProgressDialog progressDialog;
		String url;

		connectAsyncTask(String urlPass) {
			url = urlPass;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(mapa.this);
			progressDialog.setMessage("Fetching route, Please wait...");
			progressDialog.setIndeterminate(true);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(Void... params) {
			JSONParser jParser = new JSONParser();
			String json = jParser.getJSONFromUrl(url);
			return json;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progressDialog.hide();
			if (result != null) {
				drawPath(result);
			}
		}
	}

	public String makeURL(double sourcelat, double sourcelog, double destlat,
			double destlog) {
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.googleapis.com/maps/api/directions/json");
		urlString.append("?origin=");// from
		urlString.append(Double.toString(sourcelat));
		urlString.append(",");
		urlString.append(Double.toString(sourcelog));
		urlString.append("&destination=");// to
		urlString.append(Double.toString(destlat));
		urlString.append(",");
		urlString.append(Double.toString(destlog));
		urlString.append("&sensor=false&mode=driving&alternatives=true");
		return urlString.toString();
	}

	public class JSONParser {

		InputStream is = null;
		JSONObject jObj = null;
		String json = "";

		// constructor
		public JSONParser() {
		}

		public String getJSONFromUrl(String url) {

			// Making HTTP request
			try {
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}

				json = sb.toString();
				is.close();
			} catch (Exception e) {
				Log.e("Buffer Error", "Error converting result " + e.toString());
			}
			return json;

		}
	}

	public void drawPath(String result) {
		if (line != null) {
			googleMap.clear();
		}

		try {
			// Tranform the string into a json object
			final JSONObject json = new JSONObject(result);
			JSONArray routeArray = json.getJSONArray("routes");
			JSONObject routes = routeArray.getJSONObject(0);
			JSONObject overviewPolylines = routes
					.getJSONObject("overview_polyline");
			String encodedString = overviewPolylines.getString("points");
			List<LatLng> list = decodePoly(encodedString);

			for (int z = 0; z < list.size() - 1; z++) {
				LatLng src = list.get(z);
				LatLng dest = list.get(z + 1);
				line = googleMap.addPolyline(new PolylineOptions()
						.add(new LatLng(src.latitude, src.longitude),
								new LatLng(dest.latitude, dest.longitude))
						.width(5).color(Color.BLUE).geodesic(true));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<LatLng> decodePoly(String encoded) {

		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			poly.add(p);
		}

		return poly;
	}

	public class Load extends AsyncTask<String, Integer, String> {

		ProgressDialog dialog;

		protected void onPreExecute() {
			dialog = new ProgressDialog(mapa.this);
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setMax(50);
			dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			for (int i = 0; i < 50; i++) {
				publishProgress(1);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			dialog.dismiss();
			return null;
		}

		protected void onProgressUpdate(Integer... progress) {
			dialog.incrementProgressBy(progress[0]);

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(Location l) {
		// TODO Auto-generated method stub    
		String[] Idmarkery = markery.split(";");

		for (int i = 0; i < Idmarkery.length; i++) {
			String element1 = (String) powiadom.al.get(Idmarkery[i]);
			if ("true".equals(element1)) {
				int element2 = (Integer) powiadom.odl.get(Idmarkery[i]);
				LatLng element3 = (LatLng) powiadom.poz.get(Idmarkery[i]);
				float[] distance = new float[2];
				Location.distanceBetween(element3.latitude, element3.longitude,
						l.getLatitude(), l.getLongitude(), distance);
				if (distance[0] < element2) {
					powiadom.al.put(Idmarkery[i], "false");
					String element4 = (String) powiadom.al.get(Idmarkery[i]);
					if(element4.equals("false")){
						LatLng srodekk=circle1.getCenter();
						if(element3 == srodekk)
						circle1.remove();
						Intent a = new Intent("pl.zeromskiego.androidapp.ALARM");
						startActivity(a);
					}
				}
					
				}

			}

		}

	

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}
}
