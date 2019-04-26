package com.sheehabe.tourmate;

import android.content.ContentResolver;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.sheehabe.tourmate.databinding.ActivitySignUpBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage storage;
    private String uri;

    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_sign_up );


        final MediaPlayer mediaPlayer = MediaPlayer.create( SignUpActivity.this, R.raw.clicksound );
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        binding.cameraIV.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*   Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);*/
                Intent intent1 = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
                startActivityForResult( intent1, 1 );
                intent1.setType( "image/*" );
            }
        } );

        binding.signUpBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = binding.firstNameTV.getText().toString();
                String lastName = binding.lastNameTV.getText().toString();
                String email = binding.emailET2.getText().toString();
                String mobile = binding.mobileTV.getText().toString();
                String password = binding.passwordET2.getText().toString();
                String confirmPassword = binding.confirmPasswordET.getText().toString();
                //imageView.setImageResource(CurrentUser.getImage());
                //String uri = cameraIV.setImageResource( ).toString();

                if (password.contains( confirmPassword )) {
                    SignUpWithEmailAndPassword( firstName, lastName, mobile, email, password, uri );
                } else {
                    Toast.makeText( SignUpActivity.this, "Sign up is not successful", Toast.LENGTH_SHORT ).show();
                }
                mediaPlayer.start();
            }
        } );
    }


    private void SignUpWithEmailAndPassword(String firstName, String lastName, String mobile, String email, String password, String uri) {
        final Map<String, Object> UserMap = new HashMap<>();
        UserMap.put( "firstName", firstName );
        UserMap.put( "lastName", lastName );
        UserMap.put( "mobile", mobile );
        UserMap.put( "email", email );
        uri = String.valueOf( mImageUri );
        UserMap.put( "image", uri );
        firebaseAuth.createUserWithEmailAndPassword( email, password ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    String UserID = firebaseAuth.getCurrentUser().getUid();
                    UserMap.put( "UserID", UserID );
                    DatabaseReference databaseReference = firebaseDatabase.getReference().child( "UserList" ).child( UserID ).child( "UserInfo" );
                    databaseReference.setValue( UserMap );
                    Intent intent = new Intent( SignUpActivity.this, LogIn_Activity.class );
                    startActivity( intent );
                    finish();


                    //  Toast.makeText(SignUpActivity.this, "Sign Up is Successfull", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText( SignUpActivity.this, "Sign Up is Failed", Toast.LENGTH_SHORT ).show();
                }

            }
        } );


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       /* if (requestCode == 1 && resultCode == RESULT_OK) {

            Uri cameraImageURI = data.getData();
            //reference where images will be stored
            StorageReference mStorageReference = storage.getReference( "Progress Images" );
            //reference to store file
            final StorageReference cameraImageRef = mStorageReference.child( cameraImageURI.getLastPathSegment() );
            //upload to firebase storage
            cameraImageRef.putFile( cameraImageURI )
                    .addOnSuccessListener( this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            Uri downloadUri = uriTask.getResult();
                            Picasso.with(SignUpActivity.this).load(downloadUri.toString()).into(binding.cameraIV);
                        }
                    } );*/
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get( "data" );
            String image = encodeToBase64( bitmap, Bitmap.CompressFormat.JPEG, 80 );
            binding.cameraIV.setImageBitmap( bitmap );
            mImageUri = data.getData();

            //Picasso.with(this).load(mImageUri).into(cameraIV);

        }
        if (requestCode == 2) {
            mImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap( getContentResolver(), mImageUri );
                binding.cameraIV.setImageBitmap( bitmap );
            } catch (IOException e) {
                e.printStackTrace();
            }
            binding.cameraIV.setImageURI( mImageUri );

        }
    }

    String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType( contentResolver.getType( uri ) );
    }

    // decodeBase64(employee.getID());

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress( compressFormat, quality, byteArrayOS );
        return Base64.encodeToString( byteArrayOS.toByteArray(), Base64.DEFAULT );
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode( input, 0 );
        return BitmapFactory.decodeByteArray( decodedBytes, 0, decodedBytes.length );
    }

    public void back(View view) {
        super.onBackPressed();
    }


}
