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

public class RecipeListActivity extends AppCompatActivity {
    private ListView listView;
    private FloatingActionButton fab;
    private static String TAG = "LOG";
    private ArrayList<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recipes = new ArrayList<>();
        listView = (ListView) findViewById(R.id.lv_content);
        fab = (FloatingActionButton) findViewById(R.id.fab_add_new_recipe); //view injection, omogucava da *fab* koristis kao promenljivu
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //klik na plusic u donjem desnom uglu
                Intent i = new Intent(RecipeListActivity.this, AddNewRecipeActivity.class); //this - iz ovog activitia, class - u ovaj activity
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: resumed");
        recipes.clear();
        populateListView();
    }

    public void populateListView() { //popunjava list view sa podacima
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
                Log.d("whole recipe", recipeWholeString);
                for (int i = 0; i < numberOfRecipes - 1; i++ ){
                    String recipe = recipeWholeString.split("#")[i];
                    String recipeName = recipe.split(",")[0];
                    String recipeContent = recipe.split(",")[1];
                    Recipe r = new Recipe(recipeName, recipeContent);
                    recipes.add(r);
                }
            }
            else {
                Toast.makeText(RecipeListActivity.this, "There Are No Recipes :(", Toast.LENGTH_LONG).show();
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> recipeNames = new ArrayList<>();
        for (int i = 0; i < recipes.size(); i++) {
            recipeNames.add(recipes.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(RecipeListActivity.this, android.R.layout.simple_list_item_1, recipeNames); //recipeNames je arraylista imena
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) { //otvaranje novog activitia za detalje recepta
                Intent intent = new Intent(RecipeListActivity.this, RecipeDetailActivity.class);
                intent.putExtra("recipeName", recipes.get(i).getName());
                intent.putExtra("recipeContent", recipes.get(i).getContent());
                startActivity(intent);
            }
        });
    }

}
