package com.example.test.classui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;
import java.util.Objects;

public class ParseInfoActivity extends AppCompatActivity {

    // for show of data form Parse Server
    Spinner pStoreSpinner;  // 與.xml 的物件id 同名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_info);

        pStoreSpinner = (Spinner) findViewById(R.id.pStoreSpinner);
        showParseStore();
    }

    private void showParseStore()
    {
        // get ParseObject data , then get what we want and show it in Spinner
        ParseQuery<ParseObject> storeQuery = new ParseQuery<ParseObject>("StoreInfo");

        //送出ParseQuery請求，以.findInBackground( new FindCallback<ParseObject> () {…} );方式處理Parse Server傳回來的資料或訊息
        storeQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e != null) // 表示執行有錯
                {
                    Toast.makeText(ParseInfoActivity.this, "Query Fail: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                else // 表示執行成功: 當ParseQuery執行成功時，會將回傳資料放在 List<ParseObject> 物件中
                {
                    Toast.makeText(ParseInfoActivity.this, "Query Success: ", Toast.LENGTH_LONG).show();

                    // 使用ArrayAdapter方式將資料傳入Spinner顯示
                    String[] storeInfo = new String[objects.size()]; // 宣告Sring陣列 用來儲存 List<ParseObject> 中的字串資訊 // 陣列大小可設定與 來源資料數量相同
                    //逐筆 取出Parser Server傳來的 List<ParseObject>中的 <ParseObject>之JSON Key-Value內容
                    for(int i=0; i< objects.size(); i++)
                    {
                        ParseObject parseObj = objects.get(i);
                        storeInfo[i] = parseObj.getString("name") + "_地址: " + parseObj.getString("address"); // 以單一字串 顯示 店名_地址:

                        //以ArrayAdapter方式 傳入資料於 R.layout.simple_list_item1 版面 Layout顯示
                        ArrayAdapter<String> storeAdapter = new ArrayAdapter<String>(ParseInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, storeInfo);
                        pStoreSpinner.setAdapter(storeAdapter);
                    }
                }
            }
        });
    }
}
