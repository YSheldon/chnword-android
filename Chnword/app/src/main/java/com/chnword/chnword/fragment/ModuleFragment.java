package com.chnword.chnword.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chnword.chnword.R;
import com.chnword.chnword.activity.WordActivity;
import com.chnword.chnword.adapter.ModuleAdapter;
import com.chnword.chnword.beans.Module;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by khtc on 15/6/16.
 * 一级模块
 */
public class ModuleFragment extends Fragment {

    ListView listView;
    ModuleAdapter moduleAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_module, container, false);

        listView = (ListView) view.findViewById(R.id.module_listView);
        moduleAdapter = new ModuleAdapter(getActivity(), ((WordActivity) getActivity()).getModules());
        listView.setAdapter(moduleAdapter);
        listView.setOnItemClickListener(moduleItemClick);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private AdapterView.OnItemClickListener moduleItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Module module = (Module)moduleAdapter.getItem(position);
            ((WordActivity) getActivity()).showWord(module);
        }
    };


    public void updateData() {
        moduleAdapter.notifyDataSetChanged();
    }

}
