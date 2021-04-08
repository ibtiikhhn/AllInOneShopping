package com.muibsols.allinoneshopping.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.muibsols.allinoneshopping.ClickListeners.StoreClickListener;
import com.muibsols.allinoneshopping.Models.Store;
import com.muibsols.allinoneshopping.R;

import java.util.ArrayList;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> implements Filterable {

    public static final String TAG = "RVVV";

    int row_index=0;
    Context context;
    List<Store> storeList;
    StoreClickListener storeClickListener;
    private List<Store> orig;

    public StoreAdapter(Context context, StoreClickListener storeClickListener) {
        this.context = context;
        storeList = new ArrayList<>();
        this.storeClickListener = storeClickListener;
    }

    public void setList(List<Store> storeList) {
        this.storeList = storeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.store_cv, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
//        holder.storeIMG.setImageDrawable(storeList.get(position).getStoreIMG());
        Glide.with(context).load(storeList.get(position).getStoreIMG()).into(holder.storeIMG);
        holder.storeTV.setText(storeList.get(position).getStoreName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeClickListener.storeOnClick(storeList.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        return storeList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<Store> results = new ArrayList<Store>();
                if (orig == null)
                    orig = storeList;
                if (constraint != null) {
                    if (orig != null & orig.size() > 0) {
                        for (final Store g : orig) {
                            if (g.getStoreName().toLowerCase().contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                storeList = (ArrayList<Store>) results.values;
                notifyDataSetChanged();

            }
        };
    }
        class StoreViewHolder extends RecyclerView.ViewHolder {
        public TextView storeTV;
        public ImageView storeIMG;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);
            storeTV = itemView.findViewById(R.id.storeTV);
            storeIMG = itemView.findViewById(R.id.storeIMG);
        }
    }


}
