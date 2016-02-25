package com.example.anara.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Joseph on 2/24/2016.
 */
public class Search extends AppCompatActivity {

    String[] items;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;


    Firebase database = new Firebase("https://burning-fire-7007.firebaseio.com/user");
    Query queryRef = database.orderByChild("groupId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Firebase.setAndroidContext(this);




        listView = (ListView) findViewById(R.id.listview);
        editText = (EditText) findViewById(R.id.txtsearch);
        initList();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (s.toString().equals("")) {
                    initList();
                } else {
                    searchItem(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void searchItem(String textToSearch) {
        for(String item:items){
            if(!item.toLowerCase().contains(textToSearch.toLowerCase())) {
                listItems.remove(item);
            }

        }

        adapter.notifyDataSetChanged();
    }

    public void initList(){



        //items = new String[]{database.toString()};
        listItems= new ArrayList<>(Arrays.asList(database.toString()));
        adapter = new ArrayAdapter<String>(this, R.layout.listitem_layout,R.id.txtitem,listItems);
        listView.setAdapter(adapter);
    }
}
