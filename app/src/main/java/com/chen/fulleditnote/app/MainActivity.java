package com.chen.fulleditnote.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import app.chen.com.fulleditnote.R;
import com.chen.fulleditnote.util.UriUtils;
import org.sil.palaso.Graphite;

import static org.sil.palaso.Graphite.loadGraphite;

/**
 * Created by chen on 17/4/3.
 * 记事本编辑页
 */
public class MainActivity extends Activity {

    public static int color = Color.RED;//设置初始颜色
    public final int getImgResultCode = 888;//获取图片的回调函数代码
    private PictureAndTextEditorView mEditText;//自定义的富文本编辑器
    private Button btnAddPic;//添加图片按钮
    private Button btnSetFontColor;//改变字体颜色
    private Button btnSelectColor;//选择颜色
    private Button btnSetBackColor;//设置字体背景颜色

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /* Intent intent = new Intent();
        intent.setClass(this, PermissionUtilActivity.class);
        startActivity(intent);*/
        loadGraphite();
        Typeface mtfp = (Typeface) Graphite.addFontResource(getAssets(), "MenkHar_a_NoFtrTig.ttf", "MenkHar", 0, "", "");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = (PictureAndTextEditorView) findViewById(R.id.edit_text);
        btnAddPic = (Button) findViewById(R.id.btnAddPic);

        mEditText.setTypeface(mtfp);
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
                                mEditText.setFrontColorSpan(color);//将颜色设置到
                                mEditText.setBackColorSpan(color);
                            }
                        });
                colorDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialo
                colorDialog.show();
                // 橡皮擦(擦出)
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
