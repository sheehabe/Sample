package com.sheehabe.tourmate;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sheehabe.tourmate.databinding.ActivityAddTripBinding;
import com.sheehabe.tourmate.pojo.TourInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddTripActivity extends AppCompatActivity {

    private ActivityAddTripBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    //DatabaseReference mDatabase;
    ListView tourList;
    ArrayList<TourInfo> tours;
    DatabaseReference tourRef;

    String tourID="";
    String tourName="";
    String tourLocation="";
    String tourBudget="";
    String tourStartDate="";
    String tourEndDate="";
   // String tourName, tourLocation, tourBudget, tourStartDate, tourEndDate, tourID;

/*    int ID ;
    String TypeName ="";
    String Date = "";
    int Cost = 0;
    int Time = 0;*/


    private SimpleDateFormat dateAndTimeSDF = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
    private SimpleDateFormat dateSDF = new SimpleDateFormat( "dd MMM yyyy" );
    private SimpleDateFormat timeSDF = new SimpleDateFormat( "hh:mm aa" );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_add_trip );

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            tourID = bundle.getString("tourID");
            tourName = bundle.getString("tourName");
            tourLocation = bundle.getString("tourLocation");
            tourBudget = bundle.getString("tourBudget");
            tourStartDate = bundle.getString("tourStartDate");
            tourEndDate = bundle.getString("tourEndDate");

            Log.d("Sheehabe Edit", "onEditItemClick data tourID: "+tourID+",,tourName: "+tourName+",, " +
                    "tourLocation: "+tourLocation);

            binding.tourNameET.setText( tourName );
            binding.tourLocationET.setText( tourLocation );
            binding.tourBudgetET.setText( tourBudget );
            binding.tourStartDateTV.setText( tourStartDate );
            binding.tourEndDateTV.setText( tourEndDate );
//            binding.tourNameET.setText( tourName );


        }


        final MediaPlayer mediaPlayer = MediaPlayer.create( AddTripActivity.this, R.raw.clicksound );
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        binding.saveTrip.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tourName = binding.tourNameET.getText().toString();
                String tourLocation = binding.tourLocationET.getText().toString();
                String tourBudget = binding.tourBudgetET.getText().toString();
                String startDate = binding.tourStartDateTV.getText().toString();
                String endDate = binding.tourEndDateTV.getText().toString();


                if (tourName.equals( "" ) || tourLocation.equals( "" ) || tourBudget.equals( "" ) || startDate.equals( "" ) || endDate.equals( "" )) {
                    Toast.makeText( AddTripActivity.this, "please fill the contents", Toast.LENGTH_SHORT ).show();

                } else {
                    AddTripDescription( new TourInfo( tourName, tourLocation, tourBudget, startDate, endDate ) );

                    binding.tourNameET.setText( "" );
                    binding.tourLocationET.setText( "" );
                    binding.tourBudgetET.setText( "" );
                    binding.tourStartDateTV.setText( "" );
                    binding.tourEndDateTV.setText( "" );

                }
                mediaPlayer.start();

            }

        } );

        binding.tourStartDateTV.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
                mediaPlayer.start();

            }
        } );
        binding.tourEndDateTV.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker1();
                mediaPlayer.start();
            }
        } );



    }

    private void AddTripDescription(TourInfo tourInfo) {

        String userID = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference userDB = firebaseDatabase.getReference().child( "UserList" ).child( userID ).child( "TourInfo" );
        String key = userDB.push().getKey();
        tourInfo.setTourID( key );

        userDB.child( key ).setValue( tourInfo ).addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText( AddTripActivity.this, "Successfully saved", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
    }


    private void openDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String value = year + "/" + month + "/" + day + " 00:00:00";
                //e.g 2019/3/9 00:00:00

                Date date = null;

                try {
                    date = dateAndTimeSDF.parse( value );
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long fromTime = date.getTime();
                Log.d( "Date in MS", "" + fromTime );

                binding.tourStartDateTV.setText( dateSDF.format( date ) );

            }
        };


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get( Calendar.YEAR );
        int month = calendar.get( Calendar.MONTH );
        int day = calendar.get( Calendar.DAY_OF_MONTH );


        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog( AddTripActivity.this, dateSetListener, year, month, day );
        datePickerDialog.show();
    }

    private void openDatePicker1() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String value = year + "/" + month + "/" + day + " 00:00:00";
                //e.g 2019/3/9 00:00:00

                Date date = null;

                try {
                    date = dateAndTimeSDF.parse( value );
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long fromTime = date.getTime();
                Log.d( "Date in MS", "" + fromTime );

                binding.tourEndDateTV.setText( dateSDF.format( date ) );


            }
        };


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get( Calendar.YEAR );
        int month = calendar.get( Calendar.MONTH );
        int day = calendar.get( Calendar.DAY_OF_MONTH );


        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog( AddTripActivity.this, dateSetListener, year, month, day );
        datePickerDialog.show();
    }

}

