package pl.zeromskiego.androidapp;

import java.sql.SQLException;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.widget.TextView;

public class BazaSpotkan {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAZWA = "nazwa_spotkania";
	public static final String KEY_OPIS = "opis_spotkania";
	public static final String KEY_MIEJSCE = "miejsce_spotkania";
	public static final String KEY_ADRES = "adres_spotkania";
	public static final String KEY_DATA = "data_spotkania";
	public static final String KEY_TYP = "typ_spotkania";
	public static final String KEY_ALARM = "alarm_spotkania";

	private static final String DATABASE_NAME = "BazaSpotkan";
	private static final String DATABASE_TABLE = "TabelaSpotkan";
	private static final int DATABASE_VERSION = 1;

	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	public static final int Id_N = 1;
	public static final int Id_I = 0;
	public static final int Id_O = 2;
	public static final int Id_M = 3;
	public static final int Id_A = 4;
	public static final int Id_D = 5;
	public static final int Id_T = 6;
	public static final int Id_Al = 7;

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAZWA
					+ " TEXT NOT NULL, " + KEY_OPIS + " TEXT NOT NULL, "
					+ KEY_MIEJSCE + " TEXT NOT NULL, " + KEY_ADRES + " TEXT, "
					+ KEY_DATA + " TEXT, " + KEY_TYP
					+ " TEXT, " + KEY_ALARM + " TEXT);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXITSTS " + DATABASE_NAME);
			onCreate(db);
		}

	}

	public BazaSpotkan(Context c) {
		ourContext = c;
	}

	public BazaSpotkan open() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourHelper.close();
	}

	public long createEntry(String nazwa, String opis, String miejsce,
			String adres, String data, String typ, String alarm) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAZWA, nazwa);
		cv.put(KEY_OPIS, opis);
		cv.put(KEY_MIEJSCE, miejsce);
		cv.put(KEY_ADRES, adres);
		cv.put(KEY_DATA, data);
		cv.put(KEY_TYP, typ);
		cv.put(KEY_ALARM, alarm);

		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public String getData() {
		// TODO Auto-generated method stub

		String[] columns = new String[] { KEY_ROWID, KEY_NAZWA, KEY_OPIS,
				KEY_MIEJSCE, KEY_ADRES, KEY_DATA, KEY_TYP,
				KEY_ALARM };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null, null);
		String result = "";

		int iRow = c.getColumnIndex(KEY_ROWID);
		int iNazwa = c.getColumnIndex(KEY_NAZWA);
		int iOpis = c.getColumnIndex(KEY_OPIS);
		int iMiejsce = c.getColumnIndex(KEY_MIEJSCE);
		int iAdres = c.getColumnIndex(KEY_ADRES);
		int iData = c.getColumnIndex(KEY_DATA);
		int iTyp = c.getColumnIndex(KEY_TYP);
		int iAlarm = c.getColumnIndex(KEY_ALARM);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iRow) + " " + c.getString(iNazwa)
					+ " " + c.getString(iOpis) + " " + c.getString(iMiejsce)
					+ " " + c.getString(iAdres)
					+ " " + c.getString(iData) + " " + c.getString(iTyp) + " "
					+ c.getString(iAlarm) + "\n";
		}

		return result;
	}

	public String getNazwa(long l) throws SQLException {
		String[] columns = new String[] { KEY_ROWID, KEY_NAZWA, KEY_OPIS,
				KEY_MIEJSCE, KEY_ADRES, KEY_DATA, KEY_TYP,
				KEY_ALARM };

		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "="
				+ l, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			String Nazwa = c.getString(1);
			return Nazwa;
		}
		return null;
	}

	public String getOpis(long l) throws SQLException {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_NAZWA, KEY_OPIS,
				KEY_MIEJSCE, KEY_ADRES, KEY_DATA, KEY_TYP,
				KEY_ALARM };

		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "="
				+ l, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			String Opis = c.getString(2);
			return Opis;
		}
		return null;
	}

	public String getMiejsce(long l) throws SQLException {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_NAZWA, KEY_OPIS,
				KEY_MIEJSCE, KEY_ADRES, KEY_DATA, KEY_TYP,
				KEY_ALARM };

		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "="
				+ l, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			String Miejsce = c.getString(3);
			return Miejsce;
		}
		return null;
	}

	public String getAdres(long l) throws SQLException {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_NAZWA, KEY_OPIS,
				KEY_MIEJSCE, KEY_ADRES, KEY_DATA, KEY_TYP,
				KEY_ALARM };

		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "="
				+ l, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			String Adres = c.getString(4);
			return Adres;
		}
		return null;
	}

	public String getNrdomu(long l) throws SQLException {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_NAZWA, KEY_OPIS,
				KEY_MIEJSCE, KEY_ADRES, KEY_DATA, KEY_TYP,
				KEY_ALARM };

		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "="
				+ l, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			String Nrdomu = c.getString(5);
			return Nrdomu;
		}
		return null;
	}

	public String getD(long l) throws SQLException {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_NAZWA, KEY_OPIS,
				KEY_MIEJSCE, KEY_ADRES, KEY_DATA, KEY_TYP,
				KEY_ALARM };

		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "="
				+ l, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			String d = c.getString(6);
			return d;
		}
		return null;
	}

	public void updateSpotkanie(long lRow, String nazwa, String opis,
			String miejsce, String adres, String data, String typ,
			String alarm) throws SQLException {
		// TODO Auto-generated method stub
		ContentValues cvUp = new ContentValues();
		cvUp.put(KEY_NAZWA, nazwa);
		cvUp.put(KEY_OPIS, opis);
		cvUp.put(KEY_MIEJSCE, miejsce);
		cvUp.put(KEY_ADRES, adres);
		cvUp.put(KEY_DATA, data);
		cvUp.put(KEY_TYP, typ);
		cvUp.put(KEY_ALARM, alarm);
		ourDatabase.update(DATABASE_TABLE, cvUp, KEY_ROWID + "=" + lRow, null);
	}

	public void deleteSpotkanie(String lRow1) throws SQLException {
		// TODO Auto-generated method stub
		ContentValues cvUp = new ContentValues();
		cvUp.put(KEY_NAZWA, "null");
		cvUp.put(KEY_OPIS, "null");
		cvUp.put(KEY_MIEJSCE, "null");
		cvUp.put(KEY_ADRES, "null");
		cvUp.put(KEY_DATA, "null");
		cvUp.put(KEY_ALARM, "null");
		
		
		ourDatabase.update(DATABASE_TABLE, cvUp, KEY_ROWID + "=" + lRow1, null);
		ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + lRow1, null);
	}
	public String pobierzIDROW() throws SQLException{
		String[] columns = new String[] { KEY_ROWID, KEY_NAZWA, KEY_OPIS,
				KEY_MIEJSCE, KEY_ADRES, KEY_DATA, KEY_TYP,
				KEY_ALARM };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null, null);
		String result = "";

		int iRow = c.getColumnIndex(KEY_ROWID);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iRow) + " " ;
		}

		return result;
	}
	
	/*
	 * public Baza getBaza(long idd){ String[] columns = new String[] {
	 * KEY_ROWID, KEY_NAZWA, KEY_OPIS, KEY_MIEJSCE, KEY_ADRES, KEY_NRDOMU,
	 * KEY_DATA, KEY_TYP, KEY_ALARM }; Cursor c =
	 * ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + idd, null,
	 * null, null, null, null); Baza task = null; if(c != null &&
	 * c.moveToFirst()){ String Nazwa = c.getString(Id_N); String Opis =
	 * c.getString(Id_O); String Miejsce = c.getString(Id_M); String Adres =
	 * c.getString(Id_A); String Data =
	 * c.getString(Id_D); String Typ = c.getString(Id_T); String Alarm =
	 * c.getString(Id_Al); task = new
	 * Baza(idd,Nazwa,Opis,Miejsce,Adres,Data,Typ,Alarm); } return task;
	 * }
	 */
	public String DoMarker(int l) throws SQLException{
		
		String[] columns = new String[] { KEY_ROWID, KEY_NAZWA, KEY_OPIS,
				KEY_MIEJSCE, KEY_ADRES, KEY_DATA, KEY_TYP,
				KEY_ALARM };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + " = " + l , null, null,
				null, null, null);
		String Info = "";
		if(c != null){
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iNazwa = c.getColumnIndex(KEY_NAZWA);
		int iOpis = c.getColumnIndex(KEY_OPIS);
		int iMiejsce = c.getColumnIndex(KEY_MIEJSCE);
		int iAdres = c.getColumnIndex(KEY_ADRES);
		int iData = c.getColumnIndex(KEY_DATA);
		int iTyp = c.getColumnIndex(KEY_TYP);
		int iAlarm = c.getColumnIndex(KEY_ALARM);
		
		if (c != null) {
			c.moveToFirst();
			Info =  c.getString(iRow) + ";" + c.getString(iNazwa)
					+ ";" + c.getString(iOpis) + ";" + c.getString(iMiejsce)
					+ ";" + c.getString(iAdres)
					+ ";" + c.getString(iData) + ";" + c.getString(iTyp) + ";"
					+ c.getString(iAlarm);
			}
		}
		return Info;
	}
	public String GetMaks(){
		
		String[] columns = new String[] { KEY_ROWID, KEY_NAZWA, KEY_OPIS,
				KEY_MIEJSCE, KEY_ADRES, KEY_DATA, KEY_TYP,
				KEY_ALARM };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, KEY_ROWID + " DESC", "1");
		
		if (c != null) {
			c.moveToFirst();
			String Maks = c.getString(0);
			return Maks;
		}
		return null;
		
	}
}
