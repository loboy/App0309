package com.example.test.classui;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class OrderDetailActivity extends AppCompatActivity {

    TextView noteDetail;
    TextView storeDetail;
    TextView menuDetail;
    ImageView photoDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        noteDetail = (TextView) findViewById(R.id.noteDetail);
        storeDetail = (TextView) findViewById(R.id.storeDetail);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        photoDetail = (ImageView) findViewById(R.id.photoDetail);

        noteDetail.setText(getIntent().getStringExtra("note"));
        storeDetail.setText(getIntent().getStringExtra("storeInfo"));

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
        String photoURL = getIntent().getStringExtra("photoURL");
        if(photoURL != null)
        {
            // 利用  import com.squareup.picasso.Picasso;   使用套件幫忙輕鬆處理圖片顯示   // 須加上設定 compile 'com.squareup.picasso:picasso:2.5.2'
            Picasso.with(this).load(photoURL).into(photoDetail);
        }
    }
}
