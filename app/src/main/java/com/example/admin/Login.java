package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    Button login;
    EditText username,pass;
    private ProgressDialog mloading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.Euser);
        pass=findViewById(R.id.Epass);
        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Euser=username.getText().toString();
                String Epass=pass.getText().toString();
                if(Euser.equals("admin")&&Epass.equals("admin"))
                {
//                    mloading.setTitle("Entering");
//                    mloading.setMessage("Please wait,check your Email");
//                    mloading.setCanceledOnTouchOutside(false);
//                    mloading.show();
                    Intent home=new Intent(Login.this, products_orders.class);
                    startActivity(home);
                    finish();

                }
                if(Euser.isEmpty())
                {
                    username.setError("Email is empty");
                    username.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(Euser).matches())
                {
                    username.setError("Enter the valid email");
                    username.requestFocus();
                    return;
                }
                if(Epass.isEmpty())
                {
                    pass.setError("Password is empty");
                    pass.requestFocus();
                    return;
                }
                if(pass.length()<6)
                {
                    pass.setError("Length of password is more than 6");
                    pass.requestFocus();
                    return;
                }



            }
        });
    }

}