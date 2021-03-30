package com.example.beta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.beta.FBref.mAuth;
import static com.example.beta.FBref.refEX;
import static com.example.beta.FBref.refIN;
import static com.example.beta.FBref.refbus;

public class Incomes extends AppCompatActivity {

    EditText et1, et2, et3;
    int x=0;
    TextView tvdate2;
    String Iuid, Diuid;
    private IncomesC inc;
    String[] datearr = {"1"};
    String[] pricearr = {"1"};
    ArrayList<String> incList = new ArrayList<>();
    String str1, str2;
    StringBuilder Data = new StringBuilder();
    DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incomes);

        et1 = (EditText) findViewById(R.id.et1);
        et3 = (EditText) findViewById(R.id.et3);
        tvdate2 = (TextView) findViewById(R.id.tvdate2);

        tvdate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                year = year - 13;
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Incomes.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                Diuid = dayOfMonth + "/" + month + "/" + year;
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                tvdate2.setText(Diuid);
                Diuid = String.valueOf(dayOfMonth) + String.valueOf(month) + String.valueOf(year);
            };
        };
    }


    public void csvi(View view) {

        String sug2 = et1.getText().toString();
        String price2 = et3.getText().toString();


        FirebaseUser user2 = mAuth.getCurrentUser();
        Iuid = user2.getUid();

        inc = new IncomesC(sug2 , Diuid + String.valueOf(x), price2, Iuid);
        refIN.child(Iuid).child(Diuid).setValue(inc);
        x++;

        ValueEventListener ExListener = new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot ds) {

                incList.clear();

                for (DataSnapshot data : ds.getChildren()) {

                    str1 = inc.getItype();
                    incList.add(str1);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        refbus.addValueEventListener(ExListener);

      /*  Data.append("סכום ,תאריך, ");
        for (int i = 0; i<pricearr.length; i++) {
            Data.append("\n" + pricearr[i] + "," + datearr[i] + "," + incList.get(i));
        }**/

        try {
            FileOutputStream out = openFileOutput("data.csv", Context.MODE_PRIVATE);
            out.write((Data.toString()).getBytes());
            out.close();

            Context context = getApplicationContext();
            File filelocation = new File(getFilesDir(), "data.csv");
            Uri path = FileProvider.getUriForFile(context, "com.example.alpha1.fileprovider", filelocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "data");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileIntent, "send mail"));

        }catch (Exception e) {
            e.printStackTrace();

        }
    }

    public boolean onCreateOptionsMenu (Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        String st = item.getTitle().toString();
        if (st.equals("Expenses")) {
            Intent si = new Intent(this, Expenses.class);
            startActivity(si);
        }
        if (st.equals("Incomes")){
            Intent si = new Intent(this, Incomes.class);
            startActivity(si);
        }

        return true;
    }

    }
