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

    public static final int FRAGMENT_CALLBACK_CHANGE_FRAGMENT_INTRO = 1000;
    public static final int FRAGMENT_CALLBACK_CHANGE_FRAGMENT_CONNECTION = 1001;
    public static final int FRAGMENT_CALLBACK_CHANGE_FRAGMENT_WORKSPACE = 1002;
    public static final int FRAGMENT_CALLBACK_SHOW_DIALOG_INPUT =  1006;
    public static final int FRAGMENT_CALLBACK_CHANGE_FRAGMENT_PSUDO = 1007;
    public static final int FRAGMENT_CALLBACK_START_FINDING_BLUETOOTH = 1003;
    public static final int FRAGMENT_CALLBACK_SHOW_DIALOG_TESTMODE = 1008;
//    public static final int FRAGMENT_LISTENER_GET_FRAGMENT_INPUT = 1022;

    public static final int REQUEST_CODE_OPTION_INPUT = 1004;
    public static final int REQUEST_ACTIVITY_PSEUDO = 1020;
    public static final int REQUEST_ACTIVITY_TEST = 1021;
    public static final int HANDLER_TEST_ACTIVITY = 1011;

    public static final String INPUT_PARAM_1 = "P1";
    public static final String INPUT_PARAM_2 = "P2";
    public static final String INPUT_PARAM_3 = "P3";

    public static final String COMMAND_HELLO = "!HELLO\n";
    public static final String COMMAND_START = "!START\n";
    public static final String COMMAND_STOP = "!STOP\n";
    public static final String TEST_MESSAGE_DC1 = "#[DC1](001,100)[DY](001)[DC1](001,000)\n";
    public static final String TEST_MESSAGE_DC2 = "#[DC2](001,100)[DY](001)[DC2](001,000)\n";
    public static final String TEST_MESSAGE_DC3 = "#[DC3](001,100)[DY](001)[DC3](001,000)\n";
    public static final String TEST_MESSAGE_DC4 = "#[DC4](001,100)[DY](001)[DC4](001,000)\n";
    public static final String TEST_MESSAGE_DC5 = "#[DC5](001,100)[DY](001)[DC5](001,000)\n";

    public static final String TEST_MESSAGE_SM1 = "#[SM1](100)[DY](001)[SM1](050)\n";
    public static final String TEST_MESSAGE_SM2 = "#[SM2](100)[DY](001)[SM2](050)\n";
    public static final String TEST_MESSAGE_SM3 = "#[SM3](100)[DY](001)[SM3](050)\n";
    public static final String TEST_MESSAGE_SM4 = "#[SM4](100)[DY](001)[SM4](050)\n";
    public static final String TEST_MESSAGE_SM5 = "#[SM5](100)[DY](001)[SM5](050)\n";

    public static final String TEST_MESSAGE_LD1 = "#[LD1](R,100)[DY](001)[LD1](R,000)\n";
    public static final String TEST_MESSAGE_LD2 = "#[LD2](R,100)[DY](001)[LD1](R,000)\n";
    public static final String TEST_MESSAGE_LD3 = "#[LD3](R,100)[DY](001)[LD1](R,000)\n";
    public static final String TEST_MESSAGE_LD4 = "#[LD4](R,100)[DY](001)[LD1](R,000)\n";
    public static final String TEST_MESSAGE_LD5 = "#[LD5](R,100)[DY](001)[LD1](R,000)\n";

    public static final String TEST_MESSAGE_IR1 = "[IR1]()\n";
    public static final String TEST_MESSAGE_IR2 = "[IR2]()\n";
    public static final String TEST_MESSAGE_IR3 = "[IR3]()\n";
    public static final String TEST_MESSAGE_IR4 = "[IR4]()\n";
    public static final String TEST_MESSAGE_IR5 = "[IR5]()\n";

    public static final String TEST_MESSAGE_US1 = "[US1]()\n";
    public static final String TEST_MESSAGE_US2 = "[US2]()\n";
    public static final String TEST_MESSAGE_US3 = "[US3]()\n";
    public static final String TEST_MESSAGE_US4 = "[US4]()\n";
    public static final String TEST_MESSAGE_US5 = "[US5]()\n";
    public static String MODULE_NUM_STRING = "-----";

    public static String RECEIVED_STRING = null;

    public static String OPTION_BLOCK = null;
    public static String NUM_OPTION = null;
//    public static String MODULE_NUM = null;

}

