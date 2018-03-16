package com.lnsel.findmess.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lnsel.findmess.R;
import com.lnsel.findmess.SetterGetter.Messdata;
import com.lnsel.findmess.SetterGetter.UserInfo;
import com.lnsel.findmess.adapter.CustomAdapter;
import com.lnsel.findmess.appconroller.AppController;
import com.lnsel.findmess.utlity.Sharepreferences;
import com.lnsel.findmess.utlity.Webservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessDetails extends Activity {

    TextView full_name,contact_no,location,landmark,Address,vacancy,room,member,seatrent,aboutus;
    CheckBox checkbox_wifi,checkbox_foods;
    Button btn_call,btn_whats_app,btn_poke,btn_map,btn_visited;
    String mess_id="";
    String Mobile_No="";
    String address="";
    String name="";
    private static final int REQUEST_CALL = 113;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_details);
        Button btn_back=(Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MessDetails.this,FindActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Intent intent = getIntent();
        mess_id=intent.getStringExtra("mess_id");
        full_name=(TextView)findViewById(R.id.full_name);
        contact_no=(TextView)findViewById(R.id.contact_no);
        location=(TextView)findViewById(R.id.location);
        landmark=(TextView)findViewById(R.id.landmark);
        Address=(TextView)findViewById(R.id.Address);
        vacancy=(TextView)findViewById(R.id.vacancy);
        room=(TextView)findViewById(R.id.room);
        member=(TextView)findViewById(R.id.member);
        seatrent=(TextView)findViewById(R.id.seatrent);
        aboutus=(TextView)findViewById(R.id.aboutus);

        checkbox_foods=(CheckBox)findViewById(R.id.checkbox_foods);
        checkbox_wifi=(CheckBox)findViewById(R.id.checkbox_wifi);

        btn_call=(Button)findViewById(R.id.btn_call);
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call(Mobile_No);
            }
        });
        btn_whats_app=(Button)findViewById(R.id.btn_whats_app);
        btn_whats_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendWhatsAppMsg(Mobile_No);
            }
        });
        btn_poke=(Button)findViewById(R.id.btn_poke);
        btn_poke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doPoke();
            }
        });
        btn_map=(Button)findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MessDetails.this,MapActivity.class);
                intent.putExtra("address",address);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
        btn_visited=(Button)findViewById(R.id.btn_visited);
        btn_visited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doVISIT();
            }
        });
        getMessDetails();
    }



    public void getMessDetails(){

        final ProgressDialog progressDialog=new ProgressDialog(MessDetails.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.MESS_DETAILS;

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
                        Toast.makeText(MessDetails.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("mess_id",mess_id);
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
                    JSONObject data = parentObj.getJSONObject("response");

                full_name.setText("Mess Name : "+data.getString("name"));
                location.setText("Location : "+data.getString("location"));
                landmark.setText("Landmark : "+data.getString("landmark"));

                contact_no.setText("Mobile No : "+data.getString("contact_no"));
                Address.setText("Address : "+data.getString("address"));
                vacancy.setText("No of vacancy : "+data.getString("vacancy"));

                room.setText("No of room : "+data.getString("no_of_room"));
                member.setText("No of member : "+data.getString("no_of_member"));
                seatrent.setText("Seat Rent : "+data.getString("room_rent"));
                aboutus.setText("About us : "+data.getString("about_us"));

                Mobile_No=data.getString("contact_no");
                address=data.getString("address");
                name=data.getString("name");


                String wi_fi = data.getString("wi_fi");
                if(wi_fi.equalsIgnoreCase("1")){
                    checkbox_wifi.setChecked(true);
                }else {
                    checkbox_wifi.setChecked(false);
                }
                String food=data.getString("food");
                if(food.equalsIgnoreCase("1")){
                    checkbox_foods.setChecked(true);
                }else {
                    checkbox_foods.setChecked(false);
                }

            }else {
                Toast.makeText(getApplicationContext(),"No data found",Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        getMessStatus();

    }

    public void getMessStatus(){

        final ProgressDialog progressDialog=new ProgressDialog(MessDetails.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.MESS_STATUS;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        JSONmessstatus(response);
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
                        Toast.makeText(MessDetails.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                UserInfo userInfo=Sharepreferences.getUserinfo(MessDetails.this);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("mess_id",mess_id);
                params.put("user_id",userInfo.getUserId());
                return params;
            }
        };
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(postRequest);


    }

    public void JSONmessstatus(String responsedata){

        try {
            JSONObject parentObj = new JSONObject(responsedata);
            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("true")){

                String poke_u=parentObj.getString("poke_u");
                if(poke_u.equalsIgnoreCase("1")){
                   // btn_poke.setText("Poked");
                    btn_poke.setBackgroundColor(Color.parseColor("#388E3C"));
                }else {
                   // btn_poke.setText("Unpoked");
                    btn_poke.setBackgroundColor(Color.parseColor("#448AFF"));
                }
                String user_visit=parentObj.getString("user_visit");
                if(user_visit.equalsIgnoreCase("1")){
                   // btn_visited.setText("Visited");
                    btn_visited.setBackgroundColor(Color.parseColor("#388E3C"));
                }else {
                   // btn_visited.setText("Visit");
                    btn_visited.setBackgroundColor(Color.parseColor("#448AFF"));
                }
            }else {
                String error_msg=parentObj.getString("response");
                Toast.makeText(getApplicationContext(),error_msg,Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void doPoke(){

        final ProgressDialog progressDialog=new ProgressDialog(MessDetails.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.USER_POKE;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        JSONPOKE(response);
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
                        Toast.makeText(MessDetails.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                UserInfo userInfo=Sharepreferences.getUserinfo(MessDetails.this);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("mess_id",mess_id);
                params.put("user_id",userInfo.getUserId());
                return params;
            }
        };
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(postRequest);

    }

    public void JSONPOKE(String responsedata){
        try {
            JSONObject parentObj = new JSONObject(responsedata);
            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("true")){

                String poke_u=parentObj.getString("poke_u");
                if(poke_u.equalsIgnoreCase("1")){
                   // btn_poke.setText("Poked");
                    btn_poke.setBackgroundColor(Color.parseColor("#388E3C"));
                }else {
                   // btn_poke.setText("Unpoked");
                    btn_poke.setBackgroundColor(Color.parseColor("#448AFF"));
                }

                String succ_msg=parentObj.getString("response");
                Toast.makeText(getApplicationContext(),succ_msg,Toast.LENGTH_SHORT).show();
            }else {
                String error_msg=parentObj.getString("response");
                Toast.makeText(getApplicationContext(),error_msg,Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public void doVISIT(){

        final ProgressDialog progressDialog=new ProgressDialog(MessDetails.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.USER_VISIT;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        JSONVISIT(response);
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
                        Toast.makeText(MessDetails.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                UserInfo userInfo=Sharepreferences.getUserinfo(MessDetails.this);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("mess_id",mess_id);
                params.put("user_id",userInfo.getUserId());
                return params;
            }
        };
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(postRequest);

    }

    public void JSONVISIT(String responsedata){
        try {
            JSONObject parentObj = new JSONObject(responsedata);
            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("true")){


                String succ_msg=parentObj.getString("response");
                Toast.makeText(getApplicationContext(),succ_msg,Toast.LENGTH_SHORT).show();
                String user_visit=parentObj.getString("user_visit");
                if(user_visit.equalsIgnoreCase("1")){
                   // btn_visited.setText("Visited");
                    btn_visited.setBackgroundColor(Color.parseColor("#388E3C"));
                }else {
                   // btn_visited.setText("Visit");
                    btn_visited.setBackgroundColor(Color.parseColor("#448AFF"));
                }
            }else {
                String error_msg=parentObj.getString("response");
                Toast.makeText(getApplicationContext(),error_msg,Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }



    public void call(String number){

        boolean hasPermissionCall = (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermissionCall) {
            ActivityCompat.requestPermissions(MessDetails.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL);
        }else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + number));
            startActivity(callIntent);
        }
    }

    public void sendWhatsAppMsg(String number) {

        Intent waIntent = new Intent(Intent.ACTION_SEND);
        waIntent.setType("text/plain");
        String text = "Hi,I want to check in your mess";
        waIntent.setPackage("com.whatsapp");
        if (waIntent != null) {
            waIntent.putExtra(Intent.EXTRA_TEXT, text);//
            startActivity(Intent.createChooser(waIntent, text));
        } else {
            Toast.makeText(this, "WhatsApp not found", Toast.LENGTH_SHORT)
                    .show();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(MessDetails.this, "Permission granted. You can call now", Toast.LENGTH_SHORT).show();
                } else
                {
                    Toast.makeText(MessDetails.this, "The app was not allowed to call. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }

        }

    }

}
