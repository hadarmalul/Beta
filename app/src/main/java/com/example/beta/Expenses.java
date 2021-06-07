package com.example.beta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
/**
 * The type Expences.
 */

public class Expenses extends AppCompatActivity {

    EditText et1, et2, et3;
    int x = 0, pricei, monthE, deuid2;
    TextView tvdate;
    String Euid = " ", Deuid = " ",  uidi = " ", uidi2 = " ";
    Spinner Spinner;
    private expensesC exp;
    public ArrayList<String> exList = new ArrayList<String>();
    public ArrayList<String> exList2 = new ArrayList<String>();
    public ArrayList<Integer> exList3 = new ArrayList<Integer>();
    public ArrayList<Integer> exList4 = new ArrayList<Integer>();
    String[] spinE = {"Date", "type", "price"};
    String str1, str2;
    int str3, str4, year2, month2, day2;
    StringBuilder Data = new StringBuilder();
    Calendar cal = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener mDateSetListener;
    private SimpleDateFormat dateFormat;
    private String date;
    private Calendar calendar;
    AlertDialog.Builder adb;
    int hour = cal.get(Calendar.HOUR);
    int minute = cal.get(Calendar.MINUTE);
    int second = cal.get(Calendar.SECOND);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        et1 = (EditText) findViewById(R.id.et1);
        et3 = (EditText) findViewById(R.id.et3);
        tvdate = (TextView) findViewById(R.id.tvdate);
        Spinner = (Spinner) findViewById(R.id.Spinner);

        /**
         * הפעולה יוצרת את הבוחר זמן
         */


        tvdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                year2 = cal.get(Calendar.YEAR);
                month2 = cal.get(Calendar.MONTH);
                day2 = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Expenses.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year2, month2, day2);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                uidi = dayOfMonth + "/" + month + "/" + year;
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                tvdate.setText(uidi);
                uidi2 = String.valueOf(dayOfMonth) + String.valueOf(month) + String.valueOf(year);
                monthE = month;
            }
        };

        FirebaseUser user = mAuth.getCurrentUser();
        Euid = user.getUid();


        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinE);
        Spinner.setAdapter(adp);

        Query query = refEX.child(Euid).orderByChild("edate");
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dS) {
                exList.clear();
                exList2.clear();
                exList3.clear();
                exList4.clear();

                for (DataSnapshot data : dS.getChildren()) {
                    expensesC exp2 = data.getValue(expensesC.class);

                    str1 = exp2.getEtype();
                    str2 = exp2.getEdate();
                    str3 = exp2.getEprice();
                    str4 = exp2.getEmonth();
                    exList.add(str1 + "");
                    exList2.add(str2+ "");
                    exList3.add(str3);
                    exList4.add(str4);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    /**
     * Csve.
     * כאשר הכפתור נלחץ הנתונים המשתמש כתב מועברים לfurebase
     * הנתונים שיש בבסי הנתונים נקראים וממוינים לפי מה שהמשתמש בחר ברשימה
     * @param view the view
     */
    public void csve(View view) {

        Deuid = String.valueOf(year2) + String.valueOf(month2) + String.valueOf(day2) + String.valueOf(hour) + String.valueOf(minute) + String.valueOf(second);

        String sug = et1.getText().toString();
        String price = et3.getText().toString();
        pricei = Integer.parseInt(price);


        exp = new expensesC(sug,uidi, pricei, monthE, Euid);
        refEX.child(Euid).child(Deuid).setValue(exp);


        if (Spinner.getSelectedItemPosition() == 0) {
            Query query = refEX.child(Euid).orderByChild(Deuid);
            query.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dS) {
                    exList.clear();
                    exList2.clear();
                    exList3.clear();
                    exList4.clear();

                    for (DataSnapshot data : dS.getChildren()) {
                        expensesC exp2 = data.getValue(expensesC.class);

                        str1 = exp2.getEtype();
                        str2 = exp2.getEdate();
                        str3 = exp2.getEprice();
                        str4 = exp2.getEmonth();
                        exList.add(str1 + "");
                        exList2.add(str2+ "");
                        exList3.add(str3);
                        exList4.add(str4);
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
                        str4 = exp2.getEmonth();
                        exList.add(str1 + "");
                        exList2.add(str2+ "");
                        exList3.add(str3);
                        exList4.add(str4);
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
                    exList4.clear();

                    for (DataSnapshot data : dS.getChildren()) {
                        expensesC exp2 = data.getValue(expensesC.class);

                        str1 = exp2.getEtype();
                        str2 = exp2.getEdate();
                        str3 = exp2.getEprice();
                        str4 = exp2.getEmonth();
                        exList.add(str1 + "");
                        exList2.add(str2+ "");
                        exList3.add(str3);
                        exList4.add(str4);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            et1.setText(" ");
            et3.setText(" ");
        }

        /**
         * תיבת דו שיח שכאשר המשתמש לוחץ כל לא עכשיו לא קורה כלום
         * כאשר המשתמש לוחץ על העלאה נוצרת טבלת csv והיא נשלחת אליו
         */


        adb = new AlertDialog.Builder(this);
        adb.setTitle("upload a table?");
        adb.setMessage("do you want to upload the table right now? or save for later?");
        adb.setPositiveButton("UPLOAD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Data.setLength(0);
                Data.append("סכום ,תאריך, ");
                for (int k = 0; k < exList.size(); k++) {
                    Data.append("\n" + exList3.get(k) + "," + exList2.get(k) + "," + exList.get(k));
                }


                try {
                    String name ="data";
                    calendar = Calendar.getInstance();
                    dateFormat = new SimpleDateFormat("yyMMddHHmmss");
                    date = dateFormat.format(calendar.getTime());
                    name += date;
                    name += ".csv";
                    FileOutputStream out = openFileOutput((name), Context.MODE_PRIVATE);
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
        });

        adb.setNegativeButton("NOT NOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });

        AlertDialog ad = adb.create();
        ad.show();
    }


    /**
     * הפעולה יוצרת תפריט ןכאשר כפתור בתפריט נלחץ המסך עובר למסך אחר בהתאמה לשם של הכפתור
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu (Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        String st = item.getTitle().toString();
        if (st.equals("Personal Area")) {
            Intent si = new Intent(this, PersonalArea.class);
            startActivity(si);
        }
        if (st.equals("Expenses")) {
            Intent si = new Intent(this, Expenses.class);
            startActivity(si);
        }
        if (st.equals("Incomes")){
            Intent si = new Intent(this, Incomes.class);
            startActivity(si);
        }
        if (st.equals("Statistics")){
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



