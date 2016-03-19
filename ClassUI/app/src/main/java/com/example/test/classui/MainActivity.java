package com.example.test.classui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View; // For use of void submit(View view)
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
//import com.parse.Parse;
//import com.parse.ParseObject;
import static com.example.test.classui.Utils.*;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //當CheckBox物件checkBox有異動時，將最新的checkBox狀態值存入 偏好設定檔setting中所記錄的前次狀態值 key-value pair (key= "checkBox")
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //記錄於偏好設定檔setting中 (key= "checkBox")
                editor.putBoolean("checkBox", checkBox.isChecked()); // editor = sp.edit()
                editor.apply(); // editor = sp.edit()
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

    public void submit(View view)
    {
        //textView.setText("Hello~ Welcome!!!");
        //Toast.makeText(this, "Hello~Welcome!!!",Toast.LENGTH_LONG).show();
        String text = editText.getText().toString();   //宣告一個local variable text，值為EditText物件 editText的內容 (APP執行時，由使用者所輸入的內容)
        Toast.makeText(this, "Hello~Welcome:" + text ,Toast.LENGTH_LONG).show();

        if(checkBox.isChecked()) // 判斷 CheckBox物件 checkBox 是否為 勾選狀態 (True / False)
        {
            textView.setText("*********");  //將TextView物件textView內容改成 顯示*********
        }
        else
        {
            textView.setText(text);  //將TextView物件textView內容改成 區域變數text的內容!
        }
        editText.setText("");  //將EditText物件 editText的內容(使用者輸入的內容)清空
        // 將鍵盤輸入的editText text寫入.txt檔案中
        Utils.writeFile(this, "history.txt", text + '\n');
        // 更新 ListView顯示的內容
        showListView();
    }

    private void setHistory()
    {
        String[] data = Utils.readFile(this, "history.txt").split("\n");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        historyListView.setAdapter(adapter);
    }

    // 將history.txt 內容 顯示在 ListView之中
    private void showListView()
    {
        String[] data = Utils.readFile(this, "history.txt").split("\n");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        historyListView.setAdapter(adapter);
    }

    // 顯示下拉式選單的內容
    private void showSpinner()
    {
        //String[] data = {"一號店","二號店","三號店","四號店","五號店"};
        String[] data = getResources().getStringArray(R.array.storeNameList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, data);
        spinner.setAdapter(adapter);
    }

    public void goMenu(View view)
    {
        // 切換至另一個Activity (DrinkMenuActivity)，需要以Intent物件來控制轉換流程。
        Intent intent = new Intent();
        intent.setClass(this, DrinkMenuActivity.class);

        //讓 Activity (DrinkMenuActivity)  start
        startActivity(intent);
    }


}
