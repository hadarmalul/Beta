package com.example.beta;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FBref {

    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();
    public static DatabaseReference refEUID = FBDB.getReference("EXuid");
    public static DatabaseReference refEX = refEUID.child("Exp");
    public static DatabaseReference refIN = refEUID.child("Inc");

}
