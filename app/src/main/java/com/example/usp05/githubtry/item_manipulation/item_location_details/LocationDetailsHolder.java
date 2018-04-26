package com.example.usp05.githubtry.item_manipulation.item_location_details;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.usp05.githubtry.R;

/**
 * Created by nathan on 4/26/18.
 */

public class LocationDetailsHolder extends RecyclerView.ViewHolder {

    final TextView location;
    final TextView quantity;
    final TextView expirationDate;
    final TextView purchaseDate;



    public LocationDetailsHolder(View itemView) {
        super(itemView);
        location = itemView.findViewById(R.id.TV_locationLocation);
        quantity = itemView.findViewById(R.id.TV_locationQuantity);
        expirationDate = itemView.findViewById(R.id.TV_locationExpirationDate);
        purchaseDate = itemView.findViewById(R.id.TV_locationPurchaseDate);
    }



}
