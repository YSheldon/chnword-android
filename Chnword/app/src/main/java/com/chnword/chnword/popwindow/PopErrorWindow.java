package com.chnword.chnword.popwindow;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chnword.chnword.R;

import org.w3c.dom.Text;

/**
 * Created by khtc on 15/9/13.
 */
public class PopErrorWindow extends PopupWindow {

    private ImageButton btn_cancel;
    private ImageButton btn_buy;
    private TextView linkTextView;

    private View mMenuView;
    public PopErrorWindow(Context context, View.OnClickListener itemListener) {
        super(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        mMenuView = inflater.inflate(R.layout.popwindow_error, null);
        setContentView(mMenuView);

        btn_cancel = (ImageButton) mMenuView.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });

        btn_buy = (ImageButton) mMenuView.findViewById(R.id.btn_buy);
        btn_buy.setOnClickListener(itemListener);

        linkTextView = (TextView) mMenuView.findViewById(R.id.linkTextView);
        linkTextView.setText(
                Html.fromHtml("<a href=\"http://www.baidu.com\">找回用户码</a> "));
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());


        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
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
}
