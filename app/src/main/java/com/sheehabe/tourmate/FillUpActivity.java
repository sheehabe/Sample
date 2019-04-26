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
import com.sheehabe.tourmate.databinding.ActivityFillUpBinding;
import com.sheehabe.tourmate.pojo.UserInfo;

import java.util.List;

public class FillUpActivity extends AppCompatActivity {
    private ActivityFillUpBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    //DatabaseReference mDatabase;
    ListView tourList;
    List<UserInfo> userInfo;
    Uri FilePathUri;
    String Storage_Path="User Images";
    String Database_Path="User Info";
    private Context context;
    private StorageReference mstorageReference;
    private DatabaseReference mdatabaseReference;
    int Image_Request_Code = 9;
    private StorageTask uploadTask;
    private ProgressBar progressBar;
    private  String uriImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_fill_up );

        final MediaPlayer mediaPlayer = MediaPlayer.create( FillUpActivity.this, R.raw.clicksound );
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        mstorageReference = FirebaseStorage.getInstance().getReference( Storage_Path );


        binding.signUpBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uploadTask !=null&& uploadTask.isInProgress()){
                    Toast.makeText( FillUpActivity.this, "Upload is progress.........", Toast.LENGTH_SHORT ).show();
                }else{
                    FileUpload(new  UserInfo());}
                mediaPlayer.start();
            }
        } );
        binding.chooseImageBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Intent intent1 = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
                startActivityForResult( intent1, Image_Request_Code );
                intent1.setType( "image/*" );*/

                Intent intent = new Intent();
                intent.setType( "image/*" );
                intent.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( intent, "Please Select Image" ), Image_Request_Code );
                mediaPlayer.start();
            }

        } );
        mediaPlayer.start();

    }

    private void FileUpload(UserInfo userInfo) {



        final String userID = firebaseAuth.getCurrentUser().getUid();

        mdatabaseReference = FirebaseDatabase.getInstance().getReference( "UserList" ).child( userID ).child( Database_Path );

        userInfo.setUserID( userID );

        StorageReference ref=mstorageReference.child( System.currentTimeMillis()+"."+GetFileExtension(FilePathUri) );
        uploadTask=ref.putFile(FilePathUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        // Get a URL to the uploaded content
                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();

                        Toast.makeText( FillUpActivity.this, "Image uploaded Successfully.....", Toast.LENGTH_SHORT ).show();
                        String firstName = binding.firstNameTV.getText().toString().trim();
                        String lastName = binding.lastNameTV.getText().toString().trim();
                        String mobile = binding.mobileTV.getText().toString().trim();
                        String email = binding.emailET2.getText().toString().trim();
                        // String imageID = .toString().trim();
                        uriImage = downloadUri.toString();

                        Toast.makeText( getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG ).show();

                        UserInfo userInfo = new UserInfo( firstName,lastName, mobile,email, downloadUri.toString());
                        //Picasso.with(this).load(FilePathUri).into(binding.imageView);


                        Log.d( "MyImage", downloadUri.toString());

                        String userID = mdatabaseReference.push().getKey();
                        mdatabaseReference.child( userID ).setValue( userInfo );

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




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {
            FilePathUri = data.getData();
            binding.cameraIV.setImageURI( FilePathUri );
            // Picasso.with(this).load(FilePathUri).into(binding.imageView);
        }
    }

    public String GetFileExtension(Uri uri)  {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType( contentResolver.getType( uri ) );
    }

}

