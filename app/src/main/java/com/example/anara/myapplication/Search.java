package com.example.anara.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.view.View.OnKeyListener;


/**
 * Created by Joseph on 2/24/2016.
 */
public class Search extends AppCompatActivity {

    String[] items;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;
    int checkDel = 0;
    String finalData;


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
                checkDel++;

                if (s.toString().equals("") || checkDel == count + 2) {
                    initList();
                    checkDel = count;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    searchItem(editText.getText().toString());
                    return true;
                }


                return false;
            }
        });



    }

    private void searchItem(String textToSearch) {
        for(int i = listItems.size()-1; i>= 0; i--) {
            if (!listItems.get(i).toLowerCase().contains(textToSearch.toLowerCase())) {
                listItems.remove(i);
                adapter.notifyDataSetChanged();
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(Search.this, "Will go into "+listItems.get(position), Toast.LENGTH_SHORT).show();;
                    finalData = "" + listItems.get(position);



                }
            });
        }

        if(listItems.size() == 0)
        {
            Toast.makeText(Search.this, "No Group With Such Name", Toast.LENGTH_SHORT).show();
        }


        adapter.notifyDataSetChanged();
    }

    public String clickedGroup(){
        return finalData;
    }

    private void initList(){
        Firebase database = new Firebase("https://burning-fire-7007.firebaseio.com/group");
        Query query = database.orderByValue();
        listItems = new ArrayList<String>();


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String name = (String) child.child("groupName").getValue();
                    listItems.add(name);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




        adapter = new ArrayAdapter<String>(this, R.layout.listitem_layout,R.id.txtitem,listItems);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
