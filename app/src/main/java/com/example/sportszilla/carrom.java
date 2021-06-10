package com.example.sportszilla;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class carrom extends AppCompatActivity {
    EditText soloPlayerName, soloPlayerPhone;
    EditText teamPlayerName1, teamPlayerName2, teamPlayerPhone1, teamPlayerPhone2;

    String SoloPlayerName, SoloPlayerPhone, SoloClass = "", SoloHouse = "", SoloCategory = "";
    String TeamPlayerName1, TeamPlayerName2, TeamPlayerPhone1, TeamPlayerPhone2, TeamClass1 = "", TeamClass2 = "", TeamHouse1 = "", TeamHouse2 = "", TeamCategory = "";

    TextView soloCategory, soloHouse, soloClass;
    TextView teamCategory, teamClass1, teamClass2, teamHouse1, teamHouse2;

    Dialog loading_animation;
    String receipt_name;

    String Date;

    final int UPI_PAYMENT = 0;
    String Section = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrom);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        appHeader();
        tabLayout();
      /*  try{
            setupEditbox();
        }
        catch (Exception ex){
                Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_SHORT).show();
        }*/

    }

    //**************************************************************** App Header *******************************************************************//

    public void appHeader() {
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#212121"));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.carrom_app_bar);
        actionBar.setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //***********************************************************************************************************************************************//

    //**************************************************************** Tablayout *******************************************************************//
    public void tabLayout() {

        TabLayout tablayout = (TabLayout) findViewById(R.id.carrom_tabs);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.carrom_appbar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.carrom_viewpager);
        viewPagerAdapter adapter = new viewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new carrom_solo(), "Solo");
        adapter.AddFragment(new carrom_team(), "Team");
        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);
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

    //**************************************************************** Drop Down *******************************************************************//
    public void category_carrom_solo(View view) {
        AppCompatButton ok;
        Dialog dialog;

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_gender_layout);
        dialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable((R.drawable.popup_layout_design)));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        soloCategory = (TextView) findViewById(R.id.carrom_category_solo);
        ok = dialog.findViewById(R.id.gender_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup category_group = (RadioGroup) dialog.findViewById(R.id.gender_category);
                int gender_selected_id = category_group.getCheckedRadioButtonId();

                RadioButton gender_selected = (RadioButton) dialog.findViewById(gender_selected_id);
                String Gender = (String) gender_selected.getText();
                SoloCategory = Gender;
                soloCategory.setText(Gender);
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void select_house_carrom_solo(View view) {
        AppCompatButton ok;

        Dialog dialog;

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_house_layout);
        dialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable((R.drawable.popup_layout_design)));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        soloHouse = (TextView) findViewById(R.id.carrom_house_solo);
        ok = dialog.findViewById(R.id.house_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup house_group = (RadioGroup) dialog.findViewById(R.id.house_category);
                int house_selected_id = house_group.getCheckedRadioButtonId();

                RadioButton house_selected = (RadioButton) dialog.findViewById(house_selected_id);
                String House = (String) house_selected.getText();
                soloHouse.setText(House);
                SoloHouse = House;
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void select_class_carrom_solo(View view) {
        Dialog dialog;
        AppCompatButton ok;


        dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_class_layout);
        dialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable((R.drawable.popup_layout_design)));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        soloClass = (TextView) findViewById(R.id.carrom_class);
        ok = dialog.findViewById(R.id.class_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup class_group = (RadioGroup) dialog.findViewById(R.id.class_category);
                int class_selected_id = class_group.getCheckedRadioButtonId();

                RadioButton class_selected = (RadioButton) dialog.findViewById(class_selected_id);
                String Class_value = (String) class_selected.getText();
                SoloClass = Class_value;
                soloClass.setText(Class_value);
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    //***********************************************************************************************************************************************//

    //************************************************************ Setup EditText******************************************************************//
    public void setupEditbox(){


        soloPlayerName = (EditText) findViewById(R.id.carrom_single_player_name);
        soloPlayerPhone = (EditText) findViewById(R.id.carrom_single_player_phone);

        teamPlayerName1 = (EditText) findViewById(R.id.carrom_first_player_name);
        teamPlayerPhone1 = (EditText) findViewById(R.id.carrom_first_player_phone);
        teamPlayerName2 = (EditText) findViewById(R.id.carrom_second_player_name);
        teamPlayerPhone2 = (EditText) findViewById(R.id.carrom_second_player_phone);

       /* onEditBoxResponse(soloPlayerName);
        onEditBoxResponse(soloPlayerPhone);*/
      /*  onEditBoxResponse(teamPlayerName1);
        onEditBoxResponse(teamPlayerPhone1);
        onEditBoxResponse(teamPlayerName2);
        onEditBoxResponse(teamPlayerPhone2);*/




    }
    public void onEditBoxResponse(EditText editText){
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus){
                    editText.setBackground(getDrawable(R.drawable.oneditboxresponse));
                }
                else {
                    editText.setBackground(getDrawable(R.drawable.edittext_bg));
                }
            }
        });





    }









    //************************************************************************************************************************************************//


    //********************************************************************* Team Drop Down *****************************************************************//


    public void category_carrom_team(View view) {
        AppCompatButton ok;

        Dialog dialog;

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_gender_layout);
        dialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable((R.drawable.popup_layout_design)));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        teamCategory = (TextView) findViewById(R.id.carrom_category_team);
        ok = dialog.findViewById(R.id.gender_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup category_group = (RadioGroup) dialog.findViewById(R.id.gender_category);
                int gender_selected_id = category_group.getCheckedRadioButtonId();

                RadioButton gender_selected = (RadioButton) dialog.findViewById(gender_selected_id);
                String Gender = (String) gender_selected.getText();
                teamCategory.setText(Gender);
                TeamCategory = Gender;

                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void select_class_carrom_player1(View view) {
        Dialog dialog;
        AppCompatButton ok;


        dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_class_layout);
        dialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable((R.drawable.popup_layout_design)));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        teamClass1 = (TextView) findViewById(R.id.carrom_class_1);
        ok = dialog.findViewById(R.id.class_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup class_group = (RadioGroup) dialog.findViewById(R.id.class_category);
                int class_selected_id = class_group.getCheckedRadioButtonId();
                RadioButton class_selected = (RadioButton) dialog.findViewById(class_selected_id);
                String Class1 = (String) class_selected.getText();
                teamClass1.setText(Class1);
                TeamClass1 = Class1;
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void select_class_carrom_player2(View view) {
        Dialog dialog;
        AppCompatButton ok;


        dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_class_layout);
        dialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable((R.drawable.popup_layout_design)));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        teamClass2 = (TextView) findViewById(R.id.carrom_class_2);
        ok = dialog.findViewById(R.id.class_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup class_group = (RadioGroup) dialog.findViewById(R.id.class_category);
                int class_selected_id = class_group.getCheckedRadioButtonId();

                RadioButton class_selected = (RadioButton) dialog.findViewById(class_selected_id);
                String Class2 = (String) class_selected.getText();
                teamClass2.setText(Class2);
                TeamClass2 = Class2;
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void select_house_carrom_player1(View view) {
        AppCompatButton ok;

        Dialog dialog;

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_house_layout);
        dialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable((R.drawable.popup_layout_design)));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        teamHouse1 = (TextView) findViewById(R.id.carrom_house_1);
        ok = dialog.findViewById(R.id.house_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup house_group = (RadioGroup) dialog.findViewById(R.id.house_category);
                int house_selected_id = house_group.getCheckedRadioButtonId();

                RadioButton house_selected = (RadioButton) dialog.findViewById(house_selected_id);
                String House = (String) house_selected.getText();
                teamHouse1.setText(House);
                TeamHouse1 = House;
                dialog.dismiss();
            }
        });
        dialog.show();

    }

  /*  public void select_house_carrom_player2(View view) {
        AppCompatButton ok;

        Dialog dialog;

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_house_layout);
        dialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable((R.drawable.popup_layout_design)));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
      //  teamHouse2 = (TextView) findViewById(R.id.carrom_house_2);
        ok = dialog.findViewById(R.id.house_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup house_group = (RadioGroup) dialog.findViewById(R.id.house_category);
                int house_selected_id = house_group.getCheckedRadioButtonId();

                RadioButton house_selected = (RadioButton) dialog.findViewById(house_selected_id);
                String House = (String) house_selected.getText();
                teamHouse2.setText(House);
                TeamHouse2 = House;
                dialog.dismiss();
            }
        });
        dialog.show();

    }*/

    //*********************************************************************** Receipt pdf **********************************************************************************//
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void receiptPdf(String paymentMode, String paymentStatus) {

        Date date = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date = simpleDateFormat.format(date);

        Bitmap bmp, scalebmp;
        int pageWidth = 1200, pageHeight = 1550;
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.carrom_r);
        scalebmp = Bitmap.createScaledBitmap(bmp, 1200, 600, false);
        //Creating pdf
        PdfDocument mypdf = new PdfDocument();
        PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
        PdfDocument.Page myPage1 = mypdf.startPage(myPageInfo1);
        Canvas canvas = myPage1.getCanvas();


        //Adding title image
        Paint titleImage = new Paint();
        canvas.drawBitmap(scalebmp, 0, 0, titleImage);

        // creating table

        Paint table_lines = new Paint();
        table_lines.setStyle(Paint.Style.STROKE);
        table_lines.setStrokeWidth(4f);
        canvas.drawRect(20, 720, pageWidth - 20, 820, table_lines);

        Paint table_heading = new Paint();
        table_heading.setStyle(Paint.Style.FILL);
        table_heading.setTextSize(35);
        table_heading.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint lineWidth = new Paint();
        lineWidth.setStrokeWidth(3f);

        // Table heading

        canvas.drawText("Name", 40, 780, table_heading);
        canvas.drawText("Class", 410, 780, table_heading);
        canvas.drawText("House", 660, 780, table_heading);
        canvas.drawText("Category", 940, 780, table_heading);

        //Lines
        canvas.drawLine(380, 740, 380, 800, lineWidth);
        canvas.drawLine(630, 740, 630, 800, lineWidth);
        canvas.drawLine(910, 740, 910, 800, lineWidth);

        //Details

        Paint data = new Paint();
        data.setTextSize(34);
        if (Section.equals("Solo")) {
            receipt_name = SoloPlayerName + "_" + SoloPlayerPhone + "_" + "carrom.pdf";
            canvas.drawText(SoloPlayerName, 40, 900, data);
            canvas.drawText(SoloClass, 410, 900, data);
            canvas.drawText(SoloHouse, 660, 900, data);
            canvas.drawText(SoloCategory, 940, 900, data);
        } else if (Section.equals("Team")) {
            receipt_name = TeamPlayerName1 + "_" + TeamPlayerPhone1 + "_" + "carrom.pdf";
            canvas.drawText(TeamPlayerName1, 40, 900, data);
            canvas.drawText(TeamClass1, 410, 900, data);
            canvas.drawText(TeamHouse1, 660, 900, data);
            canvas.drawText(TeamCategory, 940, 900, data);

            canvas.drawText(TeamPlayerName2, 40, 960, data);
            canvas.drawText(TeamClass2, 410, 960, data);
            canvas.drawText(TeamHouse1, 660, 960, data);
            canvas.drawText(TeamCategory, 940, 960, data);

        }


        Paint data_heading = new Paint();
        data_heading.setTextSize(34);
        data_heading.setTextAlign(Paint.Align.RIGHT);
        data.setTextAlign(Paint.Align.RIGHT);
        data_heading.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        //Registration date
        canvas.drawText("Registration date", pageWidth - 20, 1130, data_heading);
        canvas.drawText(Date, pageWidth - 50, 1180, data);

        //  Payment Details
        data_heading.setTextAlign(Paint.Align.LEFT);
        data.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Payment mode", 20, 1130, data_heading);
        canvas.drawText(paymentMode, 70, 1180, data);
        canvas.drawText("Payment status", 20, 1260, data_heading);
        canvas.drawText(paymentStatus, 70, 1310, data);

        //wish
        Paint Wish = new Paint();
        Wish.setTextAlign(Paint.Align.CENTER);
        Wish.setTextSize(37);
        Wish.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("All the best", pageWidth / 2, 1500, Wish);


        mypdf.finishPage(myPage1);
        //writing data to file
        File file = new File(getExternalFilesDir(null), receipt_name);
        try {
            mypdf.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mypdf.close();
        if (Section.equals("Solo")) {
            soloPlayerPhone.getText().clear();
            soloPlayerName.getText().clear();
            SoloCategory = "";
            SoloHouse = "";
            SoloClass = "";
            soloClass.setText("");
            soloHouse.setText("");
            soloCategory.setText("");
        } else if (Section.equals("Team")) {
            teamPlayerName1.getText().clear();
            teamPlayerName2.getText().clear();
            teamPlayerPhone1.getText().clear();
            teamPlayerPhone2.getText().clear();

            TeamCategory = "";
            TeamClass1 = "";
            TeamClass2 = "";
            TeamHouse1 = "";
            //  TeamHouse2="";

            teamCategory.setText("");
            teamClass1.setText("");
            teamClass2.setText("");
            teamHouse1.setText("");
            // teamHouse2.setText("");


        }


        openPdf();


    }
    //************************************************************************************************************************************************************//

    //***************************************************************** open pdf *********************************************************************************//
    public void openPdf() {
        File openPdf = new File((getExternalFilesDir(null)), receipt_name);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri apkURI = FileProvider.getUriForFile(getApplicationContext(),
                getApplicationContext().getPackageName(), openPdf);

        MimeTypeMap myMime = MimeTypeMap.getSingleton();
        String mimeType = myMime.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(apkURI.toString()));//It will return the mimetype
        intent.setDataAndType(apkURI, mimeType);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);

    }

    //********************************************************************************************************************************************************************//


    //*********************************************************** send data to sheet **************************************************************//

    //  Solo
    public void sendData(String category, String Name, String Phone, String Class, String House, String Paymentmode, String Paymentstatus) {
        String Category = "Solo" + category;
        Toast.makeText(carrom.this, "Verifying data please wait", Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = null;

        stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbyHp-PMHlslg3oA6UCsuW2qR1EThQEuYzs6bgGqZUf4N0HToQz8wCv_/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(carrom.this,response.toString(),Toast.LENGTH_SHORT).show();
                        loadingAnimation(Paymentmode, Paymentstatus);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(carrom.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();
                parmas.put("action", Category);
                parmas.put("Name", Name);
                parmas.put("Phone", Phone);
                parmas.put("Class", Class);
                parmas.put("House", House);
                parmas.put("Paymentmode", Paymentmode);
                parmas.put("Paymentstatus", Paymentstatus);
                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }

    // Team
    public void sendDataTeam(String category, String Player1Name, String Player1Phone, String Class1, String House1, String Player2Name, String Player2Phone, String Class2, String Paymentmode, String Paymentstatus) {
        String Category = "Team" + category;
        Toast.makeText(carrom.this, "Verifying data please wait", Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = null;

        stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbyHp-PMHlslg3oA6UCsuW2qR1EThQEuYzs6bgGqZUf4N0HToQz8wCv_/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(carrom.this,response.toString(),Toast.LENGTH_SHORT).show();
                        loadingAnimation(Paymentmode, Paymentstatus);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(carrom.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();
                parmas.put("action", Category);
                parmas.put("Player1Name", Player1Name);
                parmas.put("Player1Phone", Player1Phone);
                parmas.put("Class1", Class1);
                parmas.put("House1", House1);
                parmas.put("Player2Name", Player2Name);
                parmas.put("Player2Phone", Player2Phone);
                parmas.put("Class2", Class2);
                //  parmas.put("House2", House2);
                parmas.put("Paymentmode", Paymentmode);
                parmas.put("Paymentstatus", Paymentstatus);
                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }


    //***********************************************************************************************************************************************//


    //***********************************************************************************************************************************************//

    //************************************************************ validation ************************************************************************//
    public boolean soloValidateDetails() {
        validation vali = new validation();
        // EditText
        soloPlayerName = (EditText) findViewById(R.id.carrom_single_player_name);
        soloPlayerPhone = (EditText) findViewById(R.id.carrom_single_player_phone);

        // fetching value
        SoloPlayerName = soloPlayerName.getText().toString();
        SoloPlayerPhone = soloPlayerPhone.getText().toString();

        // validation
        if (SoloPlayerName.isEmpty()) {
            vali.Msg(getApplicationContext(), "Please enter name");
            return false;
        } else if (vali.isNumeric(SoloPlayerName) || vali.specialCharacter(SoloPlayerName)) {
            vali.Msg(getApplicationContext(), "Please enter valid name");
            return false;
        } else if (SoloPlayerPhone.isEmpty()) {
            vali.Msg(getApplicationContext(), "Please enter phone no");
            return false;
        } else if (!vali.validatePhone(SoloPlayerPhone)) {
            //  String length=""+playerPhone.length();
            vali.Msg(getApplicationContext(), "Please enter valid phone no");
            return false;

        } else if (SoloClass.equals("")) {
            vali.Msg(getApplicationContext(), "Please select class");
            return false;

        } else if (SoloHouse.equals("")) {
            vali.Msg(getApplicationContext(), "Please select house");
            return false;
        } else if (SoloCategory.equals("")) {
            vali.Msg(getApplicationContext(), "Please select category");
            return false;
        } else {

            return true;
        }


    }

    public boolean teamValidateDetails() {
        validation vali = new validation();
        // EditText

        teamPlayerName1 = (EditText) findViewById(R.id.carrom_first_player_name);
        teamPlayerPhone1 = (EditText) findViewById(R.id.carrom_first_player_phone);
        teamPlayerName2 = (EditText) findViewById(R.id.carrom_second_player_name);
        teamPlayerPhone2 = (EditText) findViewById(R.id.carrom_second_player_phone);

        // fetching value
        TeamPlayerName1 = teamPlayerName1.getText().toString();
        TeamPlayerPhone1 = teamPlayerPhone1.getText().toString();
        TeamPlayerName2 = teamPlayerName2.getText().toString();
        TeamPlayerPhone2 = teamPlayerPhone2.getText().toString();

        // validation
        if (TeamPlayerName1.isEmpty()) {
            vali.Msg(getApplicationContext(), "Please enter your name");
            return false;
        } else if (vali.isNumeric(TeamPlayerName1) || vali.specialCharacter(TeamPlayerName1)) {
            vali.Msg(getApplicationContext(), "Please enter valid name");
            return false;
        } else if (TeamPlayerPhone1.isEmpty()) {
            vali.Msg(getApplicationContext(), "Please enter your phone no");
            return false;
        } else if (!vali.validatePhone(TeamPlayerPhone1)) {
            //  String length=""+playerPhone.length();
            vali.Msg(getApplicationContext(), "Please enter valid phone no");
            return false;

        }/* else if (TeamClass1.equals("")) {
            vali.Msg(getApplicationContext(), "Please select class");
            return false;

        } else if (TeamHouse1.equals("")) {
            vali.Msg(getApplicationContext(), "Please select house");
            return false;
        } */ else if (TeamPlayerName2.isEmpty()) {
            vali.Msg(getApplicationContext(), "Please enter your name");
            return false;
        } else if (vali.isNumeric(TeamPlayerName2) || vali.specialCharacter(TeamPlayerName2)) {
            vali.Msg(getApplicationContext(), "Please enter valid name");
            return false;
        } else if (TeamPlayerPhone2.isEmpty()) {
            vali.Msg(getApplicationContext(), "Please enter phone no");
            return false;
        } else if (!vali.validatePhone(TeamPlayerPhone2)) {
            vali.Msg(getApplicationContext(), "Please enter valid phone no");
            return false;
        } else if (TeamClass2.equals("")) {
            vali.Msg(getApplicationContext(), "Please select class");
            return false;
        }/* else if (TeamHouse2.equals("")) {
            vali.Msg(getApplicationContext(), "Please select house");
            return false;
        }*/ else if (TeamCategory.equals("")) {
            vali.Msg(getApplicationContext(), "Please select category");
            return false;

        } else if (TeamHouse1.equals("")) {
            vali.Msg(getApplicationContext(), "Please select house");
            return false;
        } else {

            return true;
        }


    }


    //*********************************************************************** Animation *******************************************************************************//
    public void loadingAnimation(String paymentMode, String paymentStatus) {
        AppCompatButton ok;
        loading_animation = new Dialog(this);
        loading_animation.setContentView(R.layout.loading);
        loading_animation.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loading_animation.getWindow().setBackgroundDrawable(getDrawable((R.drawable.popup_layout_design)));
        }
        loading_animation.getWindow().getAttributes().windowAnimations = R.style.animation;
        ok = loading_animation.findViewById(R.id.loading_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading_animation.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    receiptPdf(paymentMode, paymentStatus);

                }
                openPdf();
            }
        });
        loading_animation.show();


    }

    //************************************************************************************************************************************************//


    //**************************************************************** Online Payment *******************************************************************//

    public void payOnline() {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", "bogatiradhika132@okhdfcbank")
                //.appendQueryParameter("pa", "8454038760@paytm")
                .appendQueryParameter("pn", "Ashwin")
                .appendQueryParameter("mc", "")
                .appendQueryParameter("tid", "02125412")
                .appendQueryParameter("tr", "25584584")
                .appendQueryParameter("tn", "Sprotszilla")
                .appendQueryParameter("am", "1")
                .appendQueryParameter("cu", "INR")
                //.appendQueryParameter("refUrl", "blueapp")
                .build();
        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
        if (null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(carrom.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("main ", "response " + resultCode);
        /*
       E/main: response -1
       E/UPI: onActivityResult: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPIPAY: upiPaymentDataOperation: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPI: payment successfull: 922118921612
         */
        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.e("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.e("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    //when user simply back without payment
                    Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        String str = data.get(0);
        Log.e("UPIPAY", "upiPaymentDataOperation: " + str);
        String paymentCancel = "";
        if (str == null) str = "discard";
        String status = "";
        String approvalRefNo = "";
        String response[] = str.split("&");
        for (int i = 0; i < response.length; i++) {
            String equalStr[] = response[i].split("=");
            if (equalStr.length >= 2) {
                if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                    status = equalStr[1].toLowerCase();
                } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                    approvalRefNo = equalStr[1];
                }
            } else {
                paymentCancel = "Payment cancelled by user.";
            }
        }

        if (status.equals("success")) {
            //Code to handle successful transaction here.

            //   Toast.makeText(carrom.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
            if (Section.equals("Solo")) {
                sendData(SoloCategory, SoloPlayerName, SoloPlayerPhone, SoloClass, SoloHouse, "Online", "Paid");
            } else if (Section.equals("Team")) {

                sendDataTeam(TeamCategory, TeamPlayerName1, TeamPlayerPhone1, TeamClass1, TeamHouse1, TeamPlayerName2, TeamPlayerPhone2, TeamClass2, "Online", "Paid");
            }

            Log.e("UPI", "payment successfull: " + approvalRefNo);
        } else if ("Payment cancelled by user.".equals(paymentCancel)) {
            Toast.makeText(carrom.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            Log.e("UPI", "Cancelled by user: " + approvalRefNo);

        } else {
            Toast.makeText(carrom.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            Log.e("UPI", "failed payment: " + approvalRefNo);

        }
    }


    //***********************************************************************************************************************************************//


//*************************************************************** Solo Button ***********************************************************************//

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void carrom_offlinePayment_solo(View view) {
        if (soloValidateDetails()) {
            Section = "Solo";
            sendData(SoloCategory, SoloPlayerName, SoloPlayerPhone, SoloClass, SoloHouse, "Offline", "Unpaid");

        }


    }

    public void carrom_onlinePayment_solo(View view) {

        if (soloValidateDetails()) {
            Section = "Solo";
            payOnline();


        }

    }


//****************************************************************************************************************************************************//


//***************************************************************** Team Button **********************************************************************//

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void offlinepayment_carrom_team(View view) {
        if (teamValidateDetails()) {
            Section = "Team";
            sendDataTeam(TeamCategory, TeamPlayerName1, TeamPlayerPhone1, TeamClass1, TeamHouse1, TeamPlayerName2, TeamPlayerPhone2, TeamClass2, "Offline", "unPaid");

            // Toast.makeText(carrom.this, "Registration done", Toast.LENGTH_SHORT).show();
        }

    }

    public void onlinepayment_carrom_team(View view) {
        Section = "Team";
        if (teamValidateDetails()) {

            payOnline();

            // Toast.makeText(carrom.this, "Registration done", Toast.LENGTH_SHORT).show();
        }


    }

//*********************************************************************************************************************************************************//
}