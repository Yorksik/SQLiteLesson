package cybersoft.sqlitelesson;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "Log_d";

    dbHelper mDatabaseHelper;
    SQLiteDatabase mSqLiteDatabase;


    Button btnAdd;
    Button btnClear;
    Button btnRead;
    EditText Email;
    EditText Name;
    TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Email = (EditText) findViewById(R.id.editEmail);
        Name = (EditText) findViewById(R.id.editName);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnRead = (Button) findViewById(R.id.btnRead);
        btnClear = (Button) findViewById(R.id.btnClear);

        textView = (TextView) findViewById(R.id.textView);

        btnAdd.setOnClickListener(this);
        btnRead.setOnClickListener(this);
        btnClear.setOnClickListener(this);

        Log.d(TAG, "Кнопки определены");


        //так как в классе dbHelp описаны два (три ?) конструктора мы можем воспользоваться одним из них
        //mDatabaseHelper = new dbHelper(this, "mydatabase.db", null, 1);
        mDatabaseHelper = new dbHelper(this);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        Toast.makeText(getApplicationContext(), "База данных создано только что", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onClick(View v) {
        Log.d(TAG, "Зашли в онклик");

        switch (v.getId()) {
            case R.id.btnAdd:
                Log.d(TAG, "Нажата кнопка ADD");
                /*if (mSqLiteDatabase.equals(null)){
                    mDatabaseHelper = new dbHelper(this);
                    mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
                }*/

                ContentValues values = new ContentValues();
                // Задайте значения для каждого столбца
                values.put(dbHelper.DB_TEBLE_NAME_COLUMN_NAME, Name.getText().toString());
                values.put(dbHelper.DB_TEBLE_NAME_COLUMN_EMAIL, Email.getText().toString());
                // Вставляем данные в таблицу
                mSqLiteDatabase.insert(dbHelper.DB_TEBLE_NAME, null, values);

                Toast.makeText(this, "Данные записаны в таблицу!" + Name.getText().toString() + Email.getText().toString(), Toast.LENGTH_LONG).show();
                //Log.d(TAG, "Имя: " + dbHelper.DB_TEBLE_NAME_COLUMN_NAME + " Почта: " + dbHelper.DB_TEBLE_NAME_COLUMN_EMAIL);
                Log.d(TAG, "Имя: " + Name.getText().toString() + " Почта: " + Email.getText().toString());
                Name.setText("");
                Email.setText("");
                break;
            case R.id.btnRead:
                Log.d(TAG, "Зашли в Read");
                //Cursor cursor = mSqLiteDatabase.query(dbHelper.DB_TEBLE_NAME, new String[]{dbHelper.DB_TEBLE_NAME_COLUMN_NAME,
                        //dbHelper.DB_TEBLE_NAME_COLUMN_EMAIL}, null, null, null, null, null, null);

                Cursor cursor =mSqLiteDatabase.query(dbHelper.DB_TEBLE_NAME, null,null,null,null,null,null);

                if (cursor.moveToFirst()) {

                    boolean bool = cursor.moveToFirst();
                    Log.d(TAG, " " + bool);
                    do {
                        Log.d(TAG,
                                "ID " + cursor.getInt(cursor.getColumnIndex(dbHelper._ID)) +
                                        ", NAME " + cursor.getString(cursor.getColumnIndex(dbHelper.DB_TEBLE_NAME_COLUMN_NAME)) +
                                        ", EMAIl " + cursor.getString(cursor.getColumnIndex(dbHelper.DB_TEBLE_NAME_COLUMN_EMAIL)));
                        textView.setText(cursor.getString(cursor.getColumnIndex(dbHelper.DB_TEBLE_NAME_COLUMN_NAME)).toString());
                    } while (cursor.moveToNext());
                } else {
                    Log.d(TAG, "В базе нет данных!");
                }

                cursor.close();
                break;

            case R.id.btnClear:
                mSqLiteDatabase.delete(dbHelper.DB_TEBLE_NAME,null,null);
                Log.d(TAG,"Таблица " + dbHelper.DB_TEBLE_NAME +" удалена!");
                break;
        }
    }
}
