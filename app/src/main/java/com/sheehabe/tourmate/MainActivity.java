package com.sheehabe.tourmate;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.sheehabe.tourmate.NearBy.NearBy_Activity;
import com.sheehabe.tourmate.databinding.ActivityMainBinding;
import com.sheehabe.tourmate.pojo.TourInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Context context;
    public static MainActivity instance = null;
    private ActivityMainBinding binding;
    private List<TourInfo> userlist;
    private RecyclerView mainRecylerView;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private BottomNavigationView bottom_navigation;
    public String tourId;
    private FloatingActionButton newtrip;


    public static MainActivity getInstance() {
        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        context = this;
        instance = this;
        binding = DataBindingUtil.setContentView( this, R.layout.activity_main );

        newtrip = findViewById( R.id.newtrip );
        //  tourId = getIntent().getStringExtra( "tourId" ).toString();
        //initRecyclerView();
        //getDataFromDB();
//        firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = firebaseAuth.getCurrentUser();

        userlist = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();

        newtrip.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, AddTripActivity.class );
                startActivity( intent );
            }
        } );
//        mainRecylerView.setLayoutManager( new LinearLayoutManager( this ) );
//        TourAdapter tourAdapter = new TourAdapter( this, userlist );
//        mainRecylerView.setAdapter( tourAdapter );

        bottom_navigation = findViewById( R.id.bottom_navigation );
        //TextView textView= findViewById( R.id.textView );
        bottom_navigation.setOnNavigationItemSelectedListener( mOnNavigationItemSelectedListener );

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        //textView.setText("Welcome " + user.getEmail());


        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
        replaceFragment( new TripsFragment() );
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent = new Intent( MainActivity.this, MainActivity.class );
            startActivity( intent );
            // Handle the camera action
        } else if (id == R.id.trip) {
            Intent intent = new Intent( MainActivity.this, AddTripActivity.class );
            startActivity( intent );

        } else if (id == R.id.budget) {
            Intent intent = new Intent( MainActivity.this, BudgetActivity.class );
            startActivity( intent );

        } else if (id == R.id.near) {
            Intent intent = new Intent( MainActivity.this, NearBy_Activity.class );
            startActivity( intent );

        } else if (id == R.id.weather) {
            Intent intent = new Intent( MainActivity.this, WeatherActivity.class );
            startActivity( intent );

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.memory) {
            Intent intent = new Intent( this, ImageActivity.class );

            startActivity( intent );

        } else if (id == R.id.logout) {

            firebaseAuth.signOut();

            startActivity( new Intent( MainActivity.this, LogIn_Activity.class ) );
            finish();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {


                case R.id.trips:

                    replaceFragment( new TripsFragment() );
                    return true;
                case R.id.memories:

                    replaceFragment( new MemoriesFragment() );
                    return true;
                case R.id.wallet:
                    Intent intent = new Intent();

                    replaceFragment( new WalletFragment() );
                    return true;
            }
            return false;
        }
    };

    private void replaceFragment(Fragment fragment) {
        getIntent().putExtra( "tourId", "tourId" );
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace( R.id.fragment_container, fragment );
        ft.commit();


    }


}
