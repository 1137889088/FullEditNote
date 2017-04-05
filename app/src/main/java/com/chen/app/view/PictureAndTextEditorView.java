package com.chen.app.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.*;
import android.text.style.*;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 17/4/3.
 * 图文混排编辑器
 */
public class PictureAndTextEditorView extends EditText {
    private Context mContext;//上下文对象
    private List<String> mContentList;//列表
    Editable edit_text = getEditableText();//text
    int color = Color.BLACK;
    float fontSize = 1;
    //字体样式span


    private String mNewLineTag = "\n";//换行符

    public static final String superscriptSpanTag = "<span:0:L>";//上标
    public static final String imgSpanTag = "<span:1>";//图片
    public static final String frontColorSpanTag = "<span:2:L:C>";//字颜色
    public static final String backColorSpanTag = "<span:3:L:C>";//背景
    public static final String styleSpanBTag = "<span:4:L:B>";//粗体
    public static final String styleSpanITag = "<span:5:L:I>";//斜体
    public static final String fontSizeSpanTag = "<span:6:L:C>";//字体大小
    public static final String strikethroughSpanTag = "<span:7:L>";//删除线
    public static final String underlineSpanTag = "<span:8:L>";//下划线
    public static final String subscriptSpanTag = "<span:9:L>";//下标

    private String getSuperscriptSpanTag(int longth) {
        return superscriptSpanTag.replace("L", "" + longth);
    }

    private static String getImgSpanTag(int longth,String path) {
        return imgSpanTag.replace("L", "" + longth).replace("C", "" + path);
    }

    private String getFrontColorSpanTag(int longth, int color) {
        return frontColorSpanTag.replace("L", "" + longth).replace("C", "" + color);
    }

    public String getBackColorSpanTag(int longth, int color) {
        return backColorSpanTag.replace("L", "" + longth).replace("C", "" + color);
    }

    public String getStyleSpanBTag(int longth) {
        return styleSpanBTag.replace("L", "" + longth);
    }

    public String getStyleSpanITag(int longth) {
        return styleSpanITag.replace("L", "" + longth);
    }

    public static String getFontSizeSpanTag(int longth, float size) {
        return fontSizeSpanTag.replace("L", "" + longth).replace("C", "" + size);
    }

    public static String getStrikethroughSpanTag(int longth) {
        return strikethroughSpanTag.replace("L", "" + longth);
    }

    public static String getUnderlineSpanTag(int longth) {
        return underlineSpanTag.replace("L", "" + longth);
    }

    public static String getSubscriptSpanTag(int longth) {
        return subscriptSpanTag.replace("L", "" + longth);
    }


