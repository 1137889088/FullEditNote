package com.chen.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.chen.app.R;
import com.chen.app.db.NoteDB;
import com.chen.app.domain.NoteInfoBean;
import com.chen.app.util.PermissionUtilActivity;
import com.chen.app.view.ScollerLeftDeleteListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 2017/4/4.
 * 所有数据的列表页面
 */

public class NoteListActivity extends Activity {
    NoteDB db;//数据库
    private SQLiteDatabase readableDatabase; //读操作
    private SQLiteDatabase writableDatabase; //写出操作
    private List<NoteInfoBean> mData;   //读出数据库中的数控
    private ScollerLeftDeleteListView mListView; //可以左滑的列表
    private SocllerLeftDeleteListAdapter socllerLeftDeleteListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notelist);
        db = new NoteDB(this);
        readableDatabase = db.getReadableDatabase();
        writableDatabase = db.getWritableDatabase();

        socllerLeftDeleteListAdapter = new SocllerLeftDeleteListAdapter();
   /*     for (NoteInfoBean mDatum : mData) {
            insert(mDatum);
        }*/
        FloatingActionButton fabNewNote = (FloatingActionButton) findViewById(R.id.btnNewNote);
        Intent intent = new Intent(NoteListActivity.this, PermissionUtilActivity.class);
        startActivity(intent);
        fabNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteListActivity.this, EditActivity.class);
                intent.putExtra("id", -1 + "");
                startActivity(intent);
            }
        });

        mListView = (ScollerLeftDeleteListView) findViewById(R.id.list);
        mData = queryAll();

        socllerLeftDeleteListAdapter.notifyDataSetChanged();

        mListView.setAdapter(socllerLeftDeleteListAdapter);
    }

    /**
     * 在结束操作的时候关闭数据库
     */
    @Override
    protected void onDestroy() {
        writableDatabase.close();
        readableDatabase.close();
        super.onDestroy();
    }

    /**
     * 在编辑后是list刷新
     */
    @Override
    protected void onResume() {
        super.onResume();
        mData = queryAll();
        socllerLeftDeleteListAdapter.notifyDataSetChanged();
    }

    class SocllerLeftDeleteListAdapter extends BaseAdapter {

        /**
         * 返回item的总数
         * @return
         */
        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        /**
         * 返回item 的位置
         * @param position 位置
         * @return
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //c
            if (null == convertView) {
                convertView = View.inflate(NoteListActivity.this, R.layout.item, null);
            }

            TextView text_data = (TextView) convertView.findViewById(R.id.text_data);
            TextView text_date = (TextView) convertView.findViewById(R.id.text_date);

            final TextView delete = (TextView) convertView.findViewById(R.id.delete);
            TextView edit = (TextView) convertView.findViewById(R.id.edit);

            //防止非空加载导致错误
            if (mData != null) {
                text_data.setText(mData.get(position).getNoteTitle());
                text_date.setText(mData.get(position).getNoteDate());
            }

            //获取指定的位置
            final int pos = position;

           delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    delete(mData.get(pos).getNoteId());
                    mData.remove(pos);
                    notifyDataSetChanged();
                    mListView.turnToNormal();
                }
            });
            /**
             * 查看按钮点击的事件
             */
            edit.setOnClickListener(new View.OnClickListener() {
                /**
                 * Called when a view has been clicked.
                 *
                 * @param v The view that was clicked.
                 */
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NoteListActivity.this, EditActivity.class);
                    intent.putExtra("id", mData.get(pos).getNoteId());
                    startActivity(intent);//启动编辑接卖弄
                }
            });

            return convertView;
        }
    }

    /**
     * 查询出说有的数据库中的数据
     * @return 所有数据的list集合
     */
    public List<NoteInfoBean> queryAll() {
        List<NoteInfoBean> list = new ArrayList<NoteInfoBean>();
        Cursor query = readableDatabase.query(NoteDB.TABLE_NAME_NOTES, null, null, null, null, null, null);
        while (query.moveToNext()) {
            NoteInfoBean bean = new NoteInfoBean();
            bean.setNoteId(query.getString(0));
            bean.setNoteTitle(query.getString(1));
            bean.setNoteContext(query.getString(2));
            bean.setNoteDate(query.getString(3));
            list.add(bean);
        }
        return list;
    }

    /**
     * 将指定的数据从list中移除
     * @param id 要移除的主键
     * @return
     */
    public int delete(String id) {
        List<NoteInfoBean> list = new ArrayList<NoteInfoBean>();
        int delete = writableDatabase.delete(NoteDB.TABLE_NAME_NOTES, NoteDB.COLUM_NAME_NOTES_ID + "=?", new String[]{id});
        return delete;
    }
}
