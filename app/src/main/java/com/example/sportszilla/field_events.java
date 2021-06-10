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
import android.widget.CheckBox;
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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class field_events extends AppCompatActivity {
    EditText playerName, playerPhone;
    String PlayerName, PlayerPhone;
    TextView Total_price, category, house, Class;
    TextView fieldEvents;
    String Total, playerCategory = "", playerHouse = "", playerClass = "";
    int totalPrice = 0, shotPut = 10, javelinThrow = 10, DiscusThrow = 10, longJump = 10, highJump = 10;
    Dialog loading_animation;
    String receipt_name, Full_Events;
    final int UPI_PAYMENT = 0;
    String events = "", Shot_Put = "0", Long_Jump = "0", High_Jump = "0", Javelin_Throw = "0", Discus_Throw = "0";
    CheckBox shotput, highjump, javelin, discuss, longjump;
    String Date;

    String shotputUrl = "https://script.google.com/macros/s/AKfycbzlKicX1yOeRMjSyJn4Hxk0OlPEj7wFeIZ4WGk0d8XEbHeKaBhO4bjn0vt5qdqonGdptw/exec";
    String javelinthrowUrl = "https://script.google.com/macros/s/AKfycbyO5qHmZdqPL1UFJJeiUHZlSWSSIi2BNyyKf69JlmZRsbR8sZkSKlWc0s2OQZceg4D6uQ/exec";
    String discusthrowUrl = "https://script.google.com/macros/s/AKfycbzvE62-JXAzzic_slrEqq7CPo1zVutIJN8g47IpSL5x4PucBKNRWvjRAufqUDihzNoF/exec";
    String longjumpUrl = "https://script.google.com/macros/s/AKfycbxEPABif8XDSKvA8u7zpl2SeC1cd48YGe3rAxqgRfdgCfXERpISoRX--wmZ8KKnIZLz/exec";
    String HighjumpUrl = "https://script.google.com/macros/s/AKfycbw_lTxqXhSyXXGQWs504ioji_mKH-liToNPp6PX95BqX3EQZBCMBtIUiwCMxzDo09T0sA/exec";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_events);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        appHeader();
    }

    //**************************************************************** App Header *******************************************************************//

    public void appHeader() {
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#212121"));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.fieldevents_app_bar);
        actionBar.setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //***********************************************************************************************************************************************//

    //**************************************************************** Instruction Menu *******************************************************************//

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
        AppCompatButton ok;
        Dialog dialog;
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

    //***********************************************************************************************************************************************//


    //***********************************************************   Validate Details *****************************************************************************//
    public boolean validateDetails() {
        validation vali = new validation();
        playerName = (EditText) findViewById(R.id.fieldEvent_playerName);
        playerPhone = (EditText) findViewById(R.id.fieldEvent_playerPhone);

        PlayerName = playerName.getText().toString();
        PlayerPhone = playerPhone.getText().toString();

        if (PlayerPhone.isEmpty()) {
            vali.Msg(getApplicationContext(), "Please enter name");
            return false;
        } else if (vali.isNumeric(PlayerName) || vali.specialCharacter(PlayerName)) {
            vali.Msg(getApplicationContext(), "Please enter valid name");
            return false;
        } else if (PlayerPhone.isEmpty()) {
            vali.Msg(getApplicationContext(), "Please enter phone no");
            return false;
        } else if (!vali.validatePhone(PlayerPhone)) {
            vali.Msg(getApplicationContext(), "Please enter valid phone no");
            return false;
        } else if (playerClass.equals("")) {
            vali.Msg(getApplicationContext(), "Please select class");
            return false;
        } else if (playerHouse.equals("")) {
            vali.Msg(getApplicationContext(), "Please select house");
            return false;
        } else if (playerCategory.equals("")) {
            vali.Msg(getApplicationContext(), "Please select category");
            return false;
        } else if (Shot_Put.equals("0") & Long_Jump.equals("0") & High_Jump.equals("0") & Javelin_Throw.equals("0") & Discus_Throw.equals("0")) {
            vali.Msg(getApplicationContext(), "Please select event");
            return false;

        } else {

            return true;
        }

    }


    //***********************************************************************************************************************************************//


    //*******************************************************Drop Down*****************************************************************************//


    public void field_category(View view) {

        AppCompatButton ok;
        Dialog dialog;


        dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_gender_layout);
        dialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable((R.drawable.popup_layout_design)));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        category = (TextView) findViewById(R.id.field_category);
        ok = dialog.findViewById(R.id.gender_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup category_group = (RadioGroup) dialog.findViewById(R.id.gender_category);
                int gender_selected_id = category_group.getCheckedRadioButtonId();

                RadioButton gender_selected = (RadioButton) dialog.findViewById(gender_selected_id);
                String Gender = (String) gender_selected.getText();
                category.setText(Gender);
                playerCategory = Gender;
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    public void field_select_house(View view) {

        AppCompatButton ok;
        Dialog dialog;


        dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_house_layout);
        dialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable((R.drawable.popup_layout_design)));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        house = (TextView) findViewById(R.id.field_house);
        ok = dialog.findViewById(R.id.house_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup house_group = (RadioGroup) dialog.findViewById(R.id.house_category);
                int house_selected_id = house_group.getCheckedRadioButtonId();

                RadioButton house_selected = (RadioButton) dialog.findViewById(house_selected_id);
                String House = (String) house_selected.getText();
                house.setText(House);
                playerHouse = House;
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void field_select_class(View view) {
        AppCompatButton ok;

        Dialog dialog;

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_class_layout);
        dialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable((R.drawable.popup_layout_design)));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        Class = (TextView) findViewById(R.id.field_class);
        ok = dialog.findViewById(R.id.class_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup class_group = (RadioGroup) dialog.findViewById(R.id.class_category);
                int class_selected_id = class_group.getCheckedRadioButtonId();

                RadioButton class_selected = (RadioButton) dialog.findViewById(class_selected_id);
                String field_Class = (String) class_selected.getText();
                Class.setText(field_Class);
                playerClass = field_Class;
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //***********************************************************************************************************************************************//


    //******************************************************************** field events *****************************************************************//

    public void fieldEvents(View view) {

        AppCompatButton ok;

        Dialog dialog;
        Total_price = (TextView) findViewById(R.id.fieldEvent_prize);

        fieldEvents = (TextView) findViewById(R.id.field_events);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.field_event_dropdown);
        dialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable((R.drawable.popup_layout_design)));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        ok = dialog.findViewById(R.id.field_button);

        shotput = (CheckBox) dialog.findViewById(R.id.shotput);
        highjump = (CheckBox) dialog.findViewById(R.id.high_jump);
        longjump = (CheckBox) dialog.findViewById(R.id.long_jump);
        javelin = (CheckBox) dialog.findViewById(R.id.javelin_throw);
        discuss = (CheckBox) dialog.findViewById(R.id.discuss_throw);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (shotput.isChecked()) {
                    events = events + shotput.getText().toString();
                    totalPrice = totalPrice + shotPut;
                    Shot_Put = "1";
                }
                if (highjump.isChecked()) {
                    events = events + " " + highjump.getText().toString();
                    totalPrice = totalPrice + highJump;
                    High_Jump = "1";
                }
                if (longjump.isChecked()) {
                    events = events + " " + longjump.getText().toString();
                    totalPrice = totalPrice + longJump;
                    Long_Jump = "1";

                }
                if (discuss.isChecked()) {
                    events = events + " " + discuss.getText().toString();
                    totalPrice = totalPrice + DiscusThrow;
                    Discus_Throw = "1";
                }
                if (javelin.isChecked()) {
                    events = events + " " + javelin.getText().toString();
                    totalPrice = totalPrice + javelinThrow;
                    Javelin_Throw = "1";
                }
                if (events.equals("")) {
                    fieldEvents.setText("");
                }
                Total = "" + totalPrice;
                Total_price.setText(Total + "₹");
                fieldEvents.setText(events);
                Full_Events=events;
                events = "";
                totalPrice = 0;
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //****************************************************************************************************************************************************//

    //*********************************************************** send data to sheet **************************************************************//

    public void sendData(String url, String category, String Name, String Phone, String Class, String House, String Paymentmode, String Paymentstatus) {
      //  Toast.makeText(field_events.this, "Verifying data please wait", Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = null;

        stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(field_events.this, response.toString(), Toast.LENGTH_SHORT).show();
                        //loadingAnimation(Paymentmode,Paymentstatus);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(field_events.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();
                parmas.put("action", category);
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


    //***********************************************************************************************************************************************//

    //***************************************************************** Loading animation ************************************************************************//
    public void loadingAnimation(String paymentMode,String paymentStatus) {
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
                openPdf();
            }
        });
        loading_animation.show();


    }

    //***********************************************************************************************************************************************//


    //**************************************************************** Online Payment *******************************************************************//

    public void payOnline() {

        Uri uri = Uri.parse("upi://pay").buildUpon()
               // .appendQueryParameter("pa", "indrasinghbogati@oksbi")
                .appendQueryParameter("pa", "zubairgazzali@okhdfcbank")
                //    .scheme("upi")
                //   .authority("pay")
                //  .appendQueryParameter("pa", "9987988081@okbizaxis")
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
            Toast.makeText(field_events.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
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


                Toast.makeText(getApplicationContext(),"Verifying data please wait",Toast.LENGTH_SHORT).show();
                if (Shot_Put.equals("1")) {


                    sendData(shotputUrl, playerCategory, PlayerName, PlayerPhone, playerClass, playerHouse, "Online", "Paid");
                }
                if (Javelin_Throw.equals("1")) {

                    sendData(javelinthrowUrl, playerCategory, PlayerName, PlayerPhone, playerClass, playerHouse, "Online", "Paid");
                }
                if (High_Jump.equals("1")) {

                    sendData(HighjumpUrl, playerCategory, PlayerName, PlayerPhone, playerClass, playerHouse, "Online", "Paid");

                }
                if (Long_Jump.equals("1")) {

                    sendData(longjumpUrl, playerCategory, PlayerName, PlayerPhone, playerClass, playerHouse, "Online", "Paid");
                }
                if (Discus_Throw.equals("1")) {

                    sendData(discusthrowUrl, playerCategory, PlayerName, PlayerPhone, playerClass, playerHouse, "Online", "Paid");
                }
                loadingAnimation("Offline","Unpaid");






            // Toast.makeText(cricket.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
            Log.e("UPI", "payment successfull: " + approvalRefNo);
        } else if ("Payment cancelled by user.".equals(paymentCancel)) {
            Toast.makeText(field_events.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            Log.e("UPI", "Cancelled by user: " + approvalRefNo);

        } else {
            Toast.makeText(field_events.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            Log.e("UPI", "failed payment: " + approvalRefNo);

        }
    }


    //***********************************************************************************************************************************************//


    //******************************************************************** receipt ********************************************************************//

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void receiptPdf(String paymentMode, String paymentStatus) {
        receipt_name = "fieldEvent.pdf";
        Date date = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date = simpleDateFormat.format(date);

        Bitmap bmp, scalebmp;
        int pageWidth = 1200, pageHeight = 1550;
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.chess_r);
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
        canvas.drawText(PlayerName, 40, 900, data);
        canvas.drawText(playerClass, 410, 900, data);
        canvas.drawText(playerHouse, 660, 900, data);
        canvas.drawText(playerCategory, 940, 900, data);

        canvas.drawText("Selected Events:",40,1000,data);
        canvas.drawText(Full_Events,310,1000,data);

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
        playerName.getText().clear();
        playerPhone.getText().clear();
        playerCategory = "";
        playerClass = "";
        playerHouse = "";
        category.setText("");
        house.setText("");
        Class.setText("");
        fieldEvents.setText("");
        Full_Events="";



    }

    //*************************************************************************************************************************************************************//

    //******************************************************* open pdf *************************************************************************************************//
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
    //************************************************************************************************************************************************************//




    public void onlinepayment_field(View view) {
        if(validateDetails()){
            payOnline();
        }
    }

    public void offlinePayment_feild(View view) {


        if (validateDetails()) {
            Toast.makeText(getApplicationContext(),"Verifying data please wait",Toast.LENGTH_SHORT).show();
            if (Shot_Put.equals("1")) {

                sendData(shotputUrl, playerCategory, PlayerName, PlayerPhone, playerClass, playerHouse, "Offline", "unpaid");
            }
            if (Javelin_Throw.equals("1")) {

                sendData(javelinthrowUrl, playerCategory, PlayerName, PlayerPhone, playerClass, playerHouse, "Offline", "unpaid");
            }
            if (High_Jump.equals("1")) {

                sendData(HighjumpUrl, playerCategory, PlayerName, PlayerPhone, playerClass, playerHouse, "Offline", "unpaid");

            }
            if (Long_Jump.equals("1")) {

                sendData(longjumpUrl, playerCategory, PlayerName, PlayerPhone, playerClass, playerHouse, "Offline", "unpaid");
            }
            if (Discus_Throw.equals("1")) {

                sendData(discusthrowUrl, playerCategory, PlayerName, PlayerPhone, playerClass, playerHouse, "Offline", "unpaid");
            }



        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            receiptPdf("Offline","Unpaid");
        }
        loadingAnimation("Offline","Unpaid");


    }

}