    public Context getmContext() {
        return mContext;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public PictureAndTextEditorView(Context context) {
        super(context);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    public PictureAndTextEditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public PictureAndTextEditorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        color = Color.BLACK;
        mContext = context;
        mContentList = getmContentList();
        insertData();
    }

    /**
     * 设置数据
     */
    private void insertData() {
        //
        if (mContentList.size() > 0) {
            for (String str : mContentList) {
                if (str.indexOf(imgSpanTag) != -1) {//判断是否是图片地址
                    String path = str.replace(imgSpanTag, "");//还原地址字符串
                    Bitmap bitmap = bitmap = BitmapFactory.decodeFile(path);
                    //插入图片
                    insertBitmap(path, bitmap);
                } else {
                    //插入文字
                    SpannableString ss = new SpannableString(str);
                    //ss.setSpan(frontColorSpan, 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    append(ss);
                }
            }
        }
    }


    /**
     * 为插入的span指定字体背景颜色
     *
     * @param color 要设置的颜色
     */
    public void setFrontColor(int color) {
        try {
            this.color = color;
        } catch (Exception e) {
            Toast.makeText(this.mContext, "请正确的选择颜色", Toast.LENGTH_SHORT);
        }
    }

    /**
     * 为插入的span指定字体大小
     *
     * @param fontSize 要设置的字体大小
     */
    public void setFontSize(float fontSize) {
        fontSize = fontSize;
    }

   /* private void setOwnedSpan(SpannableString spannableString,int length,ParcelableSpan[] spans){
        for (ParcelableSpan mSpan : spans) {

            if (mSpan instanceof StyleSpan) {
                if (((StyleSpan) mSpan).getStyle() == Typeface.BOLD) {
                    StyleSpan span = new StyleSpan(Typeface.BOLD);
                    spannableString.setSpan(span,0,length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    StyleSpan span = new StyleSpan(Typeface.ITALIC);
                    spannableString.setSpan(span,0,length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            } else if (mSpan instanceof ForegroundColorSpan) {
                ForegroundColorSpan span = new ForegroundColorSpan(((ForegroundColorSpan) mSpan).getForegroundColor());
                spannableString.setSpan(span,0,length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (mSpan instanceof BackgroundColorSpan) {
                BackgroundColorSpan span = new BackgroundColorSpan(((BackgroundColorSpan) mSpan).getBackgroundColor());
                spannableString.setSpan(span, 0, length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (mSpan instanceof SuperscriptSpan) {
                SuperscriptSpan span = new SuperscriptSpan();
                spannableString.setSpan(span, 0, length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (mSpan instanceof SubscriptSpan) {
                SubscriptSpan span = new SubscriptSpan();
                spannableString.setSpan(span, 0, length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (mSpan instanceof StrikethroughSpan) {
                StrikethroughSpan span = new StrikethroughSpan();
                spannableString.setSpan(span, 0, length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (mSpan instanceof RelativeSizeSpan) {
                *//*RelativeSizeSpan span = new RelativeSizeSpan(((RelativeSizeSpan) mSpan).getSizeChange());
                spannableString.setSpan(span, 0, length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*//*
            } else if (mSpan instanceof UnderlineSpan) {
                UnderlineSpan span = new UnderlineSpan();
                spannableString.setSpan(span, 0, length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }*/

    private boolean removeSpan(ParcelableSpan own, ParcelableSpan[] spans) {
        boolean isExist = false;
        for (ParcelableSpan mSpan : spans) {
            if (mSpan instanceof StyleSpan) {
                if (own instanceof StyleSpan) {
                    if (((StyleSpan) mSpan).getStyle() == ((StyleSpan) own).getStyle()) {
                        getText().removeSpan(mSpan);
                        isExist = true;
                    }
                }
            } else if (mSpan instanceof ForegroundColorSpan) {
                if (own instanceof ForegroundColorSpan) {
                    getText().removeSpan(mSpan);
                    isExist = true;
                }
            } else if (mSpan instanceof BackgroundColorSpan) {
                if (own instanceof BackgroundColorSpan) {
                    getText().removeSpan(mSpan);
                    isExist = true;
                }
            } else if (mSpan instanceof SuperscriptSpan) {
                if (own instanceof SuperscriptSpan) {
                    getText().removeSpan(mSpan);
                    isExist = true;
                }
            } else if (mSpan instanceof SubscriptSpan) {
                if (own instanceof SubscriptSpan) {
                    getText().removeSpan(mSpan);
                    isExist = true;
                }
            } else if (mSpan instanceof StrikethroughSpan) {
                if (own instanceof StrikethroughSpan) {
                    getText().removeSpan(mSpan);
                    isExist = true;
                }
            } else if (mSpan instanceof RelativeSizeSpan) {
                if (own instanceof RelativeSizeSpan) {
                    getText().removeSpan(mSpan);
                    isExist = true;
                }
            } else if (mSpan instanceof UnderlineSpan) {
                if (own instanceof UnderlineSpan) {
                    getText().removeSpan(mSpan);
                    isExist = true;
                }
            }
        }
        return isExist;
    }

    /**
     * 插入图片
     *
     * @param bitmap bimmap图片
     * @param path   路径
     * @return 将图片封装的spanString
     */
    private SpannableString insertBitmap(String path, Bitmap bitmap) {

        int index = getSelectionStart(); // 获取光标所在位置
        //插入换行符，使图片单独占一行
        SpannableString newLine = new SpannableString("\n");
        edit_text.insert(index, newLine);//插入图片前换行
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        path = imgSpanTag + path + imgSpanTag;
        SpannableString spannableString = new SpannableString(path);
        // 根据Bitmap对象创建ImageSpan对象
        ImageSpan imageSpan = new ImageSpan(mContext, bitmap);
        // 用ImageSpan对象替换你指定的字符串
        spannableString.setSpan(imageSpan, 0, path.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 将选择的图片追加到EditText中光标所在位置
        if (index < 0 || index >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(index, spannableString);
        }
        edit_text.insert(index, newLine);//插入图片后换行
        return spannableString;
    }

    /**
     * 为字体设置删除线
     *
     * @param text 需要修改的文本
     * @return 封装好的spanString
     */

    private SpannableString setStrikethroughSpanString(String text) {
        int start = getSelectionStart(); // 获取光标所在位置
        int end = getSelectionEnd();


        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        SpannableString spannableString = new SpannableString(text);
        // 用ImageSpan对象替换你指定的字符串
        //setOwnedSpan(spannableString, text.length(), spans);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();//删除线
        ParcelableSpan[] spans = getText().getSpans(start, end, ParcelableSpan.class);
        if (!removeSpan(strikethroughSpan, spans)) {
            spannableString.setSpan(strikethroughSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
     /*   // 将选择的图片追加到EditText中光标所在位置
        if (start < 0 || start >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(start, spannableString);
        }*/
        edit_text.replace(start, end, spannableString);
        //edit_text.delete(start + spannableString.length(), start + spannableString.length() * 2);
        return spannableString;
    }

    /**
     * 为字体设置下划线
     *
     * @param text 需要修改的文本
     * @return 封装好的spanString
     */
    private SpannableString setUnderlineSpanString(String text) {
        int start = getSelectionStart(); // 获取光标所在位置
        int end = getSelectionEnd();
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        SpannableString spannableString = new SpannableString(text);
        // 用ImageSpan对象替换你指定的字符串
       /* ParcelableSpan[] spans = getText().getSpans(start, end, ParcelableSpan.class);
        setOwnedSpan(spannableString,text.length(),spans);*/
        UnderlineSpan underlineSpan = new UnderlineSpan();
        ParcelableSpan[] spans = getText().getSpans(start, end, ParcelableSpan.class);
        if (!removeSpan(underlineSpan, spans)) {
            spannableString.setSpan(underlineSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        // 将选择的图片追加到EditText中光标所在位置

        edit_text.replace(start, end, spannableString);
        return spannableString;
    }

    /**
     * 为字体设置上标
     *
     * @param text 需要修改的文本
     * @return 封装好的spanString
     */
    private SpannableString setSuperscriptSpanString(String text) {
        int start = getSelectionStart(); // 获取光标所在位置
        int end = getSelectionEnd();
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        SpannableString spannableString = new SpannableString(text);
        // 用ImageSpan对象替换你指定的字符串
       /* ParcelableSpan[] spans = getText().getSpans(start, end, ParcelableSpan.class);
        setOwnedSpan(spannableString, text.length(), spans);*/
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        ParcelableSpan[] spans = getText().getSpans(start, end, ParcelableSpan.class);
        if (!removeSpan(superscriptSpan, spans)) {
            spannableString.setSpan(superscriptSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        // 将选择的图片追加到EditText中光标所在位置
       /* if (start < 0 || start >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(start, spannableString);
        }
        edit_text.delete(start + spannableString.length(), start + spannableString.length() * 2);*/
        edit_text.replace(start, end, spannableString);
        return spannableString;
    }


    /**
     * 为字体设置下标
     *
     * @param text 需要修改的文本
     * @return 封装好的spanString
     */

    private SpannableString setSubscriptSpanString(String text) {
        int start = getSelectionStart(); // 获取光标所在位置
        int end = getSelectionEnd();
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        SpannableString spannableString = new SpannableString(text);
        // 用ImageSpan对象替换你指定的字符串
       /* ParcelableSpan[] spans = getText().getSpans(start, end, ParcelableSpan.class);
        setOwnedSpan(spannableString, text.length(), spans);*/
        SubscriptSpan subscriptSpan = new SubscriptSpan();
        ParcelableSpan[] spans = getText().getSpans(start, end, ParcelableSpan.class);
        if (!removeSpan(subscriptSpan, spans)) {
            spannableString.setSpan(subscriptSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        // 将选择的图片追加到EditText中光标所在位置
      /*  if (start < 0 || start >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(start, spannableString);
        }
        edit_text.delete(start + spannableString.length(), start + spannableString.length() * 2);*/
        edit_text.replace(start, end, spannableString);
        return spannableString;
    }

    /**
     * 为字体设置粗体
     *
     * @param text 需要修改的文本
     * @return 封装好的spanString
     */
    private SpannableString setStyleBOLDSpanString(String text) {
        int start = getSelectionStart(); // 获取光标所在位置
        int end = getSelectionEnd();
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        SpannableString spannableString = new SpannableString(text);
        // 用ImageSpan对象替换你指定的字符串
        /*ParcelableSpan[] spans = getText().getSpans(start, end, ParcelableSpan.class);
        setOwnedSpan(spannableString, text.length(), spans);*/
        StyleSpan styleSpan_B = new StyleSpan(Typeface.BOLD);
        ParcelableSpan[] spans = getText().getSpans(start, end, ParcelableSpan.class);
        if (!removeSpan(styleSpan_B, spans)) {
            spannableString.setSpan(styleSpan_B, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        // 将选择的图片追加到EditText中光标所在位置
       /* if (start < 0 || start >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(start, spannableString);
        }
        edit_text.delete(start + spannableString.length(), start + spannableString.length() * 2);*/
        edit_text.replace(start, end, spannableString);
        return spannableString;
    }


    /**
     * 为字体设置粗体
     *
     * @param text 需要修改的文本
     * @return 封装好的spanString
     */
    private SpannableString setStyleITALICSpanString(String text) {
        int start = getSelectionStart(); // 获取光标所在位置
        int end = getSelectionEnd();
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        SpannableString spannableString = new SpannableString(text);
        // 用ImageSpan对象替换你指定的字符串
       /* ParcelableSpan[] spans = getText().getSpans(start, end, ParcelableSpan.class);
        setOwnedSpan(spannableString, text.length(), spans);*/
        StyleSpan styleSpan_I = new StyleSpan(Typeface.ITALIC);
        ParcelableSpan[] spans = getText().getSpans(start, end, ParcelableSpan.class);
        if (!removeSpan(styleSpan_I, spans)) {
            spannableString.setSpan(styleSpan_I, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        // 将选择的图片追加到EditText中光标所在位置
       /* if (start < 0 || start >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(start, spannableString);
        }
        edit_text.delete(start + spannableString.length(), start + spannableString.length() * 2);*/
        edit_text.replace(start, end, spannableString);
        return spannableString;
    }

    /**
     * 修改字体大小
     *
     * @param text 需要修改的文本
     * @return 封装好的spanString
     */
    private SpannableString changeFontSizeSpanString(String text) {
        int start = getSelectionStart(); // 获取光标所在位置
        int end = getSelectionEnd();
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        SpannableString spannableString = new SpannableString(text);
        // 用ImageSpan对象替换你指定的字符串
       /* ParcelableSpan[] spans = getText().getSpans(start, end, ParcelableSpan.class);
        setOwnedSpan(spannableString, text.length(), spans);*/
        RelativeSizeSpan fontSizeSpan = new RelativeSizeSpan(fontSize);//字体大小
        ParcelableSpan[] spans = getText().getSpans(start, end, ParcelableSpan.class);
        removeSpan(fontSizeSpan, spans);
        spannableString.setSpan(fontSizeSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 将选择的图片追加到EditText中光标所在位置
      /*  if (start < 0 || start >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(start, spannableString);
        }
        edit_text.delete(start + spannableString.length(), start + spannableString.length() * 2);*/
        edit_text.replace(start, end, spannableString);
        return spannableString;
    }

    /**
     * 修改字体颜色
     *
     * @param text 需要修改的文本
     * @return 封装好的spanString
     */
    private SpannableString insertFrontColorSpanString(String text) {
        int start = getSelectionStart(); // 获取光标所在位置
        int end = getSelectionEnd();
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        SpannableString spannableString = new SpannableString(text);
        /*ParcelableSpan[] spans = getText().getSpans(start, end, ParcelableSpan.class);
        setOwnedSpan(spannableString, text.length(), spans);*/
        // 用ImageSpan对象替换你指定的字符串
        ForegroundColorSpan frontColorSpan = new ForegroundColorSpan(color);
        ParcelableSpan[] spans = getText().getSpans(start, end, ParcelableSpan.class);
        if (!removeSpan(frontColorSpan, spans)) {
            spannableString.setSpan(frontColorSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        // 将选择的图片追加到EditText中光标所在位置
        /*if (start < 0 || start >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(start, spannableString);
        }
        edit_text.delete(start + spannableString.length(), start + spannableString.length() * 2);*/
        edit_text.replace(start, end, spannableString);
        return spannableString;
    }


    /**
     * 修改字体的背景色
     *
     * @param text 需要修改的文本
     * @return 封装好的spanString
     */
    private SpannableString insertBackgroundColorString(String text) {
        int start = getSelectionStart(); // 获取光标所在位置
        int end = getSelectionEnd();
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        SpannableString spannableString = new SpannableString(text);
       /* ParcelableSpan[] spans = getText().getSpans(start, end, ParcelableSpan.class);
        setOwnedSpan(spannableString, text.length(), spans);*/
        ParcelableSpan[] spans = getText().getSpans(start, end, ParcelableSpan.class);
        BackgroundColorSpan backColorSpan = new BackgroundColorSpan(Color.parseColor("#AC00FF30"));//字体背景色
        removeSpan(backColorSpan, spans);
        spannableString.setSpan(backColorSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 将选择的图片追加到EditText中光标所在位置
       /* if (start < 0 || start >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(start, spannableString);
        }
        edit_text.delete(start + spannableString.length(), start + spannableString.length() * 2);*/
        edit_text.replace(start, end, spannableString);
        return spannableString;
    }


    /**
     * 插入图片
     *
     * @param path 插入图片的路径
     */
    public void insertBitmap(String path) {
        Bitmap bitmap = getSmallBitmap(path, 480, 800);
        insertBitmap(path, bitmap);
    }


    /**
     * 用集合的形式获取控件里的内容
     *
     * @return 控件内容
     */
    public List<String> getmContentList() {
        if (mContentList == null) {
            mContentList = new ArrayList<String>();
        }
        String content = getText().toString().replaceAll(mNewLineTag, "");
        if (content.length() > 0 && content.contains(imgSpanTag)) {
            String[] split = content.split(imgSpanTag);
            mContentList.clear();
            for (String str : split) {
                mContentList.add(str);
            }
        } else {
            mContentList.add(content);
        }
        return mContentList;
    }


    /**
     * 设置显示的内容集合
     *
     * @param contentList 列表内容
     */
    public void setmContentList(List<String> contentList) {
        if (mContentList == null) {
            mContentList = new ArrayList<>();
        }
        this.mContentList.clear();
        this.mContentList.addAll(contentList);
        insertData();
    }

    float oldY = 0;


    /**
     * 触摸事件
     *
     * @param event 事件
     * @return 执行结果 true，false
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldY = event.getY();
                requestFocus();
                break;
            case MotionEvent.ACTION_MOVE:
                float newY = event.getY();
                if (Math.abs(oldY - newY) > 20) {
                    clearFocus();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }


    /**
     * 根据路径获得图片并压缩，返回bitmap用于显示
     *
     * @param filePath  图片路径
     * @param reqWidth  宽
     * @param reqHeight 高
     * @return 生成的bitmap
     */
    public Bitmap getSmallBitmap(String filePath, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int w_width = w_screen;
        int b_width = bitmap.getWidth();
        int b_height = bitmap.getHeight();
        int w_height = w_width * b_height / b_width;
        bitmap = Bitmap.createScaledBitmap(bitmap, w_width, w_height, false);
        return bitmap;
    }

    /**
     * 计算图片缩放的值
     *
     * @param options   选项
     * @param reqWidth  要求的宽
     * @param reqHeight 要求的高
     * @return 图片应该缩放的比例
     */
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 将已经选择的字体颜色设置到选中的文本中
     */
    public void setFontColor() {
        int start = getSelectionStart();
        int end = getSelectionEnd();
        CharSequence selectText = getText().subSequence(start, end);
        if ((end - start) <= 0) {
            Toast.makeText(this.mContext, "请选择更改的文本", Toast.LENGTH_SHORT).show();
            return;
        }
        insertFrontColorSpanString(selectText.toString());
    }

    public void setBackColor() {
        int start = getSelectionStart();
        int end = getSelectionEnd();
        CharSequence selectText = getText().subSequence(start, end);
        if ((end - start) <= 0) {
            Toast.makeText(this.mContext, "请选择更改的文本", Toast.LENGTH_SHORT).show();
            return;
        }
        insertBackgroundColorString(selectText.toString());
    }

    public void setFontSize() {
        int start = getSelectionStart();
        int end = getSelectionEnd();
        CharSequence selectText = getText().subSequence(start, end);
        if ((end - start) <= 0) {
            Toast.makeText(this.mContext, "请选择更改的文本", Toast.LENGTH_SHORT).show();
            return;
        }
        changeFontSizeSpanString(selectText.toString());
    }

    public void setStrikethrough() {
        int start = getSelectionStart();
        int end = getSelectionEnd();
        CharSequence selectText = getText().subSequence(start, end);
        if ((end - start) <= 0) {
            Toast.makeText(this.mContext, "请选择更改的文本", Toast.LENGTH_SHORT).show();
            return;
        }
        setStrikethroughSpanString(selectText.toString());
    }

    public void setUnderline() {
        int start = getSelectionStart();
        int end = getSelectionEnd();
        CharSequence selectText = getText().subSequence(start, end);
        if ((end - start) <= 0) {
            Toast.makeText(this.mContext, "请选择更改的文本", Toast.LENGTH_SHORT).show();
            return;
        }
        setUnderlineSpanString(selectText.toString());
    }

    public void setSuperscript() {
        int start = getSelectionStart();
        int end = getSelectionEnd();
        CharSequence selectText = getText().subSequence(start, end);
        if ((end - start) <= 0) {
            Toast.makeText(this.mContext, "请选择更改的文本", Toast.LENGTH_SHORT).show();
            return;
        }
        setSuperscriptSpanString(selectText.toString());
    }

    public void setSubscript() {
        int start = getSelectionStart();
        int end = getSelectionEnd();
        CharSequence selectText = getText().subSequence(start, end);
        if ((end - start) <= 0) {
            Toast.makeText(this.mContext, "请选择更改的文本", Toast.LENGTH_SHORT).show();
            return;
        }
        setSubscriptSpanString(selectText.toString());
    }

    public void setStyleBOLD() {
        int start = getSelectionStart();
        int end = getSelectionEnd();
        CharSequence selectText = getText().subSequence(start, end);
        if ((end - start) <= 0) {
            Toast.makeText(this.mContext, "请选择更改的文本", Toast.LENGTH_SHORT).show();
            return;
        }
        setStyleBOLDSpanString(selectText.toString());
    }

    public void setStyleITALIC() {
        int start = getSelectionStart();
        int end = getSelectionEnd();
        CharSequence selectText = getText().subSequence(start, end);
        if ((end - start) <= 0) {
            Toast.makeText(this.mContext, "请选择更改的文本", Toast.LENGTH_SHORT).show();
            return;
        }
        setStyleITALICSpanString(selectText.toString());
    }

    public String save() {
        Editable text = getText();
        ParcelableSpan[] mSpans = text.getSpans(0, length(), ParcelableSpan.class);
        for (ParcelableSpan mSpan : mSpans) {
            int start = text.getSpanStart(mSpan);
            int end = text.getSpanEnd(mSpan);
            int longth = end - start;
            String tag = "";
            if (mSpan instanceof StyleSpan) {
                if (((StyleSpan) mSpan).getStyle() == Typeface.BOLD) {
                    tag = getStyleSpanBTag(longth);
                } else {
                    tag = getStyleSpanITag(longth);
                }
            } else if (mSpan instanceof ForegroundColorSpan) {
                tag = getFrontColorSpanTag(longth, ((ForegroundColorSpan) mSpan).getForegroundColor());
            } else if (mSpan instanceof BackgroundColorSpan) {
                tag = getBackColorSpanTag(longth, ((BackgroundColorSpan) mSpan).getBackgroundColor());
            } else if (mSpan instanceof SuperscriptSpan) {
                tag = getSuperscriptSpanTag(longth);
            } else if (mSpan instanceof SubscriptSpan) {
                tag = getSubscriptSpanTag(longth);
            } else if (mSpan instanceof StrikethroughSpan) {
                tag = getStrikethroughSpanTag(longth);
            } else if (mSpan instanceof RelativeSizeSpan) {
                tag = getFontSizeSpanTag(longth, ((RelativeSizeSpan) mSpan).getSizeChange());
            } else if (mSpan instanceof UnderlineSpan) {
                tag = getUnderlineSpanTag(longth);
            } else if(mSpan instanceof ImageSpan){

            }
            text.insert(start, tag);
        }
        System.out.println(text);
        return text.toString();
    }
}
