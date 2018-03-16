package com.lnsel.findmess.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lnsel.findmess.R;
import com.lnsel.findmess.SetterGetter.Messdata;
import com.lnsel.findmess.adapter.CustomAdapter;
import com.lnsel.findmess.appconroller.AppController;
import com.lnsel.findmess.utlity.Webservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FindActivity extends Activity {

    EditText etn_search;
    ListView list_view;
    ArrayList< Messdata> list;
    CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        list=new ArrayList<>();
        adapter=new CustomAdapter(FindActivity.this);


        Button btn_back=(Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindActivity.this,UserLogin.class));
                finish();
            }
        });

        Button btn_logout=(Button)findViewById(R.id.btn_logout);
        btn_logout.setBackgroundResource(R.drawable.ic_person_black_24dp);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindActivity.this,UserProfile.class));
                finish();
            }
        });

        list_view=(ListView)findViewById(R.id.list_view);
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(FindActivity.this,MessDetails.class);
                intent.putExtra("mess_id",list.get(i).getMess_id());
                startActivity(intent);
                finish();
            }
        });


        etn_search=(EditText)findViewById(R.id.etn_search);
        etn_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                // When user changed the Text
                String text = etn_search.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        doMessListing();

    }


    public void doMessListing(){

        final ProgressDialog progressDialog=new ProgressDialog(FindActivity.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.ALL_MESS_LIST;

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
                        Toast.makeText(FindActivity.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                return params;
            }
        };
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(postRequest);

    }

    public void JSONresponse(String responsedata){

        try {
            list.clear();
            JSONObject parentObj = new JSONObject(responsedata);
            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("true")){
                JSONArray result = parentObj.getJSONArray("result");
                for(int i=0;i<result.length();i++) {
                    JSONObject data = result.getJSONObject(i);
                    String name = data.getString("name");
                    String contact_no = data.getString("contact_no");
                    String landmark = data.getString("landmark");
                    Messdata messdata=new Messdata();
                    messdata.setMess_id(data.getString("mess_id"));
                    messdata.setLocation(data.getString("location"));
                    messdata.setName(name);
                    messdata.setContact_no(contact_no);
                    messdata.setLandmark(landmark);
                    messdata.setVacancy(data.getString("vacancy"));
                    list.add(messdata);
                }
            }else {
                Toast.makeText(getApplicationContext(),"No data found",Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //list_view.setAdapter(new CustomAdapter(FindActivity.this,list));

        adapter=new CustomAdapter(FindActivity.this,list);
        list_view.setAdapter(adapter);
    }
}
