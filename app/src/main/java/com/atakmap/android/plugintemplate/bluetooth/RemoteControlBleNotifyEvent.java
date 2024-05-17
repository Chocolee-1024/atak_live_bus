package com.atakmap.android.plugintemplate.bluetooth;


import android.util.Log;

import com.atakmap.android.plugintemplate.BluetoothConfig;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.exception.BleException;
//import com.jeremyliao.liveeventbus.LiveEventBus;

public class RemoteControlBleNotifyEvent extends BleNotifyCallback {

    @Override
    public void onNotifySuccess() {
        Log.e("onNotifySuccess", "onNotifySuccess");
    }

    @Override
    public void onNotifyFailure(BleException exception) {
        Log.e("onNotifyFailure", exception.getDescription());
    }

    @Override
    public void onCharacteristicChanged(byte[] data) {
        String value = new String(data).trim();
//        LiveEventBus.<String>get(BluetoothConfig.RC_DATA_TOPIC).post(value);
    }
}
