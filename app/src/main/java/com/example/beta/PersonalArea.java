package com.example.beta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

import static com.example.beta.FBref.mAuth;
import static com.example.beta.FBref.refINC;
import static com.example.beta.FBref.refU;

/**
 * The type Personal area.
 */
public class PersonalArea extends AppCompatActivity {

    ImageButton imageButton;
    TextView tv;
    StorageReference mstorageRef;
    public Uri imguri;
    UserC user;
    String s, photo, name, uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_area);

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        tv = (TextView) findViewById(R.id.tv);

        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();

        Query query = refU.orderByChild("name");
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dS) {

                for (DataSnapshot data : dS.getChildren()) {
                    UserC user = data.getValue(UserC.class);

                    tv.setText("hello, "+user.getname());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mstorageRef = FirebaseStorage.getInstance().getReference("Images");


        try {
            Download();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    public void Download() throws IOException {
        StorageReference refImages=mstorageRef.child(uid+".jpg");
        final File localFile;
        localFile = File.createTempFile(uid,"jpg");
        refImages.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>(){
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                String filePath = localFile.getPath();
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                imageButton.setImageBitmap(bitmap);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(PersonalArea.this, "Image download failed", Toast.LENGTH_LONG).show();
            }
        });
    }


    private String getExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    /**
     * Fileuploader.
     * עובר לגלריה ונותן למשתמש לבחור תמונה
     *מעלה את התמונה לfirebase
     * @param view the view
     */
    public void filechooser(View view) {
        Intent si=new Intent();
        si.setType("image/*");
        si.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(si,1);

    }


    public void fileuploader(View view) {

        StorageReference Ref=mstorageRef.child(uid+".jpg");
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
            imageButton.setImageURI(imguri);
        }
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
