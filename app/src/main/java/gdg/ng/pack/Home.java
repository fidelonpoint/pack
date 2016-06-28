package gdg.ng.pack;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import gdg.ng.pack.feeds.FeedAdapter;
import gdg.ng.pack.feeds.Feeditem;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    GridView gridview;
   FeedAdapter gridviewAdapter;
    ArrayList<Feeditem> data = new ArrayList<Feeditem>();
    ImageView support;
    int counter;
    private String [] items = {"Akwa Ibom", "Delta","Rivers"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // getting a reference to the gridview
        gridview = (GridView) findViewById(R.id.gridView1);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        data.add(new Feeditem("httpp","Na wa for this kind Ministry of health","#13 Ikpa road","12hrs ago", "23 Support","2,000",R.drawable.dirt));
        data.add(new Feeditem("httpp","Na wa for this kind Ministry of health","#13 Ikpa road","12hrs ago", "23 Support","2,000",R.drawable.dirt));
        data.add(new Feeditem("httpp","Na wa for this kind Ministry of health","#13 Ikpa road","12hrs ago", "23 Support","2,000",R.drawable.dirt));
        data.add(new Feeditem("httpp","Na wa for this kind Ministry of health","#13 Ikpa road","12hrs ago", "23 Support","2,000",R.drawable.dirt));
        data.add(new Feeditem("httpp","Na wa for this kind Ministry of health","#13 Ikpa road","12hrs ago", "23 Support","2,000",R.drawable.dirt));

    setDataAdapter();
    }

    // this method binds the dummy data to the adapter
    private void setDataAdapter() {
        gridviewAdapter = new FeedAdapter(this, R.layout.feeds_item, data);
        gridview.setAdapter(gridviewAdapter);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
       // MenuItem item = menu.findItem(R.id.toolbar_spinner);

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
            startActivity(new Intent(Home.this, SettingsActivity.class));

            return true;
        }
        else if (id == R.id.categories){
            startActivity(new Intent(Home.this,Manage_caterories.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_report) {
            startActivity(new Intent(Home.this, MyReport.class));
        } else if (id == R.id.home) {
            startActivity(new Intent(this, Home.class));

        } else if (id == R.id.profile) {
            startActivity(new Intent(this, ProfileActivity.class));

        } else if (id == R.id.wastelocation) {
            // Intent for waste Location goes here
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
