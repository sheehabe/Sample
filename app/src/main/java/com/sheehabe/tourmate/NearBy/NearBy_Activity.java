package com.sheehabe.tourmate.NearBy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sheehabe.tourmate.R;

public class NearBy_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String x,y;
   // private Object PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_near_by_ );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

   /*     FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }
        } );*/

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
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
        getMenuInflater().inflate( R.menu.near_by_, menu );
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

        if (id == R.id.atm) {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?&saddr="
                            + x
                            + ","
                            + y
                            + "&daddr=nearby atm booths "


                    ));
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.atm) {

        } else if (id == R.id.mosque) {

        } else if (id == R.id.bank) {

        } else if (id == R.id.bus) {

        }else if (id == R.id.airport) {

        }else if (id == R.id.cafe) {

        }else if (id == R.id.resturent) {

        }else if (id == R.id.hospital) {

        }else if (id == R.id.pharmacy) {

        }else if (id == R.id.police) {

        }else if (id == R.id.train) {

        }else if (id == R.id.hotel) {

        }else if (id == R.id.atm) {

        }else if (id == R.id.atm) {

        }else if (id == R.id.atm) {

        }else if (id == R.id.nav_share) {

        } else if (id == R.id.memory) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    public void atmIV(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby atm booths "


                ));
        startActivity(intent);
    }

    public void atmTV(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby atm booths "


                ));
        startActivity(intent);
    }

    public void mosqueIV(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby mosque "


                ));
        startActivity(intent);
    }

    public void mosqueTV(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby mosque "


                ));
        startActivity(intent);
    }

    public void bankIV(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby banks "
                ));
        startActivity(intent);


    }

    public void bankTV(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby banks "
                ));
        startActivity(intent);


    }

    public void busIV(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby busstand "


                ));
        startActivity(intent);
    }

    public void busTV(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby busstand "


                ));
        startActivity(intent);
    }

    public void airportIV(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby airport "


                ));
        startActivity(intent);
    }

    public void airportTV(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby airport "


                ));
        startActivity(intent);
    }

    public void cafeIV(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby cafe "


                ));
        startActivity(intent);

    }

    public void cafeTV(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby cafe "


                ));
        startActivity(intent);
    }

    public void resturentIV(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby restrurent "


                ));
        startActivity(intent);
    }

    public void resturentTV(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby restrurent "


                ));
        startActivity(intent);
    }

    public void hospitalIV(View view) {

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby hospitals"

                ));
        startActivity(intent);

        //Intent intent = new Intent(MainActivity.this,MapsActivity.class);
        //startActivity(intent);
    }

    public void hospitalTV(View view) {

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby hospitals"

                ));
        startActivity(intent);

        //Intent intent = new Intent(MainActivity.this,MapsActivity.class);
        //startActivity(intent);
    }

    public void pharmacyIV(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby pharmacy "


                ));
        startActivity(intent);
    }

    public void pharmacyTV(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby pharmacy "


                ));
        startActivity(intent);
    }

    public void policeIV(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby police "


                ));
        startActivity(intent);
    }

    public void policeTV(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby police "


                ));
        startActivity(intent);
    }

    public void trainIV(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby trains "


                ));
        startActivity(intent);
    }

    public void trainTV(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby trains "


                ));
        startActivity(intent);
    }

    public void hotelIV(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby hotels "


                ));
        startActivity(intent);
    }

    public void hotelTV(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&saddr="
                        + x
                        + ","
                        + y
                        + "&daddr=nearby hotels "


                ));
        startActivity(intent);
    }
}


