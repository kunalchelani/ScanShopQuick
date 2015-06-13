package com.example.kunal.barcodetry1;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class CartActivityFragment extends Fragment {

    public CartActivityFragment() {
    }

    ArrayAdapter<String> arrayAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

//        ArrayList<String> cart = new ArrayList<>();
        Intent i = getActivity().getIntent();
//        cart = i.getStringArrayListExtra("Cart");
        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.listcartitems, R.id.list_cart_item, ((MyApplication)getActivity().getApplication()).getArrayList());
        ListView listView = (ListView)rootView.findViewById(R.id.listv_cart_item);
        listView.setAdapter(arrayAdapter);

        final Button button = (Button)rootView.findViewById(R.id.cont);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //updateCart(prodInfo);
                /*
                MyApplication appState = ((MyApplication) getActivity().getApplication());
                appState.addToArrayList(prodInfo);
                */
                //  Intent intent =  new Intent(getActivity(), CartActivity.class).putStringArrayListExtra("Cart", cart);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        final Button button2 = (Button)rootView.findViewById(R.id.checkout);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //updateCart(prodInfo);
                /*
                MyApplication appState = ((MyApplication) getActivity().getApplication());
                appState.addToArrayList(prodInfo);
                */
                //  Intent intent =  new Intent(getActivity(), CartActivity.class).putStringArrayListExtra("Cart", cart);
                Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                startActivity(intent);
            }
        });

        ((TextView)rootView.findViewById(R.id.cart_value)).setText("Your total bill : $"+((((MyApplication)getActivity().getApplication())).getTotalCart()));

        return rootView;
    }
}
