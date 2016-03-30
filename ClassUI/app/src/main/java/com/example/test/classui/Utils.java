package com.example.test.classui;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Loboy on 2016/3/17.
 */
public class Utils {
    public static void writeFile(Context context, String fileName, String content) // String content為要寫入檔案(檔名String fileName)的新內容
    {
        try
        {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_APPEND);
            fos.write(content.getBytes());
            fos.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String readFile(Context context, String fileName)
    {
        try
        {
            FileInputStream fis = context.openFileInput(fileName);
            byte[] buffer = new byte[1024];
            fis.read(buffer, 0, buffer.length);
            fis.close();
            return new String(buffer);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return  null;
    }

    public static Uri getPhotoUri()
    {
        // 不使用上課所教的 :   File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //String pathImage = Environment.getExternalStorageDirectory().getAbsolutePath() + "/classUI_photo.jpg";
        //File classImageFile = new File(pathImage);

        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if(dir.exists() == false)
        {
            dir.mkdir();
        }

        // 在預設的Public Directory資料夾下，新建一個指定檔名的檔案
        File classImageFile = new File(dir, "classUI_photo.png");

        //回傳該檔名的圖檔Uri
        return Uri.fromFile(classImageFile);
    }
}
