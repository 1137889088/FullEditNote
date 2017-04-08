package com.chen.app.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.chen.app.R;
import com.chen.app.db.NoteDB;
import com.chen.app.domain.NoteInfoBean;
import com.chen.app.util.UriUtils;
import com.chen.app.view.ColorPickerDialog;
import com.chen.app.view.PictureAndTextEditorView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chen on 17/4/3.
 * 记事本编辑页
 */
public class EditActivity extends Activity {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static int color = Color.RED;//设置初始颜色
    public final int getImgResultCode = 888;//获取图片的回调函数代码
    private PictureAndTextEditorView etContent;//自定义的富文本编辑器
    //private boolean supportMogola = false;

    private Button btnAddPic;//添加图片按钮
    private Button btnSetFontColor;//改变字体颜色
    private Button btnSelectColor;//选择颜色
    private Button btnSetBackColor;//设置字体背景颜色
    private Button btnSetFontSize;//字体带下按钮
    private Button btnSetStrikeThrough;//设置删除线按钮
    private Button btnSetUnderline;//设置删除线按钮
    private Button btnSetSuperscript;//设置上标
    private Button btnSetSubscript;//设置下标
    private Button btnSetStyleBOLD;//设置粗体
    private Button btnSetStyleITALIC;//设置斜体
    private Button btnSave;//设置斜体
    private NoteInfoBean bean;
    private TextView tvFontSize;//字体大小选择
    private EditText etFontSize;//字体大小输入


    NoteDB db;
    private SQLiteDatabase readableDatabase;
    private SQLiteDatabase writableDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        db = new NoteDB(this);
        readableDatabase = db.getReadableDatabase();
        writableDatabase = db.getWritableDatabase();
        //Typeface mtfp = (Typeface) Graphite.addFontResource(getAssets(), "MenkHar_a_NoFtrTig.ttf", "MenkHar", 0, "", "");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        //从Intent当中根据key取得value
        if (intent != null) {
            String value = intent.getStringExtra("id");
            bean = queryById(value);
        }
        final EditText etTitle = (EditText) findViewById(R.id.etTitle);
        etContent = (PictureAndTextEditorView) findViewById(R.id.etContent);
        if (bean != null) {
            etTitle.setText(bean.getNoteTitle());
            etContent.setText(bean.getNoteContext());
        }
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etContent.save();
                String title = etTitle.getText().toString();
                String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
                if (content.toString().trim().equals("") || content == null) {
                    Toast.makeText(EditActivity.this, "请输入文本后在保存", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (title.equals("") || null == title) {
                    Toast.makeText(EditActivity.this, "为你的笔记起一个标题吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (bean == null) {
                    bean = new NoteInfoBean(title, date, content);
                    insert(bean);
                } else {
                    bean.setNoteTitle(title);
                    bean.setNoteContext(content);
                    bean.setNoteDate(date);
                    update(bean);
                }
            }
        });

