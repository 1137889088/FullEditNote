package com.chen.app.activity;

import android.app.Activity;
import android.content.Intent;
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
import com.chen.app.util.UriUtils;
import com.chen.app.view.ColorPickerDialog;
import com.chen.app.view.PictureAndTextEditorView;

/**
 * Created by chen on 17/4/3.
 * 记事本编辑页
 */
public class MainActivity extends Activity {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    public static int color = Color.RED;//设置初始颜色
    public final int getImgResultCode = 888;//获取图片的回调函数代码
    private PictureAndTextEditorView mEditText;//自定义的富文本编辑器


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

    private TextView tvFontSize;//字体大小选择
    private EditText etFontSize;//字体大小输入
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /* Intent intent = new Intent();
        intent.setClass(this, PermissionUtilActivity.class);
        startActivity(intent);*/
      /* //加载Grpahite引擎
        loadGraphite();
        //获取蒙文字体
        Typeface mtfp = (Typeface) Graphite.addFontResource(getAssets(), "MenkHar_a_NoFtrTig.ttf", "MenkHar", 0, "", "");*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* tvFontSize = (TextView) findViewById(R.id.tvFontSize);
        tvFontSize.setText("字体大小:");*/

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.save();
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
                mEditText.setSuperscript();
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
                mEditText.setSubscript();
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
                mEditText.setStyleBOLD();
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
                mEditText.setStyleITALIC();
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
                mEditText.setStrikethrough();
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
                mEditText.setUnderline();
            }
        });


        etFontSize = (EditText) findViewById(R.id.etFontSize);
        etFontSize.setText("1");
  /*      etFontSize.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            *//**
             * Called when the focus state of a view has changed.
             *
             * @param v        The view whose state has changed.
             * @param hasFocus The new focus state of v.
             *//*
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){

                }
            }
        });*/

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
                if(fontSizeText.equals("")||fontSizeText==null){
                    mEditText.setText("1");
                    mEditText.setFontSize(fontSize);
                }else{
                    try {
                        fontSize = Float.parseFloat(fontSizeText);
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this,"请正确输入数字(大于0)",Toast.LENGTH_SHORT).show();
                    }
                    if(fontSize<=0){
                        fontSize = 1;
                        Toast.makeText(MainActivity.this,"请正确输入数字(大于0)",Toast.LENGTH_SHORT).show();
                    }
                    mEditText.setFontSize(fontSize);
                }
                mEditText.setFontSize();
            }
        });

        mEditText = (PictureAndTextEditorView) findViewById(R.id.edit_text);
        btnAddPic = (Button) findViewById(R.id.btnAddPic);

        //mEditText.setTypeface(mtfp);
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
                mEditText.setFontColor();
            }
        });

        btnSetBackColor = (Button) findViewById(R.id.btnSetBackColor);
        btnSetBackColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setBackColor();
            }
        });

        /**
         * 选择颜色事件监听
         */
        btnSelectColor = (Button) findViewById(R.id.btnSelectColor);
        btnSelectColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog colorDialog = new ColorPickerDialog(MainActivity.this, R.style.dialog, color,
                        new ColorPickerDialog.OnColorChangedListener() {
                            @Override
                            public void colorChanged(int color) {
                                mEditText.setFrontColor(color);//将颜色设置到
                            }
                        });
                colorDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialo
                colorDialog.show();
            }
        });
    }

    /**
     * activity的回调判断
     * @param requestCode 回调的代码
     * @param resultCode 结果码
     * @param data 回调的数据
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
                        mEditText.insertBitmap(imageurl);
                    }
                default:
                    break;
            }
        }
    }
}
