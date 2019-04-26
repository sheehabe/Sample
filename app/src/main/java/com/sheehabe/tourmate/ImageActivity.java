package com.sheehabe.tourmate;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.sheehabe.tourmate.Memory.ItemsActivity;
import com.sheehabe.tourmate.databinding.ActivityImageBinding;
import com.sheehabe.tourmate.pojo.ImageInfo;
import com.sheehabe.tourmate.pojo.TourInfo;

import java.util.List;

public class ImageActivity extends AppCompatActivity {
    public static String TAG = "Tourmate";
    private ActivityImageBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    //DatabaseReference mDatabase;
    ListView tourList;
    List<ImageInfo> imageInfo;
    private List<TourInfo> tourlist;
    Context context;
    public static ImageActivity instance = null;
    Uri FilePathUri;
    String Storage_Path = "Upload Images";
    String Database_Path = "MemoryEvent";
    private StorageReference mstorageReference;
    private DatabaseReference mdatabaseReference;
    int Image_Request_Code = 7;
    private StorageTask uploadTask;
    private ProgressBar progressBar;
    private String uriImage;
    private String tourId;

    String imageID="";
    String imageName="";
    String location="";
    String imageURL="";



    public static ImageActivity getInstance(){
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        context = this;
        instance = this;

        tourId = getIntent().getStringExtra( "tourId" );

        Log.d(TAG,"ImageActivity tourId:"+tourId);
        binding = DataBindingUtil.setContentView( this, R.layout.activity_image );


        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            imageID = bundle.getString("imageID");
            imageName = bundle.getString("imageName");
            location = bundle.getString("location");
            imageURL = bundle.getString("imageURL");

            binding.imageNameET.setText( imageName );
            binding.locationET.setText( location );
           // binding.imageView.setImageResource( Integer.parseInt( imageID ) );


        }



        final MediaPlayer mediaPlayer = MediaPlayer.create( ImageActivity.this, R.raw.clicksound );
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        mstorageReference = FirebaseStorage.getInstance().getReference( Storage_Path );


        binding.gallary.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( ImageActivity.this, "This is Image Activity"+tourId, Toast.LENGTH_SHORT ).show();
                Intent intent = new Intent( ImageActivity.this, ItemsActivity.class );
                intent.putExtra( "tourId", tourId );
                startActivity( intent );
                //mediaPlayer.start();
            }
        } );
        binding.uploadImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText( ImageActivity.this, "Upload is progress.........", Toast.LENGTH_SHORT ).show();
                } else {
                    FileUpload( new ImageInfo() );
                }
                mediaPlayer.start();
            }
        } );
        binding.ChooseImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileChooser();
            }

        } );
        mediaPlayer.start();

    }

    private void FileUpload(ImageInfo imageInfo) {
        final String userID = firebaseAuth.getCurrentUser().getUid();

        // firebaseDatabase.getReference().child( "UserList" ).child( tourID ).child( "TourInfo" )


        StorageReference ref = mstorageReference.child( System.currentTimeMillis() + "." + GetFileExtension( FilePathUri ) );
        uploadTask = ref.putFile( FilePathUri )
                .addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        // Get a URL to the uploaded content
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        Uri downloadUri = uriTask.getResult();

                        Toast.makeText( ImageActivity.this, "Image uploaded Successfully.....", Toast.LENGTH_SHORT ).show();
                        String imageName = binding.imageNameET.getText().toString().trim();
                        String location = binding.locationET.getText().toString().trim();
                        // String imageID = .toString().trim();
                        uriImage = downloadUri.toString();

                        Toast.makeText( getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG ).show();

                        ImageInfo imageInfo = new ImageInfo( imageName, location, downloadUri.toString() );
                        //Picasso.with(this).load(FilePathUri).into(binding.imageView);
                        tourId = getIntent().getStringExtra( "tourId" ).toString();

                        DatabaseReference userDB = firebaseDatabase.getReference().child( "UserList" ).child( userID ).child( "TourInfo" ).child( tourId );
                        mdatabaseReference = userDB.child( "MemoryEvent" );
                              //  .child( "MemoryEvent" ).child( "ImageInfo" );
//                        DatabaseReference tdr=.getKey().
//                        final String tourID =userID..getCurrentUser().getUid();
//

                        String imageKey = mdatabaseReference.push().getKey();
                        imageInfo.setImageID( imageKey );

                        mdatabaseReference.child( imageKey ).setValue( imageInfo );


                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    }
                } )
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                } );
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
            binding.imageView.setImageURI( FilePathUri );
            // Picasso.with(this).load(FilePathUri).into(binding.imageView);
        }
    }

    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType( contentResolver.getType( uri ) );
    }

}

