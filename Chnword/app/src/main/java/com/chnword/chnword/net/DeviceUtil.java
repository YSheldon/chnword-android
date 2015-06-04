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

    public static String getPhoneNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        String line1Number = telephonyManager.getLine1Number();
        if (!"".equalsIgnoreCase(line1Number)) {
            return line1Number;
        }
        return null;
    }

    public static String getProviderName(Context context) {
        String ProvidersName = null;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        // 返回唯一的用户ID;就是这张卡的编号神马的
        String IMSI = telephonyManager.getSubscriberId();
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        System.out.println(IMSI);
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            ProvidersName = "中国移动";
        } else if (IMSI.startsWith("46001")) {
            ProvidersName = "中国联通";
        } else if (IMSI.startsWith("46003")) {
            ProvidersName = "中国电信";
        }
        return ProvidersName;
    }
}
