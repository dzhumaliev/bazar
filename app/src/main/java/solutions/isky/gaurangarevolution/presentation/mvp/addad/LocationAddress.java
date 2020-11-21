package solutions.isky.gaurangarevolution.presentation.mvp.addad;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.models.MyAddress;

public class LocationAddress {
    private static final String TAG = "LocationAddress";

    public static void getAddressFromLocation(final double latitude, final double longitude,
                                              final Context context, final Handler handler) {
        Thread thread = new Thread(() ->{
            List<MyAddress> retVal = new ArrayList<MyAddress>();

            StringBuilder sb = new StringBuilder();
            sb.append("https://maps.googleapis.com/maps/api/geocode/json?");
            sb.append(String.format("latlng=%s,%s", String.valueOf(latitude),
                    String.valueOf(longitude)));
            // sb.append("&components=country:UK");
            sb.append("&sensor=false");
            sb.append("&language=ru");
            sb.append("&key=AIzaSyB6SaUgVg29HnM-6rHxzzVK1A5QKFOGzKI");
            String result = null;
            String cantry = "";
            String city = "";
            Double lat = null;
            Double lng = null;
            String json="";
            Log.d(TAG, "sb: " + sb.toString());
            try {
                json = OpenHTTPGetConnection(sb.toString());

            } catch (InterruptedException ex) {
                Log.d(TAG, "getFromLocation: " + ex.getMessage());

            }

            try {
                retVal = convertFromJSONStringToAddress(json);
                if (retVal != null && retVal.size() > 0) {
                    MyAddress address = retVal.get(0);
                    StringBuilder sb1 = new StringBuilder();
                    cantry=address.country;
                    city=address.city_name;
                    if(address.street_number==null||address.street_number.equalsIgnoreCase("null")||address.street_number.equalsIgnoreCase("")&&address.route==null||address.route.equalsIgnoreCase("null")||address.route.equalsIgnoreCase("")){
                        sb1.append(address.formattedAddress);
                    }else{
                        sb1.append(address.route);
                        sb1.append(", ");
                        sb1.append(address.street_number);
                    }

                    result = sb1.toString();
                    lat=address.latitude;
                    lng=address.longitute;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                Log.e(TAG, "Unable connect to Geocoder", e);
            } finally {
                Message message = Message.obtain();
                message.setTarget(handler);
                if (result != null) {
                    message.what = 1;
                    Bundle bundle = new Bundle();
                    bundle.putString("address", result);
                    if(cantry != null&&!cantry.equalsIgnoreCase("")){
                        bundle.putString("cantry", cantry);
                    }else{
                        bundle.putString("cantry", "");
                    }
                    if(city != null&&!city.equalsIgnoreCase("")){
                        bundle.putString("city", city);
                    }else{
                        bundle.putString("city", "");
                    }
                    bundle.putDouble("lat", lat);
                    bundle.putDouble("lng", lng);
                    message.setData(bundle);
                } else {
                    message.what = 1;
                    Bundle bundle = new Bundle();
                    result = context.getString(R.string.error_get_adress);
                    bundle.putString("address", result);
                    bundle.putString("city", "");
                    bundle.putString("cantry", "");
                    message.setData(bundle);
                }
                message.sendToTarget();
            }
        } ) ;
        thread.start();
    }
    private static List<MyAddress> convertFromJSONStringToAddress(String json)
            throws JSONException {
        List<MyAddress> retVal = new ArrayList<MyAddress>();

        JSONObject obj = new JSONObject(json);
        Log.d(TAG, "getFromLocationobj: " + obj.toString());
        if (obj.getString("status").equalsIgnoreCase("OK")) {
            JSONArray result = obj.getJSONArray("results");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jsAddress = result.getJSONObject(i);

                MyAddress adr = new MyAddress();

                JSONArray addr_comp = jsAddress
                        .getJSONArray("address_components");
                for (int ii = 0; ii < addr_comp.length(); ii++) {
                    JSONObject component = addr_comp.getJSONObject(ii);

                    if (component.getString("types").contains("postal_code")) {
                        adr.postalCode = component.getString("short_name");
                    }
                    if(component.getString("types").contains("country")){
                        adr.country = component.getString("short_name");
                        Log.d("country", "adr.country: " +  adr.country);
                    }
                    if(component.getString("types").contains("street_number")){
                        adr.street_number = component.getString("short_name");
                        Log.d("street_number", "adr.street_number: " +  adr.street_number);
                    }
                    if(component.getString("types").contains("route")){
                        adr.route = component.getString("short_name");
                        Log.d("route", "adr.route: " +  adr.route);
                    }
                    if(component.getString("types").contains("locality")&&component.getString("types").contains("political")){
                        adr.city_name = component.getString("short_name");
                        Log.d("locality", "adr.locality: " +  adr.city_name);
                    }

                }

                adr.formattedAddress = jsAddress.getString("formatted_address");

                adr.latitude = jsAddress.getJSONObject("geometry")
                        .getJSONObject("location").getDouble("lat");
                adr.longitute = jsAddress.getJSONObject("geometry")
                        .getJSONObject("location").getDouble("lng");

                retVal.add(adr);
            }
        }

        return retVal;
    }
    public void  getFromLocationName(final String locationName, final Handler handler) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                List<MyAddress> retVal = new ArrayList<MyAddress>();
                String adr = TextUtils.htmlEncode(locationName);

                StringBuilder sb = new StringBuilder();
                sb.append("https://maps.googleapis.com/maps/api/geocode/json?");
                sb.append("address=" + adr.replace(" ", "+"));
                // sb.append("&components=country:UK");
                sb.append("&sensor=false");

                String result = null;
                List<String> stringList=null;
                String json="";

                try {
                    json = OpenHTTPGetConnection(sb.toString());
                } catch (InterruptedException ex) {
                    Log.d(TAG, "getFromLocation: " + ex.getMessage());

                }

                try {
                    retVal = convertFromJSONStringToAddress(json);

                    if (retVal != null && retVal.size() > 0) {
                        stringList =new ArrayList<>();
                        for (MyAddress myAddress:retVal){
                            StringBuilder sb1 = new StringBuilder();

                            sb1.append(myAddress.formattedAddress);
                            stringList.add(sb1.toString());
                        }
                        MyAddress address = retVal.get(0);
                        StringBuilder sb1 = new StringBuilder();

                        sb1.append(address.formattedAddress);
                        result = sb1.toString();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (Exception e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (stringList != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("address", (ArrayList<String>) stringList);
                        message.setData(bundle);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Unable to get address for this lat-long.";
                        bundle.putString("address", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }

    private static String OpenHTTPGetConnection(String strURL)
            throws InterruptedException {
        String response="";
        Request request = new Request.Builder()
                .url(strURL.replace("\\", "%5C"))
                .build();

        Response responses;

        try {
            responses = App.mOkHttpClient.newCall(request).execute();

            response = responses.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

}
