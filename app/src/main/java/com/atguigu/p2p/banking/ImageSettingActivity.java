package com.atguigu.p2p.banking;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.p2p.R;
import com.atguigu.p2p.base.BaseAvtivity;
import com.atguigu.p2p.utils.BitmapUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.InjectView;

public class ImageSettingActivity extends BaseAvtivity {

    @InjectView(R.id.base_title)
    TextView baseTitle;
    @InjectView(R.id.base_back)
    ImageView baseBack;
    @InjectView(R.id.base_setting)
    ImageView baseSetting;
    @InjectView(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @InjectView(R.id.tv_user_change)
    TextView tvUserChange;
    @InjectView(R.id.btn_user_logout)
    Button btnUserLogout;
    private File mFilesDir;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_setting;
    }

    @Override
    protected void initTitle() {
        baseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        baseTitle.setText("设置");
        baseSetting.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

        tvUserChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //切换用户头像
                chagerUserIcon();
            }
        });
    }

    private String changeName[] = {"相机", "相册"};

    private void chagerUserIcon() {
        new AlertDialog.Builder(this)
                .setTitle("选择方式")
                .setItems(changeName, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //which 是数组下标
                        if (which == 0) {
                            //打开系统拍照程序,选择拍照照片
                            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(camera, 0);

                        } else {
                            //打开相册  选择照片
                            Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(picture, 1);
                        }
                    }
                }).show();
    }
    //数据回调


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            //相机
            //拍照
            Bundle bundle = data.getExtras();
            //获取相机返回的数据,并转换为图片格式
            Bitmap bitmap = (Bitmap) bundle.get("data");
            //处理成圆形头像
            Bitmap circleBitmap = BitmapUtils.circleBitmap(bitmap);
            //设置图片
            ivUserIcon.setImageBitmap(circleBitmap);
            //保存图片
            savaImage(bitmap);
            //压缩
            //uploadImage(bitmap);  //压缩
        } else if (data != null) {
            //图库 解析图库的操作,跟android系统有很大相关性,不同的系统使用uri的authority有很大不同
            Uri selectedImage = data.getData();
            //android各个不同的系统版本,对于获取外部存储上的资源，返回的Uri对象都可能各不一样,
            // 所以要保证无论是哪个系统版本都能正确获取到图片资源的话就需要针对各种情况进行一个处理了
            //这里返回的uri情况就有点多了
            //在4.4.2之前返回的uri是:content://media/external/images/media/3951或者file://....
            // 在4.4.2返回的是content://com.android.providers.media.documents/document/image

            String path = getPath(selectedImage);
            //存储-----内存
            Bitmap decodeFile = BitmapFactory.decodeFile(path);
            //bitmap圆形裁剪
            Bitmap circleBitmap = null;
            try {
                circleBitmap = BitmapUtils.circleBitmap(decodeFile);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //加载显示
            ivUserIcon.setImageBitmap(circleBitmap);
            //上传到服务区
            //保存到本地
            savaImage(decodeFile);
        }
    }

    private void savaImage(Bitmap bitmap) {
        FileOutputStream os = null;
        try {
            //判断是否挂载了sd卡
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //外部存储路径
                mFilesDir = getExternalFilesDir("");
            } else {
                //内部存储路劲
                mFilesDir = getFilesDir();
            }

            //全路径
            File path = new File(mFilesDir, "p2p_icon.png");

            //输出流
            os = new FileOutputStream(path);
            //第一个参数是图片的格式,第二个参数是图片的质量数值打的质量高,第三个是输出流
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            //保存当前是否有更新
            saveImage(true);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    @SuppressLint("NewApi")
    private String getPath(Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        //高于4.4.2的版本
        if (sdkVersion >= 19) {
            Log.e("TAG", "uri auth: " + uri.getAuthority());
            if (isExternalStorageDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(this, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(this, contentUri, selection, selectionArgs);
            } else if (isMedia(uri)) {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor actualimagecursor = this.managedQuery(uri, proj, null, null, null);
                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                return actualimagecursor.getString(actual_image_column_index);
            }


        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(this, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * uri路径查询字段
     *
     * @param context
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isMedia(Uri uri) {
        return "media".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
