package com.example.assignment.View;

import android.graphics.Typeface;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChildCategoriesActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.backIconTextView) TextView backIconTextView;
    @BindView(R.id.backIconRelativeLayout) RelativeLayout backIconRelativeLayout;
    @BindView(R.id.childCategoriesRecyclerView) RecyclerView childCategoriesRecyclerView;
    @BindView(R.id.subCategoryNameTextView) TextView subCategoryNameTextView;

    private Typeface fontAwesome;
    private ArrayList<HashMap<String, Object>> childCategories = new ArrayList<>();
    private DatabaseHelper databaseHelper = null;
    private String categoryName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_categories);
        ButterKnife.bind(this);
        setOnClickListener();

        fontAwesome = Typeface.createFromAsset(this.getAssets(), "fontawesomeicon.ttf");
        backIconTextView.setTypeface(fontAwesome);

        childCategories = (ArrayList<HashMap<String, Object>>) getIntent().getSerializableExtra("childCategories");
        categoryName = getIntent().getStringExtra("categoryName");
        databaseHelper = new DatabaseHelper(this);

        if(!childCategories.isEmpty()) {
            subCategoryNameTextView.setText(categoryName);

            ChildCategoriesRecyclerViewAdapter childCategoriesRecyclerViewAdapter = new ChildCategoriesRecyclerViewAdapter(this, childCategories, databaseHelper);
            childCategoriesRecyclerView.setAdapter(childCategoriesRecyclerViewAdapter);
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
