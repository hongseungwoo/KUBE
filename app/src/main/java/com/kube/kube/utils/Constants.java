package com.kube.kube.utils;

public class Constants {

    // Service handler message key
    public static final String SERVICE_HANDLER_MSG_KEY_DEVICE_NAME = "device_name";
    public static final String SERVICE_HANDLER_MSG_KEY_DEVICE_ADDRESS = "device_address";
    public static final String SERVICE_HANDLER_MSG_KEY_TOAST = "toast";

    // Preference
    public static final String PREFERENCE_NAME = "btchatPref";
    public static final String PREFERENCE_KEY_BG_SERVICE = "BackgroundService";
    public static final String PREFERENCE_CONN_INFO_ADDRESS = "device_address";
    public static final String PREFERENCE_CONN_INFO_NAME = "device_name";

    // Message types sent from Service to Activity
    public static final int MESSAGE_CMD_ERROR_NOT_CONNECTED = -50;

    public static final int MESSAGE_BT_SCAN_STARTED = 111;
    public static final int MESSAGE_BT_NEW_DEVICE = 112;
    public static final int MESSAGE_BT_SCAN_FINISHED = 113;

    public static final int MESSAGE_BT_STATE_INITIALIZED = 1;
    public static final int MESSAGE_BT_STATE_LISTENING = 2;
    public static final int MESSAGE_BT_STATE_CONNECTING = 3;
    public static final int MESSAGE_BT_STATE_CONNECTED = 4;
    public static final int MESSAGE_BT_STATE_DISCONNECTED = 5;
    public static final int MESSAGE_BT_STATE_ERROR = 10;

    public static final int MESSAGE_READ_CHAT_DATA = 201;



    // Intent request codes
    public static final int REQUEST_CONNECT_DEVICE = 1;
    public static final int REQUEST_ENABLE_BT = 2;

    public static final int FRAGMENT_LISTENER_CHANGE_FRAGMENT_INTRO = 1000;
    public static final int FRAGMENT_LISTENER_CHANGE_FRAGMENT_CONNECTION = 1001;
    public static final int FRAGMENT_LISTENER_CHANGE_FRAGMENT_WORKSPACE = 1002;
    public static final int FRAGMENT_LISTENER_CHANGE_FRAGMENT_INPUT =  1006;
    public static final int FRAGMENT_LISTENER_START_FINDING_BLUETOOTH = 1003;

    public static final int REQUEST_CODE_OPTION_INPUT = 1004;

    public static final String INPUT_PARAM_1 = "P1";
    public static final String INPUT_PARAM_2 = "P2";
    public static final String INPUT_PARAM_3 = "P3";

    public static String OPTION_BLOCK = null;
    public static String NUM_OPTION = null;
    public static String MODULE_NUM = null;
}

