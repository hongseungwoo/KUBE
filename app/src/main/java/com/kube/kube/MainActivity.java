package com.kube.kube;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.kube.kube.fragments.ConnectionFragment;
import com.kube.kube.fragments.IntroFragment;
import com.kube.kube.fragments.OnFragmentListener;
import com.kube.kube.fragments.PseudoFragment;
import com.kube.kube.fragments.TestModeDialogFragment;
import com.kube.kube.fragments.WorkspaceFragment;
import com.kube.kube.service.MyService;
import com.kube.kube.utils.AppSettings;
import com.kube.kube.utils.Constants;
import com.kube.kube.utils.Logs;
import com.kube.kube.utils.RecycleUtils;
import com.kube.kube.fragments.InputDialogFragment;
import com.kube.kube.workspace.WorkspaceAdapter;

import java.util.Timer;
import java.util.TimerTask;

/*
*
* //TODO literally the main activity. Calls three fragments(intro, conneciton, workspace)
* //TODO and binds service which communicates with KUBE module device
*/
public class MainActivity extends FragmentActivity implements OnFragmentListener {

    // Debugging
    private static final String TAG = "BLEChatActivity";

    // Context, System
    private Context mContext;
    private MyService mService;
    private ActivityHandler mActivityHandler;
    private OnFragmentListener mFragmentListener;

    // Global

    // UI stuff
    private ViewPager mViewPager;

//    private ImageView mImageBT = null;
//    private TextView mTextStatus = null;

    // Refresh timer
    private Timer mRefreshTimer = null;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction = null;
    private Fragment mFragment = null;

    private WorkspaceFragment mWorkspaceFragment;
//    private WorkspaceAdapter mWorkspaceAdapter;

    private String numOfModules;


    /*****************************************************
     *	 Overrided methods
     ******************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //----- System, Context
        mContext = this;	//.getApplicationContext();
        mActivityHandler = new ActivityHandler();
        AppSettings.initializeAppSettings(mContext);


        // Set up the action bar.
//        final ActionBar actionBar = getActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the primary sections of the app.

        //TODD

        mFragmentListener = this;

        mFragmentManager = getFragmentManager();
        changeFragment(new IntroFragment(mActivityHandler, this));

        mWorkspaceFragment = new WorkspaceFragment(getApplicationContext(), mActivityHandler, this);
//        mWorkspaceFragment = new WorkspaceFragment(getApplicationContext(), mActivityHandler, this, mWorkspaceAdapter);

//        mSectionsPagerAdapter = new FragmentAdapter(mFragmentManager, mContext, this, mActivityHandler);

        // Set up the ViewPager with the sections adapter.
//        mViewPager = (ViewPager) findViewById(R.id.pager);
//        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding tab.
//        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                actionBar.setSelectedNavigationItem(position);
//            }
//        });

        // For each of the sections in the app, add a tab to the action bar.
//        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
//            // Create a tab with text corresponding to the page title defined by the adapter.
//            actionBar.addTab(actionBar.newTab()
//                    .setText(mSectionsPagerAdapter.getPageTitle(i))
//                    .setTabListener(this));
//        }

//        // Setup views
//        mImageBT = (ImageView) findViewById(R.id.status_title);
//        mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_invisible));
//        mTextStatus = (TextView) findViewById(R.id.status_text);
//        mTextStatus.setText(getResources().getString(R.string.bt_state_init));

        // Do data initialization after service started and binded
        doStartService();
    }

    @Override
    public synchronized void onStart() {
        super.onStart();
    }

    @Override
    public synchronized void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        // Stop the timer
        if(mRefreshTimer != null) {
            mRefreshTimer.cancel();
            mRefreshTimer = null;
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        finalizeActivity();
    }

    @Override
    public void onLowMemory (){
        super.onLowMemory();
        // onDestroy is not always called when applications are finished by Android system.
        finalizeActivity();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_scan:
//                // Launch the DeviceListActivity to see devices and do scan
//                doScan();
//                return true;
//            case R.id.action_discoverable:
//                // Ensure this device is discoverable by others
//                ensureDiscoverable();
//                return true;
//        }
//        return false;
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();		// Disable this line to run below code
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        // This prevents reload after configuration changes
        super.onConfigurationChanged(newConfig);
    }

    /**
     * Implements TabListener
     */
//    @Override
//    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//        // When the given tab is selected, switch to the corresponding page in the ViewPager.
//        mViewPager.setCurrentItem(tab.getPosition());
//    }
//
//    @Override
//    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//    }
//
//    @Override
//    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//    }

//    //TODO if you want to send message in fragments, implement "IFragmentListener" and call "OnFragmentCallBack" method
//    @Override
//    public void OnFragmentCallback(int msgType, int arg0, int arg1, String arg2, String arg3, Object arg4) {
//        switch(msgType) {
//            case IFragmentListener.CALLBACK_RUN_IN_BACKGROUND:
//                if(mService != null)
//                    mService.startServiceMonitoring();
//                break;
//            case IFragmentListener.CALLBACK_SEND_MESSAGE:
//                if(mService != null && arg2 != null)
//                    mService.sendMessageToRemote(arg2);
//
//            default:
//                break;
//        }
//    }


