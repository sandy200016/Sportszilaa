package com.example.sportszilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mtoggle;
    NavigationView navigationView;
    private TabLayout tablayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
      //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        appHeader();
        //Navigation panel
        navigationPanel();
        //Tablayout
        tabLayout();
    }

    //Navigation Menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.helpline_number) {
          //  Toast.makeText(this, "Help Line no", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(MainActivity.this,Contact.class);
            startActivity(intent);

        }

        if (id == R.id.about_us) {

            Toast.makeText(this, "About us", Toast.LENGTH_LONG).show();
        }
        if (id == R.id.timeTable) {
            Toast.makeText(this, "Timetable", Toast.LENGTH_LONG).show();
        }
        if (id == R.id.appFeedback) {
            //Toast.makeText(this, "App feed back", Toast.LENGTH_LONG).show();
            Intent intent =new Intent(MainActivity.this,feedback.class);
            startActivity(intent);
        }
        if (id == R.id.share) {
           // Toast.makeText(this, "share", Toast.LENGTH_LONG).show();
            shareApp();
        }

        mdrawerlayout.closeDrawer(GravityCompat.START);

        return true;
    }

    // onBackPress Method
    @Override
    public void onBackPressed() {
        if (mdrawerlayout.isDrawerOpen(GravityCompat.START)) {
            mdrawerlayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //setting up navigation panel
    public void navigationPanel() {
        mdrawerlayout = (DrawerLayout) findViewById(R.id.drawer);
        mtoggle = new ActionBarDrawerToggle(this, mdrawerlayout, R.string.open, R.string.close);
        mdrawerlayout.addDrawerListener(mtoggle);
       // mdrawerlayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.navigation_panel);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //setting up tablayout
    public void tabLayout() {

        tablayout = (TabLayout) findViewById(R.id.otin_tabs);
        appBarLayout = (AppBarLayout) findViewById(R.id.otin_appbar);
        viewPager = (ViewPager) findViewById(R.id.otin_viewpager);
        viewPagerAdapter adapter = new viewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new Indoor(), "Indoor");
        adapter.AddFragment(new Outdoor(), "Outdoor");
        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);

    }

    public void appHeader() {
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#212121"));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.app_bar_layout);
        actionBar.setBackgroundDrawable(colorDrawable);
    }

    public void chess(View view) {
        Intent chess_activity = new Intent(this, chess.class);
        startActivity(chess_activity);

    }

    public void table_tennis(View view) {
        Intent activity = new Intent(this, table_tennis.class);
        startActivity(activity);
    }
    public void football(View view){
        Intent activity = new Intent(this, football.class);
        startActivity(activity);
    }

    public void cricket(View view) {
        Intent activity = new Intent(this, cricket.class);
        startActivity(activity);

    }
    public void kho_kho(View view){
        Intent activity = new Intent(this, khokho.class);
        startActivity(activity);
    }

    public void kabaddi(View view) {

        Intent activity = new Intent(this, kabaddi.class);
        startActivity(activity);
    }

    public void vollyball(View view) {
        Intent activity = new Intent(this, vollyball.class);
        startActivity(activity);
    }

    public void carrom(View view) {

        Intent activity = new Intent(this, carrom.class);
        startActivity(activity);
    }
    public void track_event(View view){
        Intent activity = new Intent(this, track_events.class);
        startActivity(activity);
    }

    public void field_events(View view){
        Intent activity = new Intent(this, field_events.class);
        startActivity(activity);
    }
    public void shareApp(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("text/plain");
        //intent.putExtra(Intent.EXTRA_SUBJECT,"sportszilla");
        intent.putExtra(Intent.EXTRA_TEXT,"Hey get this app!");
        startActivity(Intent.createChooser(intent,"choose one"));
    }
}