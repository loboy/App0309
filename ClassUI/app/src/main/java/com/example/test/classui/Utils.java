package com.example.test.classui;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

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
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return  null;
    }

    public static Uri getPhotoUri()
    {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if(dir.exists() == false)
        {
            dir.mkdir();
        }

        // 在預設的Public Directory資料夾下，新建一個指定檔名的檔案
        File cameraImageFile = new File(dir, "Class_Camera_Photo.png");

        //回傳該檔名的圖檔Uri
        return Uri.fromFile(cameraImageFile);
    }

    public static byte[] urlToByte(String urlString)
    {
        try {
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream(); // 串流輸入的目的地
            byte[] buffer = new byte[1024];
            int len = 0;
            // 將 InputStream 利用 buffer_byte[1024] 轉存入 ByteArrayOutputStream， 簡寫如下
            while( (len=is.read(buffer)) != -1 ) // != -1 表示還有資料
            {
                baos.write(buffer, 0, len);
            }
            return  baos.toByteArray();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //如果try-catch 偵測到錯誤
        return null;
    }

    // 將本機欲上傳的圖檔Uri 轉成 byte[]
    public static byte[] uriToByte(Context context, Uri uri)
    {
        try {
            InputStream is = context.getContentResolver().openInputStream(uri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            // 將 InputStream 利用 buffer_byte[1024] 轉存入 ByteArrayOutputStream， 簡寫如下
            while( (len=is.read(buffer)) != -1 ) // != -1 表示還有資料
            {
                baos.write(buffer, 0, len);
            }
            return  baos.toByteArray();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //如果try-catch 偵測到錯誤
        return null;
    }

    // 轉成符合utf-8格式的Google API url
    public static String getGeocodeEncodeUrl(String address)
    {
        try
        {
            address = URLEncoder.encode(address, "utf-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        String googleApiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address;
        return googleApiUrl;
    }

    //將URLConnection取回的byte[]轉成String之後，可視為JSONObject處理，從眾多資訊中取得經緯度座標
    public static double[] getLatLngFromJsonString(String jsonString)
    {
        try
        {
            JSONObject obj = new JSONObject(jsonString);
            if(!obj.getString("status").equals("OK"))
            {
                return null;
            }
            JSONObject location = obj.getJSONArray("results")
                    .getJSONObject(0)
                    .getJSONObject("geometry")
                    .getJSONObject("location");
            double lat = location.getDouble("lat");
            double lng = location.getDouble("lng");
            Log.d("Debug", "JSON_String:" + jsonString);
            Log.d("Debug", "Lat:" + lat);
            Log.d("Debug", "Lng:" + lng);
            return new double[]{lat, lng}; //經緯度座標
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    //將String地址轉為double[]經緯度座標
    public static double[] addressToLatLng(String address)
    {
        String url = Utils.getGeocodeEncodeUrl(address); // 符合utf-8 Encode的URL
        byte[] bytes = Utils.urlToByte(url); //透過URLConnection取得JSON格式的Byte Array
        String jsonResult = new String(bytes); //將byte[] 轉成String
        double[] latLngLocation = Utils.getLatLngFromJsonString(jsonResult); //從JSONString中擷取經緯度座標
        return latLngLocation;
    }

    public static String getGoogleMapUrl(double[] latLng, int zoom)
    {
        String center = latLng[0] + "," + latLng[1];
        String mapUrl = "https://maps.googleapis.com/maps/api/staticmap?center=" + center + "&zoom=" + zoom + "&size=640x480";
        return mapUrl;
    }
}
