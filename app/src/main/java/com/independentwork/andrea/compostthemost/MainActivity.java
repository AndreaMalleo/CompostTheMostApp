package com.independentwork.andrea.compostthemost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toolbar;

import com.amazonaws.mobile.AWSMobileClient;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_LOCATION = "com.independentwork.andrea.compostthemost.LOCATION";
    /** Class name for log messages. */
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    /** Bundle key for saving/restoring the toolbar title. */
    private final static String BUNDLE_KEY_TOOLBAR_TITLE = "title";
    /** The toolbar view control. */
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obtain a reference to the mobile client. It is created in the Splash Activity.
        AWSMobileClient awsMobileClient = AWSMobileClient.defaultMobileClient();

        if (awsMobileClient == null) {
            // In the case that the activity is restarted by the OS after the application
            // is killed we must redirect to the splash activity to handle initialization.
            Intent intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return;
        }
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Obtain a reference to the mobile client. It is created in the Splash Activity.
        final AWSMobileClient awsMobileClient = AWSMobileClient.defaultMobileClient();

        if (awsMobileClient == null) {
            // At this point, the app is resuming after being shut down and the onCreate
            // method has already fired an intent to launch the splash activity. The splash
            // activity will refresh the user's credentials and re-initialize all required
            // components. We bail out here, because without that initialization, the steps
            // that follow here would fail. Note that if you don't have any features enabled,
            // then there may not be any steps here to skip.
            return;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void findFacilities(View view){
        //search in storage for local facilities
        Intent intent = new  Intent(this, DisplayFacilitiesActivity.class);
        EditText locationText = (EditText) findViewById(R.id.edit_location);
        String location = locationText.getText().toString();
        intent.putExtra(EXTRA_LOCATION ,location);
        startActivity(intent);
    }
    //switch to information input form
    public void advertiseFacilities(View view){
        Intent intent = new  Intent(this, AdvertiseFacilityActivity.class);
        startActivity(intent);
    }

}