    /*****************************************************
     *	Private methods
     ******************************************************/

    /**
     * Service connection
     */
    private ServiceConnection mServiceConn = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder binder) {
            Log.d(TAG, "Activity - Service connected");

            mService = ((MyService.ServiceBinder) binder).getService();

            // Activity couldn't work with mService until connections are made
            // So initialize parameters and settings here. Do not initialize while running onCreate()
            initialize();
        }

        public void onServiceDisconnected(ComponentName className) {
            mService = null;
        }
    };

    /**
     * Start service if it's not running
     */
    private void doStartService() {
        Log.d(TAG, "# Activity - doStartService()");
        startService(new Intent(this, MyService.class));
        bindService(new Intent(this, MyService.class), mServiceConn, Context.BIND_AUTO_CREATE);
    }

    /**
     * Stop the service
     */
    private void doStopService() {
        Log.d(TAG, "# Activity - doStopService()");
        mService.finalizeService();
        stopService(new Intent(this, MyService.class));
    }

    /**
     * Initialization / Finalization
     */
    private void initialize() {
        Logs.d(TAG, "# Activity - initialize()");

        // Use this check to determine whether BLE is supported on the device. Then
        // you can selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.bt_ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        mService.setupService(mActivityHandler);

        // If BT is not on, request that it be enabled.
        // RetroWatchService.setupBT() will then be called during onActivityResult
        if(!mService.isBluetoothEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, Constants.REQUEST_ENABLE_BT);
        }

        // Load activity reports and display
        if(mRefreshTimer != null) {
            mRefreshTimer.cancel();
        }

        // Use below timer if you want scheduled job
        //mRefreshTimer = new Timer();
        //mRefreshTimer.schedule(new RefreshTimerTask(), 5*1000);
    }

    private void finalizeActivity() {
        Logs.d(TAG, "# Activity - finalizeActivity()");

        if(!AppSettings.getBgService()) {
            doStopService();
        } else {
        }

        // Clean used resources
        RecycleUtils.recursiveRecycle(getWindow().getDecorView());
        System.gc();
    }

    /**
     * Launch the DeviceListActivity to see devices and do scan
     * //TODO start scanning bluetooth device
     */
    private void doScan() {
        Intent intent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CONNECT_DEVICE);
    }

    /**
     * Ensure this device is discoverable by others
     */
    private void ensureDiscoverable() {
        if (mService.getBluetoothScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(intent);
        }
    }


    /*****************************************************
     *	Public classes
     ******************************************************/

    /**
     * Receives result from external activity
     */

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logs.d(TAG, "onActivityResult " + resultCode);
//        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
//            if(requestCode == Constants.REQUEST_CODE_OPTION_INPUT){
            if(resultCode == 2) {
                String optionBlock = data.getStringExtra("optionBlock");
                String numOption = data.getStringExtra("numOption");
                String moduleNum = data.getStringExtra("moduleNum");
                Log.d("optionblokc", "   "+optionBlock+ numOption);
                mWorkspaceFragment.inputDatas(optionBlock, numOption, moduleNum);
//                int Block = getBlockIamge(optionBlock);
//                mWorkspaceAdapter.setOption(Block, numOption, moduleNum, curPos);
            }
        }
        switch(requestCode) {
            case Constants.REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // Attempt to connect to the device
                    if(address != null && mService != null) {
                        mService.connectDevice(address);

                    }
                }
                break;

            case Constants.REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a BT session
                    mService.setupBLE();
                    //mWorkspaceFragment.setModuleNum(); << 구현하기
