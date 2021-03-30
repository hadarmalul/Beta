package com.example.beta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import static com.example.beta.FBref.mAuth;
import static com.example.beta.FBref.refbus;

public class MainActivity extends AppCompatActivity {


    TextView tvBTitle, tvBRegister;
    String nameU = "", uidU = "", mailU = "", passU = "";
    EditText etBmail, etBpass, etname;
    // private FirebaseAuth mAuth;
    Boolean stayConnect, registered; //isUID=false,mVerificationProgress=false;
    Button btnB;
    UserC userBdb;
    CheckBox cBstayconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etBmail = (EditText) findViewById(R.id.Bmail);
        etname = (EditText) findViewById(R.id.etname);
        etBpass = (EditText) findViewById(R.id.Bpass);
        btnB = (Button) findViewById(R.id.btnB);
        tvBRegister = (TextView) findViewById(R.id.tvRegister);
        tvBTitle = (TextView) findViewById(R.id.tvTitle);
        cBstayconnect = (CheckBox) findViewById(R.id.cBstayconnect);
        stayConnect = false;
        registered = true;
        regOption();

    }


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
        Boolean isChecked = settings.getBoolean("stayConnect", false);
        Intent si = new Intent(MainActivity.this, PersonalArea.class);
        if ((mAuth.getCurrentUser() != null) && (isChecked)) {
            stayConnect = true;
            si.putExtra("UserB", false);
            startActivity(si);
        }
    }

    /**
     * On activity pause - If logged in & asked to be remembered - kill activity.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (stayConnect) finish();
    }

    /**
     * this function is called when the user is in the login option but he needs to register
     * OR
     * when the application is running for the first time in the user's device
     * the function "changes" the screen for the register option.
     */
    private void regOption() {
        SpannableString ss = new SpannableString("Don't Have an Account? Register Here!");
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                tvBTitle.setText("Register");
                etBmail.setVisibility(View.VISIBLE);
                etBpass.setVisibility(View.VISIBLE);
                etname.setVisibility(View.VISIBLE);
                btnB.setText("Register");
                registered = false;
                logOption();
            }
        };
        ss.setSpan(span, 24, 36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvBRegister.setText(ss);
        tvBRegister.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void logOption() {
        SpannableString ss = new SpannableString("Already Have an Account? Login Here!");
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                tvBTitle.setText("Login");
                etBmail.setVisibility(View.INVISIBLE);
                etBpass.setVisibility(View.VISIBLE);
                btnB.setText("Login");
                registered = true;
                regOption();
            }
        };
        ss.setSpan(span, 26, 34, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvBRegister.setText(ss);
        tvBRegister.setMovementMethod(LinkMovementMethod.getInstance());
    }


    public void logOrReg(View view) {
        mailU = etBmail.getText().toString();
        passU = etBpass.getText().toString();

        if (mailU.isEmpty() || passU.isEmpty())
            Toast.makeText(this, "please fill all the necessary fields", Toast.LENGTH_SHORT).show();
        else {
            if ((!mailU.contains("@") || !mailU.endsWith(".il")) && (!mailU.endsWith(".com") || !mailU.contains("@"))) {
                etBmail.setError("Mail is Invalid!");
            }
            if (passU.length() < 6) {
                etBpass.setError("Password Needs To Be At Least 6 Characters!");
            } else {
                if (registered) {
                    mailU = etBmail.getText().toString();
                    passU = etBpass.getText().toString();

                    final ProgressDialog pd = ProgressDialog.show(this, "Login", "Connecting...", true);
                    mAuth.signInWithEmailAndPassword(mailU, passU).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();
                            if (task.isSuccessful()) {
                                SharedPreferences settings = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putBoolean("stayConnect", cBstayconnect.isChecked());
                                editor.commit();
                                Log.d("BRegistr", "signinUserWithEmail:success");
                                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                Intent si = new Intent(MainActivity.this, PersonalArea.class);
                                si.putExtra("UserB", false);
                                startActivity(si);
                            } else {
                                Log.d("BRegistr", "signinUserWithEmail:fail");
                                Toast.makeText(MainActivity.this, "email or password are wrong!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    mailU = etBmail.getText().toString();
                    passU = etBpass.getText().toString();
                    nameU = etname.getText().toString();
                    final ProgressDialog pd = ProgressDialog.show(this, "Register", "Registering...", true);
                    mAuth.createUserWithEmailAndPassword(mailU, passU).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();
                            if (task.isSuccessful()) {
                                SharedPreferences settings = getSharedPreferences("PREF_NAME", MODE_PRIVATE);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putBoolean("stayConnect", cBstayconnect.isChecked());
                                editor.commit();
                                Log.d("BRegistr", "createUserWithEmail:success");
                                /*FirebaseUser UserB = mAuth.getCurrentUser();
                                uidU = UserB.getUid();
                                userBdb = new UserC(nameU, passU, mailU, "", "");
                                refbus.child(uidU).setValue(userBdb);**/
                                Toast.makeText(MainActivity.this, "Successful Registration", Toast.LENGTH_SHORT).show();
                                Intent si = new Intent(MainActivity.this, PersonalArea.class);
                                startActivity(si);
                                finish();
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                    Toast.makeText(MainActivity.this, "User With Email Alreasy Exist!", Toast.LENGTH_SHORT).show();
                                else {
                                    Log.w("BRegistr", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "User Creat Failed", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                }
            }
        }
    }

}

