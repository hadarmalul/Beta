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

import static com.example.beta.FBref.mAuth;
import static com.example.beta.FBref.refEX;

public class Incomes extends AppCompatActivity {
    EditText et1, et2, et3;
    int x = 0, Ipricei;
    TextView tvdate;
    String Euid = " ", Diuid = " ", monthI = " ", Iuidi = " ";
    Spinner Spinner, Spinner2;
    private IncomesC inc;
    ArrayList<String> incList = new ArrayList<String>();
    ArrayList<String> incList2 = new ArrayList<String>();
    ArrayList<Integer> incList3 = new ArrayList<Integer>();
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
        setContentView(R.layout.activity_incomes);

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
                Iuidi = dayOfMonth + "/" + month + "/" + year;
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                tvdate.setText(Iuidi);
                Diuid = String.valueOf(year) + String.valueOf(month) + String.valueOf(dayOfMonth);
                monthI = String.valueOf(month);
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
                    incList3.add(str3);
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
        Ipricei = Integer.parseInt(price);


        inc = new IncomesC(sug, Iuidi, Ipricei, monthI, Euid);
        refEX.child(Euid).child(Diuid).setValue(inc);


        if (Spinner.getSelectedItemPosition() == 0) {
            Query query = refEX.child(Euid).orderByChild(Diuid);
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
                        incList3.add(str3);
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
                        incList3.add(str3);
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
                        incList3.add(str3);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        Data.setLength(0);
        Data.append("סכום ,תאריך, ");
        for (int i = 0; i < incList.size(); i++) {
            Data.append("\n" + incList3.get(i) + "," + incList2.get(i) + "," + incList.get(i));
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