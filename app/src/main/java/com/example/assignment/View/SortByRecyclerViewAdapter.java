package com.example.assignment.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.R;

import java.util.ArrayList;

public class SortByRecyclerViewAdapter extends RecyclerView.Adapter<SortByRecyclerViewAdapter.verticalView>{

    private Context context;
    private AlertDialog mDialog;
    private Integer categoryId;
    private String categoryName;

    public class verticalView extends RecyclerView.ViewHolder {

        TextView sortByTextView;
        RelativeLayout sortByRelativeLayout;
        public verticalView(View view) {
            super(view);
            sortByTextView = view.findViewById(R.id.sortByTextView);
            sortByRelativeLayout = view.findViewById(R.id.sortByRelativeLayout);
        }
    }

    public SortByRecyclerViewAdapter(Context context, AlertDialog mDialog, Integer categoryId, String categoryName) {
        this.context = context;
        this.mDialog = mDialog;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    @Override
    public SortByRecyclerViewAdapter.verticalView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_by_recycler_view_item, parent, false);
        return new SortByRecyclerViewAdapter.verticalView(view);
    }

    @Override
    public void onBindViewHolder(final SortByRecyclerViewAdapter.verticalView holder, int position) {

        holder.sortByTextView.setText(ranking().get(position));
        holder.sortByRelativeLayout.setOnClickListener(view -> {
            mDialog.dismiss();
        });

        holder.sortByRelativeLayout.setOnClickListener(v -> {
            if(holder.sortByTextView.getText().equals("Views")) {
                Intent intent = new Intent(context, ProductsActivity.class);
                intent.putExtra("isFromViewedProducts", true);
                intent.putExtra("categoryId", categoryId);
                intent.putExtra("categoryName" , categoryName);
                context.startActivity(intent);
            }else if(holder.sortByTextView.getText().equals("Orders")){
                Intent intent = new Intent(context, ProductsActivity.class);
                intent.putExtra("isFromOrderdProducts", true);
                intent.putExtra("categoryId", categoryId);
                intent.putExtra("categoryName" , categoryName);
                context.startActivity(intent);
            }else if(holder.sortByTextView.getText().equals("Shares")){
                Intent intent = new Intent(context, ProductsActivity.class);
                intent.putExtra("isFromSharedProducts", true);
                intent.putExtra("categoryId", categoryId);
                intent.putExtra("categoryName" , categoryName);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ranking().size();
    }

    private ArrayList<String> ranking () {
        ArrayList<String> rankings = new ArrayList<>();
        rankings.add("Views");
        rankings.add("Orders");
        rankings.add("Shares");
        return rankings;
    }
}
