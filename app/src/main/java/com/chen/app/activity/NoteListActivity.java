package com.chen.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.chen.app.R;
import com.chen.app.util.PermissionUtilActivity;
import com.chen.app.view.ScollerLeftDeleteListView;

import java.util.ArrayList;

public class NoteListActivity extends Activity {
    Button btnNewNote;
    private ScollerLeftDeleteListView mListView;
    private ArrayList<String> mData = new ArrayList<String>() {
        {
            for(int i=0;i<50;i++) {
                add("hello world, hello androidasdfsadfsadfsadfffffffffffffffffffffffffffffffffffffffffffffffffffffffffasdffff" + i);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notelist);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnNewNote);
        Intent intent = new Intent(NoteListActivity.this, PermissionUtilActivity.class);
        startActivity(intent);
           fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteListActivity.this, com.chen.app.activity.MainActivity.class);
                startActivity(intent);
            }
        });

        mListView = (ScollerLeftDeleteListView) findViewById(R.id.list);
        mListView.setAdapter(new MyAdapter());
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mListView.canClick()) {
                    Toast.makeText(NoteListActivity.this, mData.get(position), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(null == convertView) {
                convertView = View.inflate(NoteListActivity.this, R.layout.item, null);
            }
            TextView text_data = (TextView)convertView.findViewById(R.id.text_data);
            TextView text_date = (TextView) convertView.findViewById(R.id.text_date);

            TextView delete = (TextView) convertView.findViewById(R.id.delete);
            text_data.setText(mData.get(position));
            final int pos = position;
            delete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mData.remove(pos);
                    notifyDataSetChanged();
                    mListView.turnToNormal();
                }
            });

            return convertView;
        }
    }
}
