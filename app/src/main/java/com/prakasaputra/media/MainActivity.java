package com.prakasaputra.media;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.ProgressBar;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.os.Bundle;
import java.io.InputStream;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;


public class MainActivity extends  AppCompatActivity  { 
	
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private FloatingActionButton _fab;
	private double n = 0;
	private double r = 0;
	private String folder = "";
	private String folderName = "";
	
	private ArrayList<String> filelist = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> imagelist = new ArrayList<>();
	private ArrayList<String> folderlist = new ArrayList<>();
	
	private ProgressBar progressbar1;
	private ListView listview1;
	
	private Intent intent = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		_app_bar = (AppBarLayout) findViewById(R.id._app_bar);
		_coordinator = (CoordinatorLayout) findViewById(R.id._coordinator);
		_toolbar = (Toolbar) findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		_fab = (FloatingActionButton) findViewById(R.id._fab);
		
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		listview1 = (ListView) findViewById(R.id.listview1);
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("whatsapp://send?text=Hi, I Like You Mas Prakasa_Jr64&phone=6285966346141d"));
				startActivity(intent);
			}
		});
	}
	
	private void initializeLogic() {
		_NavStatusBarColor("#448AFF", "#FFFFFF");
		(new GETALLTask()).execute();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _semuaFileFotokas (final String _filePath) {
		FileUtil.listDir(_filePath, filelist);
		n = 0;
		for(int _repeat112 = 0; _repeat112 < (int)(filelist.size()); _repeat112++) {
			if (FileUtil.isFile(filelist.get((int)(n)))) {
				if (filelist.get((int)(n)).endsWith(".mp4") || (filelist.get((int)(n)).endsWith(".png") || filelist.get((int)(n)).endsWith(".jpg"))) {
					{
						HashMap<String, Object> _item = new HashMap<>();
						_item.put("media", filelist.get((int)(n)));
						imagelist.add((int)0, _item);
					}
					
				}
			}
			else {
				if (filelist.get((int)(n)).contains(".")) {
					
				}
				else {
					if (FileUtil.isDirectory(filelist.get((int)(n)))) {
						folderlist.add(filelist.get((int)(n)));
					}
				}
			}
			n++;
		}
		_Carifolderkas();
	}
	
	
	public void _Carifolderkas () {
		r++;
		if (r < (folderlist.size() + 1)) {
			folder = folderlist.get((int)(r - 1));
			folderName = Uri.parse(folder.substring((int)(0), (int)(folder.length() - 1))).getLastPathSegment();
			if (folderName.toLowerCase().startsWith("sys")) {
				_Carifolderkas();
			}
			else {
				if (folderName.toLowerCase().startsWith(".")) {
					_Carifolderkas();
				}
				else {
					_semuaFileFotokas(folderlist.get((int)(r - 1)));
				}
			}
		}
	}
	
	
	public void _extra () {
	}
	
	private class GETALLTask extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected void onPreExecute() {
			progressbar1.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected Void doInBackground(Void... path) {
			r = 0;
			folderlist.clear();
			imagelist.clear();
			_semuaFileFotokas(FileUtil.getExternalStorageDir());
			return null;
		}
		
		 @Override
		protected void onProgressUpdate(Void... values) {
		}
		
		@Override
		protected void onPostExecute(Void param){
			listview1.setAdapter(new Listview1Adapter(imagelist));
			((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			progressbar1.setVisibility(View.GONE);
			
		}
		//you can use sketchware pro for gridview... and you can use lottie no use progressbar... thank you, Mr Prakasa_Jr64
		
		//GitHub: MrTamfanX
		//YouTube: Warung Cyber
	}
	
	
	public void _NavStatusBarColor (final String _color1, final String _color2) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
			Window w = this.getWindow();	w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);	w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			w.setStatusBarColor(Color.parseColor("#" + _color1.replace("#", "")));	w.setNavigationBarColor(Color.parseColor("#" + _color2.replace("#", "")));
		}
	}
	
	
	public class Listview1Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.prakasa, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final ImageView imageview2 = (ImageView) _view.findViewById(R.id.imageview2);
			
			if (imagelist.get((int)_position).get("media").toString().endsWith(".mp4")) {
				Bitmap bMap = ThumbnailUtils.createVideoThumbnail(imagelist.get(_position).get("media").toString(), android.provider.MediaStore.Video.Thumbnails.FULL_SCREEN_KIND); imageview1.setImageBitmap(bMap);
				imageview2.setVisibility(View.VISIBLE);
			}
			else {
				imageview1.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(imagelist.get((int)_position).get("media").toString(), 1024, 1024));
				imageview2.setVisibility(View.GONE);
			}
			
			return _view;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}