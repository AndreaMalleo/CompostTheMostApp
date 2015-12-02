package com.independentwork.andrea.compostthemost.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/*public class sortFacilities {

    @SuppressWarnings("unchecked")
    public static void main(String [] args){
	if (args.length == 0)
	    System.out.println("Missing facilities input");
	else if (args.length > 1)
	    System.out.println("Too many arguments");
	else {
            StringBuffer sb = new StringBuffer();
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(args[0]);
                String temp;
                while((temp = br.readLine()) != null)
                    sb.append(temp);
            }catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    br.close();
                }catch(IOException e) {
                    e.printStackTrace();
                }
                }
        String jsonString = sb.toString();
	     //read in the receipt file
	   /* org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
	    try{
		Object obj = parser.parse(new FileReader(args[0])); 
		JSONObject jsonObject = (JSONObject)obj;
		
		JSONObject results = (JSONObject)jsonObject.get("results");
		JSONObject facilityDescription = (JSONObject)results.get("FacilityDescription");

		JSONArray addressArray = (JSONArray)facilityDescription.get("Address");
		Iterator<String> iterator = addressArray.iterator();
		while(iterator.hasNext()) {
		    System.out.println(iterator.next());
		}
	    }
		    catch(Exception e){
		System.out.println("Invalid file"); 
		e.printStackTrace();
	    	

	    }
	}
    }
}
*/
