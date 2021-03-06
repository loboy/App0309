package com.example.test.classui;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseInfoActivity extends AppCompatActivity {

    // for show of data form Parse Server
    Spinner pStoreSpinner;  // 與.xml 的物件id 同名
    ListView orderRecordListView;

    //Show ProgressBar
    ProgressBar downloadProgressBar;

    // Global data
    List<ParseObject> orderQueryResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_info);

        pStoreSpinner = (Spinner) findViewById(R.id.pStoreSpinner);
        orderRecordListView = (ListView) findViewById(R.id.orderRecordListView);
        downloadProgressBar = (ProgressBar) findViewById(R.id.downloadProgressBar);
        //downloadProgressBar.setVisibility(View.GONE); //初始化隱藏；也可於XML定義初始顯示狀態為隱藏 ==> 改成 XML中此ProgressBar為顯示，所以這邊就不要設成隱藏，因為待會執行showParseStore()也會再設為顯示!!!

        showParseStore(); //顯示ParseServer訂單總表於listView

        //For 取得使用者點擊listView清單的哪個項目的位置index
        orderRecordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goDetailOrder(position);
            }
        });
    }

    private void showParseStore()
    {
        // 隱藏 ListView ; 顯示ProgressBar
        orderRecordListView.setVisibility(View.GONE);
        downloadProgressBar.setVisibility(View.VISIBLE);

        // for Observation of ProgressBar
        SystemClock.sleep(50); // 50ms

        //店家資訊
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

        //訂單資料
        // get ParseObject data , then get what we want and show it in Spinner
        ParseQuery<ParseObject> orderQuery = new ParseQuery<ParseObject>("Order");

        //送出ParseQuery請求，以.findInBackground( new FindCallback<ParseObject> () {…} );方式處理Parse Server傳回來的資料或訊息
        orderQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e != null) // 表示執行有錯
                {
                    Toast.makeText(ParseInfoActivity.this, "Query Fail of Order: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                } else // 表示執行成功: 當ParseQuery執行成功時，會將回傳資料放在 List<ParseObject> 物件中
                {
                    Toast.makeText(ParseInfoActivity.this, "Query Success of Order: ", Toast.LENGTH_LONG).show();

                    //將ParseServer傳回資料放入指定物件中
                    orderQueryResults = objects;

                    // For Debug Parse
                    String totalJSONObjectToString = ""; // raw data
                    String recordListParseObjectString = ""; //   Log.d("Debug", "XXXXXXOOOOOO:" + recordListParseObjectString);


                    //利用List & Map<string, string> & ArrayList 物件儲存欲顯示的資料
                    List<Map<String, String>> orderData = new ArrayList<Map<String, String>>();

                    //逐筆 取出Parser Server傳來的 List<ParseObject>中的 <ParseObject>之JSON Key-Value內容 ， 並逐筆存入List<Map<String, String>>物件中
                    for (int i = 0; i < orderQueryResults.size(); i++) {
                        ParseObject object = orderQueryResults.get(i);
                        String note = object.getString("note");
                        String storeInfo = object.getString("storeInfo");
                        String menu = object.getString("menu");
                        String oneOrderInfo = "";
                        String oneOrderSum_String = "0"; // 每筆飲料訂單的總杯數 初始值 0
                        int oneOrderSum_Int = 0;
                        totalJSONObjectToString = totalJSONObjectToString + "\n" + menu; // totalJSONObjectToString

                        // XXX Start of Parse Menu Order
                        try {
                            JSONArray oneStoreOrderJSONArray = new JSONArray(menu);
                            for (int j = 0; j < oneStoreOrderJSONArray.length(); j++) {
                                JSONObject order = oneStoreOrderJSONArray.getJSONObject(j);

                                // "Key" defined from JSONObject from Parse Server's Data : "name" & "l" & "m"
                                String name = order.getString("name");
                                String ice_s = String.valueOf(order.getInt("ice_s"));
                                String ice_m = String.valueOf(order.getInt("ice_m"));
                                String ice_l = String.valueOf(order.getInt("ice_l"));
                                String hot_s = String.valueOf(order.getInt("hot_s"));
                                String hot_m = String.valueOf(order.getInt("hot_m"));
                                String hot_l = String.valueOf(order.getInt("hot_l"));
                                recordListParseObjectString = recordListParseObjectString + name + "\t\t" + "ice_s:" + ice_s + "\t\t" + "ice_m:" + ice_m + "\t\t" + "ice_l:" + ice_l + "\t\t" + "hot_s:" + hot_s + "\t\t" + "hot_m:" + hot_m + "\t\t" + "hot_l:" + hot_l + "\n"; //記錄所有訂單內容
                                oneOrderInfo = oneOrderInfo + name + "\t\t" + "ice_s:" + ice_s + "\t\t" + "ice_m:" + ice_m + "\t\t" + "ice_l:" + ice_l + "\t\t" + "hot_s:" + hot_s + "\t\t" + "hot_m:" + hot_m + "\t\t" + "hot_l:" + hot_l + "\n"; //顯示一張訂單內容
                                // 計數 (想要知道的冰熱小中大杯飲料數量)
                                if (!order.isNull("ice_s"))
                                    oneOrderSum_Int = oneOrderSum_Int + order.getInt("ice_s");
                                if (!order.isNull("ice_m"))
                                    oneOrderSum_Int = oneOrderSum_Int + order.getInt("ice_m");
                                if (!order.isNull("ice_l"))
                                    oneOrderSum_Int = oneOrderSum_Int + order.getInt("ice_l");
                                if (!order.isNull("hot_s"))
                                    oneOrderSum_Int = oneOrderSum_Int + order.getInt("hot_s");
                                if (!order.isNull("hot_m"))
                                    oneOrderSum_Int = oneOrderSum_Int + order.getInt("hot_m");
                                if (!order.isNull("hot_l"))
                                    oneOrderSum_Int = oneOrderSum_Int + order.getInt("hot_l");
                                // 判斷 飲料訂單的數量為空值的情況
                                /* if (order.isNull("lNumber"))
                                                                        oneOrderSum_Int = oneOrderSum_Int;
                                                                    else
                                                                        oneOrderSum_Int = oneOrderSum_Int + order.getInt("lNumber");*/
                            }
                            oneOrderSum_String = String.valueOf(oneOrderSum_Int);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        // XXX End of Parse Menu Order
                        Map<String, String> item = new HashMap<String, String>();
                        item.put("note", note);
                        item.put("storeInfo", storeInfo);
                        item.put("drinkNum", oneOrderSum_String); // 此訂單內的飲料數量
                        orderData.add(item);
                    }
                    Log.d("Debug", "XXXXXXOOOOOO2:" + totalJSONObjectToString); // For Check of Data
                    Log.d("Debug", "XXXXXXOOOOOO3:" + recordListParseObjectString); // For Check of Data
                    String[] from = {"note", "storeInfo", "drinkNum"};
                    int[] to = {R.id.note, R.id.storeInfo, R.id.drinkNum};

                    // 使用SimpleAdapter方式將資料以陣列index對應方式設定ListView的顯示內容與套用版面格式
                    SimpleAdapter adapter = new SimpleAdapter(ParseInfoActivity.this, orderData, R.layout.order_overview_item, from, to);

                    orderRecordListView.setAdapter(adapter);

                    // 隱藏ProgressBar ; 顯示ListView
                    downloadProgressBar.setVisibility(View.GONE);
                    orderRecordListView.setVisibility(View.VISIBLE);
                }
            }
        });

    } // End of  showParseStore( )

    public void goDetailOrder(int position)
    {
        ParseObject obj = orderQueryResults.get(position); // orderQueryResults = ParseServer傳回的資料
        Intent intent = new Intent();
        intent.setClass(this, OrderDetailActivity.class); // 預計啟動 OrderDetailActivity

        //夾帶 訂單資訊
        intent.putExtra("note", obj.getString("note"));
        intent.putExtra("storeInfo", obj.getString("storeInfo"));
        intent.putExtra("menu", obj.getString("menu"));

        //特地判斷是否有附圖，有圖則改以傳送其URL ==> 讓接收端可直接顯示
        if(obj.getParseFile("photo") != null)
        {
            intent.putExtra("photoURL", obj.getParseFile("photo").getUrl());
        }

        startActivity(intent);
    }
}
