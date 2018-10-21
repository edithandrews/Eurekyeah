package com.vit.mmsb.eureka;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.FirebaseApp;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;

        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.firestore.FirebaseFirestore;


        import java.util.HashMap;
        import java.util.Map;

public class Register extends AppCompatActivity {

    private static final String USER_KEY = "Username";
    private static final String REG_KEY = "Register No";
    private static final String MAIL_KEY = "Email Address";
    private static final String PASS_KEY = "Password";
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;

    FirebaseFirestore db;

    private void updateUI(FirebaseUser user) {
        if (user != null){
        Intent myIntent = new Intent(Register.this, Feed.class);
        startActivity(myIntent);}
        else
        {
            Intent myIntent = new Intent(Register.this,
                    Register.class);
            startActivity(myIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(this);

        db = FirebaseFirestore.getInstance();

        Button b1 = findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(Register.this,
                        MainActivity.class);
                startActivity(myIntent);
            }
        });

    }

    protected void onClick(View v){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        EditText simpleEditText = findViewById(R.id.editText2);
        String uname = simpleEditText.getText().toString();

        simpleEditText = findViewById(R.id.editText3);
        String regno = simpleEditText.getText().toString();

        simpleEditText = findViewById(R.id.editText);
        String mail = simpleEditText.getText().toString();

        simpleEditText = findViewById(R.id.editText7);
        String pass = simpleEditText.getText().toString();

        Map<String, Object> User = new HashMap<>();
        User.put(USER_KEY, uname);
        User.put(REG_KEY, regno);
        User.put(MAIL_KEY, mail);
        User.put(PASS_KEY, pass);
        db.collection("Users").document(mail)
                .set(User)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

        mAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });

    }}