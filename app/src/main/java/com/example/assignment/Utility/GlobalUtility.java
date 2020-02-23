package com.example.assignment.Utility;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.example.assignment.R;

public class GlobalUtility {

    private static Dialog progressDialog;

    public static boolean checkInternetConnection(Context context) {
        return isNetworkConnected(context);
    }

    private static boolean isNetworkConnected(Context context) {
        int[] networkTypes = {ConnectivityManager.TYPE_MOBILE,
                ConnectivityManager.TYPE_WIFI};
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            for (int networkType : networkTypes) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null &&
                        activeNetworkInfo.getType() == networkType)
                    return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static void showLoader(Context mContext) {
        Dialog loader = getLoadingSpinner(mContext);
        loader.show();
    }

    private static Dialog getLoadingSpinner(Context mContext) {
        progressDialog = new Dialog(mContext, android.R.style.Theme_Black);
        View view = LayoutInflater.from(mContext).inflate(R.layout.progress_dialog, null);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawableResource(R.color.colorTransparent);
        progressDialog.setContentView(view);
        return progressDialog;
    }

    public static void hideLoader(Context mContext) {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    public static int dpToPxl(Context pContext, float pDp) {
        DisplayMetrics dm = pContext.getResources().getDisplayMetrics();
        int densityDpi = dm.densityDpi;

        return (int) (densityDpi * (pDp / 160.0f) + 0.5f);
    }

    public static int ptToPxl(Context pContext, float pPt) {
        DisplayMetrics dm = pContext.getResources().getDisplayMetrics();
        int densityDpi = dm.densityDpi;

        return (int) (densityDpi * (pPt / 72.0f) + 0.5f);
    }
    
    public static void setDynamicHeight(GridView gridView) {
        ListAdapter gridViewAdapter = gridView.getAdapter();
        if (gridViewAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = gridViewAdapter.getCount();
        int rows = 0;

        View listItem = gridViewAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        float x = 1;
        if( items > 2 ){
            x = items/2;
            if(items % 2 != 0) {
                rows = (int) (x + 1);
            }else{
                rows = (int) (x);
            }
            totalHeight *= rows;
        }else{
            totalHeight *= 1;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        //params.height = totalHeight;
        if(rows > 1) {
            params.height = totalHeight + (rows * 20) + 20;
        }else{
            params.height = totalHeight + 20;
        }
        gridView.setLayoutParams(params);
    }
}
