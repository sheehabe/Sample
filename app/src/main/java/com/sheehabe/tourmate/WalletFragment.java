package com.sheehabe.tourmate;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sheehabe.tourmate.Adapter.ExpenseAdapter;
import com.sheehabe.tourmate.pojo.ExpenseInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends Fragment {

    private ImageView walletBtn;
    private RecyclerView recyclerView;

    private List<ExpenseInfo> expenseInfo;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mdatabaseReference;
    private FirebaseAuth firebaseAuth;
    String Database_Path="ExpenseList";
    private ExpenseAdapter adapter;
    public String tourId;

    public WalletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate( R.layout.fragment_wallet, container, false );

        recyclerView=view.findViewById( R.id.recylerViewwallet );

        firebaseDatabase = FirebaseDatabase.getInstance();
        expenseInfo = new ArrayList<>();
        recyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );
        adapter = new ExpenseAdapter( getActivity(), expenseInfo ) {
        };
        recyclerView.setAdapter( adapter );






        firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getCurrentUser().getUid();
       // tourId = getIntent().getStringExtra( "tourId" ).toString();
        mdatabaseReference = FirebaseDatabase.getInstance().getReference( "UserList" ).child( userID ).child( "TourInfo" )
                .child( "ExpenseList" );

        mdatabaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    expenseInfo.clear();
                    for (DataSnapshot data: dataSnapshot.getChildren()){

                        ExpenseInfo expense=data.getValue(ExpenseInfo.class);
                        expenseInfo.add( expense );
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        return view;
    }



}
