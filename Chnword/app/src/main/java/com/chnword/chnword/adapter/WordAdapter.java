package com.chnword.chnword.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chnword.chnword.R;
import com.chnword.chnword.beans.Module;
import com.chnword.chnword.beans.Word;

import java.util.List;

/**
 * Created by khtc on 15/6/16.
 */
public class WordAdapter extends BaseAdapter {

    private Context mContext;
    private List<Word> wordList;

    public WordAdapter(Context mContext, List<Word> wordList) {
        this.mContext = mContext;
        this.wordList = wordList;
    }

    @Override
    public int getCount() {
        return wordList.size();
    }

    @Override
    public Object getItem(int position) {
        return wordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.element_word, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.module_name_item);

        Word word = wordList.get(position);
        textView.setText(word.getWord());

        return convertView;
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
    }
}