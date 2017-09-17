package com.example.rza.kuvarica;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RecipeDetailActivity extends AppCompatActivity {
    private String recipeName;
    private String recipeContent;
    private TextView textViewName;
    private TextView textViewContent;
    private FloatingActionButton fabSms;
    private FloatingActionButton fabEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        textViewName = (TextView) findViewById(R.id.tv_recipe_details_name);
        textViewContent = (TextView) findViewById(R.id.tv_recipe_details_content);
        fabSms = (FloatingActionButton) findViewById(R.id.fab_sms);
        fabEmail = (FloatingActionButton) findViewById(R.id.fab_email);

        Intent i = getIntent();
        recipeContent = i.getStringExtra("recipeContent");
        recipeName = i.getStringExtra("recipeName");

        textViewName.setTypeface(null, Typeface.BOLD);
        textViewContent.setText(recipeContent);
        textViewName.setText(recipeName);

        fabSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String smsBody = recipeName + ":\n Instructions: " + recipeContent;
                final Dialog d = new Dialog(RecipeDetailActivity.this);
                d.setContentView(R.layout.sms_layout_dialog);
                final EditText etSms = (EditText)d.findViewById(R.id.et_sms_number);
                Button btnSendSms = (Button)d.findViewById(R.id.btn_sms);
                d.show();

                btnSendSms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SmsManager sms = SmsManager.getDefault();
                        String number = etSms.getText().toString();
                        if (!number.equals("+381")) {
                            sms.sendTextMessage(number, null, smsBody, null, null);
                            Toast.makeText(RecipeDetailActivity.this, "SMS Sent to: " + number, Toast.LENGTH_SHORT).show();
                            d.cancel();
                        }
                        else {
                            Toast.makeText(RecipeDetailActivity.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        fabEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog d = new Dialog(RecipeDetailActivity.this);

                d.setContentView(R.layout.email_layout_dialog);
                Button btnSendEmail = (Button)d.findViewById(R.id.btn_email);
                final EditText etEmail = (EditText)d.findViewById(R.id.et_email_dialog);
                d.show();

                btnSendEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String emailAddress = etEmail.getText().toString();
                        if (!emailAddress.equals("")) {
                        Log.d("INFO", emailAddress);
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", emailAddress, null));
                        emailIntent.putExtra(Intent.EXTRA_TEXT, recipeContent);
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, recipeName);
                        startActivity(Intent.createChooser(emailIntent, "Send email..."));
                        d.cancel();
                        }
                        else {
                            Toast.makeText(RecipeDetailActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                        }
                    }
                });




            }
        });


    }
}
