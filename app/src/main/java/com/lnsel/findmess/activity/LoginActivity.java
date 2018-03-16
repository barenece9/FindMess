package com.lnsel.findmess.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lnsel.findmess.R;
import com.lnsel.findmess.appconroller.AppController;
import com.lnsel.findmess.utlity.CreateDialog;
import com.lnsel.findmess.utlity.Webservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {

    TextView new_mess_reg;

    Button btn_login;
    EditText etn_user,etn_password;
    String  user="",password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etn_password=(EditText)findViewById(R.id.etn_password);
        etn_user=(EditText)findViewById(R.id.etn_user);
        Button btn_back=(Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                finish();
            }
        });

        new_mess_reg=(TextView)findViewById(R.id.new_mess_reg);
        new_mess_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();

            }
        });
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(UserLogin.this,FindActivity.class));
                // finish();
                user=etn_user.getText().toString();
                password=etn_password.getText().toString();
                if(user.equalsIgnoreCase("")){
                    CreateDialog.showDialog(LoginActivity.this,"Enter username");
                }else if(password.equalsIgnoreCase("")){
                    CreateDialog.showDialog(LoginActivity.this,"Enter password");
                }else {
                    doLogin();
                }

            }
        });
    }

    public void doLogin(){

        final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.MESS_LOGIN_URL;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        JSONresponse(response);
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        System.out.println("Error=========="+error);
                        Toast.makeText(LoginActivity.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("username",user);
                params.put("contact_no",password);
                return params;
            }
        };
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(postRequest);

    }

    public void JSONresponse(String responsedata){

        try {
            JSONObject parentObj = new JSONObject(responsedata);
            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("true")){
                JSONObject response=parentObj.getJSONObject("response");
                String name=response.getString("name");
                String mess_id=response.getString("mess_id");
                startActivity(new Intent(LoginActivity.this,MessActivity.class));
                finish();
            }else {
                String error_msg=parentObj.getString("response");
                Toast.makeText(getApplicationContext(),error_msg,Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
