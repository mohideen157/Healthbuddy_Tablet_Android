package indg.com.cover2protect.views.activity.deviceConnection.bmiScale.lifesense.ble.ui;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.legacy.app.ActionBarDrawerToggle;

import com.lifesense.ble.LsBleManager;
import com.lifesense.ble.bean.LsDeviceInfo;

import org.jetbrains.annotations.NotNull;

import indg.com.cover2protect.R;
import indg.com.cover2protect.baseAeglOrbs.Base2ActivityDefault;
import indg.com.cover2protect.baseAeglOrbs.W3Obj;
import indg.com.cover2protect.util.SharedPrefUtils;
import indg.com.cover2protect.views.activity.deviceConnection.bmiScale.FragDialougeNewUserDetails;
import indg.com.cover2protect.views.activity.deviceConnection.bmiScale.FragDialougeWeightScaleData;
import indg.com.cover2protect.views.activity.deviceConnection.bmiScale.lifesense.ble.ui.menu.AlarmClockFragment;
import indg.com.cover2protect.views.activity.deviceConnection.bmiScale.lifesense.ble.ui.menu.DeviceUserFragment;
import indg.com.cover2protect.views.activity.deviceConnection.bmiScale.lifesense.ble.ui.menu.PairedDeviceListFragment;
import indg.com.cover2protect.views.activity.deviceConnection.bmiScale.lifesense.ble.ui.menu.ScanFilterFragment;
import indg.com.cover2protect.views.activity.deviceConnection.bmiScale.lifesense.ble.ui.menu.SearchDeviceFragment;
import indg.com.cover2protect.views.activity.deviceConnection.bmiScale.lifesense.ble.ui.menu.adapter.NavDrawerListAdapter;
import indg.com.cover2protect.views.activity.deviceConnection.bmiScale.lifesense.ble.ui.menu.model.NavDrawerItem;
import indg.com.cover2protect.views.activity.deviceConnection.bmiScale.lifesense.ble.ui.view.ConnectDeviceFragment;
import java.util.ArrayList;

