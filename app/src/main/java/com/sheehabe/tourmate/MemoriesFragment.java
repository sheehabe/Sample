package com.sheehabe.tourmate;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.sheehabe.tourmate.pojo.ImageInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MemoriesFragment extends Fragment {

    private FloatingActionButton memoriesBtn;

    private RecyclerView recyclerView;
    private Context context;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage mstorage;
    String Storage_Path = "Upload Images";
    String Database_Path = "MemoryEvent";
    private StorageReference mstorageReference;
    private DatabaseReference mDatabaseRef;
    private ImageAdapter adapter;
    private List<ImageInfo> imageInfoList;
    private ValueEventListener mDBListener;
    private String tourId;

/*    private void openImageActivity(String[] data) {
        Intent intent = new Intent( getActivity(), ImageActivity.class );
        intent.putExtra( "NAME_KEY", data[0] );
        intent.putExtra( "DESCRIPTION_KEY", data[1] );
        intent.putExtra( "IMAGE_KEY", data[2] );
        startActivity( intent );
    }*/

    public MemoriesFragment() {
        imageInfoList = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_memories, container, false );
        final FragmentActivity c = getActivity();

        recyclerView = view.findViewById( R.id.memoryrecyclerView );

        //mstorageReference = FirebaseStorage.getInstance().getReference( Storage_Path );

        recyclerView.setLayoutManager( new LinearLayoutManager( c) );

        String userID = firebaseAuth.getCurrentUser().getUid();
        //String tourId = MainActivity.getInstance().tourId;
        mDatabaseRef = FirebaseDatabase.getInstance().getReference( "UserList" ).child( userID ).child( "TourInfo" )
                .child( "MemoryEvent" );
        mDatabaseRef.addValueEventListener( new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //totaldays++; // total class days
                    if (snapshot.child( "TourInfo" ).getValue( String.class ).equals( "tourID" )) {
                        //count no of days present
                    }

                if (dataSnapshot.exists()) {
                    imageInfoList.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        ImageInfo info = dataSnapshot.getValue( ImageInfo.class );

                        imageInfoList.add( info );
                        adapter.notifyDataSetChanged();
                    }

                }

            }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText( getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT ).show();

            }
        } );
        adapter = new ImageAdapter( c, imageInfoList );
        recyclerView.setAdapter( adapter );



        adapter.setOnItemClickListener( new ImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ImageInfo clicked = imageInfoList.get( position );
                String[] Data = {clicked.getImageName(), clicked.getLocation(), clicked.getImageURL()};
                //openImageActivity( Data );

            }

            @Override
            public void onEditItemClick(int position) {
                ImageInfo imageInfo = imageInfoList.get( position );
                String[] Data = {imageInfo.getImageName(), imageInfo.getLocation(), imageInfo.getImageURL()};
                //openImageActivity( Data );
            }

            @Override
            public void onDeleteItemClick(int position) {
                ImageInfo selectedItem = imageInfoList.get( position );
                final int selectedKey = selectedItem.getKey();
                StorageReference imageRef = mstorage.getReferenceFromUrl( selectedItem.getImageURL() );
                imageRef.delete().addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mDatabaseRef.child( String.valueOf( selectedKey ) ).getKey();
                        Toast.makeText( getActivity(), "Item deleted", Toast.LENGTH_SHORT ).show();
                    }
                } );

            }
        } );




        return view;
    }

/*    public void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener( mDBListener );
    }*/

}


