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
import com.sheehabe.tourmate.Adapter.ImageAdapter;
import com.sheehabe.tourmate.AddTripActivity;
import com.sheehabe.tourmate.ImageActivity;
import com.sheehabe.tourmate.R;
import com.sheehabe.tourmate.pojo.ImageInfo;

import java.util.ArrayList;
import java.util.List;

public class ItemsActivity extends AppCompatActivity {
    public static String TAG = "Tourmate";

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;


    private FirebaseStorage mstorage;
    private DatabaseReference mDatabaseRef;

    private RecyclerView recyclerView;
    private ImageAdapter adapter;
    private List<ImageInfo> list;
    private String tourId;




   /* private void openImageActivity(String[] data) {
        Intent intent = new Intent( this, ImageActivity.class );
        intent.putExtra( "NAME_KEY", data[0] );
        intent.putExtra( "DESCRIPTION_KEY", data[1] );
        intent.putExtra( "IMAGE_KEY", data[2] );
        startActivity( intent );
    }*/

    private void openItemsActivity(String[] data) {
        // Intent intent = new Intent( getActivity(), AddTripActivity.class );
        Intent intent = new Intent(ItemsActivity.this, AddTripActivity.class);

        intent.putExtra( "imageID", data[0] );
        intent.putExtra( "imageName", data[1] );
        intent.putExtra( "location", data[2] );
        intent.putExtra( "imageURL", data[3] );
        startActivity( intent );
    }

    //private RecyclerView mRecylerView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_items );
        recyclerView = findViewById( R.id.mRecyclerView );
        tourId = getIntent().getStringExtra( "tourId" );
        Log.d( TAG, "ItemActivity tourId:" + tourId );

        list = new ArrayList<>();

        //ImageInfo imageInfo = new ImageInfo( "hello","dhaka","https://firebasestorage.googleapis.com/v0/b/sheehabe-f66fa.appspot.com/o/Upload%20Images%2F1555486881632.png%2FMemoryEvent?alt=media&token=b8ffe0ff-a221-497a-863f-a0c2971b5591" );
        // ImageInfo imageInfo = new ImageInfo();

        Toast.makeText( ItemsActivity.this, "This is Item A" + tourId, Toast.LENGTH_SHORT ).show();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        String userID = firebaseAuth.getCurrentUser().getUid();
        tourId = getIntent().getStringExtra( "tourId" ).toString();
        mDatabaseRef = firebaseDatabase.getReference( "UserList" ).child( userID ).child( "TourInfo" )
                .child( tourId ).child( "MemoryEvent" );

        //mDatabaseRef = firebaseDatabase.getReference( "UserList" ).child( userID ).child( "TourInfo" );
        //Log.d(TAG,"hello here",);
        mDatabaseRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        ImageInfo imagedata = data.getValue( ImageInfo.class );
                        list.add( imagedata );
                        adapter.notifyDataSetChanged();
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
        recyclerView.setLayoutManager( new LinearLayoutManager( ItemsActivity.this ) );
        recyclerView.setHasFixedSize( true );
        adapter = new ImageAdapter( ItemsActivity.this, list );
        recyclerView.setAdapter( adapter );

        adapter.setOnItemClickListener( new ImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ImageInfo clicked = list.get( position );
                String[] Data = {clicked.getImageName(), clicked.getLocation(), clicked.getImageURL()};
                openItemsActivity( Data );
            }

            @Override
            public void onEditItemClick(int position) {

                ImageInfo clicked = list.get( position );
                Intent intent = new Intent(ItemsActivity.this, ImageActivity.class);
                intent.putExtra("imageID",clicked.getImageID());
                intent.putExtra("imageName",clicked.getImageName());
                intent.putExtra("location",clicked.getLocation());
                intent.putExtra("imageURL",clicked.getImageURL());


                startActivity(intent);
            }

            @Override
            public void onDeleteItemClick(int position) {
                ImageInfo selectedItem = list.get( position );
                final int selectedKey = selectedItem.getKey();
                StorageReference imageRef = mstorage.getReferenceFromUrl( selectedItem.getImageURL() );
                imageRef.delete().addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mDatabaseRef.child( String.valueOf( selectedKey ) ).getKey();
                        Toast.makeText( ItemsActivity.this, "Item deleted", Toast.LENGTH_SHORT ).show();
                    }
                } );
    }} );
    }

}