//                    changeFragment(mWorkspaceFragment);
                } else {
                    // User did not enable Bluetooth or an error occured
                    Logs.e(TAG, "BT is not enabled");
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                }
                break;
        }	// End of switch(requestCode)
    }


    //TODO callback method which calls 'changeFragment()'
    @Override
    public void onFragmentCallBack(int msg, int clickedBlock) {
        switch(msg) {
            case Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_INTRO:
                changeFragment(new IntroFragment(mActivityHandler, this));
                break;
            case Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_CONNECTION:
                changeFragment(new ConnectionFragment(mActivityHandler, this));
                break;
            case Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_WORKSPACE:
                changeFragment(mWorkspaceFragment);
                break;
            case Constants.FRAGMENT_CALLBACK_SHOW_DIALOG_INPUT:
//                InputDialogFragment mInputDialogFragment = new InputDialogFragment(mActivityHandler, mFragmentListener,clickedBlock);
//                InputDialogFragment mInputDialogFragment = new InputDialogFragment(mInputFragmentListener, clickedBlock);
                InputDialogFragment mInputDialogFragment = new InputDialogFragment(mFragmentListener, clickedBlock);
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mInputDialogFragment.show(mFragmentTransaction, "INPUT");
// changeFragment(new InputDialogFragment(mActivityHandler, , clickedBlock));
                break;
            case Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_PSUDO:
                 changeFragment(new PseudoFragment(mFragmentListener));
                break;
            case Constants.FRAGMENT_CALLBACK_SHOW_DIALOG_TESTMODE:
                if(Constants.STRING_HELLO_ACK == null) {
                    Toast.makeText(mContext, "Error : no value of numOfModules", Toast.LENGTH_SHORT).show();
                    break;
                }
                TestModeDialogFragment mTestModeDialogFragment = new TestModeDialogFragment(mFragmentListener, Constants.STRING_HELLO_ACK);
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mTestModeDialogFragment.show(mFragmentTransaction, "TEST MODE");
                break;
            case Constants.FRAGMENT_CALLBACK_START_FINDING_BLUETOOTH:
                doScan();
                break;
//                // Receives BT state messages from service
//                // and updates BT state UI
//                case Constants.MESSAGE_BT_STATE_INITIALIZED:
//                    mTextStatus.setText(getResources().getString(R.string.bt_title) + ": " +
//                            getResources().getString(R.string.bt_state_init));
//                    mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_invisible));
//                    break;
//                case Constants.MESSAGE_BT_STATE_LISTENING:
//                    mTextStatus.setText(getResources().getString(R.string.bt_title) + ": " +
//                            getResources().getString(R.string.bt_state_wait));
//                    mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_invisible));
//                    break;
//                case Constants.MESSAGE_BT_STATE_CONNECTING:
//                    mTextStatus.setText(getResources().getString(R.string.bt_title) + ": " +
//                            getResources().getString(R.string.bt_state_connect));
//                    mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_away));
//                    break;
//                case Constants.MESSAGE_BT_STATE_CONNECTED:
//                    if(mService != null) {
//                        String deviceName = mService.getDeviceName();
//                        if(deviceName != null) {
//                            mTextStatus.setText(getResources().getString(R.string.bt_title) + ": " +
//                                    getResources().getString(R.string.bt_state_connected) + " " + deviceName);
//                            mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_online));
//                        } else {
//                            mTextStatus.setText(getResources().getString(R.string.bt_title) + ": " +
//                                    getResources().getString(R.string.bt_state_connected) + " no name");
//                            mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_online));
//                        }
//                    }
//                    break;
//                case Constants.MESSAGE_BT_STATE_ERROR:
//                    mTextStatus.setText(getResources().getString(R.string.bt_state_error));
//                    mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_busy));
//                    break;
//
//                // BT Command status
//                case Constants.MESSAGE_CMD_ERROR_NOT_CONNECTED:
//                    mTextStatus.setText(getResources().getString(R.string.bt_cmd_sending_error));
//                    mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_busy));
//                    break;
//
//                ///////////////////////////////////////////////
//                // When there's incoming packets on bluetooth
//                // do the UI works like below
//                ///////////////////////////////////////////////
//                case Constants.MESSAGE_READ_CHAT_DATA:
////                    if(msg.obj != null) {
////                        ExampleFragment frg = (ExampleFragment) mSectionsPagerAdapter.getItem(FragmentAdapter.FRAGMENT_POS_EXAMPLE);
////                        frg.showMessage((String)msg.obj);
////                    }
//                    break;

            default:
                break;
        }
    }

    @Override
    public void onInputFragmentCallBack(String[] str) {
        mWorkspaceFragment.inputDatas(str[0], str[1], str[2]);
    }

    @Override
    public void onTestModeFragmentCallBack(int id) {
        if(mService != null) {
            Handler h = new Handler();
            boolean inputFlag = false;
            String inputStr = "";
            switch (id) {
                case R.id.testmode_dcimg_1:
                    sendMessageToDevice(Constants.TEST_MESSAGE_DC1);
                    break;
                case R.id.testmode_dcimg_2:
                    sendMessageToDevice(Constants.TEST_MESSAGE_DC2);
                    break;
                case R.id.testmode_dcimg_3:
                    sendMessageToDevice(Constants.TEST_MESSAGE_DC3);
                    break;
                case R.id.testmode_dcimg_4:
                    sendMessageToDevice(Constants.TEST_MESSAGE_DC4);
                    break;
                case R.id.testmode_dcimg_5:
                    sendMessageToDevice(Constants.TEST_MESSAGE_DC5);
                    break;

                case R.id.testmode_smimg_1:
                    sendMessageToDevice(Constants.TEST_MESSAGE_SM1);
                    break;
                case R.id.testmode_smimg_2:
                    sendMessageToDevice(Constants.TEST_MESSAGE_SM2);
                    break;
                case R.id.testmode_smimg_3:
                    sendMessageToDevice(Constants.TEST_MESSAGE_SM3);
                    break;
                case R.id.testmode_smimg_4:
                    sendMessageToDevice(Constants.TEST_MESSAGE_SM4);
                    break;
                case R.id.testmode_smimg_5:
                    sendMessageToDevice(Constants.TEST_MESSAGE_SM5);
                    break;

                case R.id.testmode_ldimg_1:
                    sendMessageToDevice(Constants.TEST_MESSAGE_LD1);
                    break;
                case R.id.testmode_ldimg_2:
                    sendMessageToDevice(Constants.TEST_MESSAGE_LD2);
                    break;
                case R.id.testmode_ldimg_3:
                    sendMessageToDevice(Constants.TEST_MESSAGE_LD3);
                    break;
                case R.id.testmode_ldimg_4:
                    sendMessageToDevice(Constants.TEST_MESSAGE_LD4);
                    break;
                case R.id.testmode_ldimg_5:
                    sendMessageToDevice(Constants.TEST_MESSAGE_LD5);
                    break;

                case R.id.testmode_irimg_1:
                    inputStr = "IR1";
                    inputFlag = true;
                    sendMessageToDevice(Constants.TEST_MESSAGE_IR1);
                    break;
                case R.id.testmode_irimg_2:
                    inputStr = "IR2";
                    inputFlag = true;
                    sendMessageToDevice(Constants.TEST_MESSAGE_IR2);
                    break;
                case R.id.testmode_irimg_3:
                    inputStr = "IR3";
                    inputFlag = true;
                    sendMessageToDevice(Constants.TEST_MESSAGE_IR3);
                    break;
                case R.id.testmode_irimg_4:
                    inputStr = "IR4";
                    inputFlag = true;
                    sendMessageToDevice(Constants.TEST_MESSAGE_IR4);
                    break;
                case R.id.testmode_irimg_5:
                    inputStr = "IR5";
                    inputFlag = true;
                    sendMessageToDevice(Constants.TEST_MESSAGE_IR5);
                    break;

                case R.id.testmode_usimg_1:
                    inputStr = "US1";
                    inputFlag = true;
                    sendMessageToDevice(Constants.TEST_MESSAGE_US1);
                    break;
                case R.id.testmode_usimg_2:
                    inputStr = "US2";
                    inputFlag = true;
                    sendMessageToDevice(Constants.TEST_MESSAGE_US2);
                    break;
                case R.id.testmode_usimg_3:
                    inputStr = "US3";
                    inputFlag = true;
                    sendMessageToDevice(Constants.TEST_MESSAGE_US3);
                    break;
                case R.id.testmode_usimg_4:
                    inputStr = "US4";
                    inputFlag = true;
                    sendMessageToDevice(Constants.TEST_MESSAGE_US4);
                    break;
                case R.id.testmode_usimg_5:
                    inputStr = "US5";
                    inputFlag = true;
                    sendMessageToDevice(Constants.TEST_MESSAGE_US5);
                    break;
            }
            if(inputFlag) {
                final String module = inputStr;
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String received = Constants.RECEIVED_STRING;
                        Toast.makeText(mContext, "[TEST] " + module + " : " + received, Toast.LENGTH_SHORT).show();
                    }
                },2000);
            }
        }
    }



    /*****************************************************
     *	Handler, Callback, Sub-classes
     ******************************************************/

    public class ActivityHandler extends Handler {
        @Override
        public void handleMessage(Message msg)
        {
            switch(msg.what) {
//                case Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_INTRO:
//                    changeFragment(new IntroFragment(mActivityHandler, mFragmentListener));
//                    break;
//                case Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_CONNECTION:
//                    changeFragment(new ConnectionFragment(mActivityHandler, mFragmentListener));
//                    break;
//                case Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_WORKSPACE:
//                    changeFragment(mWorkspaceFragment);
//                    break;
//                case Constants.FRAGMENT_LISTENER_GET_FRAGMENT_INPUT:
//                    String[] rcvStr = (String[]) msg.obj;
//                    mWorkspaceFragment.inputDatas(rcvStr[0], rcvStr[1], rcvStr[2]);
//                    break;

                // Receives BT state messages from service
                // and updates BT state UI
                case Constants.HANDLER_ACTIVITY_RESEND_HELLO:
                    Logs.d("## Handler - Resend HELLO msg");
                    sendMessageToDevice(Constants.COMMAND_HELLO);
                    break;
                case Constants.MESSAGE_BT_STATE_INITIALIZED:
                    Logs.d(":::Service initialized:::");
                    Toast.makeText(mContext, ":::Service initialized:::", Toast.LENGTH_SHORT).show();
                    //                    mTextStatus.setText(getResources().getString(R.string.bt_title) + ": " +
//                            getResources().getString(R.string.bt_state_init));
//                    mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_invisible));
                    break;
                case Constants.MESSAGE_BT_STATE_LISTENING:
                    Logs.d(":::Service listening:::");
                    Toast.makeText(mContext, ":::Service istening:::", Toast.LENGTH_SHORT).show();
//                    mTextStatus.setText(getResources().getString(R.string.bt_title) + ": " +
//                            getResources().getString(R.string.bt_state_wait));
//                    mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_invisible));
                    break;
                case Constants.MESSAGE_BT_STATE_CONNECTING:
                    Logs.d(":::Service connecting:::");
                    Toast.makeText(mContext, ":::Service connecting:::", Toast.LENGTH_SHORT).show();
//                    mTextStatus.setText(getResources().getString(R.string.bt_title) + ": " +
//                            getResources().getString(R.string.bt_state_connect));
//                    mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_away));
                    break;
                case Constants.MESSAGE_BT_STATE_CONNECTED:
                    Logs.d(":::Service connected:::");
                    Toast.makeText(mContext, ":::Service connected.:::", Toast.LENGTH_SHORT).show();
                    if(mService != null) {
                        String deviceName = mService.getDeviceName();

                        //TODO get&store number of modules from main board
                        //first, send "COMMAND_HELLO"
                        Handler h = new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                sendMessageToDevice(Constants.COMMAND_HELLO);
                            }
                        }, 1000);
                        Handler h2 = new Handler();
                        h2.postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                                Constants.MODULE_NUM_STRING = Constants.RECEIVED_STRING;
//                                numOfModules = Constants.RECEIVED_STRING;
//                                if(str.split(":")[0].equals("NUM")) {
//                                    Intent testIntent = new Intent(mContext, new TestModeActivity(str, mActivityHandler).getClass());
//                                    startActivityForResult(testIntent, Constants.REQUEST_TEST_ACTIVITY);
                                    //TODO call changeFragment in ActivityForResult()-"Constants.REQUEST_TEST_ACTIVITY"
                                    changeFragment(mWorkspaceFragment);
//                                }
                            }
                        },2000);
