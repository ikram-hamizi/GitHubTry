package com.example.usp05.githubtry.item_manipulation.item_location_details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usp05.githubtry.R;

import java.util.ArrayList;

/**
 * Created by nathan on 4/26/18.
 */

public class LocationDetailsAdapter extends RecyclerView.Adapter<LocationDetailsHolder> {

    private ArrayList<ItemLocationDetails> locationDetails;
    Context c;

    /**
     * Called when RecyclerView needs a new {@link LocationDetailsHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(LocationDetailsHolder, int)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(LocationDetailsHolder, int)
     */
    @Override
    public LocationDetailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_details_locations,parent,false);
        return new LocationDetailsHolder(v);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link LocationDetailsHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike ListView, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link LocationDetailsHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(LocationDetailsHolder, int)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(LocationDetailsHolder holder, int position) {
        final ItemLocationDetails currentItem = locationDetails.get(position);

        holder.location.setText(currentItem.getLocation());
        holder.quantity.setText(Integer.toString(currentItem.getQuantity()));
        holder.expirationDate.setText(currentItem.getExpirationDate());
        holder.purchaseDate.setText(currentItem.getPurchaseDate());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return locationDetails.size();
    }

    public LocationDetailsAdapter(Context c, ArrayList<ItemLocationDetails> locationDetails) {
        this.locationDetails = locationDetails;
        this.c = c;
    }
}
