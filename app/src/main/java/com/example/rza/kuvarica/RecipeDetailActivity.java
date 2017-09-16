package com.example.rza.kuvarica;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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


        textViewContent.setText(recipeContent);
        textViewName.setText(recipeName);

        fabSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String smsBody = recipeName + "\n" + recipeContent;
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
                        sms.sendTextMessage(number, null, smsBody, null, null);
                        d.cancel();
                    }
                });
            }
        });

        fabEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, recipeName);
                intent.putExtra(Intent.EXTRA_TEXT, recipeContent);
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });


    }
}