//                        if(deviceName != null) {
//                            mTextStatus.setText(getResources().getString(R.string.bt_title) + ": " +
//                                    getResources().getString(R.string.bt_state_connected) + " " + deviceName);
//                            mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_online));
//                        } else {
//                            mTextStatus.setText(getResources().getString(R.string.bt_title) + ": " +
//                                    getResources().getString(R.string.bt_state_connected) + " no name");
//                            mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_online));
//                        }
                    }
                    break;
                case Constants.MESSAGE_BT_STATE_ERROR:
                    Logs.d(":::Service connected:::");
//                    mTextStatus.setText(getResources().getString(R.string.bt_state_error));
//                    mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_busy));
                    break;

                // BT Command status
                case Constants.MESSAGE_CMD_ERROR_NOT_CONNECTED:
                    Logs.d(":::Service not connected:::");
//                    mTextStatus.setText(getResources().getString(R.string.bt_cmd_sending_error));
//                    mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_busy));
                    break;

                ///////////////////////////////////////////////
                // When there's incoming packets on bluetooth
                // do the UI works like below
                ///////////////////////////////////////////////
                case Constants.MESSAGE_READ_CHAT_DATA:
                    Logs.d(":::Service read chat data:::");
                    if(msg.obj != null) {
                        Constants.RECEIVED_STRING = (String) msg.obj;
                    }
