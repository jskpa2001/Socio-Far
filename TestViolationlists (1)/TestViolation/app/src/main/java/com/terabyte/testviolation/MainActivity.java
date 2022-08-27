package com.terabyte.testviolation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DBAdapter adapter;
    RecyclerView recyclerViewmain;
    private ArrayList<DataModel> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewmain=findViewById(R.id.recylerview);
        recyclerViewmain.setHasFixedSize(true);
        recyclerViewmain.setLayoutManager(new LinearLayoutManager(this));
        arrayList= new ArrayList<>();
        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
        db.child("violation_location").child("xXjHbdPVa1WuKfb5WaQgIVKfYl72").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dbsnapshot :snapshot.getChildren()){
                        DataModel dm=dbsnapshot.getValue(DataModel.class);
                        arrayList.add(dm);
                        System.out.println(arrayList);
                    }
                    adapter =new DBAdapter(MainActivity.this,arrayList);
                    recyclerViewmain.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}