package com.pandita.rishabh.booksearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(getApplicationContext(), "Bye Bye!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView main_image = (ImageView) findViewById(R.id.main_image);
        main_image.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Toast toast1 = Toast.makeText(getApplicationContext(), "Short Press to preview\nLong Press to buy", Toast.LENGTH_LONG);
                toast1.show();

            }
        });

        ImageButton searchButton = (ImageButton) findViewById(R.id.image_search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchKey = (EditText) findViewById(R.id.search_textbox);
                final String searchKeyString = searchKey.getText().toString();
                if (searchKeyString == null || searchKeyString.isEmpty())
                    Toast.makeText(getApplicationContext(), "Invalid Search", Toast.LENGTH_SHORT).show();
                else {
                    Intent i = new Intent(MainActivity.this, SearchActivity.class);
                    i.putExtra("SEARCH_PARAM", searchKeyString);
                    startActivity(i);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
