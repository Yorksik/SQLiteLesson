package cybersoft.sqlitelesson;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.PublicKey;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "Log_d";

    private dbHelper mDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase;



    Button btnAdd;
    Button btnClear;
    Button btnRead;
    Cursor cursor;
    //SQLiteOpenHelper db;
    SQLiteDatabase db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText Email =(EditText)findViewById(R.id.editEmail);
        EditText Name =(EditText)findViewById(R.id.editName);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnRead = (Button)findViewById(R.id.btnRead);
        btnClear = (Button)findViewById(R.id.btnClear);

        //так как в классе dbHelp описаны два (три ?) конструктора мы можем воспользоваться одним из них
        //mDatabaseHelper = new dbHelper(this, "mydatabase.db", null, 1);
        mDatabaseHelper = new dbHelper(this);
        SQLiteDatabase sdb;
        sdb = mDatabaseHelper.getReadableDatabase();
        Toast.makeText(getApplicationContext(), "База данных создано только что", Toast.LENGTH_SHORT).show();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                // Задайте значения для каждого столбца
                String name = findViewById(R.id.editName).toString();
                values.put(dbHelper.DB_TEBLE_NAME_COLUMN_NAME, name);

                String Email = findViewById(R.id.editEmail).toString();
                values.put(dbHelper.DB_TEBLE_NAME_COLUMN_EMAIL, Email);

                // Вставляем данные в таблицу
                mSqLiteDatabase.insert("cats", null, values);
                //Toast.makeText(this, "Данные записаны в таблицу!" + name + Email, Toast.LENGTH_SHORT).show();
                Log.d(TAG,"Данные записаны в таблицу!" + name + Email);
            }
        });

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAdd:
                ContentValues values = new ContentValues();
                // Задайте значения для каждого столбца
                String name = findViewById(R.id.editName).toString();
                values.put(dbHelper.DB_TEBLE_NAME_COLUMN_NAME, name);

                String Email = findViewById(R.id.editEmail).toString();
                values.put(dbHelper.DB_TEBLE_NAME_COLUMN_EMAIL, Email);

                // Вставляем данные в таблицу
                mSqLiteDatabase.insert("cats", null, values);
                Toast.makeText(this, "Данные записаны в таблицу!" + name + Email, Toast.LENGTH_SHORT).show();
                break;
                    case R.id.btnRead:
                        cursor = db.query(dbHelper.DB_NAME, null, null, null, null, null, null);


                        if (cursor.moveToFirst()) {

                            int idColInd = cursor.getColumnIndex(dbHelper._ID);
                            int nameColInd = cursor.getColumnIndex(dbHelper.DB_TEBLE_NAME_COLUMN_NAME);
                            int emailColInd = cursor.getColumnIndex(dbHelper.DB_TEBLE_NAME_COLUMN_EMAIL);


                            do {
                                Log.d(TAG,
                                        "ID " + cursor.getInt(idColInd) +
                                                ", NAME " + cursor.getString(nameColInd) +
                                                ", EMAIl " + cursor.getString(emailColInd));

                            } while (cursor.moveToNext());

                        } else {
                            Log.d(TAG, "В базе нет данных!");
                        }

                        cursor.close();



                break;
                    case R.id.btnClear:

                break;
        }

    }

}
