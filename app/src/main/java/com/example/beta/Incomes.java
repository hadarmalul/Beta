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
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.beta.FBref.mAuth;
import static com.example.beta.FBref.refbus;

public class Incomes extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText et1, et2, et3;
    int x = 0;
    TextView tvdate2;
    String Iuid = " ", Diuid = " ", monthI = "0";
    android.widget.Spinner Spinner, Spinner2;
    private IncomesC inc;
    ArrayList<String> incList = new ArrayList<String>();
    ArrayList<String> incList2 = new ArrayList<String>();
    ArrayList<String> incList3 = new ArrayList<String>();
    String[] spinE = {"Date", "type", "price"};
    String[] spinE2 = {"this month", "last 6 months", "last year"};
    String str1, str2, str3;
    StringBuilder Data = new StringBuilder();
    DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incomes);

        et1 = (EditText) findViewById(R.id.et1);
        et3 = (EditText) findViewById(R.id.et3);
        tvdate2 = (TextView) findViewById(R.id.tvdate2);

        Spinner = (Spinner) findViewById(R.id.Spinner);
        Spinner2 = (Spinner) findViewById(R.id.Spinner2);

        Spinner.setOnItemSelectedListener(this);
        Spinner2.setOnItemSelectedListener(this);


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

        FirebaseUser user = mAuth.getCurrentUser();
        Iuid = user.getUid();

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinE);
        Spinner.setAdapter(adp);
        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinE2);
        Spinner2.setAdapter(adp2);
    }


    public void csvi(View view) {

        String sug = et1.getText().toString();
        String price = et3.getText().toString();

        inc = new IncomesC(sug, Diuid, price, monthI, Iuid);
        refbus.child(Iuid + "E").setValue(inc);
        x++;

        Data.append("סכום ,תאריך, ");
        for (int i = 0; i < incList.size(); i++) {
            Data.append("\n" + incList3.get(i) + "," + incList2.get(i) + "," + incList.get(i));
        }


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

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView == Spinner) {
            if (i == 0) {
                Query query = refbus.orderByChild("edate");
                query.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dS) {
                        incList.clear();
                        incList2.clear();
                        incList3.clear();

                        for (DataSnapshot data : dS.getChildren()) {
                            IncomesC inc2 = data.getValue(IncomesC.class);

                            str1 = inc2.getItype();
                            str2 = inc2.getIdate();
                            str3 = inc2.getIprice();
                            incList.add(str1 + "");
                            incList2.add(str2 + "");
                            incList3.add(str3 + "");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

            if (i == 1) {
                Query query = refbus.orderByChild("etype");
                query.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dS) {
                        incList.clear();
                        incList2.clear();
                        incList3.clear();

                        for (DataSnapshot data : dS.getChildren()) {
                            IncomesC inc2 = data.getValue(IncomesC.class);

                            str1 = inc2.getItype();
                            str2 = inc2.getIdate();
                            str3 = inc2.getIprice();
                            incList.add(str1 + "");
                            incList2.add(str2 + "");
                            incList3.add(str3 + "");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

            if (i == 2) {
                Query query = refbus.orderByChild("eprice");
                query.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dS) {
                        incList.clear();
                        incList2.clear();
                        incList3.clear();

                        for (DataSnapshot data : dS.getChildren()) {
                            IncomesC inc2 = data.getValue(IncomesC.class);

                            str1 = inc2.getItype();
                            str2 = inc2.getIdate();
                            str3 = inc2.getIprice();
                            incList.add(str1 + "");
                            incList2.add(str2 + "");
                            incList3.add(str3 + "");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        }
        else {
            if (i == 0) {
                Query query = refbus.orderByChild("edate");
                query.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dS) {
                        incList.clear();
                        incList2.clear();
                        incList3.clear();

                        for (DataSnapshot data : dS.getChildren()) {
                            IncomesC inc2 = data.getValue(IncomesC.class);

                            str1 = inc2.getItype();
                            str2 = inc2.getIdate();
                            str3 = inc2.getIprice();
                            incList.add(str1 + "");
                            incList2.add(str2 + "");
                            incList3.add(str3 + "");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

            if (i == 1) {
                Query query = refbus.orderByChild("etype");
                query.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dS) {
                        incList.clear();
                        incList2.clear();
                        incList3.clear();

                        for (DataSnapshot data : dS.getChildren()) {
                            IncomesC inc2 = data.getValue(IncomesC.class);

                            str1 = inc2.getItype();
                            str2 = inc2.getIdate();
                            str3 = inc2.getIprice();
                            incList.add(str1 + "");
                            incList2.add(str2 + "");
                            incList3.add(str3 + "");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

            if (i == 2) {
                Query query = refbus.orderByChild("eprice");
                query.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dS) {
                        incList.clear();
                        incList2.clear();
                        incList3.clear();

                        for (DataSnapshot data : dS.getChildren()) {
                            IncomesC inc2 = data.getValue(IncomesC.class);

                            str1 = inc2.getItype();
                            str2 = inc2.getIdate();
                            str3 = inc2.getIprice();
                            incList.add(str1 + "");
                            incList2.add(str2 + "");
                            incList3.add(str3 + "");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
