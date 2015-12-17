package com.chnword.chnword.wxapi;

import android.app.Activity;

/**
 * Created by khtc on 15/9/6.
 */
import net.sourceforge.simcpux.Constants;


import com.chnword.chnword.R;
import com.chnword.chnword.utils.NotificationName;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{

    private static final String TAG = "WXPAYMENT";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);

        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.e(TAG, "onPayFinish, errCode = " + resp.errCode);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

            Intent intent = new Intent(NotificationName.NOTIFICATION_WXPAYMENT);
            intent.putExtra(NotificationName.Extra_WX_ErrorCode, resp.errCode);
            intent.putExtra(NotificationName.Extra_WX_ErrorStr, resp.errStr);
            sendBroadcast(intent);
            sendBroadcast(intent);
        }
    }
}