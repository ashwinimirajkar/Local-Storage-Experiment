package com.example.assignment.View;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.assignment.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsGridViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<HashMap<String, Object>> products = new ArrayList<>();
    @BindView(R.id.productLinearLayout) LinearLayout productLinearLayout;
    @BindView(R.id.productNameTextView) TextView productNameTextView;
    @BindView(R.id.productPriceTextView) TextView productPriceTextView;
    @BindView(R.id.productViewCountTextView) TextView productViewCountTextView;
    @BindView(R.id.productOrderCountTextView) TextView productOrderCountTextView;
    @BindView(R.id.productShareCountTextView) TextView productShareCountTextView;
    private Double priceIncludingTax;
    private Double price;
    private Double tax;

    public ProductsGridViewAdapter(Context context, ArrayList<HashMap<String, Object>> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.product_grid_view_item, null);
        ButterKnife.bind(this, convertView);

        if(!products.isEmpty()) {
            productNameTextView.setText((CharSequence) products.get(position).get("productName"));
            price = (Double) products.get(position).get("productStartPrice");
            tax = (Double) products.get(position).get("taxPercentageValue");
            priceIncludingTax = price + ((price * tax) /100);
            productPriceTextView.setText("Price: \u20B9" + priceIncludingTax);

            if(products.get(position).get("productViewCount") != null) {
                Integer viewCount = (Integer) products.get(position).get("productViewCount");
                if (viewCount > 0) {
                    productViewCountTextView.setText("View count: " + viewCount);
                }else {
                productViewCountTextView.setText("View count: 0"); }
            }else {
                productViewCountTextView.setText("View count: 0");
            }

            if(products.get(position).get("productOrderCount") != null) {
                Integer orderCount = (Integer) products.get(position).get("productOrderCount");
                if (orderCount > 0) {
                    productOrderCountTextView.setText("Order count: " + orderCount);
                }else {
                productOrderCountTextView.setText("Order count: 0"); }
            }else {
                productOrderCountTextView.setText("Order count: 0");
            }

            if(products.get(position).get("productShareCount") != null) {
                Integer sharecount = (Integer) products.get(position).get("productShareCount");
                if (sharecount > 0) {
                    productShareCountTextView.setText("Share count: " + sharecount);
                } else {
                productShareCountTextView.setText("Share count: 0"); }
            }else {
                productShareCountTextView.setText("Share count: 0");
            }

            productLinearLayout.setOnClickListener(v -> {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("productData", products.get(position));
                intent.putExtra("productId", (Integer) products.get(position).get("productId"));
                context.startActivity(intent);
            });
        }

        return convertView;
    }
}
