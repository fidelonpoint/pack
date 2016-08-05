package gdg.ng.pack;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Hamza Fetuga on 7/23/2016.
 */
public class PackHelper {

    public static boolean isFieldEmpty(EditText editText) {
        if (editText.getText().toString().trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    //Store user data in sharedPrefrences
    protected static void setSharedPreferences(FirebaseUser user, SharedPreferences.Editor editor) {
        /* Save provider name and encodedEmail for later use and start MainActivity */
        editor.putString(Constants.KEY_PROVIDER, user.getProviderId()).apply();
        editor.putString(Constants.KEY_ENCODED_EMAIL, user.getEmail()).apply();
        editor.putBoolean(Constants.KEY_LOGGED_IN, true).apply();

        if (user.getPhotoUrl() != null) {
            editor.putString(Constants.PHOTO_URL, user.getPhotoUrl().toString()).apply();
        }
        editor.putString(Constants.KEY_DISPLAY_NAME, user.getDisplayName()).apply();
    }

    public void clearSharedPreferences(SharedPreferences.Editor editor) {
        editor.putString(Constants.KEY_ENCODED_EMAIL, "").apply();
        editor.putBoolean(Constants.KEY_LOGGED_IN, false).apply();
        editor.putString(Constants.KEY_DISPLAY_NAME, "").apply();

    }
}
