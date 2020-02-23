package com.example.assignment.View;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.assignment.R;
import com.example.assignment.SQLiteDatabase.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubCategoriesGridViewAdapter extends BaseAdapter {

    @BindView(R.id.subCategoryLinearLayout) LinearLayout subCategoryLinearLayout;
    @BindView(R.id.subCategoryImageView) ImageView subCategoryImageView;
    @BindView(R.id.subCategoryNameTextView) TextView subCategoryNameTextView;
    
    private Context context;
    private int pos = 0;
    private ArrayList<HashMap<String, Object>> immediateChildCategories = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    public SubCategoriesGridViewAdapter(Context context, int pos, ArrayList<HashMap<String, Object>> immediateChildCategories, DatabaseHelper databaseHelper) {
        this.context = context;
        this.pos = pos;
        this.immediateChildCategories = immediateChildCategories;
        this.databaseHelper = databaseHelper;
    }

    @Override
    public int getCount() {
        return immediateChildCategories.size();
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
        convertView = layoutInflater.inflate(R.layout.parent_category_grid_view_item, null);
        ButterKnife.bind(this, convertView);

        ArrayList<HashMap<String, Object>> childCategories = databaseHelper.getImmediateChildCategories((Integer) immediateChildCategories.get(position).get("categoryId"));


        if(!childCategories.isEmpty()) {
            subCategoryNameTextView.setText((CharSequence) immediateChildCategories.get(position).get("categoryName"));

            subCategoryLinearLayout.setOnClickListener(view -> {
                Intent intent = new Intent(context, ChildCategoriesActivity.class);
                intent.putExtra("childCategories", childCategories);
                intent.putExtra("categoryName", (String) immediateChildCategories.get(position).get("categoryName"));
                context.startActivity(intent);
            });
        }else {
            Intent intent = new Intent(context, ProductsActivity.class);
            intent.putExtra("categoryId", (Integer) immediateChildCategories.get(position).get("categoryId"));
            intent.putExtra("categoryName", (String) immediateChildCategories.get(position).get("categoryName"));
            intent.putExtra("isFromActivity", true);
            context.startActivity(intent);
        }
        
        return convertView;
    }
}
