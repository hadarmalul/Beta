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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.beta.FBref.mAuth;
import static com.example.beta.FBref.refEX;
import static com.example.beta.FBref.refbus;

public class Expenses extends AppCompatActivity {

    EditText et1, et2, et3;
    int x = 0, pricei;
    TextView tvdate;
    String Euid = " ", Deuid = " ", monthE = " ";
    Spinner Spinner, Spinner2;
    private expensesC exp;
    ArrayList<String> exList = new ArrayList<String>();
    ArrayList<String> exList2 = new ArrayList<String>();
    ArrayList<String> exList3 = new ArrayList<String>();
    String[] spinE = {"Date", "type", "price"};
    String[] spinE2 = {"this month", "last 6 months", "last year"};
    String str1, str2;
    int str3;
    StringBuilder Data = new StringBuilder();
    DatePickerDialog.OnDateSetListener mDateSetListener;
    private SimpleDateFormat dateFormat;
    private String date;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        et1 = (EditText) findViewById(R.id.et1);
        et3 = (EditText) findViewById(R.id.et3);
        tvdate = (TextView) findViewById(R.id.tvdate);
        Spinner = (Spinner) findViewById(R.id.Spinner);
        Spinner2 = (Spinner) findViewById(R.id.Spinner2);


        tvdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Expenses.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                Deuid = dayOfMonth + "/" + month + "/" + year;
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                tvdate.setText(Deuid);
                Deuid = String.valueOf(dayOfMonth) + String.valueOf(month) + String.valueOf(year);
                monthE = String.valueOf(month);
            }
        };

        FirebaseUser user = mAuth.getCurrentUser();
        Euid = user.getUid();


        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinE);
        Spinner.setAdapter(adp);
        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinE2);
        Spinner2.setAdapter(adp2);

        Query query = refEX.child(Euid).orderByChild("edate");
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dS) {
                exList.clear();
                exList2.clear();
                exList3.clear();

                for (DataSnapshot data : dS.getChildren()) {
                    expensesC exp2 = data.getValue(expensesC.class);

                    str1 = exp2.getEtype();
                    str2 = exp2.getEdate();
                    str3 = exp2.getEprice();
                    exList.add(str1 + "");
                    exList2.add(str2 + "");
                    exList3.add(str3 + "");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    public void csve(View view) {


        String sug = et1.getText().toString();
        String price = et3.getText().toString();

        exp = new expensesC(sug, Deuid, price, monthE, Euid);
        refEX.child(Euid).child(Deuid).setValue(exp);


        if (Spinner.getSelectedItemPosition() == 0) {
            Query query = refEX.child(Euid).orderByChild("edate");
            query.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dS) {
                    exList.clear();
                    exList2.clear();
                    exList3.clear();

                    for (DataSnapshot data : dS.getChildren()) {
                        expensesC exp2 = data.getValue(expensesC.class);

                        str1 = exp2.getEtype();
                        str2 = exp2.getEdate();
                        str3 = exp2.getEprice();
                        exList.add(str1 + "");
                        exList2.add(str2 + "");
                        exList3.add(str3 + "");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        if (Spinner.getSelectedItemPosition() == 1) {
            Query query = refEX.child(Euid).orderByChild("etype");
            query.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dS) {
                    exList.clear();
                    exList2.clear();
                    exList3.clear();

                    for (DataSnapshot data : dS.getChildren()) {
                        expensesC exp2 = data.getValue(expensesC.class);

                        str1 = exp2.getEtype();
                        str2 = exp2.getEdate();
                        str3 = exp2.getEprice();
                        exList.add(str1 + "");
                        exList2.add(str2 + "");
                        exList3.add(str3 + "");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        if (Spinner.getSelectedItemPosition() == 2) {
            Query query = refEX.child(Euid).orderByChild("eprice");
            query.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dS) {
                    exList.clear();
                    exList2.clear();
                    exList3.clear();

                    for (DataSnapshot data : dS.getChildren()) {
                        expensesC exp2 = data.getValue(expensesC.class);

                        str1 = exp2.getEtype();
                        str2 = exp2.getEdate();
                        str3 = exp2.getEprice();
                        exList.add(str1 + "");
                        exList2.add(str2 + "");
                        exList3.add(str3 + "");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
        Data.setLength(0);
        Data.append("סכום ,תאריך, ");
        for (int i = 0; i < exList.size(); i++) {
            Data.append("\n" + exList3.get(i) + "," + exList2.get(i) + "," + exList.get(i));
        }


        try {
            String name ="data";
            calendar = Calendar.getInstance();
            dateFormat = new SimpleDateFormat("yyMMddHHmmss");
            date = dateFormat.format(calendar.getTime());
            name += date;
            name += ".csv";
            FileOutputStream out = openFileOutput(name, Context.MODE_PRIVATE);
            out.write((Data.toString()).getBytes());
            out.close();

            Context context = getApplicationContext();
            File filelocation = new File(getFilesDir(), name);
            Uri path = FileProvider.getUriForFile(context, "com.example.Beta.fileprovider", filelocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, name);
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileIntent, "send mail"));

        } catch (Exception e) {
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
        if (st.equals("Graphs")){
            Intent si = new Intent(this, Graphs.class);
            startActivity(si);
        }

        return true;
    }
}



