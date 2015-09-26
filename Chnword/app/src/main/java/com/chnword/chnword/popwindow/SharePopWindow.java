package com.chnword.chnword.popwindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.chnword.chnword.R;
import com.chnword.chnword.activity.ShareEditActivity;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;

/**
 * Created by khtc on 15/9/17.
 */
public class SharePopWindow extends PopupWindow implements View.OnClickListener{

    private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
    private Activity mActivity;


    private ImageButton btn_cancel;
    private ImageButton snslogo1,snslogo2,snslogo3, snslogo4, snslogo5, snslogo6;

    private View mMenuView;
    public SharePopWindow(Activity context) {
        super(context);

        this.mActivity = context;

        LayoutInflater inflater = LayoutInflater.from(context);
        mMenuView = inflater.inflate(R.layout.popwindow_share, null);
        setContentView(mMenuView);

        btn_cancel = (ImageButton) mMenuView.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });

        snslogo1 = (ImageButton) mMenuView.findViewById(R.id.snslogo1);
        snslogo1.setOnClickListener(this);

        snslogo2 = (ImageButton) mMenuView.findViewById(R.id.snslogo2);
        snslogo2.setOnClickListener(this);

        snslogo3 = (ImageButton) mMenuView.findViewById(R.id.snslogo3);
        snslogo3.setOnClickListener(this);

        snslogo4 = (ImageButton) mMenuView.findViewById(R.id.snslogo4);
        snslogo4.setOnClickListener(this);

        snslogo5 = (ImageButton) mMenuView.findViewById(R.id.snslogo5);
        snslogo5.setOnClickListener(this);

        snslogo6 = (ImageButton) mMenuView.findViewById(R.id.snslogo6);
        snslogo6.setOnClickListener(this);

        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setTouchable(true);
        this.setAnimationStyle(R.style.AnimationFade);

        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


    }

    public SharePopWindow(Activity context, View.OnClickListener onClickListener) {
        super(context);

        this.mActivity = context;

        LayoutInflater inflater = LayoutInflater.from(context);
        mMenuView = inflater.inflate(R.layout.popwindow_share, null);
        setContentView(mMenuView);

        btn_cancel = (ImageButton) mMenuView.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });

        snslogo1 = (ImageButton) mMenuView.findViewById(R.id.snslogo1);
        snslogo1.setOnClickListener(onClickListener);

        snslogo2 = (ImageButton) mMenuView.findViewById(R.id.snslogo2);
        snslogo2.setOnClickListener(onClickListener);

        snslogo3 = (ImageButton) mMenuView.findViewById(R.id.snslogo3);
        snslogo3.setOnClickListener(onClickListener);

        snslogo4 = (ImageButton) mMenuView.findViewById(R.id.snslogo4);
        snslogo4.setOnClickListener(onClickListener);

        snslogo5 = (ImageButton) mMenuView.findViewById(R.id.snslogo5);
        snslogo5.setOnClickListener(onClickListener);

        snslogo6 = (ImageButton) mMenuView.findViewById(R.id.snslogo6);
        snslogo6.setOnClickListener(onClickListener);

        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setTouchable(true);
        this.setAnimationStyle(R.style.AnimationFade);

        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


    }

    @Override
    public void onClick(View v) {
        Intent shareEditIntent = new Intent(mActivity, ShareEditActivity.class);


        switch (v.getId()) {
            case R.id.snslogo1 :
                shareEditIntent.putExtra("share_type", SHARE_MEDIA.WEIXIN.toString());
                break;

            case R.id.snslogo2:
                shareEditIntent.putExtra("share_type", SHARE_MEDIA.WEIXIN_CIRCLE.toString());
                break;

            case R.id.snslogo3:
                shareEditIntent.putExtra("share_type", SHARE_MEDIA.SINA.toString());
                break;
            case R.id.snslogo4:
                shareEditIntent.putExtra("share_type", SHARE_MEDIA.QQ.toString());
                break;
            case R.id.snslogo5:
                shareEditIntent.putExtra("share_type", SHARE_MEDIA.QZONE.toString());
                break;
            case R.id.snslogo6:
                shareEditIntent.putExtra("share_type", SHARE_MEDIA.TENCENT.toString());
                break;
            default:
                break;
        }
        mActivity.startActivity(shareEditIntent);
        dismiss();

    }


}
