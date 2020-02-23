package com.example.assignment.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.R;
import com.example.assignment.SQLiteDatabase.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class ChildCategoriesRecyclerViewAdapter extends RecyclerView.Adapter<ChildCategoriesRecyclerViewAdapter.verticalView>{

    private Context context;
    private Typeface fontAwesome;
    private ArrayList<HashMap<String, Object>> childCategories = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    public class verticalView extends RecyclerView.ViewHolder {

        LinearLayout childCategoryLinearLayout;
        TextView childCategoryNameTextView;
        
        public verticalView(View view) {
            super(view);
            childCategoryLinearLayout = view.findViewById(R.id.childCategoryLinearLayout);
            childCategoryNameTextView = view.findViewById(R.id.childCategoryNameTextView);
        }
    }

    public ChildCategoriesRecyclerViewAdapter(Context context, ArrayList<HashMap<String, Object>> childCategories, DatabaseHelper databaseHelper) {
        this.context = context;
        this.childCategories = childCategories;
        this.databaseHelper = databaseHelper;
    }

    @Override
    public ChildCategoriesRecyclerViewAdapter.verticalView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_category_recycler_view_item, parent, false);
        fontAwesome = Typeface.createFromAsset(context.getAssets(), "fontawesomeicon.ttf");
        return new ChildCategoriesRecyclerViewAdapter.verticalView(view);
    }

    @Override
    public void onBindViewHolder(final ChildCategoriesRecyclerViewAdapter.verticalView holder, int position) {
        if(!childCategories.isEmpty()) {
            holder.childCategoryNameTextView.setText((CharSequence) childCategories.get(position).get("categoryName"));
        }

        holder.childCategoryLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductsActivity.class);
                intent.putExtra("categoryId", (Integer) childCategories.get(position).get("categoryId"));
                intent.putExtra("categoryName", (String) childCategories.get(position).get("categoryName"));
                intent.putExtra("isFromActivity", true);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return childCategories.size();
    }
}