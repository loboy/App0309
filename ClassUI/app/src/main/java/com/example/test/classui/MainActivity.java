package com.example.test.classui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View; // For use of void submit(View view)
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // ? 根據XML LAYOUT的物件 ID來先行宣告，稍後會於程式碼中取得正確的值!
    TextView textView;
    EditText editText;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 畫面上建置的兩個文字方塊，要在此程式內能處理，需要進行幾個動作! {1. 先宣告一個相同類別的物件，準備來存取該物件內容 ;  2. ; 必須以 findViewById() 在java中取得Layout XML的物件聯繫關係，並再加強制轉型 }
        // 習慣上，JAVA這邊宣告的物件名稱會故意命名為跟XML的物件ID名稱相同，方便程式開發者了解其對應關係!!!
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
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
    }
}
