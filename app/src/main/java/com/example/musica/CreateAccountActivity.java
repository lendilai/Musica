package com.example.musica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.nameEditText) EditText mUserName;
    @BindView(R.id.emailEditText) EditText mUserEmail;
    @BindView(R.id.passwordEditText) EditText mUserPassword;
    @BindView(R.id.confirmPasswordEditText) EditText mConfirmPassword;
    @BindView(R.id.createUserButton) Button mCreateUserButton;
    @BindView(R.id.loginTextView) TextView mLoginText;

    public static final String TAG = CreateAccountActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);

        mLoginText.setOnClickListener(this);
        mCreateUserButton.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        createAuthStateListener();
    }

    @Override
    public void onClick(View view){
        if(view == mLoginText){
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else if(view == mCreateUserButton){
            createNewUser();
            Toast.makeText(CreateAccountActivity.this, "clicked", Toast.LENGTH_SHORT).show();
        }

    }

    private void createNewUser(){
        mName = mUserName.getText().toString().trim();
        final String name = mUserName.getText().toString().trim();
        final String email = mUserEmail.getText().toString().trim();
        String password  = mUserPassword.getText().toString().trim();
        String confirmation = mConfirmPassword.getText().toString().trim();

        boolean isValidEmail = isGoodEmail(email);
        boolean isValidPassword = isGoodPassword(password, confirmation);
        boolean isValidName = isValidName(name);

        if (!isValidEmail || !isValidName || !isValidPassword) return;


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(CreateAccountActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    createFireBaseUser(task.getResult().getUser());
                }else {
                    Toast.makeText(CreateAccountActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createFireBaseUser(final FirebaseUser user){
        UserProfileChangeRequest addUserProfile = new UserProfileChangeRequest.Builder().setDisplayName(mName).build();

        user.updateProfile(addUserProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.d(TAG, user.getDisplayName());
                }
            }
        });
    }

    private boolean isGoodEmail(String email){
        boolean isValidEmail = (email != null | Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if(!isValidEmail){
            mUserEmail.setError("Enter a valid email");
            return false;
        }
        return true;
    }

    private boolean isValidName(String name){
        if(name == ""){
            mUserName.setError("Enter your username");
            return false;
        }
        return true;
    }

    private boolean isGoodPassword(String password, String confirmation){
        if(password.length() < 6){
            mUserPassword.setError("Password should exceed 6 characters");
            return false;
        }else if(!password.equals(confirmation)){
            mConfirmPassword.setError("Passwords do not match");
            return false;
        }
        return true;
    }

    private void createAuthStateListener(){
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                 Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                 startActivity(intent);
                 finish();
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null){
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
}
