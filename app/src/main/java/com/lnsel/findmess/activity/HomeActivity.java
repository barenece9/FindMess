package com.lnsel.findmess.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.lnsel.findmess.R;

public class HomeActivity extends Activity {

    Button btn_find_mess_login,btn_mess_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        btn_mess_login=(Button)findViewById(R.id.btn_mess_login);
        btn_find_mess_login=(Button)findViewById(R.id.btn_find_mess_login);

        btn_find_mess_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,UserLogin.class));
                finish();
            }
        });
        btn_mess_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                finish();
            }
        });
    }
}
