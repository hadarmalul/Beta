package com.example.beta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static com.example.beta.FBref.mAuth;
import static com.example.beta.FBref.refEX;
import static com.example.beta.FBref.refbus;

public class Expenses extends AppCompatActivity {

    EditText et1, et2, et3;
    String Etype, Edate, Eprice, Euid;
    private expensesC exp;
  //  String[] sugarr = {"1","2","3"};
    String[] datearr = {"1","2","3"};
    String[] pricearr = {"1","2","3"};
    UserC expU;
    ArrayList<String> exList = new ArrayList<String>();
    ArrayList<expensesC> exValues = new ArrayList<expensesC>();
    String str1, str2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);

        ValueEventListener stuListener = new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot ds) {

                exList.clear();
                exValues.clear();
                for (DataSnapshot data : ds.getChildren()) {

                    str1 = (String) data.getKey();
                    expensesC stuTmp = data.getValue(expensesC.class);
                    exValues.add(stuTmp);
                    str2 = stuTmp.getEtype();
                    exList.add(str1 + " " + str2);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

    }




    public void csve(View view) {


        String sug = et1.getText().toString();
        String date = et2.getText().toString();
        String price = et3.getText().toString();


        FirebaseUser user = mAuth.getCurrentUser();
        Euid = user.getUid();

        exp = new expensesC(sug , date, price, Euid);
        refbus.child(Euid).setValue(exp);


        StringBuilder data = new StringBuilder();
        data.append("סכום ,תאריך, ");
        for (int i = 0; i<pricearr.length; i++) {
            data.append("\n" + pricearr[i] + "," + datearr[i] + "," + exList.get(i));
        }
        try {
            FileOutputStream out = openFileOutput("data.csv", Context.MODE_PRIVATE);
            out.write((data.toString()).getBytes());
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
}



