package com.example.rza.kuvarica;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> recipes = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        listView = (ListView) findViewById(R.id.lv_content);
        fab = (FloatingActionButton) findViewById(R.id.fab_add_new_recipe);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddNewRecipeActivity.class);
                startActivity(i);
            }
        });

        StringBuilder text = new StringBuilder();
        try {

            File file = getFileStreamPath("recipes.txt");
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append("\n");
                }
                br.close();
                String recipeWholeString = text.toString();

                int numberOfRecipes = recipeWholeString.split("#").length;
                Log.d("Info", recipeWholeString);
                for (int i = 0; i < numberOfRecipes - 1; i++ ){
                    String recipe = recipeWholeString.split("#")[i];
                    String recipeName = recipe.split(",")[0];
                    names.add(recipeName);
                }
            }
            else {
                Toast.makeText(MainActivity.this, "There Are No Recipes :(", Toast.LENGTH_LONG).show();
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }





        recipes.addAll(names);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, recipes);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "View " + String.valueOf(i) + " clicked", Toast.LENGTH_LONG).show();
            }
        });

    }
}
