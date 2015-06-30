package com.chnword.chnword.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.chnword.chnword.R;
import com.chnword.chnword.activity.WordActivity_t;
import com.chnword.chnword.adapter.CategoryAdapter;
import com.chnword.chnword.beans.Category;

/**
 * Created by khtc on 15/6/16.
 * 一级模块
 */
public class CategoryFragment extends Fragment {

    GridView listView;
    CategoryAdapter categoryAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_module, container, false);

        listView = (GridView) view.findViewById(R.id.module_listView);
        categoryAdapter = new CategoryAdapter(getActivity(), ((WordActivity_t) getActivity()).getCategories());
        listView.setAdapter(categoryAdapter);
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
            Category category = (Category) categoryAdapter.getItem(position);
            ((WordActivity_t) getActivity()).showWord(category);
        }
    };


    public void updateData() {
        categoryAdapter.notifyDataSetChanged();
    }

}
