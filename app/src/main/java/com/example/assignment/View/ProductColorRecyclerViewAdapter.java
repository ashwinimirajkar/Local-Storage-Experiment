package com.example.assignment.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.R;

import java.util.ArrayList;

public class ProductColorRecyclerViewAdapter extends RecyclerView.Adapter<ProductColorRecyclerViewAdapter.verticalView>{

    private Context context;
    private ArrayList<String> colors = new ArrayList<>();

    public class verticalView extends RecyclerView.ViewHolder {
        TextView productColorOrSizeTextView;
        public verticalView(View view) {
            super(view);
            productColorOrSizeTextView = view.findViewById(R.id.productColorOrSizeTextView);
        }
    }

    public ProductColorRecyclerViewAdapter(Context context, ArrayList<String> colors) {
        this.context = context;
        this.colors = colors;
    }

    @Override
    public ProductColorRecyclerViewAdapter.verticalView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_color_size_recycler_view_item, parent, false);
        return new ProductColorRecyclerViewAdapter.verticalView(view);
    }

    @Override
    public void onBindViewHolder(final ProductColorRecyclerViewAdapter.verticalView holder, int position) {
        holder.productColorOrSizeTextView.setText(colors.get(position));
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }
}