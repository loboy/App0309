package com.example.test.classui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DrinkMenuActivity extends AppCompatActivity {

    TextView textView2_1, textView2_2, textView2_3, textView2_4, textView2_5, textView2_6; // 紅茶 (冰)小中大 (熱)小中大
    TextView textView4_1, textView4_2, textView4_3, textView4_4, textView4_5, textView4_6; // 綠茶 (冰)小中大 (熱)小中大
    TextView textView6_1, textView6_2, textView6_3, textView6_4, textView6_5, textView6_6; // 奶茶 (冰)小中大 (熱)小中大

    LinearLayout orderCountLinearLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_menu);
        Log.d("Debug", "DrinkMenuActivity : 1 onCreate"); // for Trace of Activity Life-Cycle

        // Change into DrinkMenuActivity: Hello Message.
        Toast.makeText(this, "Hello~Welcome DrinkMenuActivity!!!", Toast.LENGTH_LONG).show();

        // For order menu
        textView2_1 = (TextView) findViewById(R.id.textView2_1); // for case "RedTeaIceS"        // 紅茶
        textView2_2 = (TextView) findViewById(R.id.textView2_2); // for case "RedTeaIceM"
        textView2_3 = (TextView) findViewById(R.id.textView2_3); // for case "RedTeaIceL"
        textView2_4 = (TextView) findViewById(R.id.textView2_4); // for case "RedTeaHotS"
        textView2_5 = (TextView) findViewById(R.id.textView2_5); // for case "RedTeaHotM"
        textView2_6 = (TextView) findViewById(R.id.textView2_6); // for case "RedTeaHotL"
        textView4_1 = (TextView) findViewById(R.id.textView4_1); // for case "GreenTeaIceS"        // 綠茶
        textView4_2 = (TextView) findViewById(R.id.textView4_2); // for case "GreenTeaIceM"
        textView4_3 = (TextView) findViewById(R.id.textView4_3); // for case "GreenTeaIceL"
        textView4_4 = (TextView) findViewById(R.id.textView4_4); // for case "GreenTeaHotS"
        textView4_5 = (TextView) findViewById(R.id.textView4_5); // for case "GreenTeaHotM"
        textView4_6 = (TextView) findViewById(R.id.textView4_6); // for case "GreenTeaHotL"
        textView6_1 = (TextView) findViewById(R.id.textView6_1); // for case "MilkTeaIceS"        // 奶茶
        textView6_2 = (TextView) findViewById(R.id.textView6_2); // for case "MilkTeaIceM"
        textView6_3 = (TextView) findViewById(R.id.textView6_3); // for case "MilkTeaIceL"
        textView6_4 = (TextView) findViewById(R.id.textView6_4); // for case "MilkTeaHotS"
        textView6_5 = (TextView) findViewById(R.id.textView6_5); // for case "MilkTeaHotM"
        textView6_6 = (TextView) findViewById(R.id.textView6_6); // for case "MilkTeaHotL"
    }

    // for Trace of Activity Life-Cycle
    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("Debug", "DrinkMenuActivity : 2 onStart");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d("Debug", "DrinkMenuActivity : 3 onResume");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d("Debug", "DrinkMenuActivity : 4 onPause");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d("Debug", "DrinkMenuActivity : 5 onStop");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.d("Debug", "DrinkMenuActivity : 6 onRestart");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d("Debug", "DrinkMenuActivity : 7 onDestroy");
    }
    // End for Trace of Activity Life-Cycle

    public void increase (View view)
    {
        String orderItem = (String) view.getTag(); //  {RedTea / BlackTea / MilkTea}、{Ice / Hot}、{S / M / L}
        int number = 0; // 宣告local variable 預設0 ，在case中再作替換值運算，應該是OK，若有別的考量再調整寫法!!!
        switch (orderItem)
        {
            case "RedTeaIceS":
                number = Integer.parseInt(textView2_1.getText().toString());
                number++;
                textView2_1.setText(String.valueOf(number));
                break;
            case "RedTeaIceM":
                number = Integer.parseInt(textView2_2.getText().toString());
                number++;
                textView2_2.setText(String.valueOf(number));
                break;
            case "RedTeaIceL":
                number = Integer.parseInt(textView2_3.getText().toString());
                number++;
                textView2_3.setText(String.valueOf(number));
                break;
            case "RedTeaHotS":
                number = Integer.parseInt(textView2_4.getText().toString());
                number++;
                textView2_4.setText(String.valueOf(number));
                break;
            case "RedTeaHotM":
                number = Integer.parseInt(textView2_5.getText().toString());
                number++;
                textView2_5.setText(String.valueOf(number));
                break;
            case "RedTeaHotL":
                number = Integer.parseInt(textView2_6.getText().toString());
                number++;
                textView2_6.setText(String.valueOf(number));
                break;
            case "GreenTeaIceS":
                number = Integer.parseInt(textView4_1.getText().toString());
                number++;
                textView4_1.setText(String.valueOf(number));
                break;
            case "GreenTeaIceM":
                number = Integer.parseInt(textView4_2.getText().toString());
                number++;
                textView4_2.setText(String.valueOf(number));
                break;
            case "GreenTeaIceL":
                number = Integer.parseInt(textView4_3.getText().toString());
                number++;
                textView4_3.setText(String.valueOf(number));
                break;
            case "GreenTeaHotS":
                number = Integer.parseInt(textView4_4.getText().toString());
                number++;
                textView4_4.setText(String.valueOf(number));
                break;
            case "GreenTeaHotM":
                number = Integer.parseInt(textView4_5.getText().toString());
                number++;
                textView4_5.setText(String.valueOf(number));
                break;
            case "GreenTeaHotL":
                number = Integer.parseInt(textView4_6.getText().toString());
                number++;
                textView4_6.setText(String.valueOf(number));
                break;
            case "MilkTeaIceS":
                number = Integer.parseInt(textView6_1.getText().toString());
                number++;
                textView6_1.setText(String.valueOf(number));
                break;
            case "MilkTeaIceM":
                number = Integer.parseInt(textView6_2.getText().toString());
                number++;
                textView6_2.setText(String.valueOf(number));
                break;
            case "MilkTeaIceL":
                number = Integer.parseInt(textView6_3.getText().toString());
                number++;
                textView6_3.setText(String.valueOf(number));
                break;
            case "MilkTeaHotS":
                number = Integer.parseInt(textView6_4.getText().toString());
                number++;
                textView6_4.setText(String.valueOf(number));
                break;
            case "MilkTeaHotM":
                number = Integer.parseInt(textView6_5.getText().toString());
                number++;
                textView6_5.setText(String.valueOf(number));
                break;
            case "MilkTeaHotL":
                number = Integer.parseInt(textView6_6.getText().toString());
                number++;
                textView6_6.setText(String.valueOf(number));
                break;
            default:
                break;

        }

    }

    public void decrease (View view)
    {
        String orderItem = (String) view.getTag(); //  {RedTea / BlackTea / MilkTea}、{Ice / Hot}、{S / M / L}
        int number = 0;
        switch (orderItem)
        {
            case "RedTeaIceSXXX":
                number = Integer.parseInt(textView2_1.getText().toString());
                if(number >= 1)
                {
                    number--;
                }
                textView2_1.setText(String.valueOf(number));
                break;
            case "RedTeaIceS":
                number = Integer.parseInt(textView2_1.getText().toString());
                if(number >= 1)
                {
                    number--;
                }
                textView2_1.setText(String.valueOf(number));
                break;
            case "RedTeaIceM":
                number = Integer.parseInt(textView2_2.getText().toString());
                if(number >= 1)
                {
                    number--;
                }
                textView2_2.setText(String.valueOf(number));
                break;
            case "RedTeaIceL":
                number = Integer.parseInt(textView2_3.getText().toString());
                if(number >= 1)
                {
                    number--;
                }
                textView2_3.setText(String.valueOf(number));
                break;
            case "RedTeaHotS":
                number = Integer.parseInt(textView2_4.getText().toString());
                if(number >= 1)
                {
                    number--;
                }
                textView2_4.setText(String.valueOf(number));
                break;
            case "RedTeaHotM":
                number = Integer.parseInt(textView2_5.getText().toString());
                if(number >= 1)
                {
                    number--;
                }
                textView2_5.setText(String.valueOf(number));
                break;
            case "RedTeaHotL":
                number = Integer.parseInt(textView2_6.getText().toString());
                if(number >= 1)
                {
                    number--;
                }
                textView2_6.setText(String.valueOf(number));
                break;
            case "GreenTeaIceS":
                number = Integer.parseInt(textView4_1.getText().toString());
                if(number >= 1)
                {
                    number--;
                }
                textView4_1.setText(String.valueOf(number));
                break;
            case "GreenTeaIceM":
                number = Integer.parseInt(textView4_2.getText().toString());
                if(number >= 1)
                {
                    number--;
                }
                textView4_2.setText(String.valueOf(number));
                break;
            case "GreenTeaIceL":
                number = Integer.parseInt(textView4_3.getText().toString());
                if(number >= 1)
                {
                    number--;
                }
                textView4_3.setText(String.valueOf(number));
                break;
            case "GreenTeaHotS":
                number = Integer.parseInt(textView4_4.getText().toString());
                if(number >= 1)
                {
                    number--;
                }
                textView4_4.setText(String.valueOf(number));
                break;
            case "GreenTeaHotM":
                number = Integer.parseInt(textView4_5.getText().toString());
                if(number >= 1)
                {
                    number--;
                }
                textView4_5.setText(String.valueOf(number));
                break;
            case "GreenTeaHotL":
                number = Integer.parseInt(textView4_6.getText().toString());
                if(number >= 1)
                {
                    number--;
                }
                textView4_6.setText(String.valueOf(number));
                break;
            case "MilkTeaIceS":
                number = Integer.parseInt(textView6_1.getText().toString());
                if(number >= 1)
                {
                    number--;
                }
                textView6_1.setText(String.valueOf(number));
                break;
            case "MilkTeaIceM":
                number = Integer.parseInt(textView6_2.getText().toString());
                if(number >= 1)
                {
                    number--;
                }
                textView6_2.setText(String.valueOf(number));
                break;
            case "MilkTeaIceL":
                number = Integer.parseInt(textView6_3.getText().toString());
                if(number >= 1)
                {
                    number--;
                }
                textView6_3.setText(String.valueOf(number));
                break;
            case "MilkTeaHotS":
                number = Integer.parseInt(textView6_4.getText().toString());
                if(number >= 1)
                {
                    number--;
                }
                textView6_4.setText(String.valueOf(number));
                break;
            case "MilkTeaHotM":
                number = Integer.parseInt(textView6_5.getText().toString());
                if(number >= 1)
                {
                    number--;
                }
                textView6_5.setText(String.valueOf(number));
                break;
            case "MilkTeaHotL":
                number = Integer.parseInt(textView6_6.getText().toString());
                if(number >= 1)
                {
                    number--;
                }
                textView6_6.setText(String.valueOf(number));
                break;
            default:
                break;

        }

    }

    public JSONArray getOrderData()
    {
        JSONArray array = new JSONArray();
        // ...
        orderCountLinearLayout = (LinearLayout) findViewById(R.id.orderCountLinearLayout);

        //先取得 orderCountLinearLayout 底下有多少個LinearLayout物件
        int childCount = orderCountLinearLayout.getChildCount(); // childCount =7
        // 顯示品項訂單數量的LinearLayout剛好是位於index=0、2、4。
        for (int i=0; i< childCount - 2; i=i+2) // i= 0, 2, 4會執行； i=6 因不符合 < (7-2) 所以就不會執行!
        {
            //宣告一個 LinearLyout 物件用來儲存 所對應的Child LinearLayout：
            LinearLayout horizontalLayout = (LinearLayout) orderCountLinearLayout.getChildAt(i);

            //宣告1 個 Name & 6個數字的原型物件object (屬於Horizontal LinearLayout 的.getChildAt(i) index=0~6)，用來儲存於該物件，在從各物件讀取資料值
            TextView nameTextView = (TextView) horizontalLayout.getChildAt(0);
            TextView ice_s_TextView = (TextView) horizontalLayout.getChildAt(1);
            TextView ice_m_nameTextView = (TextView) horizontalLayout.getChildAt(2);
            TextView ice_l_nameTextView = (TextView) horizontalLayout.getChildAt(3);
            TextView hot_s_nameTextView = (TextView) horizontalLayout.getChildAt(4);
            TextView hot_m_nameTextView = (TextView) horizontalLayout.getChildAt(5);
            TextView hot_l_nameTextView = (TextView) horizontalLayout.getChildAt(6);

            //宣告1 個 Name & 6個數字的字串變數，用來儲存於JSONObject之中
            String name = nameTextView.getText().toString();
            int ice_s = Integer.parseInt(ice_s_TextView.getText().toString());
            int ice_m = Integer.parseInt(ice_m_nameTextView.getText().toString());
            int ice_l = Integer.parseInt(ice_l_nameTextView.getText().toString());
            int hot_s = Integer.parseInt(hot_s_nameTextView.getText().toString());
            int hot_m = Integer.parseInt(hot_m_nameTextView.getText().toString());
            int hot_l = Integer.parseInt(hot_l_nameTextView.getText().toString());

            // try-catch 例外處理
            try
            {
                JSONObject obj = new JSONObject();
                obj.put("name",name);
                obj.put("ice_s",ice_s);
                obj.put("ice_m",ice_m);
                obj.put("ice_l",ice_l);
                obj.put("hot_s",hot_s);
                obj.put("hot_m",hot_m);
                obj.put("hot_l",hot_l);

                array.put(obj);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            catch (Exception e) // no need ?
            {
                e.printStackTrace();
            }
        } //end of for-loop
        
        return  array;
    }

    public void ok(View view)
    {
        Intent data = new Intent();

        JSONArray array = getOrderData();

        //data.putExtra("orderData","Order Done");
        data.putExtra("orderData", array.toString());

        setResult(RESULT_OK, data); // int resultCode = RESULT_OK ; Intent data = data
        finish();
    }

    public void cancel(View view)
    {
        Toast.makeText(this, "CountBye", Toast.LENGTH_LONG).show();
        finish();
    }
}
