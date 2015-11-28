package com.chnword.chnword.popwindow;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.chnword.chnword.R;

/**
 * Created by khtc on 15/9/14.
 */
public class PopScanWindow extends PopupWindow {

    private ImageButton btn_cancel;
    private View mMenuView;

    private ImageButton btn_submit,btn_card;
    private EditText usercodeEditText;
    private Button gotoLogonButton;

    public PopScanWindow(Context context, View.OnClickListener itemsOnClick) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popwindow_scan, null);
        this.setContentView(mMenuView);



        btn_cancel = (ImageButton) mMenuView.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        usercodeEditText = (EditText) mMenuView.findViewById(R.id.usercodeEditText);

        btn_card = (ImageButton) mMenuView.findViewById(R.id.btn_card);
        btn_card.setOnClickListener(itemsOnClick);

        btn_submit = (ImageButton) mMenuView.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(itemsOnClick);

        gotoLogonButton = (Button) mMenuView.findViewById(R.id.gotoLogonButton);
        gotoLogonButton.setOnClickListener(itemsOnClick);



        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimationFade);

        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    public String text() {
        return usercodeEditText.getText().toString();
    }
}
