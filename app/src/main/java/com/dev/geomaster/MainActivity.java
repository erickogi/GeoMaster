package com.dev.geomaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<ClientModel> clientModels;
    private ClientAdapter clientAdapter;
    private RecyclerView recyclerView;

    private StaggeredGridLayoutManager mStaggeredLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.hide();
        recyclerView = findViewById(R.id.recyclerView);

        clientModels=new ArrayList<>();
        upDateUi(clientModels);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(getString(R.string.firebase_path));
        clientModels = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                clientModels.clear();


                for (DataSnapshot postSnapshot: snapshot.getChildren()) {

                    ClientModel clientModel = postSnapshot.getValue(ClientModel.class);
                    clientModel.setName(postSnapshot.getKey());

                    Log.d("datanap",clientModel.getName());

                    clientModels.add(clientModel);





                }

                if(clientModels!=null){
                    upDateUi(clientModels);
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
                if(clientModels!=null){
                    upDateUi(clientModels);
                }

            }
        });
    }

    private void upDateUi(final List<ClientModel> clientModels) {
        clientAdapter =new ClientAdapter(MainActivity.this, clientModels, new OnclickRecyclerListener() {
            @Override
            public void onClickListener(int position) {

                Intent intent=new Intent(MainActivity.this,MapsActivity.class);
                intent.putExtra("client",clientModels.get(position).getName());
                startActivity(intent);
            }

            @Override
            public void onLongClickListener(int position) {

            }

            @Override
            public void onClickListener(int adapterPosition, ImageView imageView) {

            }
        });

        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);


        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(mStaggeredLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //setEmptyState(ima geAdapter.getItemCount()>0);
        recyclerView.setAdapter(clientAdapter);


    }

    public void showAll(View view) {
        Intent intent=new Intent(MainActivity.this,MapsActivity.class);
        intent.putExtra("client","All");
        startActivity(intent);
    }
}
