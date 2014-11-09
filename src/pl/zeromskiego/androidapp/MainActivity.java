package pl.zeromskiego.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


/*test czy dziala mi git */
public class MainActivity extends Activity{

	MediaPlayer startSong;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startSong = MediaPlayer.create(MainActivity.this, R.raw.song);
		startSong.start();
	
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(3000);
					
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally{
					Intent StartMapa = new Intent("pl.zeromskiego.androidapp.STARTINGPOINT");
					startActivity(StartMapa);
				}
			}
		};
		timer.start();	
	}
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		startSong.release();
		finish();
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
