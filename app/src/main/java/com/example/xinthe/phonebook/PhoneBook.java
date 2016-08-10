package com.example.xinthe.phonebook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PhoneBook extends AppCompatActivity {

    DBHelper dbHelper;
    ListView lv;
    ArrayList<HolderPhoneBook> data;
    ImageButton add_contact;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_book);
        dbHelper = new DBHelper(PhoneBook.this);
        add_contact = (ImageButton) findViewById(R.id.addcntct);
        Intent i=getIntent();
        add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact();
            }
        });
        populateList();
    }
    @Override
    public void onResume() {
        super.onResume();
        populateList();
    }

    public void addContact() {
        startActivity(new Intent(PhoneBook.this, NewOrEditContact.class));

    }
    private void populateList() {
        data = dbHelper.getPhoneBookList();
        if (data == null || data.size() == 0) {
            Toast.makeText(this, "Empty List", Toast.LENGTH_LONG).show();
            lv = (ListView) findViewById(R.id.listView);
            lv.setAdapter(new CustomAdapter(this,data));
        } else {
            Log.e("hey", "hello");
            lv = (ListView) findViewById(R.id.listView);

            if(adapter == null){
                adapter = new CustomAdapter(this,data);
                lv.setAdapter(adapter);
            }
            else{adapter.setData(data);
                lv.setAdapter(adapter);}
            // lv.setAdapter(new CustomAdapter(this,data));
            // lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //   @Override
            //   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //     startActivity(new Intent(MainActivity.this,EnterDetails.class).putExtra("id",data.get(position).getId()));
            //  }
            // });
        }
    }
    class CustomAdapter extends BaseAdapter {
        Context context;  ArrayList<HolderPhoneBook> data;

        CustomAdapter(Context context,ArrayList<HolderPhoneBook>data){ this.context = context;this.data = data;}

        public void setData(ArrayList<HolderPhoneBook> data){
            this.data = data;
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return data.get(position).getId();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View layout = convertView;
            if (layout == null) {
                layout = getLayoutInflater().inflate(R.layout.row, parent, false);

            }
            TextView cntctName = (TextView) layout.findViewById(R.id.cntctname);
            TextView cntctno = (TextView) layout.findViewById(R.id.cntctno);
            ImageButton edit = (ImageButton) layout.findViewById(R.id.edit);
            ImageButton delete = (ImageButton) layout.findViewById(R.id.delete);
            HolderPhoneBook hbd = data.get(position);
            if (data.size() != 0) {
                cntctName.setText(hbd.getFname());

                cntctno.setText(hbd.getPhoneNumber());
                //edit.setTag(R.id.db_id,hbd.getId());
                edit.setTag(R.id.db_id, position);
                delete.setTag(R.id.view_id, position);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) v.getTag(R.id.view_id);
                        boolean success = new DBHelper(PhoneBook.this).deletePhoneBookContact(data.get(pos).getId());
                        if (success == true) {
                            Toast.makeText(PhoneBook.this, "delete is successful", Toast.LENGTH_LONG).show();
                            populateList();
                        } else {
                            Toast.makeText(PhoneBook.this, "delete unsuccessful", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) v.getTag(R.id.db_id);
                        startActivity(new Intent(PhoneBook.this, NewOrEditContact.class).putExtra("id", data.get(pos).getId()));
                    }
                });
                return layout;
            } else return layout;
        }
    }




}
