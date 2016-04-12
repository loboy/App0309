package com.example.test.classui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import com.parse.Parse;
//import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    //  根據XML LAYOUT的物件 ID來先行宣告，稍後會於程式碼中取得正確的值!
    TextView textView;
    EditText editText;
    CheckBox checkBox;
    //使用SharedPreferences儲存應用程式的設定值，方便下次啟動時可載入偏好設定
    SharedPreferences sp;
    // editor看起來只是個 方便別名...-而已...但是當Mode採用 Context.MODE_PRIVATE，則必須另外宣告 SharedPreferences.Editor物件以 editor來執行Edit編輯介面功能!!! 若是以sp.Editor來執行，則跑不出效果!!!
    SharedPreferences.Editor editor;

    ListView historyListView;
    Spinner spinner;

    // Show Camera Photo
    ImageView cameraImageView;

    //Show Progress Status as Submit ( )
    ProgressDialog submitProgressDialog;
    //Show ProgressBar
    ProgressBar submitProgressBar;


    // For Store Test of orderData from DrinkMenuActivity
    String orderData;

    private static final int REQUEST_CODE_MENU_ACTIVITY = 0; // 定義  固定的 常數參數 REQUEST_CODE_MENU_ACTIVITY (for go to DrinkMenuActivity)
    private static final int REQUEST_CODE_PARSE_ACTIVITY = 1; // 定義  固定的 常數參數 REQUEST_CODE_PARSE_ACTIVITY (for go to ParseInfoActivity)
    private static final int REQUEST_CODE_CAMERA = 2;           // 定義  固定的 常數參數 REQUEST_CODE_CAMERA (for goToCamera())

    // for check if there is Camara Photo File
    private boolean hasCameraPhoto = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item)
    {
        int itemId = item.getItemId();

        //判斷 使用者點下哪個選項
        if(itemId == R.id.action_take_photo)
        {
            Toast.makeText(this, "take photo", Toast.LENGTH_LONG).show();
            goToCamera();
        }
        return super.onOptionsItemSelected(item); // for what and why???
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Debug", "MainActivity : 1 onCreate"); // for Trace of Activity Life-Cycle

        // 畫面上建置的兩個文字方塊，要在此程式內能處理，需要進行幾個動作! {1. 先宣告一個相同類別的物件，準備來存取該物件內容 ;  2. ; 必須以 findViewById() 在java中取得Layout XML的物件聯繫關係，並再加強制轉型 }
        // 習慣上，JAVA這邊宣告的物件名稱會故意命名為跟XML的物件ID名稱相同，方便程式開發者了解其對應關係!!!
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);

        // 核取方塊 For顯示textView的方式!
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        // For relation with ListView in XML  // 這邊故意取名為 historyListView 不同於id.listView，寫程式時需要特別記住!!!
        historyListView = (ListView) findViewById(R.id.listView);

        // For relation with Spinner in XML
        spinner = (Spinner) findViewById(R.id.spinner);

        // For relation with ImageView in XML
        cameraImageView = (ImageView) findViewById(R.id.cameraImageView);

        // For initialization of ProgressDialog
        submitProgressDialog = new ProgressDialog(this);
        // For relation with ProgressBar in XML
        submitProgressBar = (ProgressBar) findViewById(R.id.submitProgressBar);
        submitProgressBar.setVisibility(View.GONE);

        // SharedPreferences偏好設定檔(自定名稱：setting；模式：應用程式專用)
        sp = getSharedPreferences("setting", Context.MODE_PRIVATE);
        editor = sp.edit();
        // 讓EditText物件editText顯示上次離開時畫面上editText的保存內容
        editText.setText(sp.getString("editText", ""));

        //設定for 虛擬鍵盤
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    submit(v);
                    return true;
                }
                else
                    return false;
            }
        });

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                //在虛擬鍵盤輸入內容時，也順便將該文字方塊內容儲存至SharedPreferences物件sp (for 設定檔setting)
                // 透過SharedPreferences.Editor 編輯介面putString(String key, String value)
                editor.putString("editText", editText.getText().toString()); // editor = sp.edit()
                editor.apply(); // editor = sp.edit()

                // Submit View
                if ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN)) {
                    submit(v);
                    return true;
                } else
                    return false;
            }
        });

        //設定CheckBox狀態顯示 前次執行時最後狀態值，若找無狀態紀錄值則顯示False，
        //從SharedPreferences物件 以sp.getBoolean("checkBox", false) 取得偏好設定檔setting中所記錄的前次狀態值 key-value pair (key= "checkBox")
        checkBox.setChecked(sp.getBoolean("checkBox", false));
        if(checkBox.isChecked()) // Initially Check
        {
            cameraImageView.setVisibility(View.INVISIBLE);
        }
        else
        {
            cameraImageView.setVisibility(View.VISIBLE);
        }

        //當CheckBox物件checkBox有異動時，將最新的checkBox狀態值存入 偏好設定檔setting中所記錄的前次狀態值 key-value pair (key= "checkBox")
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //記錄於偏好設定檔setting中 (key= "checkBox")
                editor.putBoolean("checkBox", checkBox.isChecked()); // editor = sp.edit()
                editor.apply(); // editor = sp.edit()
                if(isChecked) // Change Listener Check // try "isChecked" or "checkBox.isChecked()"
                {
                    cameraImageView.setVisibility(View.INVISIBLE);
                }
                else
                {
                    cameraImageView.setVisibility(View.VISIBLE);
                }
            }
        });

        //顯示 history.txt的內容於ListView
        showListView();

        //設定Spinner下拉式選單的內容
        showSpinner();


        /*
        // For Parse Server
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("hi", "XXX123456OOO");
        testObject.saveInBackground();
        */


    }

    // for Trace of Activity Life-Cycle
    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("Debug", "MainActivity : 2 onStart");
    }

    @Override
     protected void onResume()
    {
        super.onResume();
        Log.d("Debug", "MainActivity : 3 onResume");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d("Debug", "MainActivity : 4 onPause");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d("Debug", "MainActivity : 5 onStop");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.d("Debug", "MainActivity : 6 onRestart");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d("Debug", "MainActivity : 7 onDestroy");
    }
    // End for Trace of Activity Life-Cycle


    public void submit(View view)
    {
        // 彈出一視窗顯示 ProgressDialog訊息 // submitProgressDialog.show(this, "Data Trans.", "Data Processing: 資料處理中!");
        submitProgressDialog.setTitle("Data Trans.");
        submitProgressDialog.setMessage("Data Processing: 資料處理中!");
        submitProgressDialog.show();
        //顯示ProgressBar
        submitProgressBar.setVisibility(View.VISIBLE);

        // for Observation of ProgressBar
        SystemClock.sleep(50); // 50ms

        //textView.setText("Hello~ Welcome!!!");
        //Toast.makeText(this, "Hello~Welcome!!!",Toast.LENGTH_LONG).show();
        String text = editText.getText().toString();   //宣告一個local variable text，值為EditText物件 editText的內容 (APP執行時，由使用者所輸入的內容)
        Toast.makeText(this, "Hello~Welcome:" + text ,Toast.LENGTH_LONG).show();

        //處理將 訂單資訊上傳至 Parse Server上面! (相關系統設定請參考如何下載Parse Sever上的資料)
        ParseObject orderParseObject = new ParseObject("Order"); // 指定要上傳至Parser Server上的哪個ParseObject
        orderParseObject.put("note", text);
        orderParseObject.put("storeInfo", spinner.getSelectedItem());
        orderParseObject.put("menu", orderData);
        //判斷是否需要處理上傳圖片
        if(hasCameraPhoto)
        {
            Uri photoUri = Utils.getPhotoUri();
            ParseFile photoParseFile = new ParseFile("Class_Camera_Photo.png", Utils.uriToByte(this, photoUri)); // Class_Camera_Photo.png 檔名如果跟 Uri.fromFile(cameraImageFile)的檔名不同的話是否OK?
            orderParseObject.put("photo", photoParseFile);
        }
        else
        {
            Toast.makeText(this, "No need to upload Camera photo.",Toast.LENGTH_LONG).show();
        }
        //使用callback function 來得知上傳ParseObject是否順利!
        orderParseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                // 移除 顯示狀態中的ProgressDialog進度條 & ProgressBar
                submitProgressDialog.dismiss();
                submitProgressBar.setVisibility(View.GONE); // Object in XML


                if(e != null) //執行錯誤
                {
                    Toast.makeText(MainActivity.this, "Submit Failed.",Toast.LENGTH_LONG).show();
                }
                else        ////執行成功
                {
                    Toast.makeText(MainActivity.this, "Submit Success.",Toast.LENGTH_LONG).show();

                    //還原畫面初始狀態
                    cameraImageView.setImageResource(0);
                    editText.setText("");
                    //textView.setText("");
                    hasCameraPhoto = false;
                    //更新show history.txt
                    showListView();
                }
            }
        });


        textView.setText(text);  //將TextView物件textView內容改成 區域變數text的內容!
        editText.setText("");  //將EditText物件 editText的內容(使用者輸入的內容)清空
        // 將鍵盤輸入的editText text寫入.txt檔案中
        Utils.writeFile(this, "history.txt", text + '\n');
        // 更新 ListView顯示的內容
        showListView();
    }

    // 將history.txt 內容 顯示在 ListView之中
    private void showListView() {
        if(Utils.readFile(this, "history.txt") == null) //懷疑 因一開始的開發環境若無建立 history.txt ，而導致專案轉移環境後的執行失敗!
        {
            return;
        }
        else
        {
            String[] data = Utils.readFile(this, "history.txt").split("\n");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
            historyListView.setAdapter(adapter);
        }

    }

    // 顯示下拉式選單的內容
    private void showSpinner()
    {
        //String[] data = {"一號店","二號店","三號店","四號店","五號店"};
        String[] data = getResources().getStringArray(R.array.storeNameList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, data);
        spinner.setAdapter(adapter);
    }

    private void goToCamera()
    {
        Toast.makeText(this, "goToCamera1", Toast.LENGTH_LONG).show();
        //判別SDK版本:  SDK >=23的版本 一定需要有 Manifest.permission.WRITE_EXTERNAL_STORAGE 的授權
        if(Build.VERSION.SDK_INT >= 23)
        {
            //Toast.makeText(this, "goToCamera1-1", Toast.LENGTH_LONG).show();
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "goToCamera1-2", Toast.LENGTH_LONG).show();
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                return;
            }
        }

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE); // 設定啟動相機程式
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Utils.getPhotoUri()); // 於Utils class新增一個getPhotoUri()函式，指定相片存檔路徑檔名(Class_Camera_Photo.png)，讓相機相片Uri物件儲存

        Toast.makeText(this, "goToCamera2", Toast.LENGTH_LONG).show();
        //Start Activity
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
        Toast.makeText(this, "goToCamera3", Toast.LENGTH_LONG).show();
    }

    public void goMenu(View view)
    {
        // 切換至另一個Activity (DrinkMenuActivity)，需要以Intent物件來控制轉換流程。
        Intent intent = new Intent();
        intent.setClass(this, DrinkMenuActivity.class);

        //讓 Activity (DrinkMenuActivity)  start
            // 單向傳輸 //startActivity(intent);
            // 雙向傳輸
        startActivityForResult(intent, REQUEST_CODE_MENU_ACTIVITY); // 並且要以 Override方式 設計一個 onActivityResult
    }

    public void goParse(View view)
    {
        // 切換至另一個Activity (ParseInfoActivity)，需要以Intent物件來控制轉換流程。
        Intent intent = new Intent();
        intent.setClass(this, ParseInfoActivity.class);

        //讓 Activity (DrinkMenuActivity)  start
        // 單向傳輸 //startActivity(intent);
        // 雙向傳輸
        startActivityForResult(intent, REQUEST_CODE_PARSE_ACTIVITY); // 並且要以 Override方式 設計一個 onActivityResult
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_MENU_ACTIVITY)  // 判斷requestCode是否為 goMenu按鈕所觸發的 startActivityForResult
        {
            // 處理當DrinkMenuActivity finish()之後回傳的狀態碼 判斷：
            // 例如 當處理當DrinkMenuActivity執行無誤時，在setResult(int resultCode , Intent data)時將resultCode設定為RESULT_OK狀態碼
            if(resultCode == RESULT_OK)
            {
                //For Test: 將回傳資料Intent data，以.getStringExtra()方式取出其中Name= "orderData"的Value，顯示於TextView物件textView
                //textView.setText(data.getStringExtra("orderData"));
                orderData = data.getStringExtra("orderData");
                try
                {
                    JSONArray array = new JSONArray(orderData); // copy orderData (String) ==> (JSONArray)
                    String orderDataTxt = "";
                    for(int i=0; i < array.length(); i++)
                    {
                        JSONObject obj = array.getJSONObject(i);

                        String name = obj.getString("name");
                        String ice_s = String.valueOf(obj.getInt("ice_s")); // original data type from INT to String
                        String ice_m = String.valueOf(obj.getInt("ice_m")); // original data type from INT to String
                        String ice_l = String.valueOf(obj.getInt("ice_l")); // original data type from INT to String
                        String hot_s = String.valueOf(obj.getInt("hot_s")); // original data type from INT to String
                        String hot_m = String.valueOf(obj.getInt("hot_m")); // original data type from INT to String
                        String hot_l = String.valueOf(obj.getInt("hot_l")); // original data type from INT to String

                        orderDataTxt = orderDataTxt + "name:" + name + "\t"
                                + "ice_s:" + ice_s + "\t\t"
                                + "ice_m:" + ice_m +  "\t\t"
                                + "ice_l:" + ice_l +  "\t\t"
                                + "hot_s:" + hot_s +  "\t\t"
                                + "hot_m:" + hot_m +  "\t\t"
                                + "hot_l:" + hot_l +  "\n";
                    }

                    textView.setText(orderDataTxt);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        if(requestCode == REQUEST_CODE_PARSE_ACTIVITY)  // 判斷requestCode是否為 goMenu按鈕所觸發的 startActivityForResult
        {
            // 處理當DrinkMenuActivity finish()之後回傳的狀態碼 判斷：
            // 例如 當處理當DrinkMenuActivity執行無誤時，在setResult(int resultCode , Intent data)時將resultCode設定為RESULT_OK狀態碼
            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, "ParseInfoActivity Run Finish. ", Toast.LENGTH_LONG).show();
            }
        }

        if(requestCode == REQUEST_CODE_CAMERA)  // 判斷requestCode是否為 右上角功能選單選項Take Photo所觸發的goToCamera ()回傳的
        {
            // 例如 當處理當DrinkMenuActivity執行無誤時，在setResult(int resultCode , Intent data)時將resultCode設定為RESULT_OK狀態碼
            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, "Take Photo Finish. ", Toast.LENGTH_LONG).show();
                cameraImageView.setImageURI(Utils.getPhotoUri()); // 已經將照片存在 Utils.getPhotoUri() 指定位置路徑的檔案 (???是存檔 還是顯示圖片???)
                hasCameraPhoto = true;
            }
        }
    }
}
