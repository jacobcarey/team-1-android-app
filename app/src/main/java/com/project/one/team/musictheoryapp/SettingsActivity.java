package com.project.one.team.musictheoryapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "SettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.getDefaultNightMode();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        ToggleButton nmButton = (ToggleButton) findViewById(R.id.nightModeToggle);
        if (((Theoryously) getApplication()).getNightMode()) {
            nmButton.setChecked(true);
        } else {
            nmButton.setChecked(false);
        }



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    ((Theoryously) getApplication()).setSignedIn(true);

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        nmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((Theoryously) getApplication()).getNightMode()) {
                    ((Theoryously) getApplication()).setNightMode(false);
                } else {
                    ((Theoryously) getApplication()).setNightMode(true);
                }
                recreate();
            }
        });

        final EditText userEmail = (EditText) findViewById(R.id.userEmail);
        userEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!((Theoryously) getApplication()).getSignedIn()) {
                    userEmail.setText("");
                }
            }
        });

        final EditText userPass = (EditText) findViewById(R.id.userPass);
        userPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!((Theoryously) getApplication()).getSignedIn()) {
                    userPass.setText("");
                }
            }
        });

        Button loginInButton = (Button) findViewById(R.id.loginSubmit);
        Button signUpButton = (Button) findViewById(R.id.signUp);

        if(((Theoryously) getApplication()).getSignedIn()){
            userEmail.setFocusable(false);
            userPass.setFocusable(false);
            userEmail.setFocusableInTouchMode(false);
            userPass.setFocusableInTouchMode(false);
            userEmail.setText("Already signed in.");
            userPass.setText("");
            loginInButton.setText("Sign Out");
        }

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!((Theoryously) getApplication()).getSignedIn()) {
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    Intent i = new Intent(SettingsActivity.this, SignUpActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_left,
                            R.anim.slide_right_out);
                    recreate();
                }else{
                    Toast.makeText(SettingsActivity.this, R.string.ask_sign_out,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        loginInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(((Theoryously) getApplication()).getSignedIn()){
                    mAuth.signOut();
                    ((Theoryously) getApplication()).setSignedIn(false);
                    userEmail.setText("Email");
                    recreate();
                }else {
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    String user = userEmail.getText().toString();
                    String password = userPass.getText().toString();
                    signInWithEmail(user, password);
                    recreate();
                }
            }
        });

    }

    private void signInWithEmail(String email, String password) {
        Log.d(TAG, "Login:" + email);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(SettingsActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SettingsActivity.this, R.string.auth_pass,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SettingsActivity.this, MainPageActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_right, R.anim.slide_left_out);
    }
}
