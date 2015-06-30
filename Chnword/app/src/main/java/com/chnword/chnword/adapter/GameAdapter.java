package com.chnword.chnword.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chnword.chnword.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khtc on 15/6/29.
 */
public class GameAdapter extends BaseAdapter {

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
            convertView = inflater.inflate(R.layout.item_game, null);
        }
        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(gameNames.get(position));

        return convertView;
    }
}
