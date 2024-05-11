package com.atakmap.android.plugintemplate;

import java.util.UUID;

public class BluetoothConfig {
    public static final String RC_DATA_TOPIC = "RC_DATA_TOPIC";
    public static final String[] BLE_ADDRESSES = {"04:A3:16:9F:92:62", "80:64:6F:C4:7E:0A"};
    public static final UUID SERVICE_UUID = UUID.fromString("0000FFE0-0000-1000-8000-00805F9B34FB");
    public static final UUID CHARACTERISTIC_UUID = UUID.fromString("0000FFE1-0000-1000-8000-00805F9B34FB");
}
