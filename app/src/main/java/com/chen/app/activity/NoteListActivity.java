package com.chen.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.chen.app.R;
import com.chen.app.domain.NoteInfoBean;
import com.chen.app.util.PermissionUtilActivity;
import com.chen.app.view.ScollerLeftDeleteListView;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends Activity {
    private ScollerLeftDeleteListView mListView;
    private ArrayList<NoteInfoBean> mData = new ArrayList<NoteInfoBean>() {
        {
            for (int i = 0; i < 3; i++) {
                NoteInfoBean bean = new NoteInfoBean("欢迎大家使用这个记事本，wasdfsadfsadf", "2017.4.7");
                add(bean);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notelist);

        FloatingActionButton fabNewNote = (FloatingActionButton) findViewById(R.id.btnNewNote);
        Intent intent = new Intent(NoteListActivity.this, PermissionUtilActivity.class);
        startActivity(intent);
        fabNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteListActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        mListView = (ScollerLeftDeleteListView) findViewById(R.id.list);
        SocllerLeftDeleteListAdapter socllerLeftDeleteListAdapter = new SocllerLeftDeleteListAdapter();
        socllerLeftDeleteListAdapter.setDate(mData);
        mListView.setAdapter(socllerLeftDeleteListAdapter);
    }

    class SocllerLeftDeleteListAdapter extends BaseAdapter {
        private List<NoteInfoBean> itemInfos = null;

        public void setDate(List<NoteInfoBean> itemInfo) {
            this.itemInfos = itemInfo;
        }

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
            System.out.println("getView" + position);
            if (null == convertView) {
                convertView = View.inflate(NoteListActivity.this, R.layout.item, null);
            }
            TextView text_data = (TextView) convertView.findViewById(R.id.text_data);
            TextView text_date = (TextView) convertView.findViewById(R.id.text_date);

            TextView delete = (TextView) convertView.findViewById(R.id.delete);
            TextView edit = (TextView) convertView.findViewById(R.id.edit);
            if (itemInfos != null) {
                text_data.setText(itemInfos.get(position).getNoteTitle());
                text_date.setText(itemInfos.get(position).getNoteDate());
            }
            final int pos = position;
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemInfos.remove(pos);
                    notifyDataSetChanged();
                    mListView.turnToNormal();
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                /**
                 * Called when a view has been clicked.
                 *
                 * @param v The view that was clicked.
                 */
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NoteListActivity.this, EditActivity.class);
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
}
