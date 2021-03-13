package com.example.beta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.FileOutputStream;

import static com.example.beta.FBref.mAuth;
import static com.example.beta.FBref.refEUID;
import static com.example.beta.FBref.refEX;
import static com.example.beta.FBref.refIN;

public class Incomes extends AppCompatActivity {

    EditText et1, et2, et3;
    String Iuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incomes);


        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
    }

    private void updateINui(FirebaseUser currentUser){

    }


    public void csvi(View view) {

        String sug = et1.getText().toString();
        String date = et2.getText().toString();
        String sum = et3.getText().toString();


        FirebaseUser user = mAuth.getCurrentUser();
        Iuid = user.getUid();

        updateINui(user);

        refIN.child("type").setValue(sug);
        refIN.child("date").setValue(date);
        refIN.child("price").setValue(sum);

        StringBuilder data = new StringBuilder();
        data.append("סכום ,תאריך, ");
        data.append("\n" + sum + "," + date + "," + sug);

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
