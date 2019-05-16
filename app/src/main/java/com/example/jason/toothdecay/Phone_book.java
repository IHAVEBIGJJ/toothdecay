package com.example.jason.toothdecay;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Phone_book extends AppCompatActivity {
    private static final String NAME = "name";
    private static final String NUMBER = "number";
    private List<Map<String, String>> contactsArrayList;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_book);
        initData();
        initView();
    }
    private void initView(){
        listView = (ListView) findViewById(R.id.listview);
        SimpleAdapter adapter = new SimpleAdapter(this,
                contactsArrayList,
                android.R.layout.simple_list_item_1,
                new String[] { NAME,NUMBER },
                new int[] { android.R.id.text1,android.R.id.text2 });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String number = contactsArrayList.get(position).get(NUMBER);
                new AlertDialog.Builder(Phone_book.this)
                        .setTitle(number)
                        .setItems(new String[]{"Call"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                                        startActivity(call);
                                        break;
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
    }

    private void initData(){
        getPhoneBookData();
    }

    public void getPhoneBookData(){
        contactsArrayList = new ArrayList<>();
        Cursor contacts_name = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null);

        while (contacts_name.moveToNext()) {
            Map<String, String> map = new HashMap<>();
            String phoneNumber = "";
            long id = contacts_name.getLong(
                    contacts_name.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor contacts_number = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                            + "=" + Long.toString(id),
                    null,
                    null);

            while (contacts_number.moveToNext()) {
                phoneNumber = contacts_number
                        .getString(contacts_number.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            contacts_number.close();
            String name = contacts_name.getString(contacts_name
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            map.put(NAME, name);
            map.put(NUMBER, phoneNumber);
            contactsArrayList.add(map);
        }
    }
}
