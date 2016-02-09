package com.example.razor.ejevika.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.razor.ejevika.dummy.Category;
import com.example.razor.ejevika.dummy.Product;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by razor on 06.02.2016.
 */
public class DBEjevika {

    private static String LOG_DB_TAG = "db_tag";

    private EjevikaHelper ejevikaHelper;
    private SQLiteDatabase sqLiteDatabase;

    public DBEjevika(Context appContext) {
        ejevikaHelper = new EjevikaHelper(appContext);
        sqLiteDatabase = ejevikaHelper.getWritableDatabase();
    }

    public void insertCategories(ArrayList<Category> categories, boolean clearPrev){
        if(clearPrev){
            deleteCategories();
        }
        String sql = "INSERT INTO "+EjevikaHelper.TABLE_CATEGORIES+" VALUES(?,?,?);";
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);

        sqLiteDatabase.beginTransaction();
        for (int i = 0;categories.size()>i;i++){
            Category category = categories.get(i);
            sqLiteStatement.clearBindings();
            sqLiteStatement.bindLong(1, category.getId());
            sqLiteStatement.bindString(2, category.getPicture());
            sqLiteStatement.bindString(3, category.getName());

            sqLiteStatement.execute();
        }

        Log.d(LOG_DB_TAG,"inserting "+categories.size()+" row  for time "+new Date(System.currentTimeMillis()));
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

    public void insertProduct(ArrayList<Product> products, long categoryId, boolean clearPrev){
        if(clearPrev){
            deleteProducts(categoryId);
        }
        String sql = "INSERT INTO "+EjevikaHelper.TABLE_PRODUCT+" VALUES(?,?,?,?,?);";
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);

        sqLiteDatabase.beginTransaction();
        for (int i = 0;products.size()>i;i++){
            Product product = products.get(i);
            sqLiteStatement.clearBindings();
            sqLiteStatement.bindLong(1, product.getId());
            sqLiteStatement.bindString(2, product.getName());
            sqLiteStatement.bindString(3, product.getPicture());
            sqLiteStatement.bindLong(4, product.getSectionId());
            sqLiteStatement.bindDouble(5, product.getPrice());
            sqLiteStatement.execute();
        }

        Log.d(LOG_DB_TAG,"inserting products "+products.size()+" row  for time "+new Date(System.currentTimeMillis()));
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

    public Cursor readCategory(){
        Cursor cursor = sqLiteDatabase.query(EjevikaHelper.TABLE_CATEGORIES,null,null,null,null,null,null);
        return cursor;
    }

    public Cursor readProduct(long sectionId){
        String where = EjevikaHelper.COLUMN_SECTION_ID_PRODUCT+"=?";
        if(sectionId==-1)
            where = null;
        Cursor cursor = sqLiteDatabase.query(EjevikaHelper.TABLE_PRODUCT,
                null,where,
                new String[]{String.valueOf(sectionId)},
                null,
                null,
                null);
        return cursor;
    }

