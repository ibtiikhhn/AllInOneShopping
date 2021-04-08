package com.muibsols.allinoneshopping.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.muibsols.allinoneshopping.ClickListeners.CategoryClickListener;
import com.muibsols.allinoneshopping.Models.CategoryModel;
import com.muibsols.allinoneshopping.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    public static final String TAG = "RVVV";

    int row_index=0;
    Context context;
    List<CategoryModel> categoryModelList;
    CategoryClickListener categoryClickListener;

    public CategoryAdapter(Context context, CategoryClickListener categoryClickListener) {
        this.context = context;
        categoryModelList = new ArrayList<>();
        this.categoryClickListener = categoryClickListener;
    }

    public void setList(List<CategoryModel> zodiacSignModelList) {
        this.categoryModelList = zodiacSignModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tab, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.zodiacNameTv.setText(categoryModelList.get(position).getCategory());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                notifyDataSetChanged();
                categoryClickListener.categoryOnClick(categoryModelList.get(position));
            }
        });
        if (row_index == position) {
            holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.round_corner_selected_tv));
            holder.zodiacNameTv.setTextColor(Color.parseColor("#ffffff"));
        }else {
            holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.round_corner_tv));
            holder.zodiacNameTv.setTextColor(Color.parseColor("#000000"));
        }

    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        public TextView zodiacNameTv;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            zodiacNameTv = itemView.findViewById(R.id.categoryTV);

        }
    }
}
