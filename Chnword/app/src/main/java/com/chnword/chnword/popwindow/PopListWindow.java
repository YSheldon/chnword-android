package com.chnword.chnword.popwindow;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chnword.chnword.R;
import com.chnword.chnword.adapter.PoplistWindowAdapter;

/**
 * Created by khtc on 15/11/16.
 */
public class PopListWindow extends PopupWindow {

    private ListView popListView;
    PoplistWindowAdapter adapter;



    private View mMenuView;
    public PopListWindow(Context context, AdapterView.OnItemClickListener itemListener) {
        super(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        mMenuView = inflater.inflate(R.layout.popwindow_list, null);
        setContentView(mMenuView);

        popListView = (ListView) mMenuView.findViewById(R.id.poplistView);

        adapter = new PoplistWindowAdapter(context);
        popListView.setAdapter(adapter);
        popListView.setOnItemClickListener(itemListener);


        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimationFadeInvert);

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
