package com.example.kunal.barcodetry1;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements ZBarScannerView.ResultHandler {

    public MainActivityFragment() {
    }
    private ZBarScannerView mScannerView;
    private int cameraId = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mScannerView = new ZBarScannerView(getActivity()); // Programmatically initialize the scanner view
        View rootView = mScannerView;
       // setContentView(mScannerView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera(cameraId);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }


    @Override
    public void handleResult(Result result) {
        Log.v("Hello", result.getContents()); // Prints scan results
        Log.v("Hello", result.getBarcodeFormat().getName());
        GetWalMartInfo getWalmartInfo = new GetWalMartInfo();
        getWalmartInfo.execute(result.getContents());

    }

    public class GetWalMartInfo extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent = new Intent(getActivity(), prod_scannedActivity.class);
            if(s!=null) {
                intent = new Intent(getActivity(), prod_scannedActivity.class).putExtra(Intent.EXTRA_TEXT, s);
                Log.v("InOnPostExec", s);
            }
            else
            Log.e("InPostExec", "kuch na aaya yahan");
            startActivity(intent);
        }

        protected String getInfoFromJSON(String stringJson) throws JSONException{
            JSONObject jsonObject =  new JSONObject(stringJson);
            JSONArray  jsonArray = jsonObject.getJSONArray("items");
            JSONObject insideJSON  = jsonArray.getJSONObject(0);
            String name = insideJSON.getString("name");
            Double price = insideJSON.getDouble("salePrice");
            ((MyApplication)getActivity().getApplication()).setLastPrice(price);
            String outputString  = ("Name :" + name + "\n"+ "Price :"+ price);
            return outputString;

        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String prodInfo  = new String();
            int flag = 0;
            try{
                final String FORECAST_BASE_URL = "http://api.walmartlabs.com/v1/items?";
                final String API_KEY_PARAM = "apiKey";
                final String UPC_PARAM = "upc";
                /*final String UNITS_PARAM = "units";
                final String DAYS_PARAM = "cnt";
*/
                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(API_KEY_PARAM, "vkvmv4hg4xxajxy45k83xt2c")
                        .appendQueryParameter(UPC_PARAM, strings[0]).build();

                URL url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if(inputStream==null){
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line = reader.readLine()) != null){
                    buffer.append(line+"\n");
                }

                if(buffer.length()==0){
                    return null;
                }
                prodInfo = buffer.toString();
                Log.v("GOT_Info", prodInfo);

            } catch (IOException e) {
                e.printStackTrace();
                flag = 1;
            }
            finally{
                if(urlConnection!=null){
                    urlConnection.disconnect();
                }
                if(flag==1){
                    prodInfo = "Product name : ABCD" +"\n"+  "Price : $4.22";
                    Double price = 4.22;
                    ((MyApplication)getActivity().getApplication()).setLastPrice(price);
                    Log.v("GOT_Info", prodInfo);
                    return prodInfo;
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Getting_info_from_WM", "Error closing stream", e);
                    }
                }
            }
            try {
                return getInfoFromJSON(prodInfo);
            }catch (JSONException e){
                Log.e("JSON se String nai", e.getMessage(), e);
            }

            return null;
        }
    }

}