//                    if(msg.obj != null) {
//                        ExampleFragment frg = (ExampleFragment) mSectionsPagerAdapter.getItem(FragmentAdapter.FRAGMENT_POS_EXAMPLE);
//                        frg.showMessage((String)msg.obj);
//                    }
                    break;

                default:
                    break;
            }

            super.handleMessage(msg);
        }
    }	// End of class ActivityHandler


    //TODO switch the fragment 'f'
    private void changeFragment(Fragment f) {
        mFragment = f;
        if(mFragment != null) {
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.layout_activity_main, f);
            mFragmentTransaction.commit();
        }
    }

//    public static WorkspaceFragment afterInput(String a, String b, String c) {
//        WorkspaceFragment fragment = MainActivity.mWorkspaceFragment;
//        Bundle args = new Bundle();
//
//        args.putString(Constants.INPUT_PARAM_1, a);
//        args.putString(Constants.INPUT_PARAM_2, b);
//        args.putString(Constants.INPUT_PARAM_2, c);
//        return fragment;
//    }

    /**
     * Auto-refresh Timer
     */
    private class RefreshTimerTask extends TimerTask {
        public RefreshTimerTask() {}

        public void run() {
            mActivityHandler.post(new Runnable() {
                public void run() {
                    mRefreshTimer = null;
                }
            });
        }
    }

    private void sendMessageToDevice(String message) {
        if(message == null || message.length() < 1) return;

        Logs.d("# sendMessage - SEND : " + message);
        Toast.makeText(mContext, "SEND : " + message, Toast.LENGTH_SHORT).show();
        if(mService != null) {
            if(message.charAt(0) == '#') { // if message is the translated language
                for (int i = 0; i < message.length(); i += 20) {
                    if (i + 20 > message.length())
                        mService.sendMessageToRemote(message.substring(i, message.length()));
                    else
                        mService.sendMessageToRemote(message.substring(i, i + 20));
                    SystemClock.sleep(15);
                }
            } else //if no '#' at first, no longer than 20 words
                mService.sendMessageToRemote(message);
        }
    }


    /**
     *
     * //TODO menu
     */
    private void addOptionMenuItems(Menu menu) {
        menu.clear();
        menu.add(R.id.menu_trans, R.id.menu_trans, Menu.NONE, "전송");
        menu.add(R.id.menu_start, R.id.menu_start, Menu.NONE, "시작");
        menu.add(R.id.menu_stop, R.id.menu_stop, Menu.NONE, "중지");
        menu.add(R.id.menu_pseudo, R.id.menu_pseudo, Menu.NONE, "수도 코드");
        menu.add(R.id.menu_modulenum, R.id.menu_modulenum, Menu.NONE, "모듈 개수");
        menu.add(R.id.menu_test, R.id.menu_test, Menu.NONE, "테스트모드");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        addOptionMenuItems(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_trans: //send message
//                String trans = mWorkspaceFragment.MakeTransStr(0);

                String trans = mWorkspaceFragment.translateToModuleLanguage();
                if(mService != null && trans != null)
                    sendMessageToDevice(trans);
                Logs.d("##", ""+trans);
                return true;
            case R.id.menu_start:
                Logs.d("# menu_start");
                if(mService != null) {
                    sendMessageToDevice(Constants.COMMAND_START);
                }
                break;
            case R.id.menu_stop:
                Logs.d("# menu_stop");
                if(mService != null) {
                    sendMessageToDevice(Constants.COMMAND_STOP);
                }
                break;
            case R.id.menu_pseudo:
//                Intent pseudosIntent = new Intent(mContext, PseudoActivity.class);
                //startActivityforResult(pseudosIntent, Constants.REQUEST_ACTIVITY_PSEUDO);
                changeFragment(new PseudoFragment(mFragmentListener));
                break;
            case R.id.menu_modulenum:
//                String s = Constants.MODULE_NUM_STRING;
                if(Constants.STRING_HELLO_ACK == null) {
                    Toast.makeText(mContext, "Error : no value of numOfModules", Toast.LENGTH_SHORT).show();
                    break;
                }
                String s = Constants.STRING_HELLO_ACK;
                Toast.makeText(mContext,
                        "DC : " + s.charAt(6) +
                        " SM : " + s.charAt(9) +
                        " LD : " + s.charAt(12) +
                        " IR : " + s.charAt(15) +
                        " SM : " + s.charAt(18), Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_test:
                if(Constants.STRING_HELLO_ACK == null) {
                    Toast.makeText(mContext, "Error : no value of numOfModules", Toast.LENGTH_SHORT).show();
                    break;
                }
                onFragmentCallBack(Constants.FRAGMENT_CALLBACK_SHOW_DIALOG_TESTMODE, 0);

//                TestModeActivity testModeActivity = new TestModeActivity(numOfModules, mActivityHandler);
//                Intent testIntent = new Intent(mContext, testModeActivity.getClass());
//                startActivityForResult(testIntent, Constants.REQUEST_ACTIVITY_TEST);
                break;
            default:
                break;
        }
        return false;
    }

}
