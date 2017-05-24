package jm.projectmaliys;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

// DB 관리
public class DBUtil_H {

    private static final String dbName = "diarys.db";
    private static final int dbVersion = 1;

    private Context _context;
    private SQLiteDatabase db;

    public DBUtil_H(Context context) {
        _context = context;
        OpenHelper openHelper = new OpenHelper(context, dbName, null, dbVersion);
        db = openHelper.getWritableDatabase();
    }

    // INSET, UPDATE, DELETE 문 실행
    public int executeDML(String sql) {
        int result = 0;
        try {
            db.execSQL(sql);
            result++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // SELECT 문 실행
    public Cursor executeQuery(String sql, String[] selectionArgs) {
        Cursor result = null;
        try {
            result = db.rawQuery(sql, selectionArgs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 데이터베이스를 열거나 업그레이드하는 것을 도와주는 내부 클래스
    private class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createQuery = "CREATE TABLE IF NOT EXIST ";

            String diaryTable = "diary(d_date TEXT PRIMARY KEY," +
                    " d_weather TEXT," +
                    " d_content TEXT NOT NULL)";

            String mapTable = "map(m_number INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " d_date TEXT NOT NULL," +
                    " m_time TEXT, m_xPoint TEXT NOT NULL," +
                    " m_yPoint TEXT NOT NULL," +
                    " FOREIGN KEY(d_date) REFERENCES diary(d_date)" +
                    ")";

            String imageTable = "image(i_number INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " d_date TEXT NOT NULL," +
                    " i_path TEXT NOT NULL" +
                    " FOREIGN KEY(d_date) REFERENCES diary(d_date)" +
                    ")";

            db.execSQL(createQuery + diaryTable);
            db.execSQL(createQuery + mapTable);
            db.execSQL(createQuery + imageTable);

            Toast.makeText(_context, "DB is opened", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("onUpgrade()", "Database is upgraded from " + oldVersion + " to " + newVersion);
            Toast.makeText(_context,
                    "Database is upgraded from " + oldVersion +
                            " to " + newVersion, Toast.LENGTH_SHORT)
                    .show();

            onCreate(db);
        }
    }
}
