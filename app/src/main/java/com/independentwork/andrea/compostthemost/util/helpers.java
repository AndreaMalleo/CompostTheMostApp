package com.independentwork.andrea.compostthemost.util;

/**
 * Created by andrea on 12/1/15.
 */
public class helpers {
    public static String[] getStateFromAddress(String location){
        String[] words = location.split(" ");
        String[] returnArray = new String[2];
        int n = words.length;
        String state;
        if (n==0) {
            state = "invalid";
        }
        else
            state = words[n-1];
        switch (state) {
            case "NJ": returnArray[0] = "NJFacilityInfo.json";
                        returnArray[1] = "NJFacilityNames.json";
                break;
            case "NY": returnArray[0] = "NYFacilityInfo.json";
                returnArray[1] = "NYFacilityNames.json";
                break;
            default : returnArray[0] = "invalid addresss";
                       returnArray[1] = "invalid address";
                break;
        }
        return returnArray;
    }
}
