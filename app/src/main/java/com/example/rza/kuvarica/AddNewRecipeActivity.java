package com.example.rza.kuvarica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddNewRecipeActivity extends AppCompatActivity {
    private EditText etRecipeName;
    private EditText etRecipeInstructions;
    private Button btnAddRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_recipe);

        etRecipeInstructions = (EditText) findViewById(R.id.et_recipe_instructions);
        etRecipeName = (EditText) findViewById(R.id.et_recipe_name);
        btnAddRecipe = (Button) findViewById(R.id.btn_add);

        btnAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //dugme za dodavanje novog recepta
                String recipeName = etRecipeName.getText().toString();
                String recipeInstructions = etRecipeInstructions.getText().toString();
                if (recipeName.equals("") || recipeInstructions.equals("")) {
                    Toast.makeText(AddNewRecipeActivity.this, "Please fill all necessary fields!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String txtEntry = recipeName + ", " + recipeInstructions + "#";

                    File file = getFileStreamPath("recipes.txt");

                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        writeNewEntry(file, txtEntry);
                    }
                    else {
                        writeNewEntry(file, txtEntry);
                        Log.d("Info", txtEntry);
                    }
                    Toast.makeText(AddNewRecipeActivity.this, "Recipe added", Toast.LENGTH_LONG).show();
                    etRecipeInstructions.setText("");
                    etRecipeName.setText("");

                }

            }
        });
    }

    public void writeNewEntry(File file, String txtEntry) {
        try {
            FileOutputStream writer = openFileOutput(file.getName(), MODE_APPEND);
            writer.write(txtEntry.getBytes());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

