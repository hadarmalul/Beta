package com.example.beta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * The type Credits.
 */
public class Credits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        String st = item.getTitle().toString();
        if (st.equals("Personal Area")) {
            Intent si = new Intent(this, PersonalArea.class);
            startActivity(si);
        }
        if (st.equals("Expenses")) {
            Intent si = new Intent(this, Expenses.class);
            startActivity(si);
        }
        if (st.equals("Incomes")) {
            Intent si = new Intent(this, Incomes.class);
            startActivity(si);
        }
        if (st.equals("Graphs")) {
            Intent si = new Intent(this, Graphs.class);
            startActivity(si);
        }
        if (st.equals("Credits")){
            Intent si = new Intent(this, Credits.class);
            startActivity(si);
        }

        return true;

    }
}