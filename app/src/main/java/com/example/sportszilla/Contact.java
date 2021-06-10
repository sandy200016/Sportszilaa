package com.example.sportszilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Contact extends AppCompatActivity {
    RecyclerView recyclerView;
    contactAdapter recyclerAdapter;
    ItemTouchHelper.SimpleCallback simpleCallback;
    List<contactList> phoneList;
    SearchView searchView;
    String number;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        removeData();
        appHeader();
        phoneData();
        initRecyclerView();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        context=getApplicationContext();
       // whatsApp("8898494581");
    }


    public void appHeader() {
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#212121"));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.contact_appbar);
        actionBar.setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void phoneData() {
        phoneList = new ArrayList<>();
        phoneList.add(new contactList("Cricket", "9967782459","Santosh"));
        phoneList.add(new contactList("Football", "9967782459","Ashwin"));
        phoneList.add(new contactList("Chess", "8898494581","Radhika"));
        phoneList.add(new contactList("Carrom", "9967782459","Hemant"));

    }

    public void initRecyclerView() {
        recyclerAdapter = new contactAdapter(phoneList);
        recyclerView.setAdapter(recyclerAdapter);

    }

    public void removeData() {


        simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosistion = viewHolder.getAdapterPosition();
                int toPosistion = target.getAdapterPosition();
                Collections.swap(phoneList, fromPosistion, toPosistion);
                recyclerView.getAdapter().notifyItemMoved(fromPosistion, toPosistion);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                contactList Number = phoneList.get(position);
                number = Number.getPhoneNo();

              /*  contactList Number = phoneList.get(position);
                number = Number.getPhoneNo();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + number));
                startActivity(callIntent);
                phoneData();
                initRecyclerView();*/
                switch(direction){

                    case ItemTouchHelper.RIGHT:

                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:" + number));
                        startActivity(callIntent);
                        phoneData();
                        initRecyclerView();
                        break;
                    case ItemTouchHelper.LEFT:
                        whatsApp(number);
                        phoneData();
                        initRecyclerView();
                        break;




                }

            }

            //RecyclerViewSwipeDecorator
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(Contact.this, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                         .addSwipeRightBackgroundColor(ContextCompat.getColor(Contact.this,R.color.blue))
                         .addSwipeRightActionIcon(R.drawable.addphone)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(Contact.this,R.color.backgroundColor))
                        .addSwipeLeftActionIcon(R.drawable.whatsapp_icon)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_menu);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.getFilter().filter(newText.toString());
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    public void whatsApp(String number){

        PackageManager packageManager= context.getPackageManager();
        Intent intent=new Intent(Intent.ACTION_VIEW);
        try{
            String url="https://api.whatsapp.com/send?phone="+"+91"+ number + "&text="+ URLEncoder.encode("Hi","UTF-8");
            intent.setPackage("com.whatsapp");
            intent.setData(Uri.parse(url));
            if(intent.resolveActivity(packageManager)!=null){
                 startActivity(intent);
            }


        }
        catch (Exception e){


        }


    }


}