package com.example.xinthe.phonebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class NewOrEditContact extends AppCompatActivity {
    EditText fname;
    EditText lname;
    EditText phnno;
    EditText nickname;
    ImageButton editcntct;
    ImageButton clear;
    ImageButton back;
    DBHelper dbHelper;

    int id = -1;
    HolderPhoneBook hpb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_or_edit_contact);
        fname = (EditText) findViewById(R.id.frstname);
        lname = (EditText) findViewById(R.id.lastname);
        phnno = (EditText) findViewById(R.id.phno);
        nickname = (EditText) findViewById(R.id.nick);
        dbHelper = new DBHelper(NewOrEditContact.this);
        editcntct=(ImageButton)findViewById(R.id.editcntct);
        clear=(ImageButton)findViewById(R.id.clear);
        back=(ImageButton)findViewById(R.id.back);
        Intent i = getIntent();
        id = i.getIntExtra("id", -1);
        if (id != -1) {
            hpb = dbHelper.getPhoneBookList(id);
            fname.setText(hpb.getFname());
            lname.setText(hpb.getLname());
            phnno.setText(hpb.getPhoneNumber());
            nickname.setText(hpb.getNickname());

        }
        editcntct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fname = fname.getText().toString();
                if (Fname.matches("")) {
                    Toast.makeText(NewOrEditContact.this, "You did not enter a firstname", Toast.LENGTH_SHORT).show();
                    return;
                }
                String Lname = lname.getText().toString();
                if (Lname.matches(" ")) {
                    Toast.makeText(NewOrEditContact.this, "You did not enter a lastname", Toast.LENGTH_SHORT).show();
                    return;
                }
                String Phn = phnno.getText().toString();
                if (Phn.matches("")) {
                    Toast.makeText(NewOrEditContact.this, "You did not enter a  phonenumber", Toast.LENGTH_SHORT).show();
                    return;
                }
                String Nickname = nickname.getText().toString();
                if (Nickname.matches("")) {
                    Toast.makeText(NewOrEditContact.this, "You did not enter a nick name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (id == -1) {
                    HolderPhoneBook hpb = new HolderPhoneBook(Fname, Lname, Phn, Nickname);
                    boolean id = dbHelper.insertPhoneBook(hpb);
                    if (id == true) {
                        Toast.makeText(NewOrEditContact.this, "added successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(NewOrEditContact.this, PhoneBook.class));
                    } else {
                        Toast.makeText(NewOrEditContact.this, "Failed to save", Toast.LENGTH_LONG).show();
                    }

                } else {
                    HolderPhoneBook hpb = new HolderPhoneBook(id, Fname, Lname, Phn, Nickname);
                    long id = dbHelper.updatePhoneBook(hpb);
                    if (id != -1) {
                        Toast.makeText(NewOrEditContact.this, "Updated successfully", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(NewOrEditContact.this, "Failed to update", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname.setText("");
                lname.setText("");
                phnno.setText("");
                nickname.setText("");;
                startActivity(new Intent(NewOrEditContact.this,PhoneBook.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname.setText("");
                lname.setText("");
                phnno.setText("");
                nickname.setText("");;
                startActivity(new Intent(NewOrEditContact.this,PhoneBook.class));
            }
        });

    }

}


