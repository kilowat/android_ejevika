package com.example.razor.ejevika.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.razor.ejevika.MyApplication;
import com.example.razor.ejevika.dummy.BasketItem;
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

        Log.d(LOG_DB_TAG, "inserting " + categories.size() + " row  for time " + new Date(System.currentTimeMillis()));
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

        Log.d(LOG_DB_TAG, "inserting products " + products.size() + " row  for time " + new Date(System.currentTimeMillis()));
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

    public void insertToBasket(Product product, int count){
        ContentValues contentValues = new ContentValues();
        contentValues.put(EjevikaHelper.COLUMN_BASKET_PRODUCT_ID, product.getId());
        contentValues.put(EjevikaHelper.COLUMN_BASKET_PRODUCT_NAME, product.getName());
        contentValues.put(EjevikaHelper.COLUMN_BASKET_PRODUCT_PICTURE, product.getPicture());
        contentValues.put(EjevikaHelper.COLUMN_BASKET_PRODUCT_PRICE, product.getPrice());
        contentValues.put(EjevikaHelper.COLUMNT_BASKET_PRODUCT_COUNT, count);
        long addId = product.getId();

        Cursor c = sqLiteDatabase.query(EjevikaHelper.TABLE_BASKET,
                new String[]{EjevikaHelper.COLUMNT_BASKET_PRODUCT_COUNT},
                EjevikaHelper.COLUMN_BASKET_PRODUCT_ID+"=?",
                new String[]{String.valueOf(addId)},null, null, null);

        c.moveToFirst();
        if(c.getCount()>0){
            ContentValues changeContentValues = new ContentValues();
            int curCount = c.getInt(0);
            changeContentValues.put(EjevikaHelper.COLUMNT_BASKET_PRODUCT_COUNT,curCount++);
            sqLiteDatabase.update(EjevikaHelper.TABLE_BASKET,contentValues,EjevikaHelper.COLUMN_BASKET_PRODUCT_ID+"=?",new String[]{String.valueOf(addId)});
        }else {
            sqLiteDatabase.insert(EjevikaHelper.TABLE_BASKET, null, contentValues);
            Log.d(LOG_DB_TAG, contentValues.toString() + ":put to basket");
        }
    }

    public Cursor readFromBasket(){
        Cursor cursor = sqLiteDatabase.query(EjevikaHelper.TABLE_BASKET,null,null,null,null,null,null,null);
        return cursor;
    }
    public ArrayList<BasketItem> readBasketItemFromCursor(Cursor cursor){
        ArrayList<BasketItem> basketItems = new ArrayList<>();
        cursor.moveToFirst();
        do {
            long id = cursor.getLong(cursor.getColumnIndex(EjevikaHelper.COLUMN_BASKET_PRODUCT_ID));
            String name = cursor.getString(cursor.getColumnIndex(EjevikaHelper.COLUMN_BASKET_PRODUCT_NAME));
            String picture = cursor.getString(cursor.getColumnIndex(EjevikaHelper.COLUMN_BASKET_PRODUCT_PICTURE));
            Double price = cursor.getDouble(cursor.getColumnIndex(EjevikaHelper.COLUMN_BASKET_PRODUCT_PRICE));
            int count = cursor.getInt(cursor.getColumnIndex(EjevikaHelper.COLUMNT_BASKET_PRODUCT_COUNT));

            BasketItem basketItem = new BasketItem(id, name, picture, price, count);
            basketItems.add(basketItem);
        }while(cursor.moveToNext());
        return basketItems;
    }
    public void removeFromBakset(int productId){
        String where = EjevikaHelper.COLUMN_BASKET_PRODUCT_ID+"=?";
        String[] bind = {String.valueOf(productId)};

        if(productId==-1){
            where = null;
            bind = null;
        }
        try{
            sqLiteDatabase.delete(EjevikaHelper.TABLE_BASKET,where,bind);
        }catch (SQLException e){
            Log.d(LOG_DB_TAG,e.getMessage());
        }

    }

    public Cursor getCountBasket(){
        Cursor cursor = sqLiteDatabase.rawQuery(EjevikaHelper.TABLE_BASKET_COUNT,null);
        Log.d("test",EjevikaHelper.TABLE_BASKET_COUNT);
        return cursor;
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
    public static ArrayList<BasketItem> getBasketProductsFromCursor(Cursor cursor){
        ArrayList<BasketItem> basketItems = new ArrayList<>();
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                long id = cursor.getLong(cursor.getColumnIndex(EjevikaHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(EjevikaHelper.COLUMN_BASKET_PRODUCT_NAME));
                String picture = cursor.getString(cursor.getColumnIndex(EjevikaHelper.COLUMN_BASKET_PRODUCT_PICTURE));
                double price = cursor.getDouble(cursor.getColumnIndex(EjevikaHelper.COLUMN_BASKET_PRODUCT_PRICE));
                int count = cursor.getInt(cursor.getColumnIndex(EjevikaHelper.COLUMNT_BASKET_PRODUCT_COUNT));
                basketItems.add(new BasketItem(id,name,picture,price,count));
            }while (cursor.moveToNext());
        }
        return basketItems;
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
            sqLiteDatabase.execSQL(EjevikaHelper.DROP_TABLE_BASKET);
            Log.d(LOG_DB_TAG, EjevikaHelper.DROP_TABLE_CATEGORIES);
            Log.d(LOG_DB_TAG, EjevikaHelper.DROP_TABLE_PRODUCTS);
            Log.d(LOG_DB_TAG, EjevikaHelper.DROP_TABLE_BASKET);
            sqLiteDatabase.execSQL(EjevikaHelper.CREATE_CATEGORIES_TABLE);
            sqLiteDatabase.execSQL(EjevikaHelper.CREATE_PRODUCTS_TABLE);
            sqLiteDatabase.execSQL(EjevikaHelper.CREATE_BASKET_TABLE);
            Log.d(LOG_DB_TAG, EjevikaHelper.CREATE_CATEGORIES_TABLE);
            Log.d(LOG_DB_TAG, EjevikaHelper.CREATE_PRODUCTS_TABLE);
            Log.d(LOG_DB_TAG, EjevikaHelper.CREATE_BASKET_TABLE);
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
/*******************************BasketItem table***************************************************/
        public final static String TABLE_BASKET = "basket";
        public final static String COLUMN_BASKET_PRODUCT_ID="basket_product_id";
        public final static String COLUMN_BASKET_PRODUCT_NAME = "basket_product_name";
        public final static String COLUMN_BASKET_PRODUCT_PICTURE = "basket_product_picture";
        public final static String COLUMN_BASKET_PRODUCT_PRICE = "basket_product_price";
        public final static String COLUMNT_BASKET_PRODUCT_COUNT = "basket_product_count";
        public final static String CREATE_BASKET_TABLE = "CREATE TABLE "+TABLE_BASKET+"( "+
                COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_BASKET_PRODUCT_ID+" INTEGER, "+
                COLUMN_BASKET_PRODUCT_NAME+" TEXT, "+
                COLUMN_BASKET_PRODUCT_PICTURE+" TEXT, "+
                COLUMN_BASKET_PRODUCT_PRICE+" INTEGER, "+
                COLUMNT_BASKET_PRODUCT_COUNT+" INTEGER);";
        public static final String DROP_TABLE_BASKET = "DROP TABLE IF EXISTS "+ TABLE_BASKET;
        public static final String TABLE_BASKET_COUNT = "SELECT count(*) as count FROM "+TABLE_BASKET;
/**********************************************************************************************/

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
                db.execSQL(CREATE_BASKET_TABLE);
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
