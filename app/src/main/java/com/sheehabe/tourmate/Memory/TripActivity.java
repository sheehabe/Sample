package com.sheehabe.tourmate.Memory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sheehabe.tourmate.Adapter.ExpenseAdapter;
import com.sheehabe.tourmate.BudgetActivity;
import com.sheehabe.tourmate.R;
import com.sheehabe.tourmate.UpdateActivity;
import com.sheehabe.tourmate.pojo.ExpenseInfo;

import java.util.ArrayList;
import java.util.List;

public class TripActivity extends AppCompatActivity {
    public static String TAG = "Tourmate";

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    private FirebaseStorage mstorage;

    private DatabaseReference mDatabaseRef;

    private RecyclerView recyclerView;
    public ExpenseAdapter adapter;
    private List<ExpenseInfo> list;
    private String tourId;

    private void openTripActivity(String[] data) {
        // Intent intent = new Intent( getActivity(), AddTripActivity.class );
        Intent intent = new Intent(TripActivity.this, BudgetActivity.class);

        intent.putExtra( "expenseID", data[0] );
        intent.putExtra( "typeName", data[1] );
        intent.putExtra( "cost", data[2] );
        intent.putExtra( "date", data[3] );
        intent.putExtra( "time", data[4] );
        intent.putExtra( "imageURL", data[5] );
        startActivity( intent );
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_trip );
        recyclerView = findViewById( R.id.tRecyclerView );
        Log.d(TAG,"ItemActivity tourId:"+tourId);

        list = new ArrayList<>();

        Toast.makeText( TripActivity.this, "This is Item A"+tourId, Toast.LENGTH_SHORT ).show();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        String userID = firebaseAuth.getCurrentUser().getUid();
        tourId = getIntent().getStringExtra( "tourId" ).toString();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference( "UserList" ).child( userID ).child( "TourInfo" ).child( tourId )
                .child( "ExpenseList" );

        mDatabaseRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        ExpenseInfo imagedata = data.getValue( ExpenseInfo.class );
                        list.add( imagedata );
                        adapter.notifyDataSetChanged();
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
        recyclerView.setLayoutManager( new LinearLayoutManager( TripActivity.this ) );
        adapter = new ExpenseAdapter( TripActivity.this, list );
        recyclerView.setAdapter( adapter );

        adapter.setOnItemClickListener( new ExpenseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ExpenseInfo clicked = list.get( position );
                String[] Data = {clicked.getTypeName(), clicked.getCost(), clicked.getDate(),clicked.getTime(),clicked.getImageURL()};
                //openTripActivity( Data );

            }

            @Override
            public void onEditItemClick(int position) {

                ExpenseInfo clicked = list.get( position );
                Intent intent = new Intent(TripActivity.this, UpdateActivity.class);
                intent.putExtra("expenseID",clicked.getExpenseID());
                intent.putExtra("typeName",clicked.getTypeName());
                intent.putExtra("date",clicked.getDate());
                intent.putExtra("cost",clicked.getCost());
                intent.putExtra("time",clicked.getTime());
                intent.putExtra("imageURL",clicked.getImageURL());


                startActivity(intent);

               /* String[] Data = {imageInfo.getImageName(), imageInfo.getLocation(), imageInfo.getImageURL()};
                //openTripActivity( Data );*/
            }

            @Override
            public void onDeleteItemClick(int position) {
                ExpenseInfo selectedItem = list.get( position );
                final int selectedKey = selectedItem.getKey();
                StorageReference imageRef = mstorage.getReferenceFromUrl( selectedItem.getImageURL() );
                imageRef.delete().addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mDatabaseRef.child( String.valueOf( selectedKey ) ).getKey();
                        Toast.makeText( TripActivity.this, "Item deleted", Toast.LENGTH_SHORT ).show();
                    }
                } );

            }
        } );



    }

}