        btnSetSuperscript = (Button) findViewById(R.id.btnSetSuperscript);
        btnSetSuperscript.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                etContent.setSuperscript();
            }
        });

        btnSetSubscript = (Button) findViewById(R.id.btnSetSubscript);
        btnSetSubscript.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                etContent.setSubscript();
            }
        });

        btnSetStyleBOLD = (Button) findViewById(R.id.btnSetStyleBOLD);
        btnSetStyleBOLD.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                etContent.setStyleBOLD();
            }
        });

        btnSetStyleITALIC = (Button) findViewById(R.id.btnSetStyleITALIC);
        btnSetStyleITALIC.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                etContent.setStyleITALIC();
            }
        });


        btnSetStrikeThrough = (Button) findViewById(R.id.btnSetStrikeThrough);
        btnSetStrikeThrough.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                etContent.setStrikethrough();
            }
        });


        btnSetUnderline = (Button) findViewById(R.id.btnSetUnderline);
        btnSetUnderline.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                etContent.setUnderline();
            }
        });


        etFontSize = (EditText) findViewById(R.id.etFontSize);
        etFontSize.setText("1");

        btnSetFontSize = (Button) findViewById(R.id.btnSetFontSize);
        btnSetFontSize.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                String fontSizeText = etFontSize.getText().toString().trim();
                float fontSize = 1;
                if (fontSizeText.equals("") || fontSizeText == null) {
                    etContent.setText("1");
                    etContent.setFontSize(fontSize);
                } else {
                    try {
                        fontSize = Float.parseFloat(fontSizeText);
                    } catch (NumberFormatException e) {
                        Toast.makeText(EditActivity.this, "请正确输入数字(大于0)", Toast.LENGTH_SHORT).show();
                    }
                    if (fontSize <= 0) {
                        fontSize = 1;
                        Toast.makeText(EditActivity.this, "请正确输入数字(大于0)", Toast.LENGTH_SHORT).show();
                    }
                    etContent.setFontSize(fontSize);
                }
                etContent.setFontSize();
            }
        });


        //etContent.setTypeface(mtfp);
        //etContent.getTypeface();
        btnAddPic = (Button) findViewById(R.id.btnAddPic);

        //etContent.setTypeface(mtfp);
        /**
         * 改变字体颜色按钮监听
         */
        btnAddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用系统的相册来回去图片
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, getImgResultCode);
            }
        });

        /**
         * 设置字体颜色
         */
        btnSetFontColor = (Button) findViewById(R.id.btnSetFontColor);
        btnSetFontColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setFontColor();
            }
        });

        btnSetBackColor = (Button) findViewById(R.id.btnSetBackColor);
        btnSetBackColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setBackColor();
            }
        });

        /**
         * 选择颜色事件监听
         */
        btnSelectColor = (Button) findViewById(R.id.btnSelectColor);
        btnSelectColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog colorDialog = new ColorPickerDialog(EditActivity.this, R.style.dialog, color,
                        new ColorPickerDialog.OnColorChangedListener() {
                            @Override
                            public void colorChanged(int color) {
                                etContent.setFrontColor(color);//将颜色设置到
                            }
                        });
                colorDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialo
                colorDialog.show();
            }
        });
    }

    /**
     * activity的回调判断
     *
     * @param requestCode 回调的代码
     * @param resultCode  结果码
     * @param data        回调的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case getImgResultCode:
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        String imageurl = UriUtils.getImageAbsolutePath(this, selectedImage);
                        etContent.insertBitmap(imageurl);
                    }
                default:
                    break;
            }
        }
    }

    private NoteInfoBean queryById(String id) {
        NoteInfoBean infoBean = null;
        Cursor query = readableDatabase.query(NoteDB.TABLE_NAME_NOTES, null, NoteDB.COLUM_NAME_NOTES_ID + "=?", new String[]{id}, null, null, null);
        while (query.moveToNext()) {
            infoBean = new NoteInfoBean();
            infoBean.setNoteId(query.getString(0));
            infoBean.setNoteTitle(query.getString(1));
            infoBean.setNoteContext(query.getString(2));
            infoBean.setNoteDate(query.getString(3));
        }
        return infoBean;
    }

    public int insert(NoteInfoBean noteInfoBean) {

        ContentValues cv = new ContentValues();
        cv.put(NoteDB.COLUM_NAME_NOTES_TITLE, noteInfoBean.getNoteTitle());
        cv.put(NoteDB.COLUM_NAME_NOTES_CONTENT, noteInfoBean.getNoteContext());
        //new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())
        cv.put(NoteDB.COLUM_NAME_NOTES_DATE, noteInfoBean.getNoteDate());
        return (int) writableDatabase.insert(NoteDB.TABLE_NAME_NOTES, "''", cv);
    }

    public int update(NoteInfoBean noteInfoBean) {

        ContentValues cv = new ContentValues();
        cv.put(NoteDB.COLUM_NAME_NOTES_TITLE, noteInfoBean.getNoteTitle());
        cv.put(NoteDB.COLUM_NAME_NOTES_CONTENT, noteInfoBean.getNoteContext());
        //new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())
        cv.put(NoteDB.COLUM_NAME_NOTES_DATE, noteInfoBean.getNoteDate());
        return (int) writableDatabase.update(NoteDB.TABLE_NAME_NOTES, cv, NoteDB.COLUM_NAME_NOTES_ID + "=?", new String[]{noteInfoBean.getNoteId()});
    }
}
