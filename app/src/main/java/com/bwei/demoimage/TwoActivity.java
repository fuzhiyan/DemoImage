package com.bwei.demoimage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Administrator on 2017/6/7.
 * time:
 * author:付智焱
 */

public class TwoActivity extends AppCompatActivity {
    private ViewPagerFixed pager;
    private AlertDialog builder;
    private int[] images = new int[]{R.drawable.view1, R.drawable.view2, R.drawable.view3};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two);
        pager = (ViewPagerFixed) findViewById(R.id.vp);


        pager.setAdapter(new ImageAdapter(this));
    }


    private void setDialog(final Bitmap bitmap) {
        builder = new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher).setTitle("系统提示").setMessage("是否保存图片到本地相册").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                saveImageToGallery(TwoActivity.this, bitmap);
                Toast.makeText(TwoActivity.this, "success", Toast.LENGTH_SHORT).show();

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create();
        Window window = builder.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.mystyle);  //添加动画
        builder.show();
    }

    public void saveImageToGallery(Context context, Bitmap bmp) {

        // 首先保存图片
        File file = Environment.getExternalStorageDirectory();
        File appDir = new File(file, "Photo");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File currentFile = new File(appDir, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(currentFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(currentFile.getPath()))));
    }

    class ImageAdapter extends PagerAdapter {
        Context context;

        public ImageAdapter(Context context) {
            this.context = context;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public View instantiateItem(ViewGroup container, final int position) {

            ImageView image = new ImageView(context);
            image.setImageResource(images[position]);

            //使图片实现可以放大缩小的功能
            PhotoViewAttacher mAttacher = new PhotoViewAttacher(image);
            mAttacher.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Resources res = getResources();
                    BitmapDrawable d = (BitmapDrawable) res.getDrawable(images[position]);
                    Bitmap img = d.getBitmap();
                    setDialog(img);
                    return true;
                }
            });

            container.addView(image);

            return image;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }
    }

