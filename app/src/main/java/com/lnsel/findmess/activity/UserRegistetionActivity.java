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

public class UserRegistetionActivity extends Activity {

    EditText etn_name,etn_user_name,etn_mobile,etn_address,etn_occupation,etn_requirment;
    Button btn_submit;
    String user_name="",name="",mobile="",address="",occupation="",requirment="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        etn_name=(EditText)findViewById(R.id.etn_name);
        etn_user_name=(EditText)findViewById(R.id.etn_user_name);
        etn_mobile=(EditText)findViewById(R.id.etn_mobile);
        etn_address=(EditText)findViewById(R.id.etn_address);
        etn_occupation=(EditText)findViewById(R.id.etn_occupation);
        etn_requirment=(EditText)findViewById(R.id.etn_requirment);
        btn_submit=(Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_name=etn_user_name.getText().toString();
                name=etn_name.getText().toString();
                mobile=etn_mobile.getText().toString();
                address=etn_address.getText().toString();
                occupation=etn_occupation.getText().toString();
                requirment=etn_requirment.getText().toString();
                if(name.equalsIgnoreCase("")){
                    CreateDialog.showDialog(UserRegistetionActivity.this,"Name can't be blank");
                }else if(user_name.equalsIgnoreCase("")){
                    CreateDialog.showDialog(UserRegistetionActivity.this,"Username can't be blank");
                }else if(mobile.equalsIgnoreCase("")){
                    CreateDialog.showDialog(UserRegistetionActivity.this,"Mobile no can't be blank");
                }else if(address.equalsIgnoreCase("")){
                    CreateDialog.showDialog(UserRegistetionActivity.this,"Address can't be blank");
                }else if(occupation.equalsIgnoreCase("")){
                    CreateDialog.showDialog(UserRegistetionActivity.this,"Occupation password can't be blank");
                }else if(requirment.equalsIgnoreCase("")){
                    CreateDialog.showDialog(UserRegistetionActivity.this,"Requirment password can't be blank");
                }else {
                    doResistretion();
                }
            }
        });
        Button btn_back=(Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserRegistetionActivity.this,UserLogin.class));
                finish();
            }
        });
    }

    public void doResistretion(){
        final ProgressDialog progressDialog=new ProgressDialog(UserRegistetionActivity.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.USER_REGISTER_URL;

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
                        Toast.makeText(UserRegistetionActivity.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name",name);
                params.put("username",user_name);
                params.put("occupation",occupation);
                params.put("address",address);
                params.put("mobile",mobile);
                params.put("requirment",requirment);
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
                String user_id=response.getString("user_id");
                startActivity(new Intent(UserRegistetionActivity.this,FindActivity.class));
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
