package cybersoft.sqlitelesson;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;


public class dbHelper extends SQLiteOpenHelper implements BaseColumns {
    public static final String DB_NAME = "MyDB.db";
    public static final int DB_VERSION = 1;
    public static final String DB_TEBLE_NAME = "Tebel_Name";
    public static final String DB_TEBLE_NAME_COLUMN_NAME = "Name";
    public static final String DB_TEBLE_NAME_COLUMN_EMAIL = "Phone";
    //public static final String DB_TEBLE_NAME_COLUMN_Age = "Age";

    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + DB_TEBLE_NAME + " (" + BaseColumns._ID
            + " integer primary key autoincrement, " + DB_TEBLE_NAME_COLUMN_NAME
            + " text not null, " + DB_TEBLE_NAME_COLUMN_EMAIL + " integer);";



    dbHelper(Context context){
        super(context, DB_NAME,null,DB_VERSION);

    }

    public dbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public dbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Запишем в журнал
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);

        // Удаляем старую таблицу и создаём новую
        db.execSQL("DROP TABLE IF IT EXISTS " + DB_TEBLE_NAME);
        // Создаём новую таблицу
        onCreate(db);
    }
}
