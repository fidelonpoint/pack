package gdg.ng.pack;

import android.app.Application;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Hamza Fetuga on 7/23/2016.
 */
public class MyApplication extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "CVeUzGZw61WYqzh3flHl1SRXs";
    private static final String TWITTER_SECRET = "GIpITK1V1nSkqPkcyCfwLCAOGL2xPpzZqZiwFu9xxzH0aGg1LX";


    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
    }
}
