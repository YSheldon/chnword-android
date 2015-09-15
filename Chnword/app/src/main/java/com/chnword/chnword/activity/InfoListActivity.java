package com.chnword.chnword.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.chnword.chnword.R;
import com.chnword.chnword.adapter.InfolistAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khtc on 15/9/15.
 */
public class InfoListActivity extends Activity {

    private ImageButton backImageButton;
    private ListView info_listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_infolist);

        backImageButton = (ImageButton) findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();//回退
            }
        });

        info_listView = (ListView) findViewById(R.id.info_listView);

        List<String> lists = new ArrayList<String>();
        lists.add("在线产品订购");
        lists.add("三千字课特点");
        lists.add("会员尊享权利");
        lists.add("只是创富计划");
        lists.add("关于中聿华源");

        InfolistAdapter adapter = new InfolistAdapter(this, lists);
        info_listView.setAdapter(adapter);
        info_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        break;

                    case 1:
                        Intent featureIntent = new Intent(InfoListActivity.this, FeatureActivity.class);
                        startActivity(featureIntent);
                        break;

                    case 2:
                        break;

                    case  3:
                        Intent knowledgeIntent = new Intent(InfoListActivity.this, KnowledgeActivity.class);
                        startActivity(knowledgeIntent);
                        break;

                    case 4:
                        Intent aboutIntent = new Intent(InfoListActivity.this, AboutActivity.class);
                        startActivity(aboutIntent);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
