package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class products_orders extends AppCompatActivity {
TextView Products1,Orders1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_orders);
        Products1=findViewById(R.id.product);
        Orders1=findViewById(R.id.orders);
        Products1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home=new Intent(products_orders.this, Gategory.class);
                startActivity(home);
                finish();
            }
        });
    }
}