package com.example.sportszilla;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class track_events extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_events);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        tabLayout();
        appHeader();
    }

    //**************************************************************** Tablayout *******************************************************************//
    public void tabLayout() {

        TabLayout tablayout = (TabLayout) findViewById(R.id.track_tabs);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.track_appbar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.track_viewpager);
        viewPagerAdapter adapter = new viewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new trackevent_solo(), "Solo");
        adapter.AddFragment(new trackevent_team(), "Relay");
        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);
    }
    //***********************************************************************************************************************************************//

    //**************************************************************** App Header *******************************************************************//

    public void appHeader() {
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#212121"));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.trackevents_app_bar);
        actionBar.setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //***********************************************************************************************************************************************//


    //**************************************************************** App Menu *******************************************************************//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chess_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.chess_Instruction) {
            instructionPopUp();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void instructionPopUp() {
        Dialog dialog;
        AppCompatButton ok;
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.pop_up_layout);
        dialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable((R.drawable.popup_layout_design)));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        ok = dialog.findViewById(R.id.instruction_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.show();
    }





    public void trackEvents(View view){

        AppCompatButton ok;
        TextView trackEvents;
        Dialog dialog;
        trackEvents=(TextView) findViewById(R.id.track_events);
        CheckBox race_100m,race_200m,race_400m,race_800m,marathon;
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.trackevent_dropdown);
        dialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable((R.drawable.popup_layout_design)));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        ok = dialog.findViewById(R.id.field_button);

        race_100m=(CheckBox) dialog.findViewById(R.id.race_100m);
        race_200m=(CheckBox)dialog.findViewById(R.id.race_200m);
        race_400m=(CheckBox)dialog.findViewById(R.id.race_400m);
        race_800m=(CheckBox) dialog.findViewById(R.id.race_800m);
        marathon=(CheckBox) dialog.findViewById(R.id.marathon);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String events=" ";
                if (race_100m.isChecked()){
                    events=events+race_100m.getText().toString();
                }
                if(race_200m.isChecked()){
                    events=events+ " " +race_200m.getText().toString();
                }
                if(race_400m.isChecked()){
                    events=events+" "+race_400m.getText().toString();
                }
                if(race_800m.isChecked()){
                    events=events+ " " +race_800m.getText().toString();
                }
                if(marathon.isChecked()){
                    events=events+" "+marathon.getText().toString();
                }

                trackEvents.setText(events);


                dialog.dismiss();
            }
        });
        dialog.show();

    }



    //******************************************************************* Solo Drop Down ***********************************************************************//

    public void track_category_solo(View view) {
        AppCompatButton ok;
        TextView gender;
        Dialog dialog;

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_gender_layout);
        dialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable((R.drawable.popup_layout_design)));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        gender = (TextView) findViewById(R.id.track_category_solo);
        ok = dialog.findViewById(R.id.gender_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup category_group = (RadioGroup) dialog.findViewById(R.id.gender_category);
                int gender_selected_id = category_group.getCheckedRadioButtonId();

                RadioButton gender_selected = (RadioButton) dialog.findViewById(gender_selected_id);
                String Gender = (String) gender_selected.getText();
                gender.setText(Gender);
                dialog.dismiss();
            }
        });
        dialog.show();



    }

    public void track_select_house_solo(View view) {

        AppCompatButton ok;
        TextView house;
        Dialog dialog;

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_house_layout);
        dialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable((R.drawable.popup_layout_design)));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        house = (TextView) findViewById(R.id.track_house_solo);
        ok = dialog.findViewById(R.id.house_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup house_group = (RadioGroup) dialog.findViewById(R.id.house_category);
                int house_selected_id = house_group.getCheckedRadioButtonId();

                RadioButton house_selected = (RadioButton) dialog.findViewById(house_selected_id);
                String House = (String) house_selected.getText();
                house.setText(House);
                dialog.dismiss();
            }
        });
        dialog.show();



    }

    public void track_select_class_solo(View view) {
        Dialog dialog;
        AppCompatButton ok;
        TextView Class;

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_class_layout);
        dialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable((R.drawable.popup_layout_design)));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        Class = (TextView) findViewById(R.id.track_class_solo);
        ok = dialog.findViewById(R.id.class_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup class_group = (RadioGroup) dialog.findViewById(R.id.class_category);
                int class_selected_id = class_group.getCheckedRadioButtonId();

                RadioButton class_selected = (RadioButton) dialog.findViewById(class_selected_id);
                String House = (String) class_selected.getText();
                Class.setText(House);
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    //*******************************************************************************************************************************************************//

    //****************************************************** Drop Down Team *********************************************************************************//


    public void track_category_team(View view) {
        AppCompatButton ok;
        TextView gender;
        Dialog dialog;

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_gender_layout);
        dialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable((R.drawable.popup_layout_design)));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        gender = (TextView) findViewById(R.id.track_select_category_team);
        ok = dialog.findViewById(R.id.gender_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup category_group = (RadioGroup) dialog.findViewById(R.id.gender_category);
                int gender_selected_id = category_group.getCheckedRadioButtonId();

                RadioButton gender_selected = (RadioButton) dialog.findViewById(gender_selected_id);
                String Gender = (String) gender_selected.getText();
                gender.setText(Gender);
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    public void track_house_team(View view) {

        AppCompatButton ok;
        TextView house;
        Dialog dialog;

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_house_layout);
        dialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable((R.drawable.popup_layout_design)));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        house = (TextView) findViewById(R.id.track_select_house_team);
        ok = dialog.findViewById(R.id.house_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup house_group = (RadioGroup) dialog.findViewById(R.id.house_category);
                int house_selected_id = house_group.getCheckedRadioButtonId();

                RadioButton house_selected = (RadioButton) dialog.findViewById(house_selected_id);
                String House = (String) house_selected.getText();
                house.setText(House);
                dialog.dismiss();
            }
        });
        dialog.show();

    //**********************************************************************************************************************************************************//

    }

    public void onlinepayment_track_solo(View view) {
    }

    public void offlinePayment_track_solo(View view) {
    }

    public void onlinePayment_track_team(View view){

    }
    public void offlinePayment_track_team(View view){




    }



}