package gdg.ng.pack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailSignUpActivity extends AppCompatActivity {

    private static final String TAG = "EmailSignUpActivity";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_sign_up);

        //Remove any existing users
        FirebaseAuth.getInstance().signOut();
        Button button_signup = (Button) findViewById(R.id.button_sign_up);
        final EditText editText_confirm_password = (EditText) findViewById(R.id.editText_confirm_password);
        final EditText editText_password = (EditText) findViewById(R.id.editText_password);
        final EditText editText_email = (EditText) findViewById(R.id.editText_email);

        sharedPreferences = getSharedPreferences(Constants.PACK_USER_DETAILS_PREFS, MODE_PRIVATE);
        progressDialog = new ProgressDialog(this);


        if (button_signup != null) {
            button_signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = editText_email.getText().toString();
                    String password = editText_password.getText().toString();
                    String confirm_password = editText_confirm_password.getText().toString();

                    boolean clear = true;

                    if (!PackHelper.isValidEmail(email)) {
                        clear = false;
                        editText_email.setError("Invalid email");
                    }

                    if (password.length() < 5) {
                        clear = false;
                        editText_password.setError("Password should contain at least 5 characters");
                    } else {
                        if (!password.equals(confirm_password)) {
                            clear = false;
                            editText_confirm_password.setError("Invalid password");
                        }
                    }

                    if (clear) {
                        CreateEmailAccount(email, password);
                    }

                }
            });
        }

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(EmailSignUpActivity.this, "Account created!", Toast.LENGTH_SHORT).show();
                    PackHelper.setSharedPreferences(user, sharedPreferences.edit());
                    Intent intent = new Intent(EmailSignUpActivity.this, SignInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }


            }
        };

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

    public void showProgressDialog() {
        progressDialog = progressDialog == null ? new ProgressDialog(this) : progressDialog;
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
    }

    public void hideProgressDialog() {
        progressDialog.cancel();
    }


    private void CreateEmailAccount(String email, String password) {
        showProgressDialog();

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
                            Toast.makeText(EmailSignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        hideProgressDialog();
                    }
                });
    }
}