package com.roger.img.crop;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.roger.demo4roger.R;

import java.io.File;
import java.util.List;

public class CropActivity extends AppCompatActivity {

    public static final int VALUE_PICK_PICTURE = 2;
    private static final String TAG = "CropAct";
    private static final int CROP_PICTURE = 3;
    private static final int PHONE_CAMERA = 4;
    //裁剪图片存放地址的Uri
    private Uri cropImageUri;
    private Context mContext;
    public Uri mPhotoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.crop_btn:
                cropPic("");
                break;

            case R.id.pick_crop_btn:

                selectPicFromLocal();
                break;
            case R.id.takePhoto:
                cameraPic();
                break;
        }
    }

    /**
     * 跳转到系统裁剪图片页面
     * @param imagePath 需要裁剪的图片路径
     */
    private void cropPic(String imagePath) {
        File file = new File(imagePath);
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            Uri contentUri = FileProvider.getUriForFile(this, "com.leon.crop.fileprovider", file);
            Uri contentUri = Uri.fromFile(file);
            intent.setDataAndType(contentUri, "image/*");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "image/*");
        }
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0.1);
        intent.putExtra("aspectY", 0.1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        intent.putExtra("scale", true);
//     startActivityForResult(intent, CROP_PHOTO);
    }

    private void selectPicFromLocal() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, VALUE_PICK_PICTURE);
                // 第2种方案,直接跳转图库选择 图片
/*        Intent intent = new Intent();
        intent.setType("image*//*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mFragment.startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_CHOOSE_PHOTO);*/

        // 第3种方案,也可以,会弹出下载和图片可供选择
/*        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image*//*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image*//*");
        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, VALUE_PICK_PICTURE);*/
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case VALUE_PICK_PICTURE: // 如果是直接从相册获取
                    startPhotoZoom(data.getData());//拿到所选图片的Uri
                    break;
                case PHONE_CAMERA:
                    startPhotoZoom(mPhotoUri);//拿到所选图片的Uri
//                    startPhotoZoom(data.getData());//拿到所选图片的Uri 没有用,setData后无法打开相机
                    break;
            }
        }
    }

    public void startPhotoZoom(Uri uri) {
        cropImageUri = createTempImageUri(mContext, "crop_image.jpg", false);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            // 获取权限,否则图库报错SecurityException: Permission Denial: opening provider
            List<ResolveInfo> resInfoList = getPackageManager()
                    .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                grantUriPermission(packageName, cropImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                        | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        }
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);

        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, CROP_PICTURE);
    }

    /**
     * 打开相机截图
     */
    private void cameraPic() {
        mPhotoUri = createTempImageUri(mContext, "photo.png", false);
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
//        intent.setDataAndType(mPhotoUri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            // 获取权限,否则图库报错SecurityException: Permission Denial: opening provider
            List<ResolveInfo> resInfoList = getPackageManager()
                    .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                grantUriPermission(packageName, mPhotoUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                        | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        }
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
//        intent.setClipData(ClipData.newRawUri(MediaStore.EXTRA_OUTPUT, mPhotoUri));
        startActivityForResult(intent, PHONE_CAMERA);
    }



    private Uri createTempImageUri(Context context, String fileName, boolean purge) {
//        File outputFile = new File(mContext.getExternalCacheDir(),"photo.png");
        final File folder = context.getCacheDir();
        folder.mkdirs();
        final File fullPath = new File(folder, fileName);
        if (purge) {
            fullPath.delete();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context,
                    RestrictedProfileSettings.FILE_PROVIDER_AUTHORITY, fullPath);
        }else {
            return Uri.fromFile(fullPath);
        }
    }

    class RestrictedProfileSettings{
//        public static final String FILE_PROVIDER_AUTHORITY = "com.android.settings.files";
        public static final String FILE_PROVIDER_AUTHORITY = "com.baoliyota.demo.FileProvider";
    }

}
