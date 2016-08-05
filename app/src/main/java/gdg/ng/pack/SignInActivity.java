package gdg.ng.pack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class SignInActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private SignInButton mSignInButton;
    private LoginButton button_facebook;
    private Button button_email;
    private EditText editText_email;
    private EditText editText_password;
    private ProgressDialog progressDialog;
    private TwitterLoginButton twitterLoginButton;

    private CallbackManager mCallbackManager;

    private GoogleApiClient mGoogleApiClient;

    private FirebaseAuth.AuthStateListener mAuthListener;

    // Firebase instance variables
    private com.google.firebase.auth.FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_sign_in);

        // Initialize FirebaseAuth
        mFirebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent = new Intent(SignInActivity.this, MyReport.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        // Assign fields
        mSignInButton = (SignInButton) findViewById(R.id.google_sign_in_button);
        button_facebook = (LoginButton) findViewById(R.id.button_facebook);
        button_email = (Button) findViewById(R.id.button_email_sign_in);
        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_password = (EditText) findViewById(R.id.editText_password);
        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.button_twitter_login);
        TextView signUpText = (TextView) findViewById(R.id.sign_up);

        if (signUpText != null) {
            signUpText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SignInActivity.this, EmailSignUpActivity.class);
                    startActivity(intent);
                }
            });
        }

        // Set click listeners
        mSignInButton.setOnClickListener(this);
        button_email.setOnClickListener(this);
        button_facebook.setOnClickListener(this);
        twitterLoginButton.setOnClickListener(this);


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;
                AuthCredential credential = TwitterAuthProvider.getCredential(
                        session.getAuthToken().token,
                        session.getAuthToken().secret);
                handleSession(credential);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d(TAG, "twitterLogin:failure", exception);
                Toast.makeText(SignInActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        });

        mCallbackManager = CallbackManager.Factory.create();
        button_facebook.setReadPermissions("email", "public_profile");
        button_facebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
                handleSession(credential);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                Toast.makeText(SignInActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                Toast.makeText(SignInActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void signInPassword(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        showProgressDialog();

        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail :", task.getException());

                            //for debugging purposes, set errorMessage to task.getException().getLocalizedMessage()

                            String errorMessage = task.getException() == null ? "" : "Invalid credentials.";
                            Toast.makeText(SignInActivity.this, errorMessage,
                                    Toast.LENGTH_SHORT).show();
                        }

                        hideProgressDialog();
                    }
                });

    }

    private void GoogleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View v) {

        //clear any existing user
        FirebaseAuth.getInstance().signOut();

        switch (v.getId()) {
            case R.id.google_sign_in_button:

                GoogleSignIn();
                break;
            case R.id.button_email_sign_in:

                boolean clear = true;
                String email = editText_email.getText().toString().trim();
                String password = editText_password.getText().toString();
                if (PackHelper.isFieldEmpty(editText_email)) {
                    clear = false;
                    editText_email.setError("Invalid email");
                } else {
                    if (!PackHelper.isValidEmail(email)) {
                        editText_email.setError("Invalid email");
                        clear = false;
                    }
                }

                if (password.length() < 5) {
                    editText_password.setError("Invalid password");
                    clear = false;
                }

                if (clear) {
                    //begin email sign in process
                    signInPassword(email, password);
                }

                break;
            case R.id.sign_up:
                Intent intent = new Intent(this, EmailSignUpActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                handleSession(credential);
            } else {
                Log.e(TAG, "Login Unsuccessful. ");
                Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT)
                        .show();
            }

        } else if (requestCode == 140) {
            // TODO: 7/23/2016 Disambiguate the requestCode 
            twitterLoginButton.onActivityResult(requestCode, resultCode, data);
        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
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

    private void handleSession(AuthCredential credential) {
        showProgressDialog();

        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential" + task.getException().getMessage());
                            task.getException().printStackTrace();
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignInActivity.this, "Login successful",
                                    Toast.LENGTH_SHORT).show();
                            //start success activity
                        }

                        hideProgressDialog();
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }


}

