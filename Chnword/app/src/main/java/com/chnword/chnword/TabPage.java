package com.chnword.chnword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khtc on 15/4/23.
 */
public class TabPage extends Fragment {

    private TabActivityListener listener;
    private ListView gameListView;

    private View zi_scan;
    private View zi_anim;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragtab_page, container, false);

        gameListView = (ListView) view.findViewById(R.id.gameListView);
        gameListView.setAdapter(new GameAdapter(getActivity()));

        zi_anim = (View) view.findViewById(R.id.zi_anim);
        zi_scan = (View) view.findViewById(R.id.zi_scan);

        zi_anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPageAnim();
            }
        });

        zi_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPageScan();
            }
        });



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



    public TabActivityListener getListener() {
        return listener;
    }

    public void setListener(TabActivityListener listener) {
        this.listener = listener;
    }


    //事件处理


    public void onPageAnim() {

        Intent i = new Intent(getActivity(), AnimActivity.class);
        startActivity(i);
    }

    public void onPageScan(){

        Intent i = new Intent(getActivity(), ScanActivity.class);
        startActivity(i);
    }

    //adapter
    class GameAdapter extends BaseAdapter{

        List<String> gameNames = new ArrayList<String>();

        private Context context;
        GameAdapter(Context context) {

            gameNames.add("小游戏1");
            gameNames.add("小游戏2");
            gameNames.add("小游戏3");
            gameNames.add("小游戏4");
            gameNames.add("小游戏1");
            gameNames.add("小游戏2");
            gameNames.add("小游戏3");
            gameNames.add("小游戏4");
            gameNames.add("小游戏1");
            gameNames.add("小游戏2");
            gameNames.add("小游戏3");
            gameNames.add("小游戏4");
            gameNames.add("小游戏1");
            gameNames.add("小游戏2");
            gameNames.add("小游戏3");
            gameNames.add("小游戏4");
            this.context = context;

        }

        @Override
        public int getCount() {
            return gameNames.size();
        }

        @Override
        public Object getItem(int position) {
            return gameNames.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null){
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.tab_page_item, null);
            }
            TextView title = (TextView) convertView.findViewById(R.id.title);
            title.setText(gameNames.get(position));

            return convertView;
        }
    }
}
