package com.atakmap.android.plugintemplate.bluetooth;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.atakmap.android.plugintemplate.BluetoothConfig;
import com.atakmap.android.plugintemplate.plugin.R;
import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;

import java.util.List;


public class RcManager {
    private static final String TAG = "RcManager";
    private static RcBleGattCallback mBleGattCallback = null;
    private static final BleNotifyCallback mBleNotifyCallback = new RemoteControlBleNotifyEvent();

    private BleDevice bleDevice = null;


    public void onStart(Context context) {
        Log.d(TAG, "藍芽服務啟動中!!!!");
        mBleGattCallback = new RcBleGattCallback();

        BleManager.getInstance().enableBluetooth();

        if (BleManager.getInstance().isBlueEnable()) {
            BleManager.getInstance().scan(new BleScanCallback() {
                @Override
                public void onScanFinished(List<BleDevice> scanResultList) {
                    Log.d(TAG, "onScanFinished...藍芽掃描完成");
                    for (BleDevice device : scanResultList) {
                        if (device.getMac().equals(BluetoothConfig.BLE_ADDRESSES[0])) {
                            bleDevice = device;
                            BleManager.getInstance().connect(bleDevice, mBleGattCallback);
                        }
                    }
                }

                @Override
                public void onScanStarted(boolean success) {
                    Log.d(TAG, "onScanStarted...藍芽掃描開始 success: " + success);
                }

                @Override
                public void onScanning(BleDevice bleDevice) {
                    Log.d(TAG, "onScanning...藍芽掃描中");
                }
            });
        } else {
            Log.e(TAG, "藍芽未開啟");
        }
    }

    private void connectToDeviceMac(BleDevice device, RcBleGattCallback mBleGattCallback) {
        if (device != null && BleManager.getInstance().isConnected(device)) return;

        Log.d(TAG, "[connectToDevice] deviceMac: " + device.getMac());
    }

    private class RcBleGattCallback extends BleGattCallback {

        @Override
        public void onStartConnect() {}

        @Override
        public void onConnectFail(BleDevice bleDevice, BleException e) {
            Log.e(TAG, "onConnectFail...藍芽連接失敗");
            connectToDeviceMac(bleDevice, this);
        }

        @Override
        public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
            // 連線成功
            Log.d(TAG, "onConnectSuccess...藍芽連接成功");
            BleManager.getInstance().notify(
                    bleDevice,
                    BluetoothConfig.SERVICE_UUID.toString(),
                    BluetoothConfig.CHARACTERISTIC_UUID.toString(),
                    mBleNotifyCallback
            );
        }

        @Override
        public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {
            // 連線中斷，重新掃描
            Log.i(TAG, "onDisConnected...藍芽連接中斷, 重新連線中");
            connectToDeviceMac(device, this);
        }
    }
}
