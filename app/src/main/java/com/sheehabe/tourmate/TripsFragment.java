package com.sheehabe.tourmate;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.sheehabe.tourmate.Adapter.TourAdapter;
import com.sheehabe.tourmate.pojo.TourInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TripsFragment extends Fragment {
    //private FloatingActionButton addition;

    private List<TourInfo> tourlist;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference userDB;
    private Activity mActivity;
    private FirebaseStorage mstorage;
    private RecyclerView recyclerView;
    private TourAdapter adapter;
    // Map<String,Object> UserMap=new HashMap<>();
    private void openAddTripActivity(String[] data) {
       // Intent intent = new Intent( getActivity(), AddTripActivity.class );
        Intent intent = new Intent(mActivity,AddTripActivity.class);

        intent.putExtra( "tourId", data[0] );
        intent.putExtra( "tourName", data[1] );
        intent.putExtra( "tourBudget", data[2] );
        intent.putExtra( "tourStartDate", data[3] );
        intent.putExtra( "tourEndDate", data[4] );
        startActivity( intent );
    }

    public TripsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_trips, container, false );
        //initialize();
        firebaseDatabase = FirebaseDatabase.getInstance();
        tourlist = new ArrayList<>();
        //addition = view.findViewById( R.id.additionBtn );
        recyclerView = view.findViewById( R.id.recylerViewTrip );
        recyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );
        adapter = new TourAdapter( getActivity(), tourlist );
        recyclerView.setAdapter( adapter );

        adapter.setOnItemClickListener( new TourAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                TourInfo clicked = tourlist.get( position );
                String[] Data = {clicked.getTourName(), clicked.getTourLocation(), clicked.getTourBudget(),clicked.getTourStartDate(),clicked.getTourEndDate()};

                Log.d("Sheehabe", "onItemClick data pos: "+position+",,expenseId: "+clicked.getTourID()+",, " +
                        "tourName: "+clicked.getTourName());
                openAddTripActivity( Data );
            }

            @Override
            public void onEditItemClick(int position) {
                TourInfo clicked = tourlist.get( position );
                Intent intent = new Intent(mActivity,AddTripActivity.class);
                intent.putExtra("tourID",clicked.getTourID());
                intent.putExtra("tourName",clicked.getTourName());
                intent.putExtra("tourLocation",clicked.getTourLocation());
                intent.putExtra("tourBudget",clicked.getTourBudget());
                intent.putExtra("tourStartDate",clicked.getTourStartDate());
                intent.putExtra("tourEndDate",clicked.getTourEndDate());

                Log.d("Sheehabe", "onEditItemClick data pos: "+position+",,expenseId: "+clicked.getTourID()+",, " +
                        "tourName: "+clicked.getTourName());
                mActivity.startActivity(intent);

               /* TourInfo tourInfo = tourlist.get( position );
                String[] Data = {tourInfo.getTourName(), tourInfo.getTourLocation(), tourInfo.getTourBudget(),tourInfo.getTourStartDate(),tourInfo.getTourEndDate()};
                openAddTripActivity( Data );*/

            }

            @Override
            public void onDeleteItemClick(int position) {
                TourInfo selectedItem = tourlist.get( position );
                final int selectedKey = selectedItem.getKey();
                StorageReference ref = mstorage.getReference( selectedItem.getTourID() );
                ref.delete().addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        userDB.child( String.valueOf( selectedKey ) ).getKey();
                        Toast.makeText( getActivity(), "Item deleted", Toast.LENGTH_SHORT ).show();
                    }
                } );
            }
        } );

        firebaseAuth = FirebaseAuth.getInstance();
        /*firebaseDatabase=FirebaseDatabase.getInstance();*/

        String tourID = firebaseAuth.getCurrentUser().getUid();
      userDB = FirebaseDatabase.getInstance().getReference( "UserList" ).child( tourID ).child( "TourInfo" );

        userDB.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sum=0;
                final int total = 0;
                if (dataSnapshot.exists()) {
                    tourlist.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        TourInfo tour = data.getValue( TourInfo.class );
                        tourlist.add( tour );
                        adapter.notifyDataSetChanged();
                    }


                }
                //else if(total>=0){
/*
                    for (DataSnapshot data: dataSnapshot.getChildren()){
                        Map<String,Object> map=(Map<String, Object>) data.getValue();
                        Object budget=map.get("tourBudget");
                        int value = Integer.parseInt( String.valueOf( budget ) );
                        Log.d( "sheehabe value","budget"+value );

                        Object cost=map.get("cost");
                        int pvalue=Integer.parseInt( String.valueOf( cost ) );
                        sum+=pvalue;
                        //total=(budget-sum);
                    //}
                }*/


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );


       /* addition.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getActivity(), AddTripActivity.class );
                getActivity().startActivity( intent );

            }
        } );*/


        return view;
    }

   /* private void initialize() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        tourlist = new ArrayList<>();
        //tourlist.add( new TourInfo( "Gazni","Sherpur","10april,19", "12april,19","20,000" ) );

    }*/


}
