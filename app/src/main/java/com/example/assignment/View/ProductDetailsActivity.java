package com.example.assignment.View;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.R;
import com.example.assignment.SQLiteDatabase.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Typeface fontAwesome;
    @BindView(R.id.backIconTextView) TextView backIconTextView;
    @BindView(R.id.backIconRelativeLayout) RelativeLayout backIconRelativeLayout;
    @BindView(R.id.sizeRecyclerView) RecyclerView sizeRecyclerView;
    @BindView(R.id.colorsRecyclerView) RecyclerView colorsRecyclerView;
    @BindView(R.id.titleTextView) TextView titleTextView;
    @BindView(R.id.productPriceTextView) TextView productPriceTextView;
    @BindView(R.id.colorTextView) TextView colorTextView;
    @BindView(R.id.sizeTextView) TextView sizeTextView;

    private HashMap<String, Object> productData = new HashMap<>();
    private int productId;
    private DatabaseHelper databaseHelper;
    private ArrayList<HashMap<String, Object>> varients = new ArrayList<>();
    private Double priceIncludingTax;
    private Double price;
    private Double tax;

    ArrayList<String> colors = new ArrayList<>();
    ArrayList<Integer> sizes = new ArrayList<>();
    ArrayList<String> uniqueColors = new ArrayList<>();
    ArrayList<Integer> uniqueSizes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_deatails);
        ButterKnife.bind(this);
        fontAwesome = Typeface.createFromAsset(this.getAssets(), "fontawesomeicon.ttf");
        backIconTextView.setTypeface(fontAwesome);
        setOnClickListener();

        productData = (HashMap<String, Object>) getIntent().getSerializableExtra("productData");
        productId = getIntent().getIntExtra("productId", 0);
        databaseHelper = new DatabaseHelper(this);

        if(!productData.isEmpty()) {

            price = (Double) productData.get("productStartPrice");
            tax = (Double) productData.get("taxPercentageValue");
            priceIncludingTax = price + ((price * tax) /100);
            productPriceTextView.setText("Price: \u20B9" + priceIncludingTax);

            titleTextView.setText((CharSequence) productData.get("productName"));
            productPriceTextView.setText("Price: \u20B9" + priceIncludingTax);

            varients = databaseHelper.getVarients(productId);
            if(!varients.isEmpty()) {
                for(int i = 0; i < varients.size(); i++) {
                    if((String) varients.get(i).get("varientColor") != null || ((String)  varients.get(i).get("varientColor")).isEmpty()) {
                        String color = (String) varients.get(i).get("varientColor");
                        if (color != null || !color.isEmpty()) {
                            colors.add(color);
                        }
                    }

                    if((Integer) varients.get(i).get("varientSize") != null && (Integer) varients.get(i).get("varientSize") != 0) {
                        Integer size = (Integer) varients.get(i).get("varientSize");
                        if (size != null) {
                            sizes.add(size);
                        }
                    }
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uniqueColors = (ArrayList<String>) colors.stream().distinct().collect(Collectors.toList());
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uniqueSizes = (ArrayList<Integer>) sizes.stream().distinct().collect(Collectors.toList());
                }

                if(uniqueColors.size() > 0) {
                    colorTextView.setVisibility(View.VISIBLE);
                    colorsRecyclerView.setVisibility(View.VISIBLE);
                    ProductColorRecyclerViewAdapter productColorRecyclerViewAdapter = new ProductColorRecyclerViewAdapter(this, uniqueColors);
                    colorsRecyclerView.setAdapter(productColorRecyclerViewAdapter);
                }else {
                    colorTextView.setVisibility(View.GONE);
                    colorsRecyclerView.setVisibility(View.GONE);
                }

                if(uniqueSizes.size() > 0) {
                    sizeTextView.setVisibility(View.VISIBLE);
                    sizeRecyclerView.setVisibility(View.VISIBLE);
                    ProductSizeRecyclerViewAdapter productSizeRecyclerViewAdapter = new ProductSizeRecyclerViewAdapter(this, uniqueSizes);
                    sizeRecyclerView.setAdapter(productSizeRecyclerViewAdapter);
                }else {
                    sizeTextView.setVisibility(View.GONE);
                    sizeRecyclerView.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backIconRelativeLayout:
                super.onBackPressed();
                break;
        }
    }

    private void setOnClickListener() {
        backIconRelativeLayout.setOnClickListener(this);
    }
}
