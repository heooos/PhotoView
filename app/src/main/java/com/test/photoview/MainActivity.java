package com.test.photoview;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private Uri uri;
    private static final String FILE_NAME = "image.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyImageToSDcard();
            }
        });
    }

    private void copyImageToSDcard() {
        new AsyncTask<Void, Void, File>() {
            @Override
            protected File doInBackground(Void... params) {
                FileOutputStream fos = null;
                InputStream in = null;
                File file = new File(Environment.getExternalStorageDirectory(), FILE_NAME);
                try {
                    in = getResources().getAssets().open(FILE_NAME);
                    fos = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
                    fos.flush();
                    fos.close();
                    in.close();
                    return file;
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //异常出现在fos和in实例化的时候，如果出现异常，说明对象没有完成实例化，还是null，并不能在这里close();
                    //如果在这里调用close(),会报错提示对象可能未实例化。
                }
                return file;
            }

            @Override
            protected void onPostExecute(File imageFile) {
                if (imageFile != null) {
                    //图片复制完成时打开图片选择器
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    //获得文件的uri，启动activity进行查看
                    Uri imageUri = Uri.fromFile(imageFile);
                    intent.setDataAndType(imageUri, "image/*");
                    startActivity(intent);
                }
            }
        }.execute();
    }
}
