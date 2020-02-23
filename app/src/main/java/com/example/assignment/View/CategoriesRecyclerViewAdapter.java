package com.example.assignment.View;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.R;
import com.example.assignment.SQLiteDatabase.DatabaseHelper;
import com.example.assignment.Utility.GlobalUtility;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoriesRecyclerViewAdapter extends RecyclerView.Adapter<CategoriesRecyclerViewAdapter.verticalView>{

    private Context context;
    private Typeface fontAwesome;
    private Boolean isClicked = false;
    private ArrayList<HashMap<String, Object>> parentCategories = null;
    private DatabaseHelper databaseHelper;
    
    public class verticalView extends RecyclerView.ViewHolder {

        ImageView categoryIconImageView;
        TextView categoryNameTextView;
        TextView expandCollapseTextView;
        GridView subCategoryGridView;
        RelativeLayout expandCollapseRelativeLayout;
        LinearLayout parentCategoryLinearLayout;

        public verticalView(View view) {
            super(view);
            categoryIconImageView = view.findViewById(R.id.categoryIconImageView);
            categoryNameTextView = view.findViewById(R.id.categoryNameTextView);
            expandCollapseTextView = view.findViewById(R.id.expandCollapseTextView);
            subCategoryGridView = view.findViewById(R.id.subCategoryGridView);
            expandCollapseRelativeLayout = view.findViewById(R.id.expandCollapseRelativeLayout);
            parentCategoryLinearLayout = view.findViewById(R.id.parentCategoryLinearLayout);
        }
    }

    public CategoriesRecyclerViewAdapter(Context context, ArrayList<HashMap<String, Object>> parentCategories, DatabaseHelper databaseHelper) {
        this.context = context;
        this.parentCategories = parentCategories;
        this.databaseHelper = databaseHelper;
    }

    @Override
    public verticalView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_recycler_view_item, parent, false);
        fontAwesome = Typeface.createFromAsset(context.getAssets(), "fontawesomeicon.ttf");
        return new verticalView(view);
    }

    @Override
    public void onBindViewHolder(final verticalView holder, int position) {

        holder.expandCollapseTextView.setTypeface(fontAwesome);
        holder.categoryIconImageView.setImageResource(R.drawable.tshirt);

        if(!parentCategories.isEmpty()) {
           holder.categoryNameTextView.setText((CharSequence) parentCategories.get(position).get("categoryName"));
        }


        ArrayList<HashMap<String, Object>> immediateChildCategories = databaseHelper.getImmediateChildCategories((Integer) parentCategories.get(position).get("categoryId"));
        if(!immediateChildCategories.isEmpty()) {
            holder.expandCollapseRelativeLayout.setVisibility(View.VISIBLE);
            SubCategoriesGridViewAdapter subCategoriesGridViewAdapter = new SubCategoriesGridViewAdapter(context, position, immediateChildCategories, databaseHelper);
            holder.subCategoryGridView.setAdapter(subCategoriesGridViewAdapter);
            GlobalUtility.setDynamicHeight(holder.subCategoryGridView);

            holder.parentCategoryLinearLayout.setOnClickListener(view -> {
                if (holder.expandCollapseTextView.getText().equals("\uF107")) {
                    holder.expandCollapseTextView.setText(R.string.icon_angle_up);
                    holder.subCategoryGridView.setVisibility(View.VISIBLE);
                } else {
                    holder.expandCollapseTextView.setText(R.string.icon_angle_down);
                    holder.subCategoryGridView.setVisibility(View.GONE);
                }
            });
        }else {
            holder.expandCollapseRelativeLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return parentCategories.size();
    }
}
