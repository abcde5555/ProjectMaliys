package jm.projectmaliys;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

// DB 관리
public class DBUtil_H {

    private static final String dbName = "diarys.db";
    private static final int dbVersion = 1;

    private OpenHelper _openHelper;
    private SQLiteDatabase db;

    private Context _context;

    public DBUtil_H(Context context) {
        _context = context;
        _openHelper = new OpenHelper(context, dbName, null, dbVersion);
        db = _openHelper.getWritableDatabase();
    }

    // 다이어리 추가
    public int insertDiary() {

        return 1;
    }

    // 다이어리 수정
    public int updateDiary() {

        return 1;
    }

    // 다이어리 사진 추가
    public int updateDiaryPhotos() {

        return 0;
    }

    // 다이어리 삭제
    public int deleteDiary() {

        return 0;
    }

    // 다이어리 목록보기
    public Cursor selectAllDiary() {

        return null;
    }

    // 다이어리 자세히 보기
    public Cursor selectDiary() {
        db.query("diary", new String[] {"",""},"selection", new String[] {"",""},null,null,"orderBy");
        return null;
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
