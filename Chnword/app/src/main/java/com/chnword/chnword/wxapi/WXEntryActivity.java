package com.chnword.chnword.wxapi;
import android.content.Intent;

import com.chnword.chnword.utils.NotificationName;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

import io.vov.vitamio.utils.Log;

public class WXEntryActivity extends WXCallbackActivity {
    @Override
    public void onResp(BaseResp resp) {
        super.onResp(resp);
        Log.e("WXEntryActivity", "errorCode " + resp.errCode + " , errorStr : " + resp.errStr);

        Intent intent = new Intent(NotificationName.NOTIFICATION_WXPAYMENT);
        intent.putExtra(NotificationName.Extra_WX_ErrorCode, resp.errCode);
        intent.putExtra(NotificationName.Extra_WX_ErrorStr, resp.errStr);
        sendBroadcast(intent);

    }
}