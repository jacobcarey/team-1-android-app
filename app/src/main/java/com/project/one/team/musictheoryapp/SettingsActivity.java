package com.project.one.team.musictheoryapp;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * <p>Activity for controlling various settings in the app.</p>
 *
 * <p>This activity enables the user to toggle the app-wide night mode theme, which is persisted
 * across app restarts using the {@link Theoryously} application state class.</p>
 *
 * <p>The SettingsActivity also handles the <a href="https://firebase.google.com/">Firebase</a>
 * authentication integration within the app. Using Firebase, the user is able to create or log in
 * to an account which enables them to save their topic progress as well as receive push notifications
 * when new topics or quizzes are added to the app.</p>
 *
 * @author Team One
 *
 * @see SignUpActivity
 * @see com.project.one.team.musictheoryapp.FirebaseMessaging
 */

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
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        final MediaPlayer buttonMP = MediaPlayer.create(this, R.raw.menu_click);

        ToggleButton nmButton = (ToggleButton) findViewById(R.id.nightModeToggle);
        if (((Theoryously) getApplication()).getNightMode()) {
            nmButton.setChecked(true);
        } else {
            nmButton.setChecked(false);
        }

        final ToggleButton notificationsToggle = (ToggleButton) findViewById(R.id.notifications);

        nmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonMP.start();
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
                buttonMP.start();
                if (!((Theoryously) getApplication()).getSignedIn()) {
                    userEmail.setText("");
                }
            }
        });

        final EditText userPass = (EditText) findViewById(R.id.userPass);
        userPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonMP.start();
                if (!((Theoryously) getApplication()).getSignedIn()) {
                    userPass.setText("");
                }
            }
        });

        final Button loginInButton = (Button) findViewById(R.id.loginSubmit);
        final Button signUpButton = (Button) findViewById(R.id.signUp);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonMP.start();
                if (!((Theoryously) getApplication()).getSignedIn()) {
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    Intent i = new Intent(SettingsActivity.this, SignUpActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_left,
                            R.anim.slide_right_out);
                    recreate();
                } else {
                    Toast.makeText(SettingsActivity.this, R.string.ask_sign_out,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        loginInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonMP.start();

                if (((Theoryously) getApplication()).getSignedIn()) {
                    mAuth.signOut();
                    ((Theoryously) getApplication()).setSignedIn(false);
                    userEmail.setText("Email");
                    recreate();
                } else {
                    if (userEmail.getText().toString().isEmpty() || userPass.getText().toString().isEmpty()) {
                        //Snackbar.make(findViewById(R.id.settingsLayout),
                                //"Email/Password cannot be blank!", Snackbar.LENGTH_LONG).show();
                        return;
                    }


                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    String user = userEmail.getText().toString();
                    String password = userPass.getText().toString();
                    signInWithEmail(user, password);
//                    recreate();
                }
            }
        });

        notificationsToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonMP.start();
                final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (notificationsToggle.isChecked()) {
                    FirebaseMessaging.getInstance().subscribeToTopic("all");
                    database.child("users").child(userId).child("notifications").setValue("true");
                } else {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("all");
                    database.child("users").child(userId).child("notifications").setValue("false");
                }

            }
        });

        final Button resetButton = (Button) findViewById(R.id.reset);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Progression.getInstance(getApplicationContext()).resetAll();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    notificationsToggle.setEnabled(true);
                    final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    database.getDatabase().getReference().child("users").child(userId).child("notifications").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String notificationBoolStr = dataSnapshot.getValue(String.class);
                            if (notificationBoolStr.equals("true")) {
                                notificationsToggle.setChecked(true);
                            } else {
                                notificationsToggle.setChecked(false);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    userPass.setEnabled(false);
                    userEmail.setEnabled(false);
                    userEmail.setText("Already signed in.");
                    userPass.setText("");
                    loginInButton.setText("Sign Out");

                    // Update the user's progression when they log in.
                    Progression.getInstance(SettingsActivity.this).getProgression("basic");
                    //Progression.getInstance(SettingsActivity.this).getProgression("intermediate");
                    //Progression.getInstance(SettingsActivity.this).getProgression("advanced");

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    notificationsToggle.setEnabled(false);
                    userPass.setEnabled(true);
                    userEmail.setEnabled(true);
                    userEmail.setText("Email:");
                    userPass.setText("Password");
                    loginInButton.setText("Sign In");
                    notificationsToggle.setChecked(false);
                    notificationsToggle.setEnabled(false);
                }
                // ...
            }
        };

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
                            ((Theoryously) getApplication()).setSignedIn(true);
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
