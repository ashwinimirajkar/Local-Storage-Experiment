package com.example.assignment.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.R;
import com.example.assignment.SQLiteDatabase.DatabaseHelper;
import com.example.assignment.Utility.GlobalUtility;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.backIconTextView) TextView backIconTextView;
    @BindView(R.id.backIconRelativeLayout) RelativeLayout backIconRelativeLayout;
    @BindView(R.id.productsGridView) GridView productsGridView;
    @BindView(R.id.sortIconRelativeLayout) RelativeLayout sortIconRelativeLayout;
    @BindView(R.id.sortIconTextView) TextView sortIconTextView;
    @BindView(R.id.childCategoryNameTextView) TextView childCategoryNameTextView;
    
    private Typeface fontAwesome;
    private int categoryId;
    private DatabaseHelper databaseHelper;
    private ArrayList<HashMap<String, Object>> products = new ArrayList<>();
    private String categoryName = null;
    private Boolean isFromActivity;

    private ArrayList<HashMap<String, Object>> mostViewedProducts = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> mostOrderedProducts = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> mostSharedProducts = new ArrayList<>();

    private Boolean isFromViewedProducts;
    private Boolean isFromOrderdProducts;
    private Boolean isFromSharedProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        ButterKnife.bind(this);
        fontAwesome = Typeface.createFromAsset(this.getAssets(), "fontawesomeicon.ttf");
        backIconTextView.setTypeface(fontAwesome);
        sortIconTextView.setTypeface(fontAwesome);
        setOnClickListener();

        categoryId = getIntent().getIntExtra("categoryId", 0);
        categoryName = getIntent().getStringExtra("categoryName");
        isFromActivity = getIntent().getBooleanExtra("isFromActivity", false);
        isFromViewedProducts = getIntent().getBooleanExtra("isFromViewedProducts", false);;
        isFromOrderdProducts = getIntent().getBooleanExtra("isFromOrderdProducts", false);;
        isFromSharedProducts = getIntent().getBooleanExtra("isFromSharedProducts", false);;

        databaseHelper = new DatabaseHelper(this);
        products = databaseHelper.getProducts(categoryId);

        mostViewedProducts = databaseHelper.getMostViewedProductsProducts(categoryId);
        mostOrderedProducts = databaseHelper.getMostOrderedProductsProducts(categoryId);
        mostSharedProducts = databaseHelper.getMostSharedProductsProducts(categoryId);


        if(!products.isEmpty()) {
            sortIconRelativeLayout.setVisibility(View.VISIBLE);
            childCategoryNameTextView.setText(categoryName);
            if(isFromActivity) {
                ProductsGridViewAdapter productsGridViewAdapter = new ProductsGridViewAdapter(this, products);
                productsGridView.setAdapter(productsGridViewAdapter);
            }else if(isFromViewedProducts){
                ProductsGridViewAdapter productsGridViewAdapter = new ProductsGridViewAdapter(this, mostViewedProducts);
                productsGridView.setAdapter(productsGridViewAdapter);
            }else if(isFromOrderdProducts) {
                ProductsGridViewAdapter productsGridViewAdapter = new ProductsGridViewAdapter(this, mostOrderedProducts);
                productsGridView.setAdapter(productsGridViewAdapter);
            }else if(isFromSharedProducts) {
                ProductsGridViewAdapter productsGridViewAdapter = new ProductsGridViewAdapter(this, mostSharedProducts);
                productsGridView.setAdapter(productsGridViewAdapter);
            }
        }else {
            sortIconRelativeLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backIconRelativeLayout:
                onBackIconPressed();
                break;
                
            case R.id.sortIconRelativeLayout:
                sort();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        onBackIconPressed();
    }

    private void onBackIconPressed() {
        Intent intent = new Intent(this, ChildCategoriesActivity.class);
        startActivity(intent);
    }

    private void sort() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alert_message_box, null, false);
        RecyclerView sortByRecyclerView = view.findViewById(R.id.sortByRecyclerView);
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog mDialog = builder.show();
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        int[] array = new int[2];
        sortIconRelativeLayout.getLocationOnScreen(array);

        if (array.length >= 2) {
            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            Window dialogWindow = mDialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            dialogWindow.setGravity(Gravity.START | Gravity.TOP);
            lp.x = array[0];
            lp.y = array[1] - GlobalUtility.dpToPxl(this, (18.0f + 22.0f));
            int mWidth = GlobalUtility.ptToPxl(this, 70.0f);
            lp.width = mWidth;
            dialogWindow.setAttributes(lp);
        }

        SortByRecyclerViewAdapter sortByRecyclerViewAdapter = new SortByRecyclerViewAdapter(this, mDialog, categoryId, categoryName);
        sortByRecyclerView.setAdapter(sortByRecyclerViewAdapter);

        mDialog.show();
    }
    
    private void setOnClickListener() {
        backIconRelativeLayout.setOnClickListener(this);
        sortIconRelativeLayout.setOnClickListener(this);
    }
}
