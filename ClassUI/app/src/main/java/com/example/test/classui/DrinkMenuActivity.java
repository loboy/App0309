package com.example.test.classui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkMenuActivity extends AppCompatActivity {

    TextView textView2_1, textView2_2, textView2_3, textView2_4, textView2_5, textView2_6; // 紅茶 (冰)小中大 (熱)小中大
    TextView textView4_1, textView4_2, textView4_3, textView4_4, textView4_5, textView4_6; // 綠茶 (冰)小中大 (熱)小中大
    TextView textView6_1, textView6_2, textView6_3, textView6_4, textView6_5, textView6_6; // 奶茶 (冰)小中大 (熱)小中大


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_menu);

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

    public void ok(View view)
    {
        Intent data = new Intent();
        data.putExtra("orderData","Order Done");
        setResult(RESULT_OK, data); // int resultCode = RESULT_OK ; Intent data = data
        finish();
    }

    public void cancel(View view)
    {
        Toast.makeText(this, "CountBye", Toast.LENGTH_LONG).show();
        finish();
    }
}
