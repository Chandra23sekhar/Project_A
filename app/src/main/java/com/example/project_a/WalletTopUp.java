package com.example.project_a;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class WalletTopUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_top_up);

        //status ba color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.darkpurple));
        }


        // Displaying the tick mark on payment options
        ImageView apple_pay_tick = findViewById(R.id.apple_correct_mark);
        ImageView googple_pay_tick = findViewById(R.id.gpay_correct_mark);
        ImageButton apple_pay_opt = findViewById(R.id.apple_pay_sel);
        ImageButton google_pay_opt = findViewById(R.id.google_pay_sel);
        LinearLayout select_amounts = findViewById(R.id.select_amount);

        // Adding onclick listener to the button
        apple_pay_opt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apple_pay_tick.setVisibility(View.VISIBLE);
            }
        });

        google_pay_opt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googple_pay_tick.setVisibility(View.VISIBLE);
            }
        });

        // Adding onclcik listener

        //
        // TODO: Add gpay, applepay toggle option
        // TODO: Adding color effects and toggle option to the amount selected
        // TODO: Displaying the amount selected from the layout
    }

    public void getAmount(View v) {

        int is_amount_selected = 0;

        String fin_amount = "Selected amount: ";
        switch (v.getId()) {
            case R.id.amount_100:
                fin_amount += "100";
                break;
            case R.id.amount_200:
                fin_amount += "200";
                break;
            case R.id.amount_250:
                fin_amount += "250";
                break;
            case R.id.amount_500:
                fin_amount += "500";
                break;
        }

        Toast.makeText(this, fin_amount, Toast.LENGTH_SHORT).show();
    }
}