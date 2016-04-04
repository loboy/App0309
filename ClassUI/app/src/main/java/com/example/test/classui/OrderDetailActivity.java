package com.example.test.classui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class OrderDetailActivity extends AppCompatActivity {

    // for photo URL from Parse Server
    private String photoURL;

    //for address extracted from Parse Server
    private String address;

    TextView noteDetail;
    TextView storeDetail;
    TextView menuDetail;
    ImageView photoDetail;
    ImageView mapImageView;
    WebView mapWebView;
    Switch imageWebViewSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        noteDetail = (TextView) findViewById(R.id.noteDetail);
        storeDetail = (TextView) findViewById(R.id.storeDetail);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        photoDetail = (ImageView) findViewById(R.id.photoDetail);
        mapImageView = (ImageView) findViewById(R.id.mapImageView);
        mapWebView = (WebView) findViewById(R.id.mapWebView);
        imageWebViewSwitch = (Switch) findViewById(R.id.imageWebViewSwitch);
        imageWebViewSwitch.setChecked(false);


        // ImageView and WebView Default Setting of Visible
        mapImageView.setVisibility(View.VISIBLE);
        mapWebView.setVisibility(View.GONE);

        noteDetail.setText(getIntent().getStringExtra("note"));

        String storeInformation = getIntent().getStringExtra("storeInfo");
        storeDetail.setText(storeInformation); //storeDetail.setText(getIntent().getStringExtra("storeInfo"));
        address = storeInformation.split(",")[1]; //取出 店家的地址

        //處理menu
        String menu = getIntent().getStringExtra("menu");
        try
        {
            JSONArray oneStoreOrderJSONArray = new JSONArray(menu);
            String orderText = ""; // 用來儲存 有斷行排版的訂單內容
            for (int i = 0; i < oneStoreOrderJSONArray.length(); i++) {
                JSONObject order = oneStoreOrderJSONArray.getJSONObject(i);

                // "Key" defined from JSONObject from Parse Server's Data : "name" & "l" & "m"
                String name = order.getString("name");
                String lNumber = String.valueOf(order.getInt("lNumber")); // l
                String mNumber = String.valueOf(order.getInt("mNumber")); // m
                orderText = orderText + "name:" + name + "\t\t" + "l:" + lNumber + "\t\t" + "m:" + mNumber + "\n"; //記錄所有訂單內容
            }
            menuDetail.setText(orderText);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //處理圖像
        photoURL = getIntent().getStringExtra("photoURL");
        if(photoURL != null)
        {
            /*
                            // 利用  import com.squareup.picasso.Picasso;   使用套件幫忙輕鬆處理圖片顯示   // 須加上設定 compile 'com.squareup.picasso:picasso:2.5.2'
                            Picasso.with(this).load(photoURL).into(photoDetail);
                         */

            // Self Implement for Photo Show // photoURL為ParseServer上的圖片資源；photoDetail為欲顯示圖片的ImageView
            // (new ImageLoad(photoDetail)).execute(photoURL);
            (new ImageLoad(photoDetail)).execute(photoURL); // 需要使用new 宣告物件，才能在同一行以.execute寫法執行
        }

        //處理地圖顯示
        if(address != null) //如何判斷addresss是個正確合理、GoogleMAP能定位的地址?
        {
            (new GeoMap()).execute(address);
        }

        //處理Switch切換顯示ImageView 或 WebView
        imageWebViewSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) // 只顯示 WebView
                {
                    mapImageView.setVisibility(View.GONE); // ImageView設為Gone: 不顯示且不佔預留版面空間
                    mapWebView.setVisibility(View.VISIBLE);
                    Toast.makeText(OrderDetailActivity.this, "Switch is-Checked State!!! ", Toast.LENGTH_LONG).show();
                }
                else // 只顯示 ImageView
                {
                    mapWebView.setVisibility(View.GONE);
                    mapImageView.setVisibility(View.VISIBLE);
                    Toast.makeText(OrderDetailActivity.this, "Switch un-Checked State!!! ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // For Image Load in OrderDetailActivity
    class ImageLoad extends AsyncTask<String, Void, byte[]>
    {
        ImageView imageView;

        public ImageLoad(ImageView imageView)
        {
            this.imageView = imageView; // 將傳入參數的imageView 存入此class下的ImageView物件
        }

        @Override
        protected byte[] doInBackground(String... params)
        {
            String url = params[0]; //傳入參數陣列中的第一個參數
            return Utils.urlToByte(url); // 透過自訂Utils class的urlToByte()將URL轉成byte[]
        }

        @Override
        protected void onPostExecute(byte[] bytes)
        {
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imageView.setImageBitmap(bmp);
            // super.onPostExecute(bytes);
        }
    }

    //以地址Address向Google MAP API取得地圖資訊並顯示
    class GeoMap extends AsyncTask<String, Void, byte[]>
    {
        private double[] latLng;
        private String mapUrl;

        @Override
        protected byte[] doInBackground(String... params)
        {
            latLng = Utils.addressToLatLng(params[0]);
            mapUrl = Utils.getGoogleMapUrl(latLng, 17);
            return Utils.urlToByte(mapUrl);
        }

        @Override
        protected void onPostExecute(byte[] bytes)
        {
            super.onPostExecute(bytes); // 有沒有都沒關係!

            //Shown in ImageView
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            mapImageView.setImageBitmap(bmp);

            //Shown in WebView
            mapWebView.loadUrl(mapUrl);
        }
    }
}
