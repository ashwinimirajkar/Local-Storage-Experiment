package com.example.assignment.View;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.Controller.GetApiDataController;
import com.example.assignment.Interface.GetApiInterface;
import com.example.assignment.Models.ResponseModel.GetApiResponse;
import com.example.assignment.Models.ResponseModel.RankingProductItem;
import com.example.assignment.R;
import com.example.assignment.SQLiteDatabase.DatabaseHelper;
import com.example.assignment.Utility.GlobalUtility;
import com.example.assignment.Utility.SharedPrefrenceManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesActivity extends AppCompatActivity implements GetApiInterface, View.OnClickListener {

    @BindView(R.id.toolBar) Toolbar toolBar;
    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.categoriesRecyclerView) RecyclerView categoriesRecyclerView;
    @BindView(R.id.noNetworkRelativeLayout) RelativeLayout noNetworkRelativeLayout;
    @BindView(R.id.retryTextView) TextView retryTextView;
    private ActionBarDrawerToggle toggle;
    boolean doubleBackToExitPressedOnce = false;
    private GetApiDataController getApiDataController = null;
    private SharedPrefrenceManager sharedPrefrenceManager = null;
    DatabaseHelper databaseHelper;

    private static HashMap<String, String> rankingMapping = new HashMap<>();
    private static HashMap<String, String> countMapping = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.left_navigation_drawer);
        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        setUpLeftNavigationView();
        databaseHelper = new DatabaseHelper(this);
        setOnClickListener();
        sharedPrefrenceManager = new SharedPrefrenceManager(this);

        if (sharedPrefrenceManager.isNull(sharedPrefrenceManager.IS_FIRST_API_CALL) ||
                sharedPrefrenceManager.isTrue(SharedPrefrenceManager.IS_FIRST_API_CALL)) {

            if(GlobalUtility.checkInternetConnection(this)) {
                noNetworkRelativeLayout.setVisibility(View.GONE);
                categoriesRecyclerView.setVisibility(View.VISIBLE);

                getApiDataController = new GetApiDataController(this);
                GlobalUtility.showLoader(this);
                getApiDataController.fireGetDataApi(this);

            }else {
                noNetworkRelativeLayout.setVisibility(View.VISIBLE);
                categoriesRecyclerView.setVisibility(View.GONE);
            }
        }

        if(!databaseHelper.getParentCategories().isEmpty()) {
            ViewCompat.setNestedScrollingEnabled(categoriesRecyclerView, false);
            CategoriesRecyclerViewAdapter categoriesRecyclerViewAdapter = new CategoriesRecyclerViewAdapter(this, databaseHelper.getParentCategories(), databaseHelper);
            categoriesRecyclerView.setAdapter(categoriesRecyclerViewAdapter);
        }
    }

    private void setUpLeftNavigationView(){
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toggle.setDrawerIndicatorEnabled(true);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorWhite));

        toolBar.setNavigationOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        toggle.syncState();
    }
    
    @Override
    public void onBackPressed() {
        backButtonPress();
    }
    private void backButtonPress(){
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            System.exit(0);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.exit_msg), Toast.LENGTH_LONG).show();
        new android.os.Handler().postDelayed(() -> doubleBackToExitPressedOnce=false, 5000);
    }

    @Override
    public void getApiDataSucessfull(GetApiResponse data) {
        if(data != null) {
            sharedPrefrenceManager.saveValue(SharedPrefrenceManager.IS_FIRST_API_CALL, SharedPrefrenceManager.FALSE);

            if(!data.getCategories().isEmpty()) {
                for(int i = 0; i < data.getCategories().size(); i++) {
                    databaseHelper.insertDataIntoCategoriesTable(data.getCategories().get(i).getId(), data.getCategories().get(i).getName(), 0);
                    if(!data.getCategories().get(i).getProducts().isEmpty()) {
                        for(int j = 0; j < data.getCategories().get(i).getProducts().size(); j++) {
                            databaseHelper.insertDataIntoProductsTable(data.getCategories().get(i).getProducts().get(j).getId(), data.getCategories().get(i).getProducts().get(j).getName(), data.getCategories().get(i).getProducts().get(j).getDateAdded(), 0, 0, 0, data.getCategories().get(i).getProducts().get(j).getTax().getName(), data.getCategories().get(i).getProducts().get(j).getTax().getValue(), 0.0, data.getCategories().get(i).getId() );


                            if(!data.getCategories().get(i).getProducts().get(j).getVariants().isEmpty()) {
                                ArrayList<Double> prices = new ArrayList<>();
                                for(int k = 0; k < data.getCategories().get(i).getProducts().get(j).getVariants().size(); k++) {
                                   databaseHelper.insertDataIntoVarientsTable(data.getCategories().get(i).getProducts().get(j).getVariants().get(k).getId(), data.getCategories().get(i).getProducts().get(j).getVariants().get(k).getColor(), data.getCategories().get(i).getProducts().get(j).getVariants().get(k).getSize(), data.getCategories().get(i).getProducts().get(j).getVariants().get(k).getPrice(), data.getCategories().get(i).getProducts().get(j).getId());
                                   prices.add(data.getCategories().get(i).getProducts().get(j).getVariants().get(k).getPrice());
                                }

                                databaseHelper.updateStartPrice(data.getCategories().get(i).getProducts().get(j).getId(), Collections.min(prices));
                            }
                        }
                    }
                }

                for(int i = 0; i < data.getCategories().size(); i++) {
                    if (!data.getCategories().get(i).getChildCategories().isEmpty()) {
                        for (int n = 0; n < data.getCategories().get(i).getChildCategories().size(); n++) {
                            if (databaseHelper.isCategoryExist(data.getCategories().get(i).getChildCategories().get(n))) {
                                databaseHelper.updateCategoryTableParentCategoryId(data.getCategories().get(i).getChildCategories().get(n), data.getCategories().get(i).getId());
                            }
                        }
                    }
                }
            }

            try {
                if(!data.getRankings().isEmpty()) {
                    for(int i = 0; i < data.getRankings().size(); i++) {
                        String columnName = getRankingMapping(data.getRankings().get(i).getRanking());
                        String countKey = getCountMapping(data.getRankings().get(i).getRanking());

                        if(!data.getRankings().get(i).getProducts().isEmpty()) {
                            for(int j = 0; j < data.getRankings().get(i).getProducts().size(); j++) {
                                RankingProductItem rankingProductItem = data.getRankings().get(i).getProducts().get(j);
                                Field countField = rankingProductItem.getClass().getDeclaredField(countKey);

                                databaseHelper.updateRankableCount(
                                        rankingProductItem.getId(),
                                        columnName,
                                        (Integer) countField.get(rankingProductItem)
                                );
                            }
                        }
                    }
                }
            } catch(Exception e) {
                System.out.println(e.toString());
            }
        }
        recreate();
        GlobalUtility.hideLoader(this);
    }

    private String getCountMapping(String rankingLabel){
        if(countMapping.isEmpty()) {
            countMapping.put("Most Viewed Products", "view_count");
            countMapping.put("Most OrdeRed Products", "order_count");
            countMapping.put("Most ShaRed Products", "shares");
        }
        return countMapping.get(rankingLabel);
    }

    private String getRankingMapping(String rankingLabel){
        if(rankingMapping.isEmpty()) {
            rankingMapping.put("Most Viewed Products", "productViewCount");
            rankingMapping.put("Most OrdeRed Products", "productOrderCount");
            rankingMapping.put("Most ShaRed Products", "productShareCount");
        }
        return rankingMapping.get(rankingLabel);
    }

    @Override
    public void getApiDataFailed(String error) {
        GlobalUtility.hideLoader(this);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.retryTextView:
                recreate();
                break;
        }
    }

    private void setOnClickListener() {
        retryTextView.setOnClickListener(this);
    }
}
