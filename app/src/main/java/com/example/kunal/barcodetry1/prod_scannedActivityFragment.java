package com.example.kunal.barcodetry1;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class prod_scannedActivityFragment extends Fragment {

    public prod_scannedActivityFragment() {
    }

  //  public ArrayList<String> cart = new ArrayList<String>();
/*
    public void updateCart(String s){
        this.cart.add(s);
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_prod_scanned, container, false);
        Intent intent = getActivity().getIntent();
       // if(intent!=null && intent.hasExtra(Intent.EXTRA_TEXT)){
            final String prodInfo =  intent.getStringExtra(Intent.EXTRA_TEXT);
            ((TextView)rootView.findViewById(R.id.prodInfo)).setText(prodInfo);
      //  }

        final Button button = (Button)rootView.findViewById(R.id.add_to_cart);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //updateCart(prodInfo);
                MyApplication appState =  ((MyApplication)getActivity().getApplication());
                appState.addToArrayList(prodInfo);

              //  Intent intent =  new Intent(getActivity(), CartActivity.class).putStringArrayListExtra("Cart", cart);
                Intent intent =  new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });

        return rootView;

    }
}
