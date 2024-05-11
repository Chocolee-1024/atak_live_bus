
package com.atakmap.android.plugintemplate.plugin;

import java.io.File;

import android.app.Application;
import android.content.Context;

import com.atakmap.android.plugintemplate.BluetoothConfig;
import com.clj.fastble.BleManager;
import com.clj.fastble.scan.BleScanRuleConfig;

/**
 * Boilerplate code for loading native.
 */
public class PluginNativeLoader {

    private static final String TAG = "NativeLoader";
    private static String ndl = null;

    /**
    * If a plugin wishes to make use of this class, they will need to copy it into their plugin.
    * The classloader that loads this class is a key component of getting System.load to work 
    * properly.   If it is desirable to use this in a plugin, it will need to be a direct copy in a
    * non-conflicting package name.
    */
    synchronized static public void init(final Context context) {
        if (ndl == null) {
            try {
                ndl = context.getPackageManager()
                        .getApplicationInfo(context.getPackageName(),
                                0).nativeLibraryDir;

                BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                        .setAutoConnect(true)
                        .setScanTimeOut(10000)
                        .setDeviceMac(BluetoothConfig.BLE_ADDRESSES[0])
                        .build();
                BleManager.getInstance().initScanRule(scanRuleConfig);
                BleManager.getInstance().init((Application)context.getApplicationContext());
                BleManager.getInstance().setConnectOverTime(1000);
                BleManager.getInstance().enableLog(true);
            } catch (Exception e) {
                throw new IllegalArgumentException(
                        "native library loading will fail, unable to grab the nativeLibraryDir from the package name");
            }

        }
    }

    /**
    * Security guidance from our recent audit:
    * Pass an absolute path to System.load(). Avoid System.loadLibrary() because its behavior 
    * depends upon its implementation which often relies on environmental features that can be 
    * manipulated. Use only validated, sanitized absolute paths.
    */

    public static void loadLibrary(final String name) {
        if (ndl != null) {
            final String lib = ndl + File.separator
                    + System.mapLibraryName(name);
            if (new File(lib).exists()) {
                System.load(lib);
            }
        } else {
            throw new IllegalArgumentException("NativeLoader not initialized");
        }

    }

}
