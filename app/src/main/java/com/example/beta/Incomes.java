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
import static com.example.beta.FBref.refINC;

/**
 * The type Incomes.
 */
public class Incomes extends AppCompatActivity {


    EditText et1, et2, et3;
    int x = 0, pricei, monthI, deuid2;
    TextView tvdate;
    String Iuid = " ", DIuid = " ", uidi = " ", uidi2 = " ";
    Spinner Spinner;
    private IncomesC inc;
    public ArrayList<String> incList = new ArrayList<String>();
    public ArrayList<String> incList2 = new ArrayList<String>();
    public ArrayList<Integer> incList3 = new ArrayList<Integer>();
    public ArrayList<Integer> incList4 = new ArrayList<Integer>();
    String[] spinE = {"Date", "type", "price"};
    String str1, str2;
    int str3, str4;
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
        setContentView(R.layout.activity_incomes);

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
                uidi = dayOfMonth + "/" + month + "/" + year;
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                tvdate.setText(uidi);
                DIuid = String.valueOf(year) + String.valueOf(month) + String.valueOf(dayOfMonth) + String.valueOf(hour) + String.valueOf(minute) + String.valueOf(second);
                uidi2 = String.valueOf(dayOfMonth) + String.valueOf(month) + String.valueOf(year);
                monthI = month;
            }
        };

        FirebaseUser user = mAuth.getCurrentUser();
        Iuid = user.getUid();


        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinE);
        Spinner.setAdapter(adp);

        Query query = refINC.child(Iuid).orderByChild("idate");
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dS) {
                incList.clear();
                incList2.clear();
                incList3.clear();
                incList4.clear();

                for (DataSnapshot data : dS.getChildren()) {
                    IncomesC inc2 = data.getValue(IncomesC.class);

                    str1 = inc2.getItype();
                    str2 = inc2.getIdate();
                    str3 = inc2.getIprice();
                    str4 = inc2.getImonth();
                    incList.add(str1 + "");
                    incList2.add(str2 + "");
                    incList3.add(str3);
                    incList4.add(str4);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    /**
     * Csvi.
     * כאשר הכפתור נלחץ הנתונים המשתמש כתב מועברים לfurebase
     * הנתונים שיש בבסי הנתונים נקראים וממוינים לפי מה שהמשתמש בחר ברשימה
     * @param view the view
     */
    public void csvi(View view) {

        String sug = et1.getText().toString();
        String price = et3.getText().toString();
        pricei = Integer.parseInt(price);


        inc = new IncomesC(sug,uidi, pricei, monthI, Iuid);
        refINC.child(Iuid).child(DIuid).setValue(inc);


        if (Spinner.getSelectedItemPosition() == 0) {
            Query query = refINC.child(Iuid).orderByChild(DIuid);
            query.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dS) {
                    incList.clear();
                    incList2.clear();
                    incList3.clear();
                    incList4.clear();

                    for (DataSnapshot data : dS.getChildren()) {
                        IncomesC inc2 = data.getValue(IncomesC.class);

                        str1 = inc2.getItype();
                        str2 = inc2.getIdate();
                        str3 = inc2.getIprice();
                        str4 = inc2.getImonth();
                        incList.add(str1 + "");
                        incList2.add(str2+ "");
                        incList3.add(str3);
                        incList4.add(str4);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        if (Spinner.getSelectedItemPosition() == 1) {
            Query query = refINC.child(Iuid).orderByChild("itype");
            query.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dS) {
                    incList.clear();
                    incList2.clear();
                    incList3.clear();
                    incList4.clear();

                    for (DataSnapshot data : dS.getChildren()) {
                        IncomesC inc2 = data.getValue(IncomesC.class);

                        str1 = inc2.getItype();
                        str2 = inc2.getIdate();
                        str3 = inc2.getIprice();
                        str4 = inc2.getImonth();
                        incList.add(str1 + "");
                        incList2.add(str2+ "");
                        incList3.add(str3);
                        incList4.add(str4);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        if (Spinner.getSelectedItemPosition() == 2) {
            Query query = refINC.child(Iuid).orderByChild("iprice");
            query.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dS) {
                    incList.clear();
                    incList2.clear();
                    incList3.clear();
                    incList4.clear();

                    for (DataSnapshot data : dS.getChildren()) {
                        IncomesC inc2 = data.getValue(IncomesC.class);

                        str1 = inc2.getItype();
                        str2 = inc2.getIdate();
                        str3 = inc2.getIprice();
                        str4 = inc2.getImonth();
                        incList.add(str1 + "");
                        incList2.add(str2+ "");
                        incList3.add(str3);
                        incList4.add(str4);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        /**
         * תיבת דו שיח שכאשר המשתמש לוחץ כל לא עכשיו לא קורה כלום
         * כאשר המשתמש לוחץ על העלאה נוצרת טבלת csv והיא נשלחת אליו
         */

        adb = new AlertDialog.Builder(this);
        adb.setTitle("upload a table?");
        adb.setMessage("do you want to upload the table right now?");
        adb.setPositiveButton("UPLOAD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Data.setLength(0);
                Data.append("סכום ,תאריך, ");
                for (int k = 0; k < incList.size(); k++) {
                    Data.append("\n" + incList3.get(k) + "," + incList2.get(k) + "," + incList.get(k));
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
        if (st.equals("Graphs")){
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