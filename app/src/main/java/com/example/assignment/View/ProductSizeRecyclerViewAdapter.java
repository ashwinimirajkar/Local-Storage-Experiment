package com.example.assignment.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.R;

import java.util.ArrayList;

public class ProductSizeRecyclerViewAdapter extends RecyclerView.Adapter<ProductSizeRecyclerViewAdapter.verticalView>{

    private Context context;
    private ArrayList<Integer> sizes = new ArrayList<>();

    public class verticalView extends RecyclerView.ViewHolder {
        TextView productColorOrSizeTextView;
        public verticalView(View view) {
            super(view);
            productColorOrSizeTextView = view.findViewById(R.id.productColorOrSizeTextView);
        }
    }

    public ProductSizeRecyclerViewAdapter(Context context, ArrayList<Integer> sizes) {
        this.context = context;
        this.sizes = sizes;
    }

    @Override
    public ProductSizeRecyclerViewAdapter.verticalView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_color_size_recycler_view_item, parent, false);
        return new ProductSizeRecyclerViewAdapter.verticalView(view);
    }

    @Override
    public void onBindViewHolder(final ProductSizeRecyclerViewAdapter.verticalView holder, int position) {
        holder.productColorOrSizeTextView.setText(String.valueOf(sizes.get(position)));
    }

    @Override
    public int getItemCount() {
        return sizes.size();
    }
}