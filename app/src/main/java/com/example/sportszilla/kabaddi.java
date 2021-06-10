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

public class kabaddi extends AppCompatActivity {

    EditText player1, player2, player3, player4, player5, player6, player7, phone_no;
    TextView category, house;
    String Player1, Player2, Player3, Player4, Player5, Player6, Player7, Phone_no, Category = "", House = "";
    String receipt_name;
    String Date;
    Dialog loading_animation;
    final int UPI_PAYMENT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kabaddi);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        appHeader();
    }

    //**************************************************************** App Header *******************************************************************//

    public void appHeader() {
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#212121"));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.kabaddi_app_bar);
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

    //***********************************************************************************************************************************************//


    //**************************************************************** Drop down *******************************************************************//

    public void kabaddi_house(View view) {

        AppCompatButton ok;

        Dialog dialog;
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_house_layout);
        dialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable((R.drawable.popup_layout_design)));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        house = (TextView) findViewById(R.id.kabaddi_select_house);
        ok = dialog.findViewById(R.id.house_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup house_group = (RadioGroup) dialog.findViewById(R.id.house_category);
                int house_selected_id = house_group.getCheckedRadioButtonId();

                RadioButton house_selected = (RadioButton) dialog.findViewById(house_selected_id);
                String kabaddi_house = (String) house_selected.getText();
                house.setText(kabaddi_house);
                House=kabaddi_house;
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    public void kabaddi_category(View view) {


        AppCompatButton ok;

        Dialog dialog;

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_gender_layout);
        dialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable((R.drawable.popup_layout_design)));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        category = (TextView) findViewById(R.id.kabaddi_select_category);
        ok = dialog.findViewById(R.id.gender_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup category_group = (RadioGroup) dialog.findViewById(R.id.gender_category);
                int gender_selected_id = category_group.getCheckedRadioButtonId();

                RadioButton gender_selected = (RadioButton) dialog.findViewById(gender_selected_id);
                String Gender = (String) gender_selected.getText();
                category.setText(Gender);
                Category=Gender;
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    //***********************************************************************************************************************************************//

    //*************************************************************************** Validate details *****************************************************************************************//

    public boolean validateDetails() {
        validation vali = new validation();

        player1 = (EditText) findViewById(R.id.kabaddi_player1);
        player2 = (EditText) findViewById(R.id.kabaddi_player2);
        player3 = (EditText) findViewById(R.id.kabaddi_player3);
        player4 = (EditText) findViewById(R.id.kabaddi_player4);
        player5 = (EditText) findViewById(R.id.kabaddi_player5);
        player6 = (EditText) findViewById(R.id.kabaddi_player6);
        player7 = (EditText) findViewById(R.id.kabaddi_player7);
        phone_no = (EditText) findViewById(R.id.kabaddi_player_phone);

        Player1 = player1.getText().toString();
        Player2 = player2.getText().toString();
        Player3 = player3.getText().toString();
        Player4 = player4.getText().toString();
        Player5 = player5.getText().toString();
        Player6 = player6.getText().toString();
        Player7 = player7.getText().toString();
        Phone_no = phone_no.getText().toString();

        if (Player1.isEmpty()) {
            vali.Msg(getApplicationContext(), "Player1 please enter your name");
            return false;
        } else if (vali.specialCharacter(Player1) || vali.isNumeric(Player1)) {
            vali.Msg(getApplicationContext(), "Player1 please enter valid name");
            return false;
        } else if (Player2.isEmpty()) {
            vali.Msg(getApplicationContext(), "Player2 please enter your name");
            return false;
        } else if (vali.specialCharacter(Player2) || vali.isNumeric(Player2)) {
            vali.Msg(getApplicationContext(), "Player2 please enter valid name");
            return false;
        } else if (Player3.isEmpty()) {
            vali.Msg(getApplicationContext(), "Player3 please enter your name");
            return false;
        } else if (vali.specialCharacter(Player3) || vali.isNumeric(Player3)) {
            vali.Msg(getApplicationContext(), "Player3 please enter valid name");
            return false;
        } else if (Player4.isEmpty()) {
            vali.Msg(getApplicationContext(), "Player4 please enter your name");
            return false;
        } else if (vali.specialCharacter(Player4) || vali.isNumeric(Player4)) {
            vali.Msg(getApplicationContext(), "Player4 please enter valid name");
            return false;
        } else if (Player5.isEmpty()) {
            vali.Msg(getApplicationContext(), "Player5 please enter your name");
            return false;
        } else if (vali.specialCharacter(Player5) || vali.isNumeric(Player5)) {
            vali.Msg(getApplicationContext(), "Player5 please enter valid name");
            return false;
        } else if (Player6.isEmpty()) {
            vali.Msg(getApplicationContext(), "Player6 please enter your name");
            return false;
        } else if (vali.specialCharacter(Player6) || vali.isNumeric(Player6)) {
            vali.Msg(getApplicationContext(), "Player6 please enter valid name");
            return false;
        } else if (Player7.isEmpty()) {
            vali.Msg(getApplicationContext(), "Player7 please enter your name");
            return false;
        } else if (vali.specialCharacter(Player7) || vali.isNumeric(Player7)) {
            vali.Msg(getApplicationContext(), "Player7 please enter valid name");
            return false;
        }  else if (Phone_no.isEmpty()) {
            vali.Msg(getApplicationContext(), "Please enter your phone no");
            return false;
        } else if (!vali.validatePhone(Phone_no)) {
            vali.Msg(getApplicationContext(), "Please enter valid phone no");
            return false;
        } else if (Category.equals("")) {
            vali.Msg(getApplicationContext(), "Please select category");
            return false;
        } else if (House.equals("")) {
            vali.Msg(getApplicationContext(), "Please select house");
            return false;
        } else {
            return true;
        }

    }


    //**************************************************************************************************************************************************************************************//


    //*********************************************************************** Receipt pdf **********************************************************************************//
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void receiptPdf(String paymentMode, String paymentStatus) {
        receipt_name = Player1 + "_" + Phone_no + "_" + "kabaddi.pdf";

        java.util.Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date = simpleDateFormat.format(date);

        Bitmap bmp, scalebmp;
        int pageWidth = 1200, pageHeight = 2200;
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.kabadddi_r);
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

        canvas.drawText("Sr no", 40, 780, table_heading);
        canvas.drawText("Name", 210, 780, table_heading);
        canvas.drawText("House", 660, 780, table_heading);
        canvas.drawText("Category", 940, 780, table_heading);

        //Lines
        canvas.drawLine(180, 740, 180, 800, lineWidth);
        canvas.drawLine(630, 740, 630, 800, lineWidth);
        canvas.drawLine(910, 740, 910, 800, lineWidth);

        //Details

        Paint data = new Paint();
        data.setTextSize(34);
        canvas.drawText("1", 40, 900, data);
        canvas.drawText(Player1, 210, 900, data);
        canvas.drawText(House, 660, 900, data);
        canvas.drawText(Category, 940, 900, data);

        canvas.drawText("2", 40, 950, data);
        canvas.drawText(Player2, 210, 950, data);
        canvas.drawText(House, 660, 950, data);
        canvas.drawText(Category, 940, 950, data);

        canvas.drawText("3", 40, 1000, data);
        canvas.drawText(Player3, 210, 1000, data);
        canvas.drawText(House, 660, 1000, data);
        canvas.drawText(Category, 940, 1000, data);

        canvas.drawText("4", 40, 1050, data);
        canvas.drawText(Player4, 210, 1050, data);
        canvas.drawText(House, 660, 1050, data);
        canvas.drawText(Category, 940, 1050, data);

        canvas.drawText("5", 40, 1100, data);
        canvas.drawText(Player5, 210, 1100, data);
        canvas.drawText(House, 660, 1100, data);
        canvas.drawText(Category, 940, 1100, data);

        canvas.drawText("6", 40, 1150, data);
        canvas.drawText(Player6, 210, 1150, data);
        canvas.drawText(House, 660, 1150, data);
        canvas.drawText(Category, 940, 1150, data);

        canvas.drawText("7", 40, 1200, data);
        canvas.drawText(Player7, 210, 1200, data);
        canvas.drawText(House, 660, 1200, data);
        canvas.drawText(Category, 940, 1200, data);


        Paint data_heading = new Paint();
        data_heading.setTextSize(34);
        data_heading.setTextAlign(Paint.Align.RIGHT);
        data.setTextAlign(Paint.Align.RIGHT);
        data_heading.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        //Registration date
        canvas.drawText("Registration date", pageWidth - 20, pageHeight - 500, data_heading);
        canvas.drawText(Date, pageWidth - 50, pageHeight - 450, data);

        //  Payment Details
        data_heading.setTextAlign(Paint.Align.LEFT);
        data.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Payment mode", 20, pageHeight - 500, data_heading);
        canvas.drawText(paymentMode, 70, pageHeight - 450, data);
        canvas.drawText("Payment status", 20, pageHeight - 300, data_heading);
        canvas.drawText(paymentStatus, 70, pageHeight - 250, data);

        //wish
        Paint Wish = new Paint();
        Wish.setTextAlign(Paint.Align.CENTER);
        Wish.setTextSize(37);
        Wish.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("All the best", pageWidth / 2, pageHeight - 100, Wish);


        mypdf.finishPage(myPage1);
        //writing data to file
        File file = new File(getExternalFilesDir(null), receipt_name);
        try {
            mypdf.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mypdf.close();

        player1.getText().clear();
        player2.getText().clear();
        player3.getText().clear();
        player4.getText().clear();
        player5.getText().clear();
        player6.getText().clear();
        player7.getText().clear();

        phone_no.getText().clear();
        house.setText("");
        category.setText("");

        House = "";
        Category = "";
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

    //*********************************************************** send data to sheet **************************************************************//

    public void sendData(String category, String Player1, String Player2, String Player3, String Player4, String Player5, String Player6, String Player7, String Phone, String House, String Paymentmode, String Paymentstatus) {
        Toast.makeText(kabaddi.this, "Verifying data please wait", Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = null;

        stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxHSzzM32vVRVIoM2P5sFSPSTKE5HWat-Ws0_dj7nxHn0JKX7djBGsfng/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(cricket.this,response.toString(),Toast.LENGTH_SHORT).show();
                        loadingAnimation(Paymentmode, Paymentstatus);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(kabaddi.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();
                parmas.put("action", category);
                parmas.put("Player1", Player1);
                parmas.put("Player2", Player2);
                parmas.put("Player3", Player3);
                parmas.put("Player4", Player4);
                parmas.put("Player5", Player5);
                parmas.put("Player6", Player6);
                parmas.put("Player7", Player7);

                parmas.put("Phone", Phone);
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

    //**************************************************************** Online Payment *******************************************************************//

    public void payOnline() {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", "indrasinghbogati@oksbi")
                //   .appendQueryParameter("pa", "zubairgazzali@okhdfcbank")
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
            Toast.makeText(kabaddi.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
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

            sendData(Category, Player1, Player2, Player3, Player4, Player5, Player6, Player7, Phone_no, House, "Online", "Paid");

            // Toast.makeText(cricket.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
            Log.e("UPI", "payment successfull: " + approvalRefNo);
        } else if ("Payment cancelled by user.".equals(paymentCancel)) {
            Toast.makeText(kabaddi.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            Log.e("UPI", "Cancelled by user: " + approvalRefNo);

        } else {
            Toast.makeText(kabaddi.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            Log.e("UPI", "failed payment: " + approvalRefNo);

        }
    }


    //***********************************************************************************************************************************************//





    //**************************************************************** Payment Buttons *******************************************************************//


    public void kabaddi_offlinePayment(View view) {
        if (validateDetails()) {
            // loadingAnimation("Offline","Unpaid");
            sendData(Category, Player1, Player2, Player3, Player4, Player5, Player6, Player7, Phone_no, House, "Offline", "Unpaid");
        }
    }


    public void kabaddi_onlinePayment(View view) {
        if (validateDetails()) {
            payOnline();
        }

    }

    //***********************************************************************************************************************************************//


}