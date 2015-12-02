package com.independentwork.andrea.compostthemost;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableLayout;
import android.graphics.Color;
import org.json.*;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobile.content.ContentItem;
import com.amazonaws.mobile.content.ContentListHandler;
import com.amazonaws.mobile.content.ContentManager;
import com.amazonaws.mobile.content.ContentProgressListener;
import com.amazonaws.mobile.content.ContentState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.independentwork.andrea.compostthemost.util.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class DisplayFacilitiesActivity extends AppCompatActivity {

    /** Content Manager that manages the content for this demo. */
    private ContentManager contentManager;
    /** Handles the main content list. */
    //private ListIterator<ContentItem> contentList;
   // private ListIterator<String> addresses;
    private ArrayList<List<String>> ContentList;
    private List<String> nameList = new ArrayList<String>();
    private List<String> addressList = new ArrayList<String>();
    private List<String> addressList2 = new ArrayList<String>();
    private List<String> websiteList = new ArrayList<String>();
    /** Flag to keep track of whether currently listing content. */
    private volatile boolean listingContentInProgress = false;
    private final String BUCKETNAME = "compostthemostappfor-contentdelivery-mobilehub-2095335410";
    private final int indexofNames = 1;
    private final int indexofContacts = 0;
    private final String IDENTITY_POOL_ID = "us-east-1:8f237859-4920-41d6-87f3-87d153886389";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String location = intent.getStringExtra(MainActivity.EXTRA_LOCATION);
        //ContentList = new ArrayList<List<String>>();

        final String[] filesToRetrieve = helpers.getStateFromAddress(location);

        setContentView(R.layout.activity_display_facilities);

        AsyncTask<String, Void, Void> LoadFacilityNames = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                        getApplicationContext(),    /* get the context for the application */
                        IDENTITY_POOL_ID,    /* Identity Pool ID */
                        Regions.US_EAST_1           /* Region for your identity pool--US_EAST_1 or EU_WEST_1*/
                );
                AmazonS3 s3 = new AmazonS3Client(credentialsProvider);
                String file = filesToRetrieve[indexofNames];
                S3Object s3ob = s3.getObject(new GetObjectRequest(BUCKETNAME,    /* The bucket to download from */
                        file));
                InputStream obData = s3ob.getObjectContent();
                StringBuffer sb = new StringBuffer();
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new InputStreamReader(obData));
                    String temp;
                    while ((temp = br.readLine()) != null)
                        sb.append(temp);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    JSONObject obj = new JSONObject(sb.toString());
                    JSONObject resultsob = obj.getJSONObject("results");
                    JSONArray nameCollection = resultsob.getJSONArray("collection1");
                    //List<String> names = new ArrayList<String>();

                    for (int i = 0; i < nameCollection.length(); i++) {
                        JSONObject nameRow = nameCollection.getJSONObject(i);
                        //names.add(nameRow.getString("Names"));
                        nameList.add(nameRow.getString("Names"));
                    }
                    //ContentList.add(names);
                }
                catch(JSONException e){e.printStackTrace(); }
                return null;
            }
            protected void onPostExecute(Void unused){
            }
        };
    LoadFacilityNames.execute();

        //new LoadFacilitiesTask().execute(location);
        AsyncTask<String, Void, Void> LoadFacilities = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                        getApplicationContext(),    /* get the context for the application */
                        IDENTITY_POOL_ID,    /* Identity Pool ID */
                        Regions.US_EAST_1           /* Region for your identity pool--US_EAST_1 or EU_WEST_1*/
                );
                AmazonS3 s3 = new AmazonS3Client(credentialsProvider);
                S3Object s3ob = s3.getObject(new GetObjectRequest(BUCKETNAME,     /* The bucket to download from */
                        "NJFacilityInfo.json"));
                InputStream obData = s3ob.getObjectContent();
                StringBuffer sb = new StringBuffer();
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new InputStreamReader(obData));
                    String temp;
                    while ((temp = br.readLine()) != null)
                        sb.append(temp);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    JSONObject obj = new JSONObject(sb.toString());
                    JSONObject resultsob = obj.getJSONObject("results");
                    JSONArray addressCollection = resultsob.getJSONArray("collection1");
                    JSONArray websiteCollection = resultsob.getJSONArray("collection2");
                    for (int i = 0; i < addressCollection.length(); i++) {
                        JSONObject addressRow = addressCollection.getJSONObject(i);
                        addressList.add(addressRow.getString("Address"));
                        i = i + 1;
                        if (i < addressCollection.length()) {
                            //repeat, adding second address line this time,
                            addressRow = addressCollection.getJSONObject(i);
                            addressList2.add(addressRow.getString("Address"));
                        }
                    }
                    for (int i = 0; i < websiteCollection.length(); i++) {
                        JSONObject websiteRow = websiteCollection.getJSONObject(i);
                        try{String website = websiteRow.getString("Website.text");
                        if(!website.isEmpty())
                             websiteList.add(website); }
                        catch(JSONException e){
                            websiteList.add("Not Posted"); }
                    }
                       // List<String> descriptions = new ArrayList<String>();
                        //   List<String> phonenumbers = new ArrayList<String>();
                      //  phonenumbers.add(facilityrow.getString("PhoneNumber.Phone"));
                      //  website.add(facilityrow.getString("Website"));
                        //descriptions.add(facilityrow.getString("Description"));

                   // ContentList.add(addressl1);
                   /// ContentList.add(addressl2);
                   // ContentList.add(website);

                    // ContentList.add(phonenumbers);
                  //  ContentList.add(descriptions);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            protected void onPostExecute(Void unused) {
                initializeTable();
            }
        };
        LoadFacilities.execute();
    }

    public void initializeTable(){

        TableLayout displayTable = (TableLayout) findViewById(R.id.table_main);
        TableRow  columnHeaders = new TableRow(this);
        TextView col0 = new TextView(this);
        col0.setText("Name");
        col0.setTextColor(Color.BLACK);
        columnHeaders.addView(col0);

        TextView col1 = new TextView(this);
        col1.setText("Address");
        col1.setTextColor(Color.BLACK);
        columnHeaders.addView(col1);

        TextView col2 = new TextView(this);
        col2.setText("Website");
        columnHeaders.addView(col2);

        TextView col3 = new TextView(this);
        col3.setText("Distance From You");
        columnHeaders.addView(col3);

        displayTable.addView(columnHeaders);

        ListIterator<String> nameIterator = nameList.listIterator();
        ListIterator<String> addressIterator = addressList.listIterator();
        ListIterator<String> addressIterator2 = addressList2.listIterator();
        ListIterator<String> websiteIterator = websiteList.listIterator();

        while(nameIterator.hasNext()){
            TableRow row = new TableRow(this);
            TextView text1 = new TextView(this);
            String name = nameIterator.next();
            text1.setText(name);
            text1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(DisplayFacilitiesActivity.this, ViewFacilityActivity.class);
                    String pass = "passonwards";
                    intent.putExtra(pass, true);
                    startActivity(intent);
                }
            });
            text1.setLayoutParams(new ActionBar.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            row.addView(text1);

            TextView text2 = new TextView(this);
            text2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            if(addressIterator.hasNext() & addressIterator2.hasNext())
           // text2.setText(address1Iterator.next() + "\n" + address12terator.next());
             text2.setText(addressIterator.next() + "\n" + addressIterator2.next());
            else
            text2.setText("Not Available");

            row.addView(text2);

           TextView text3 = new TextView(this);
            if(websiteIterator.hasNext())
                 text3.setText(websiteIterator.next());
            else
                text3.setText("Not Available");
            row.addView(text3);
            /*
           TextView text4 = new TextView(this);
            text4.setText(numberIterator.next());
            row.addView(text4);
            */
            displayTable.addView(row, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_facilities, menu);
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
}
