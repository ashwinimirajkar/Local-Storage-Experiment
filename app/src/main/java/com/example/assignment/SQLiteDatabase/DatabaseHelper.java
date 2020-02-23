package com.example.assignment.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    public SQLiteDatabase db;
    public static final String DATABASE_NAME = "EcommerceDatabase.db";
    public static final String CATEGORIES_TABLE_NAME = "CategoriesTable";
    public static final String PRODUCTS_TABLE_NAME = "ProductsTable";
    public static final String VARIENTS_TABLE_NAME = "VarientsTable";

    // Categories table column names
    public static final String categoryId = "categoryId";
    public static final String categoryName = "categoryName";
    public static final String parentCategoryId = "parentCategoryId";
    
    // Products table column names
    public static final String productId = "productId";
    public static final String productName = "productName";
    public static final String lastUpdatedDateTime = "lastUpdatedDateTime";
    public static final String productViewCount = "productViewCount";
    public static final String productOrderCount = "productOrderCount";
    public static final String productShareCount = "productShareCount";
    public static final String taxName = "taxName";
    public static final String taxPercentageValue = "taxPercentageValue";
    public static final String productStartPrice = "productStartPrice";

    // Varients table column names
    public static final String varientId = "varientId";
    public static final String varientColor = "varientColor";
    public static final String varientSize = "varientSize";
    public static final String varientPrice = "varientPrice";

    public static ArrayList<HashMap<String, Object>> parentCategories = new ArrayList<>();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 17);
        db = getWritableDatabase();
    }
    
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.db = sqLiteDatabase;
        db.execSQL("create table " + CATEGORIES_TABLE_NAME + " (categoryId INTEGER, categoryName TEXT, parentCategoryId INTEGER) ");
        db.execSQL("create table " + PRODUCTS_TABLE_NAME + " (productId INTEGER, productName TEXT, lastUpdatedDateTime TEXT, productViewCount INTEGER, productOrderCount INTEGER, productShareCount IINTEGER, taxName TEXT, taxPercentageValue REAL, productStartPrice REAL, categoryId INTEGER) ");
        db.execSQL("create table " + VARIENTS_TABLE_NAME + " (varientId INTEGER, varientColor TEXT, varientSize INTEGER, varientPrice REAL, productId INTEGER)");
    
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        this.db = sqLiteDatabase;
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + VARIENTS_TABLE_NAME);

        onCreate(db);
    }

    public boolean insertDataIntoCategoriesTable(Integer categoryId, String categoryName, Integer parentCategoryId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.categoryId, categoryId);
        contentValues.put(DatabaseHelper.categoryName, categoryName);
        contentValues.put(DatabaseHelper.parentCategoryId, parentCategoryId);
        long result = db.insert(CATEGORIES_TABLE_NAME, null, contentValues);
        if(result == -1) {
            return false;
        }else {
            return true;
        }
    }

    public boolean insertDataIntoProductsTable(Integer productId, String productName, String lastUpdatedDateTime, Integer productViewCount, Integer productOrderCount, Integer productShareCount, String taxName, Double taxPercentageValue, Double productStartPrice, Integer categoryId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.productId, productId);
        contentValues.put(DatabaseHelper.productName, productName);
        contentValues.put(DatabaseHelper.lastUpdatedDateTime, lastUpdatedDateTime);
        contentValues.put(DatabaseHelper.productViewCount, productViewCount);
        contentValues.put(DatabaseHelper.productOrderCount, productOrderCount);
        contentValues.put(DatabaseHelper.productShareCount, productShareCount);
        contentValues.put(DatabaseHelper.taxName, taxName);
        contentValues.put(DatabaseHelper.taxPercentageValue, taxPercentageValue);
        contentValues.put(DatabaseHelper.productStartPrice, productStartPrice);
        contentValues.put(DatabaseHelper.categoryId, categoryId);
        long result = db.insert(PRODUCTS_TABLE_NAME, null, contentValues);
        if(result == -1) {
            return false;
        }else {
            return true;
        }
    }

    public boolean insertDataIntoVarientsTable(Integer varientId, String varientColor, Integer varientSize, Double varientPrice, Integer productId ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.varientId, varientId);
        contentValues.put(DatabaseHelper.varientColor, varientColor);
        contentValues.put(DatabaseHelper.varientSize, varientSize);
        contentValues.put(DatabaseHelper.varientPrice, varientPrice);
        contentValues.put(DatabaseHelper.productId, productId);
        long result = db.insert(VARIENTS_TABLE_NAME, null, contentValues);
        if(result == -1) {
            return false;
        }else {
            return true;
        }
    }

    public boolean updateCategoryTableParentCategoryId(Integer categoryId, Integer parentCategoryId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.parentCategoryId, parentCategoryId);
        long result = db.update(CATEGORIES_TABLE_NAME, contentValues, DatabaseHelper.categoryId + " = " + categoryId, null);
        return true;
    }

    public boolean updateRankableCount(Integer productId, String columnName, Integer count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(columnName, count);
        long result = db.update(PRODUCTS_TABLE_NAME, contentValues, DatabaseHelper.productId + " = " + productId, null);
        return true;
    }

    public boolean isCategoryExist(Integer categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + CATEGORIES_TABLE_NAME + " WHERE " + DatabaseHelper.categoryId + " = " + categoryId, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean updateStartPrice(Integer productId, Double productStartPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.productStartPrice, productStartPrice);
        long result = db.update(PRODUCTS_TABLE_NAME, contentValues, DatabaseHelper.productId + " = " + productId, null);
        return true;
    }

    public ArrayList<HashMap<String, Object>> getParentCategories() {
        if(!parentCategories.isEmpty()) {
            return parentCategories;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + DatabaseHelper.categoryId + "," + DatabaseHelper.categoryName + " from " + CATEGORIES_TABLE_NAME + " WHERE " + DatabaseHelper.parentCategoryId + " = " + 0, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, Object> parentCategory = new HashMap<>();
                    parentCategory.put(categoryId, cursor.getInt(cursor.getColumnIndex(categoryId)));
                    parentCategory.put(categoryName, cursor.getString(cursor.getColumnIndex(categoryName)));
                    parentCategories.add(parentCategory);

                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(e.getMessage(), "Error while trying to get categories from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return parentCategories;
    }

    public ArrayList<HashMap<String, Object>> getImmediateChildCategories(Integer parentCategoryId) {

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, Object>> childCategories = new ArrayList<>();
        Cursor cursor = db.rawQuery("select " + DatabaseHelper.categoryId + "," + DatabaseHelper.categoryName + " from " + CATEGORIES_TABLE_NAME + " WHERE " + DatabaseHelper.parentCategoryId + " = " + parentCategoryId, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, Object> childCategory = new HashMap<>();
                    childCategory.put(categoryId, cursor.getInt(cursor.getColumnIndex(categoryId)));
                    childCategory.put(categoryName, cursor.getString(cursor.getColumnIndex(categoryName)));
                    childCategories.add(childCategory);

                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(e.getMessage(), "Error while trying to get categories from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return childCategories;
    }

    public ArrayList<HashMap<String, Object>> getProducts(Integer categoryId) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + PRODUCTS_TABLE_NAME + " WHERE " + DatabaseHelper.categoryId + " = " + categoryId, null);
        ArrayList<HashMap<String, Object>> products = new ArrayList<>();

        try {
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, Object> singleProduct = new HashMap<>();
                    singleProduct.put(productId, cursor.getInt(cursor.getColumnIndex(productId)));
                    singleProduct.put(productName, cursor.getString(cursor.getColumnIndex(productName)));
                    singleProduct.put(lastUpdatedDateTime, cursor.getString(cursor.getColumnIndex(lastUpdatedDateTime)));
                    singleProduct.put(productViewCount, cursor.getInt(cursor.getColumnIndex(productViewCount)));
                    singleProduct.put(productOrderCount, cursor.getInt(cursor.getColumnIndex(productOrderCount)));
                    singleProduct.put(productShareCount, cursor.getInt(cursor.getColumnIndex(productShareCount)));
                    singleProduct.put(taxName, cursor.getString(cursor.getColumnIndex(taxName)));
                    singleProduct.put(taxPercentageValue, cursor.getDouble(cursor.getColumnIndex(taxPercentageValue)));
                    singleProduct.put(productStartPrice, cursor.getDouble(cursor.getColumnIndex(productStartPrice)));
                    products.add(singleProduct);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(e.getMessage(), "Error while trying to get categories from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return products;
    }

    public ArrayList<HashMap<String, Object>> getVarients(Integer productId) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + VARIENTS_TABLE_NAME + " WHERE " + DatabaseHelper.productId + " = " + productId, null);
        ArrayList<HashMap<String, Object>> varients = new ArrayList<>();

        try {
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, Object> singleProductVarients = new HashMap<>();
                    singleProductVarients.put(varientId, cursor.getInt(cursor.getColumnIndex(varientId)));
                    singleProductVarients.put(varientColor, cursor.getString(cursor.getColumnIndex(varientColor)));
                    singleProductVarients.put(varientSize, cursor.getInt(cursor.getColumnIndex(varientSize)));
                    singleProductVarients.put(varientPrice, cursor.getDouble(cursor.getColumnIndex(varientPrice)));
                    varients.add(singleProductVarients);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(e.getMessage(), "Error while trying to get categories from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return varients;
    }


    public ArrayList<HashMap<String, Object>> getMostOrderedProductsProducts(Integer categoryId) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + DatabaseHelper.productId + "," + DatabaseHelper.productName + "," + DatabaseHelper.productOrderCount + "," + DatabaseHelper.productViewCount + "," + DatabaseHelper.productShareCount+ "," + DatabaseHelper.taxPercentageValue + "," + DatabaseHelper.productStartPrice + " from " + PRODUCTS_TABLE_NAME + " WHERE " + DatabaseHelper.categoryId + " = " + categoryId + " ORDER BY " + DatabaseHelper.productOrderCount  + " DESC ", null);

        ArrayList<HashMap<String, Object>> products = new ArrayList<>();

        try {
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, Object> singleProduct = new HashMap<>();
                    singleProduct.put(productId, cursor.getInt(cursor.getColumnIndex(productId)));
                    singleProduct.put(productName, cursor.getString(cursor.getColumnIndex(productName)));
                    singleProduct.put(productOrderCount, cursor.getInt(cursor.getColumnIndex(productOrderCount)));
                    singleProduct.put(productViewCount, cursor.getInt(cursor.getColumnIndex(productViewCount)));
                    singleProduct.put(productShareCount, cursor.getInt(cursor.getColumnIndex(productShareCount)));
                    singleProduct.put(taxPercentageValue, cursor.getDouble(cursor.getColumnIndex(taxPercentageValue)));
                    singleProduct.put(productStartPrice, cursor.getDouble(cursor.getColumnIndex(productStartPrice)));
                    products.add(singleProduct);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(e.getMessage(), "Error while trying to get categories from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return products;
    }



    public ArrayList<HashMap<String, Object>> getMostViewedProductsProducts(Integer categoryId) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + DatabaseHelper.productId + "," + DatabaseHelper.productName + "," + DatabaseHelper.productViewCount + "," + DatabaseHelper.productOrderCount + "," + DatabaseHelper.productShareCount +"," + DatabaseHelper.taxPercentageValue + "," + DatabaseHelper.productStartPrice + " from " + PRODUCTS_TABLE_NAME + " WHERE " + DatabaseHelper.categoryId + " = " + categoryId + " ORDER BY " + DatabaseHelper.productViewCount  + " DESC ", null);

        ArrayList<HashMap<String, Object>> products = new ArrayList<>();

        try {
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, Object> singleProduct = new HashMap<>();
                    singleProduct.put(productId, cursor.getInt(cursor.getColumnIndex(productId)));
                    singleProduct.put(productName, cursor.getString(cursor.getColumnIndex(productName)));
                    singleProduct.put(productViewCount, cursor.getInt(cursor.getColumnIndex(productViewCount)));
                    singleProduct.put(productOrderCount, cursor.getInt(cursor.getColumnIndex(productOrderCount)));
                    singleProduct.put(productShareCount, cursor.getInt(cursor.getColumnIndex(productShareCount)));
                    singleProduct.put(taxPercentageValue, cursor.getDouble(cursor.getColumnIndex(taxPercentageValue)));
                    singleProduct.put(productStartPrice, cursor.getDouble(cursor.getColumnIndex(productStartPrice)));
                    products.add(singleProduct);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(e.getMessage(), "Error while trying to get categories from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return products;
    }


    public ArrayList<HashMap<String, Object>> getMostSharedProductsProducts(Integer categoryId) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + DatabaseHelper.productId + "," + DatabaseHelper.productName + "," + DatabaseHelper.productShareCount + "," + DatabaseHelper.productViewCount + "," + DatabaseHelper.productOrderCount+ "," + DatabaseHelper.taxPercentageValue + "," + DatabaseHelper.productStartPrice + " from " + PRODUCTS_TABLE_NAME + " WHERE " + DatabaseHelper.categoryId + " = " + categoryId + " ORDER BY " + DatabaseHelper.productShareCount  + " DESC ", null);

        ArrayList<HashMap<String, Object>> products = new ArrayList<>();

        try {
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, Object> singleProduct = new HashMap<>();
                    singleProduct.put(productId, cursor.getInt(cursor.getColumnIndex(productId)));
                    singleProduct.put(productName, cursor.getString(cursor.getColumnIndex(productName)));
                    singleProduct.put(productShareCount, cursor.getInt(cursor.getColumnIndex(productShareCount)));
                    singleProduct.put(productViewCount, cursor.getInt(cursor.getColumnIndex(productViewCount)));
                    singleProduct.put(productOrderCount, cursor.getInt(cursor.getColumnIndex(productOrderCount)));
                    singleProduct.put(taxPercentageValue, cursor.getDouble(cursor.getColumnIndex(taxPercentageValue)));
                    singleProduct.put(productStartPrice, cursor.getDouble(cursor.getColumnIndex(productStartPrice)));
                    products.add(singleProduct);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(e.getMessage(), "Error while trying to get categories from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return products;
    }

}

