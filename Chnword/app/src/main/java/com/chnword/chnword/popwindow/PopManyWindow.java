package com.chnword.chnword.popwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.chnword.chnword.R;

/**
 * Created by khtc on 15/9/12.
 */
public class PopManyWindow extends PopupWindow {

//调用方式：
//    menuWindow = new SelectPicPopupWindow(MainActivity.this, itemsOnClick);
//    //��ʾ����
//    menuWindow.showAtLocation(MainActivity.this.findViewById(R.id.main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);

    private ImageButton btn_cancel;
    private View mMenuView;

    public PopManyWindow(Context context, View.OnClickListener itemsOnClick) {
        super(context);


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popwindow_toomany, null);
        this.setContentView(mMenuView);



        btn_cancel = (ImageButton) mMenuView.findViewById(R.id.btn_cancel);

//        btn_take_photo.setOnClickListener(itemsOnClick);

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                dismiss();
            }
        });




        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimationFade);

//        ColorDrawable dw = new ColorDrawable(0xb0000000);
//        this.setBackgroundDrawable(dw);
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
}
