package com.example.beta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * The type Personal area.
 */
public class PersonalArea extends AppCompatActivity {

    ImageButton imageButton;
    TextView tv;
    StorageReference mstorageRef;
    public Uri imguri;
    UserC user;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_area);

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        tv = (TextView) findViewById(R.id.tv);

        mstorageRef= FirebaseStorage.getInstance().getReference("Images");


               // tv.setText("hello, " + user.getname());


    }

    private String getExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    /**
     * Fileuploader.
     *מעלה את התמונה לfirebase
     * @param view the view
     */
    public void fileuploader(View view) {

        StorageReference Ref=mstorageRef.child(System.currentTimeMillis()+"."+getExtension(imguri));
        Ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(PersonalArea.this,"image uploaded successfully",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imguri=data.getData();
            imageButton.setImageResource(android.R.color.transparent);
            imageButton.setImageURI(imguri);
        }
    }

    /**
     * Filechooser.
     *עובר לגלריה ונותן למשתמש לבחור תמונה
     * @param view the view
     */
    public void filechooser(View view) {

        Intent si=new Intent();
        si.setType("image/*");
        si.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(si,1);
    }


    /**
     * Ex.
     *אם הכפתור נלחץ המסך עובר למסך ההוצאות
     * @param view the view
     */
    public void ex(View view) {
        Intent ssi = new Intent(PersonalArea.this,Expenses.class);
        startActivity(ssi);
    }

    /**
     * Inc.
     *אם הכפתור נלחץ המסך עובר למסך ההכנסות
     * @param view the view
     */
    public void inc(View view) {

        Intent ssi = new Intent(PersonalArea.this,Incomes.class);
        startActivity(ssi);
    }

    /**
     * Stat.
     *אם הכפתור נלחץ המסך עובר למסך הסטטיסטיקה
     * @param view the view
     */
    public void stat(View view) {
        Intent ssi = new Intent(PersonalArea.this,Graphs.class);
        startActivity(ssi);
    }
}
