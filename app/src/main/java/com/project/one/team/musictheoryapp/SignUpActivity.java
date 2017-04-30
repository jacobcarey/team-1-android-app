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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.project.one.team.musictheoryapp.R.id.userEmail;
import static com.project.one.team.musictheoryapp.R.id.userPass;

/**
 * <p>Activity for handling user account sign up.</p>
 * <p>
 * <p>The Activity makes use <a href="https://firebase.google.com/">Firebase</a> authentication,
 * allowing the user to create an account with a Display Name, Email Address and Password. Upon creation,
 * the user is automatically logged into their new account and their Display Name is set.</p>
 *
 * @author Team One
 */

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "SignUpActivity";
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.getDefaultNightMode();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();


        final EditText userNameInput = (EditText) findViewById(R.id.userName);
        userNameInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameInput.setText("");
            }
        });

        final EditText userEmailInput = (EditText) findViewById(userEmail);
        userEmailInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmailInput.setText("");
            }
        });

        final EditText userPassInput = (EditText) findViewById(userPass);
        userPassInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPassInput.setText("");
            }
        });

        Button signUpButton = (Button) findViewById(R.id.signUp);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                String user = userEmailInput.getText().toString();
                String password = userPassInput.getText().toString();
                createAccount(user, password);
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    ((Theoryously) getApplication()).setSignedIn(true);
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(userNameInput.getText().toString()).build();
                    user.updateProfile(profileUpdates);

                    Intent i = new Intent(SignUpActivity.this, SettingsActivity.class);
                    startActivity(i);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.


                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUpActivity.this, R.string.auth_signUp,
                                    Toast.LENGTH_SHORT).show();
                            final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            FirebaseMessaging.getInstance().subscribeToTopic("all");
                            database.child("users").child(userId).child("notifications").setValue("true");
                        }

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    /**
     * Ensures that the input fields for registering a user account are not empty, and that the
     * entered email address is of a valid format.
     * @return <code>true</code> if everything in form is valid, <code>false</code> otherwise.
     */
    private boolean validateForm() {
        final EditText userNameInput = (EditText) findViewById(R.id.userName);
        final EditText userEmailInput = (EditText) findViewById(userEmail);
        final EditText userPassInput = (EditText) findViewById(userPass);
        Pattern emailPattern = Pattern.compile("[A-Z0-9._+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}");
        Matcher matcher = emailPattern.matcher(userEmailInput.getText().toString());


        // Check for empty fields
        if (userNameInput.getText().toString().isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Username cannot be blank!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (userEmailInput.getText().toString().isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Email cannot be blank!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!matcher.matches()) {
            Toast.makeText(SignUpActivity.this, "Email is invalid!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (userPassInput.getText().toString().isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Password cannot be blank!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
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
        Intent i = new Intent(SignUpActivity.this, SettingsActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_right, R.anim.slide_left_out);
    }
}

