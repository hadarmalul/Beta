package com.example.beta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import static com.example.beta.FBref.refEX;
import static com.example.beta.FBref.refU;

// סטורג אימייל שם מהפיירבייס גרף

/**
 * The type Main activity.
 * @author hadarmalul
 */
public class MainActivity extends AppCompatActivity {


    TextView tVtitle, tVregister;
    EditText eTname, eTemail, eTpass;
    CheckBox cBstayconnect;
    Button btn;
    String name, desc, email, password, uid;
    UserC userdb;
    Boolean stayConnect, registered, firstrun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tVtitle=(TextView) findViewById(R.id.tVtitle);
        eTname=(EditText)findViewById(R.id.eTname);
        eTemail=(EditText)findViewById(R.id.eTemail);
        eTpass=(EditText)findViewById(R.id.eTpass);
        cBstayconnect=(CheckBox)findViewById(R.id.cBstayconnect);
        tVregister=(TextView) findViewById(R.id.tVregister);
        btn=(Button)findViewById(R.id.btn);

        stayConnect=false;
        registered=true;

        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();

        regoption();
    }

    /**protected void sendEmail() {

        Log.i("Send email", "");

        String[] TO = {userdb.getmail()};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Send Email");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Welcome, "+userdb.getname());

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email.", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }**/



    /**
     * בודקת אם המשתמש בחר להשאר מחובר לאפליקציה
     * אם כן, עובר למסך הבא
     * אם לא, נפתח מסך ההתחברות.
     */

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        Boolean isChecked=settings.getBoolean("stayConnect",false);
        Intent si = new Intent(MainActivity.this,PersonalArea.class);
        if (mAuth.getCurrentUser()!=null && isChecked) {
            stayConnect=true;
            si.putExtra("newuser",false);
            startActivity(si);
        }
    }

    /**
     * אם המשתמש התחבר וביקש להשאר מחובר הפעולה מורידה את המסך
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (stayConnect) finish();
    }

    /**
     * הפעולה מופעלת כאשר המשתמש נכנס לראשונה או כאשר הוא צריך להרשם
     * הפעולה מציגה את כל הפרטים שהמשתמש צריך למלא בהרשמה
     */

    private void regoption() {
        SpannableString ss = new SpannableString("Don't have an account?  Register here!");
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                tVtitle.setText("Register");
                eTname.setVisibility(View.VISIBLE);
                btn.setText("Register");
                registered=false;
                logoption();
            }
        };
        ss.setSpan(span, 24, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tVregister.setText(ss);
        tVregister.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * כאשר המשתמש רוצה לעבור ממסך הרשמה להתחברות הפעולה מציגה את המסך
     * המסך נפתח בכל פתיחה שהיא לא ראשונית
     */

    private void logoption() {
        SpannableString ss = new SpannableString("Already have an account?  Login here!");
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                tVtitle.setText("Login");
                eTname.setVisibility(View.INVISIBLE);
                btn.setText("Login");
                registered=true;
                regoption();
            }
        };
        ss.setSpan(span, 26, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tVregister.setText(ss);
        tVregister.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     *אם המשתמש במצב התחברות הפעולה תאמת את השם משתמש וסיסמא
     * אם המשתמש במצב הרשמה הפעולה תבדוק את הנתונים שהוכנסו ובנוסף תעלה אותם אל ה-firebase
     * @param view the view
     */
    public void logorreg(View view) {
        if (registered) {
            email=eTemail.getText().toString();
            password=eTpass.getText().toString();

            final ProgressDialog pd=ProgressDialog.show(this,"Login","Connecting...",true);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();
                            if (task.isSuccessful()) {
                                SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
                                SharedPreferences.Editor editor=settings.edit();
                                editor.putBoolean("stayConnect",cBstayconnect.isChecked());
                                editor.commit();
                                Log.d("MainActivity", "signinUserWithEmail:success");
                                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                Intent si = new Intent(MainActivity.this,PersonalArea.class);
                                si.putExtra("newuser",false);
                                startActivity(si);
                            } else {
                                Log.d("MainActivity", "signinUserWithEmail:fail");
                                Toast.makeText(MainActivity.this, "e-mail or password are wrong!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            name=eTname.getText().toString();
            email=eTemail.getText().toString();
            password=eTpass.getText().toString();

            final ProgressDialog pd=ProgressDialog.show(this,"Register","Registering...",true);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();
                            if (task.isSuccessful()) {
                                SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
                                SharedPreferences.Editor editor=settings.edit();
                                editor.putBoolean("stayConnect",cBstayconnect.isChecked());
                                editor.commit();
                                Log.d("MainActivity", "createUserWithEmail:success");
                                //sendEmail();
                                FirebaseUser user = mAuth.getCurrentUser();
                                uid = user.getUid();
                                userdb=new UserC(name, " ",email," ",uid);
                                refU.child(uid).setValue(userdb);
                                Toast.makeText(MainActivity.this, "Successful registration", Toast.LENGTH_SHORT).show();
                                Intent si = new Intent(MainActivity.this,PersonalArea.class);
                                si.putExtra("newuser",true);
                                startActivity(si);
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                    Toast.makeText(MainActivity.this, "User with e-mail already exist!", Toast.LENGTH_SHORT).show();
                                else {
                                    Log.w("MainActivity", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "User create failed.",Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
        }
    }
}

