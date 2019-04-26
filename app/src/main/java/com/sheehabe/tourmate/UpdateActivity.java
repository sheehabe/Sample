package com.sheehabe.tourmate;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.sheehabe.tourmate.databinding.ActivityUpdateBinding;
import com.sheehabe.tourmate.pojo.ExpenseInfo;
import com.sheehabe.tourmate.pojo.ImageInfo;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {
    private ActivityUpdateBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    //DatabaseReference mDatabase;
    public String tourId;

    List<ExpenseInfo> expenseInfo;
    Uri FilePathUri;
    String Storage_Path="Expense Images";
    String Database_Path="ExpenseList";
    private Context context;
    private StorageReference mstorageReference;
    private DatabaseReference mdatabaseReference;
    int Image_Request_Code = 7;
    private StorageTask uploadTask;
    private ProgressBar progressBar;
    private  String uriImage;

    String expenseID="";
    String typeName="";
    String date="";
    String cost="";
    String time="";
    String imageURL="";


    private SimpleDateFormat dateAndTimeSDF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private SimpleDateFormat dateSDF = new SimpleDateFormat("dd MMM yyyy");
    private SimpleDateFormat timeSDF = new SimpleDateFormat("hh:mm aa");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this,R.layout.activity_update );

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            expenseID = bundle.getString("expenseID");
            typeName = bundle.getString("typeName");
            cost = bundle.getString("cost");
            date = bundle.getString("date");
            time = bundle.getString("time");
            imageURL = bundle.getString("imageURL");



            binding.expenseTypeET.setText( typeName );
            binding.updateAmount.setText( cost );
            binding.updateDate.setText( date );
            binding.updateTime.setText( time );
            //binding.expenseTimeIV.setImageResource( Integer.parseInt( imageURL ) );
//            binding.tourNameET.setText( tourName );


        }
        binding.updateDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker();
            }
        });
        binding.updateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePicker();
            }
        });

        final MediaPlayer mediaPlayer = MediaPlayer.create( UpdateActivity.this, R.raw.clicksound );
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        mstorageReference = FirebaseStorage.getInstance().getReference( Storage_Path );

        binding.updateDocumentBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String typeName = binding.expenseTypeET.getText().toString();
                String cost = binding.updateAmount.getText().toString();
                String date = binding.updateDate.getText().toString();
                String time = binding.updateTime.getText().toString();



/*        boolean savedOrNot =new ExpenseInfo(typeName, date, Integer.parseInt(cost), Integer.parseInt(time),ID);
        if (savedOrNot){
            Toast.makeText(this, "Data Updated" , Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(UpdateActivity.this,WalletFragment.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Data is not updated.", Toast.LENGTH_SHORT).show();

        }*/

            }
        } );



        binding.updateBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uploadTask !=null&& uploadTask.isInProgress()){
                    Toast.makeText( UpdateActivity.this, "Upload is progress.........", Toast.LENGTH_SHORT ).show();
                }else{
                    FileUpload(new ImageInfo());}
                mediaPlayer.start();
            }
        } );
        binding.updateDocumentBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileChooser();
            }

        } );
        mediaPlayer.start();

    }


    private void FileUpload(ImageInfo imageInfo) {
        final String userID = firebaseAuth.getCurrentUser().getUid();

        StorageReference ref=mstorageReference.child( System.currentTimeMillis()+"."+GetFileExtension(FilePathUri) );
        uploadTask=ref.putFile(FilePathUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        // Get a URL to the uploaded content
                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();

                        Toast.makeText( UpdateActivity.this, "Image uploaded Successfully.....", Toast.LENGTH_SHORT ).show();
                        String expenseType = binding.expenseTypeET.getText().toString();
                        String expenseAmount = binding.updateAmount.getText().toString();
                        String expenseDate = binding.updateDate.getText().toString();
                        String expenseTime = binding.updateTime.getText().toString();
                        // String imageID = .toString().trim();
                        uriImage = downloadUri.toString();

                        Toast.makeText( getApplicationContext(), "Data Update Successfully ", Toast.LENGTH_LONG ).show();

                        ExpenseInfo expenseInfo = new ExpenseInfo( expenseType, expenseAmount, expenseDate, expenseTime, downloadUri.toString() );
                        //Picasso.with(this).load(FilePathUri).into(binding.imageView);
                        tourId = getIntent().getStringExtra( "tourId" ).toString();
                        mdatabaseReference = FirebaseDatabase.getInstance().getReference( "UserList" ).child( userID ).child( "TourInfo" ).child( tourId )
                                .child( "ExpenseList" );
                        String expenseKey = mdatabaseReference.push().getKey();
                        expenseInfo.setExpenseID( expenseKey );

                        mdatabaseReference.child( expenseKey ).setValue( expenseInfo );

                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
        //mediaPlayer.start();
    }

    private void FileChooser() {
        Intent intent = new Intent();
        intent.setType( "image/*" );
        intent.setAction( Intent.ACTION_GET_CONTENT );
        startActivityForResult( Intent.createChooser( intent, "Please Select Image" ), Image_Request_Code );

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {
            FilePathUri = data.getData();
            binding.documentIV.setImageURI( FilePathUri );
            // Picasso.with(this).load(FilePathUri).into(binding.imageView);
        }
    }

    public String GetFileExtension(Uri uri)  {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType( contentResolver.getType( uri ) );
    }
    private void openTimePicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_time_picker, null);

        Button doneBtn = view.findViewById(R.id.doneBtn);
        final TimePicker timePicker = view.findViewById(R.id.timePickerID);


        builder.setView(view);

        final Dialog dialog = builder.create();
        dialog.show();

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                dialog.dismiss();
                Time time = new Time(hour, minute, 0);
                binding.updateTime.setText(timeSDF.format(time));
            }
        });
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
                    date = dateAndTimeSDF.parse(value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long fromTime = date.getTime();
                Log.d("Date in MS", "" + fromTime);

                binding.updateDate.setText(dateSDF.format(date));


            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.show();

    }

}

