package com.sheehabe.tourmate;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LogIn_Activity extends AppCompatActivity {
    private TextView nightmareTV,hintTV,SignUpTV,bottomTV,emtyTV;
    private EditText emailET,passwordET;
    private ImageView emailIV,passwordIV;
    private Button loginBtn;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_log_in_ );

        final MediaPlayer mediaPlayer=MediaPlayer.create(LogIn_Activity.this,R.raw.clicksound);

        firebaseAuth=FirebaseAuth.getInstance();

        nightmareTV=findViewById(R.id.nightmareTV);
        emailET=findViewById(R.id.emailET);
        passwordET=findViewById(R.id.passwordET);
        emailIV=findViewById(R.id.emailIV);
        passwordIV=findViewById(R.id.passwordIV);
        loginBtn =findViewById(R.id.loginBtn);
        hintTV=findViewById(R.id.hintTV);
        SignUpTV=findViewById(R.id.SignUpTV);
        bottomTV=findViewById(R.id.bottomTV);
        emtyTV=findViewById(R.id.emtyTV);

        nightmareTV.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( LogIn_Activity.this,MainActivity.class );
                startActivity( intent );
            }
        } );
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailET.getText().toString();
                String password=passwordET.getText().toString();
                if(email.equals("")||password.equals("")){
                    Toast.makeText(LogIn_Activity.this, "Please input Details", Toast.LENGTH_SHORT).show();
                }
                else{
                    logInWithEmailAndPassword(email,password);
                }
                mediaPlayer.start();
            }


        });
        SignUpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LogIn_Activity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
    private void logInWithEmailAndPassword(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){ Intent intent  = new Intent( LogIn_Activity.this,MainActivity.class );
            startActivity( intent );
            finish();

                }else {
                    Toast.makeText(LogIn_Activity.this, "Log in Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
