package com.chnword.chnword.net;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.UUID;

/**
 * Created by khtc on 15/5/12.
 */
public class DeviceUtil {

    public static String getDeviceId(Context context) {
        return getDeviceUuid(context).toString();
    }

    public static UUID getDeviceUuid(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        return deviceUuid;
    }
}