@SuppressLint("NewApi")
public class ActWeightScale extends Base2ActivityDefault
		implements FragDialougeWeightScaleData.InterfaceResponse,
		PairedDeviceListFragment.BmiHistoricInterface,
		FragDialougeNewUserDetails.InterfaceResponse{

	@Override
	public void onHistoricItemClicked(@NotNull W3Obj w3obj) {


		Bundle args = new Bundle();
		args.putSerializable("weightData", w3obj);


		FragmentManager fm = getFragmentManager();
		FragDialougeWeightScaleData dialogFragment = new FragDialougeWeightScaleData ();
		dialogFragment.setArguments(args);
		dialogFragment.show(fm, "FragmentWeightData");




	}

	private static final int MSG_FRAGMENT_POSITION_USER_INFO=0;
	private static final int MSG_FRAGMENT_POSITION_SCAN_FILTER=1;
	private static final int MSG_FRAGMENT_POSITION_ALARM_CLOCK=2;
	private static final int MSG_FRAGMENT_POSITION_SCAN_BARCODE=3;
	private static final int MSG_FRAGMENT_POSITION_SEARCH_DEVICE=4;
	private static final int MSG_FRAGMENT_POSITION_PAIRED_DEVICE_LIST=5;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;

	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	private Handler screenFragmentHandler;
	private LsBleManager mLsBleManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

		setContentView(R.layout.activity_main_weight);

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));


		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));


		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);


		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
				)
		{
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(5,true);
		}

		//
		mLsBleManager= LsBleManager.newInstance();
		mLsBleManager.initialize(getApplicationContext());

		screenFragmentHandler=new ScreenFragmentHandler(getMainLooper());
		//get LSDeviceBluetooth SDK version
		System.err.println("Bluetooth SDK Version:"+ LsBleManager.SDK_VERSION);


		if(!spGetter().getBoolean(SharedPrefUtils.USER_DETAILS_SAVED,false)){

			FragmentManager fm = getFragmentManager();
			FragDialougeNewUserDetails dialogFragment = new FragDialougeNewUserDetails ();
			dialogFragment.setCancelable(false);
			dialogFragment.show(fm, "NewUser");

		}

		}



	@Override
	protected void onResume() {
		super.onResume();

	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu){

		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId())
		{
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);

		return super.onPrepareOptionsMenu(menu);
	}



	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);

	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private void displayView(Fragment targetFragment)
	{
		if(targetFragment!=null)
		{
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction ft=fragmentManager.beginTransaction();
			ft.replace(R.id.frame_container, targetFragment).commit();
		}

	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position,boolean firstLoad) {


		Fragment fragment = null;
		switch (position) {
		case MSG_FRAGMENT_POSITION_USER_INFO:
			fragment=new DeviceUserFragment();
			break;
		case MSG_FRAGMENT_POSITION_SCAN_FILTER:
			fragment = new ScanFilterFragment();

			break;
		case MSG_FRAGMENT_POSITION_ALARM_CLOCK:
			fragment =new AlarmClockFragment();
			break;
		case MSG_FRAGMENT_POSITION_SEARCH_DEVICE:
			fragment =  new SearchDeviceFragment();
			break;
		case MSG_FRAGMENT_POSITION_PAIRED_DEVICE_LIST:
			fragment = new PairedDeviceListFragment();
			break;

		default:
			break;
		}

		if (fragment != null)
		{
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction ft=fragmentManager.beginTransaction();

			if(!firstLoad)
			{
				ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
			}


			ft.replace(R.id.frame_container, fragment).commit();

			updateDrawerLayout(position);



		} else {
			// error in creating fragment
			Log.e("ActWeightScale2", "Error in creating fragment");
		}
	}

	/**
	 * @param position
	 */
	private void updateDrawerLayout(int position)
	{
		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		mDrawerList.setSelection(position);
		setTitle(navMenuTitles[position]);

		mDrawerLayout.closeDrawer(mDrawerList);

	}

	@Override
	public void notificationItemSelected(int it) {

	}

	@Override
	public void dialogClosed() {

		this.finish();

		showToast("Need the above details to proceed!");

	}


	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			System.err.println("选择菜单=============");
			// display view for selected nav drawer item
			displayView(position,false);
		}
	}

	public Handler getScreenFragmentHandler()
	{
		return screenFragmentHandler;
	}

	public class ScreenFragmentHandler extends Handler
	{
		public ScreenFragmentHandler(Looper myLooper)
		{
			super(myLooper);
		}

		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg)
		{
			switch(msg.arg1)
			{
				case ScreenFragmentMessage.MSG_SCREEN_FRAGMENT_CONNECT_DEVICE:
				{
					LsDeviceInfo deviceInfo=(LsDeviceInfo) msg.obj;

					Fragment connectFragment = new ConnectDeviceFragment();

					final Bundle args = new Bundle();
					args.putParcelable("LS_DEVICE_INFO", deviceInfo);
					connectFragment.setArguments(args);
					displayView(connectFragment);
				}break;
				case ScreenFragmentMessage.MSG_ADD_PRODUCT_BARCODE:
				{
					Fragment searchFragment = new SearchDeviceFragment();
					displayView(searchFragment);
					updateDrawerLayout(MSG_FRAGMENT_POSITION_SEARCH_DEVICE);
				}break;
				case ScreenFragmentMessage.MSG_SHOW_PAIRED_DEVICE_LIST:
				{

					Fragment deviceListFragment = new PairedDeviceListFragment();
					displayView(deviceListFragment);
					updateDrawerLayout(MSG_FRAGMENT_POSITION_PAIRED_DEVICE_LIST);

				}break;
				case ScreenFragmentMessage.MSG_ADD_DEVICE:
				{
					Fragment addDeviceFragment = new SearchDeviceFragment();
					displayView(addDeviceFragment);
					updateDrawerLayout(MSG_FRAGMENT_POSITION_SEARCH_DEVICE);
				}break;
			}
		}
	}

}
