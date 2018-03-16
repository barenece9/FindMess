package com.lnsel.findmess.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.lnsel.findmess.R;
import com.lnsel.findmess.SetterGetter.Messdata;
import com.lnsel.findmess.activity.FindActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class CustomAdapter extends BaseAdapter{

    Context context;
    ArrayList<Messdata> listitems;
    private ArrayList<Messdata> arraylist;

    private static LayoutInflater inflater=null;
    public CustomAdapter(FindActivity mainActivity, ArrayList<Messdata> listitems1) {
        // TODO Auto-generated constructor stub
        listitems=listitems1;

        this.arraylist = new ArrayList<Messdata>();
        this.arraylist.addAll(listitems);

        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CustomAdapter(FindActivity mainActivity){
        context=mainActivity;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listitems.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView name,contact_no,vacancy,landmark,location;
        Button btn_delete;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.mess_list_item, null);

        holder.name=(TextView) rowView.findViewById(R.id.name);
        holder.contact_no=(TextView) rowView.findViewById(R.id.contact_no);
        holder.vacancy=(TextView)rowView.findViewById(R.id.vacancy);
        holder.landmark=(TextView) rowView.findViewById(R.id.landmark);
        holder.location=(TextView)rowView.findViewById(R.id.location);
        holder.name.setText("Name : "+listitems.get(position).getName());
        holder.contact_no.setText("Mobile : "+listitems.get(position).getContact_no());
        holder.landmark.setText("Landmark : "+listitems.get(position).getLandmark());
        holder.vacancy.setText("Vacancy : "+listitems.get(position).getVacancy());
        holder.location.setText("Location : "+listitems.get(position).getLocation());

        return rowView;
    }


    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        listitems.clear();
        if (charText.length() == 0||charText.equalsIgnoreCase("")) {
            listitems.addAll(arraylist);
           // btn_clear.setVisibility(View.GONE);
        }
        else
        {
            for (Messdata wp : arraylist)
            {
                if (wp.getLocation().toLowerCase(Locale.getDefault()).contains(charText)||
                        wp.getLandmark().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    listitems.add(wp);
                }
            }

           // btn_clear.setVisibility(View.VISIBLE);
        }
        notifyDataSetChanged();
    }
}