    public static ArrayList<Category>  getCategoriesFromCursor(Cursor cursor) {
        ArrayList<Category> categories = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                long id = cursor.getLong(cursor.getColumnIndex(EjevikaHelper.COLUMN_ID));

               String name = cursor.getString(cursor.getColumnIndex(EjevikaHelper.COLUMN_NAME_CATEGORY));

               String picture = cursor.getString(cursor.getColumnIndex(EjevikaHelper.COLUMN_PICTURE_CATEGORY));
                Category category = new Category(id, name, picture);
                categories.add(category);
            } while (cursor.moveToNext());
        }
        return categories;
    }

    public static ArrayList<Product> getProductsFromCursor(Cursor cursor){
        ArrayList<Product> products = new ArrayList<>();
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                long id = cursor.getLong(cursor.getColumnIndex(EjevikaHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(EjevikaHelper.COLUMN_NAME_PRODUCT));
                String picture = cursor.getString(cursor.getColumnIndex(EjevikaHelper.COLUMN_PICTURE_PRODUCT));
                long sectionId = cursor.getLong(cursor.getColumnIndex(EjevikaHelper.COLUMN_SECTION_ID_PRODUCT));
                double price = cursor.getDouble(cursor.getColumnIndex(EjevikaHelper.COLUMN_PRICE_PRODUCT));
                products.add(new Product(id, name, picture, sectionId, price));
            }while (cursor.moveToNext());
        }
        return products;
    }

    private void deleteCategories() {
        sqLiteDatabase.delete(EjevikaHelper.TABLE_CATEGORIES,null,null);
    }

    private void deleteProducts(long categoryId) {
        String where = EjevikaHelper.COLUMN_SECTION_ID_PRODUCT+"="+categoryId;
        sqLiteDatabase.delete(EjevikaHelper.TABLE_PRODUCT,where,null);
    }

    public void resetTables(){
        try {
            sqLiteDatabase.execSQL(EjevikaHelper.DROP_TABLE_CATEGORIES);
            sqLiteDatabase.execSQL(EjevikaHelper.DROP_TABLE_PRODUCTS);
            Log.d(LOG_DB_TAG, EjevikaHelper.DROP_TABLE_CATEGORIES);
            Log.d(LOG_DB_TAG, EjevikaHelper.DROP_TABLE_PRODUCTS);
            sqLiteDatabase.execSQL(EjevikaHelper.CREATE_CATEGORIES_TABLE);
            sqLiteDatabase.execSQL(EjevikaHelper.CREATE_PRODUCTS_TABLE);
            Log.d(LOG_DB_TAG, EjevikaHelper.CREATE_CATEGORIES_TABLE);
            Log.d(LOG_DB_TAG,EjevikaHelper.CREATE_PRODUCTS_TABLE);
        }catch (SQLException e){
            Log.d(LOG_DB_TAG,"exception_ "+e.getMessage());
        }
    }

    private static class EjevikaHelper extends SQLiteOpenHelper{
/****************************Category table************************************/
        public static final String TABLE_CATEGORIES = "categories";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME_CATEGORY = "name_category";
        public static final String COLUMN_PICTURE_CATEGORY = "picture_category";
        public static final String CREATE_CATEGORIES_TABLE = "CREATE TABLE "+TABLE_CATEGORIES+" ("+
                COLUMN_ID+" INTEGER, "+
                COLUMN_NAME_CATEGORY+ " TEXT, "+
                COLUMN_PICTURE_CATEGORY+ " TEXT);";
        public static final String DROP_TABLE_CATEGORIES = "DROP TABLE IF EXISTS "+TABLE_CATEGORIES;
/**************************Prpduct table********************************************/
        public static final String TABLE_PRODUCT = "products";
        public static final String COLUMN_NAME_PRODUCT = "name_product";
        public static final String COLUMN_PICTURE_PRODUCT = "picture_product";
        public static final String COLUMN_PRICE_PRODUCT = "price_product";
        public static final String COLUMN_SECTION_ID_PRODUCT = "section_id_product";
        public static final String CREATE_PRODUCTS_TABLE = "CREATE TABLE "+TABLE_PRODUCT+" ("+
                COLUMN_ID+ " INTEGER, "+
                COLUMN_NAME_PRODUCT+" TEXT, "+
                COLUMN_PICTURE_PRODUCT+" TEXT, "+
                COLUMN_SECTION_ID_PRODUCT+" INTEGER, "+
                COLUMN_PRICE_PRODUCT+" INTEGER);";
        public static final String DROP_TABLE_PRODUCTS = "DROP TABLE IF EXISTS "+TABLE_PRODUCT;
/**********************************************************************************/
        private static final String DB_NAME = "ejevika_db";
        private static final int DB_VERSION = 1;
        private Context context;

        public EjevikaHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try{
                db.execSQL(CREATE_CATEGORIES_TABLE);
                db.execSQL(CREATE_PRODUCTS_TABLE);
                Log.d(LOG_DB_TAG,"database created");
            }catch (SQLException e){
                Log.d(LOG_DB_TAG,"exception "+e.getMessage());
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE_CATEGORIES);
                onCreate(db);
            }catch (SQLException e){
                Log.d(LOG_DB_TAG,"exception_ "+e.getMessage());
            }

        }
    }
}
