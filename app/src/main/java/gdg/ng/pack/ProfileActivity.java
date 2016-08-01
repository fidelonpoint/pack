package gdg.ng.pack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

/**
 * Created by Dominic on 6/24/2016.
 *
 * Updated by Hamza Fetuga on 7/23/2016
 */
public class ProfileActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    private ImageView picture;
    private ImageView badge;
    private TextView username;
    private TextView gender;
    private TextView age;
    private TextView occupation;
    private TextView reports;
    private TextView confirmations;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_profile);
        this.confirmations = (TextView) findViewById(R.id.confirmations);
        this.reports = (TextView) findViewById(R.id.reports);
        this.occupation = (TextView) findViewById(R.id.occupation);
        this.age = (TextView) findViewById(R.id.age);
        this.gender = (TextView) findViewById(R.id.gender);
        this.username = (TextView) findViewById(R.id.username);
        this.badge = (ImageView) findViewById(R.id.badge);
        this.picture = (ImageView) findViewById(R.id.picture);
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        loadProfileDetails(mFirebaseAuth);

        // in case a sign out button is coming here, a simple mFirebaseAuth.signOut() will sign the user out!
    }

    private void loadProfileDetails(FirebaseAuth mFirebaseAuth) {
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        // user is not signed in. Take user to SignInActivity
        if (firebaseUser == null) {
            Intent intent = new Intent(this, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            showProgressDialog();
            String name = firebaseUser.getDisplayName();
            username.setText(name);
            String picture_url = firebaseUser.getPhotoUrl() == null ? "" : firebaseUser.getPhotoUrl().toString();

            if (picture_url.trim().length() != 0) {
                try {
                    Picasso.with(this).load(picture_url.trim()).into(picture);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();

    }

    private void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.cancel();
            ;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}