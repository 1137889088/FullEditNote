package com.chen.fulleditnote.app.view;

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

    //字体样式span
    ForegroundColorSpan frontColorSpan = new ForegroundColorSpan(Color.RED);//字体颜色
    BackgroundColorSpan backColorSpan = new BackgroundColorSpan(Color.parseColor("#AC00FF30"));//字体背景色
    RelativeSizeSpan fontSizeSpan = new RelativeSizeSpan(1.2f);//字体大小

    private String mNewLineTag = "\n";//换行符

    public static final String superscriptSpanTag = "<span:0:L>";//上标
    public static final String mBitmapTag = "<span:1:L>";//图片
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

    private static String getmBitmapTag(int longth) {
        return mBitmapTag.replace("L", "" + longth);
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
                if (str.indexOf(mBitmapTag) != -1) {//判断是否是图片地址
                    String path = str.replace(mBitmapTag, "");//还原地址字符串
                    Bitmap bitmap = bitmap = BitmapFactory.decodeFile(path);
                    //插入图片
                    insertBitmap(path, bitmap);
                } else {
                    //插入文字
                    SpannableString ss = new SpannableString(str);
                    ss.setSpan(frontColorSpan, 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
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
    public void setBackColorSpan(int color) {
        try {
            backColorSpan = new BackgroundColorSpan(color);
        } catch (Exception e) {
            Toast.makeText(this.mContext, "请正确的选择颜色", Toast.LENGTH_SHORT);
        }
    }

    /**
     * 为插入的span指定字体背景颜色
     *
     * @param color 要设置的颜色
     */
    public void setFrontColorSpan(int color) {
        try {
            frontColorSpan = new ForegroundColorSpan(color);
        } catch (Exception e) {
            Toast.makeText(this.mContext, "请正确的选择颜色", Toast.LENGTH_SHORT);
        }
    }

    /**
     * 为插入的span指定字体大小
     *
     * @param fontSize 要设置的字体大小
     */
    public void setFontSizeSpan(float fontSize) {
        fontSizeSpan = new RelativeSizeSpan(fontSize);
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
        path = mBitmapTag + path + mBitmapTag;
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
        int index = getSelectionStart(); // 获取光标所在位置
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        SpannableString spannableString = new SpannableString(text);
        // 用ImageSpan对象替换你指定的字符串
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();//删除线
        spannableString.setSpan(strikethroughSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 将选择的图片追加到EditText中光标所在位置
        if (index < 0 || index >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(index, spannableString);
        }
        edit_text.delete(index + spannableString.length(), index + spannableString.length() * 2);
        return spannableString;
    }

    /**
     * 为字体设置下划线
     *
     * @param text 需要修改的文本
     * @return 封装好的spanString
     */
    private SpannableString setUnderlineSpanString(String text) {
        int index = getSelectionStart(); // 获取光标所在位置
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        SpannableString spannableString = new SpannableString(text);
        // 用ImageSpan对象替换你指定的字符串
        UnderlineSpan underlineSpan = new UnderlineSpan();
        spannableString.setSpan(underlineSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 将选择的图片追加到EditText中光标所在位置
        if (index < 0 || index >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(index, spannableString);
        }
        edit_text.delete(index + spannableString.length(), index + spannableString.length() * 2);
        return spannableString;
    }

    /**
     * 为字体设置下划线
     *
     * @param text 需要修改的文本
     * @return 封装好的spanString
     */
    private SpannableString setSuperscriptSpanString(String text) {
        int index = getSelectionStart(); // 获取光标所在位置
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        SpannableString spannableString = new SpannableString(text);
        // 用ImageSpan对象替换你指定的字符串
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        spannableString.setSpan(superscriptSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 将选择的图片追加到EditText中光标所在位置
        if (index < 0 || index >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(index, spannableString);
        }
        edit_text.delete(index + spannableString.length(), index + spannableString.length() * 2);
        return spannableString;
    }


    /**
     * 为字体设置下标
     *
     * @param text 需要修改的文本
     * @return 封装好的spanString
     */

    private SpannableString setSubscriptSpanString(String text) {
        int index = getSelectionStart(); // 获取光标所在位置
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        SpannableString spannableString = new SpannableString(text);
        // 用ImageSpan对象替换你指定的字符串
        SubscriptSpan subscriptSpan = new SubscriptSpan();
        spannableString.setSpan(subscriptSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 将选择的图片追加到EditText中光标所在位置
        if (index < 0 || index >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(index, spannableString);
        }
        edit_text.delete(index + spannableString.length(), index + spannableString.length() * 2);
        return spannableString;
    }

    /**
     * 为字体设置粗体
     *
     * @param text 需要修改的文本
     * @return 封装好的spanString
     */
    private SpannableString setStyleBOLDSpanString(String text) {
        int index = getSelectionStart(); // 获取光标所在位置
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        SpannableString spannableString = new SpannableString(text);
        // 用ImageSpan对象替换你指定的字符串
        StyleSpan styleSpan_B = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(styleSpan_B, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 将选择的图片追加到EditText中光标所在位置
        if (index < 0 || index >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(index, spannableString);
        }
        edit_text.delete(index + spannableString.length(), index + spannableString.length() * 2);
        return spannableString;
    }


    /**
     * 为字体设置粗体
     *
     * @param text 需要修改的文本
     * @return 封装好的spanString
     */
    private SpannableString setStyleITALICSpanString(String text) {
        int index = getSelectionStart(); // 获取光标所在位置
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        SpannableString spannableString = new SpannableString(text);
        // 用ImageSpan对象替换你指定的字符串
        StyleSpan styleSpan_I = new StyleSpan(Typeface.ITALIC);
        spannableString.setSpan(styleSpan_I, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 将选择的图片追加到EditText中光标所在位置
        if (index < 0 || index >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(index, spannableString);
        }
        edit_text.delete(index + spannableString.length(), index + spannableString.length() * 2);
        return spannableString;
    }

    /**
     * 修改字体大小
     *
     * @param text 需要修改的文本
     * @return 封装好的spanString
     */
    private SpannableString changeFontSizeSpanString(String text) {
        int index = getSelectionStart(); // 获取光标所在位置
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        SpannableString spannableString = new SpannableString(text);
        // 用ImageSpan对象替换你指定的字符串
        spannableString.setSpan(fontSizeSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 将选择的图片追加到EditText中光标所在位置
        if (index < 0 || index >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(index, spannableString);
        }
        edit_text.delete(index + spannableString.length(), index + spannableString.length() * 2);
        return spannableString;
    }

    /**
     * 修改字体颜色
     *
     * @param text 需要修改的文本
     * @return 封装好的spanString
     */
    private SpannableString insertFrontColorSpanString(String text) {
        int index = getSelectionStart(); // 获取光标所在位置
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        SpannableString spannableString = new SpannableString(text);

        // 用ImageSpan对象替换你指定的字符串
        spannableString.setSpan(frontColorSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 将选择的图片追加到EditText中光标所在位置
        if (index < 0 || index >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(index, spannableString);
        }
        edit_text.delete(index + spannableString.length(), index + spannableString.length() * 2);
        return spannableString;
    }


    /**
     * 修改字体的背景色
     *
     * @param text 需要修改的文本
     * @return 封装好的spanString
     */
    private SpannableString insertBackgroundColorString(String text) {
        int index = getSelectionStart(); // 获取光标所在位置
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(backColorSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 将选择的图片追加到EditText中光标所在位置
        if (index < 0 || index >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(index, spannableString);
        }
        edit_text.delete(index + spannableString.length(), index + spannableString.length() * 2);
        return spannableString;
    }


    /**
     * 插入图片
     *
     * @param path
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
        if (content.length() > 0 && content.contains(mBitmapTag)) {
            String[] split = content.split(mBitmapTag);
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
            }else {
                tag = mSpan.toString();
            }
            text.insert(start,tag);
        }
        System.out.println(text);
        return text.toString();
    }
}
