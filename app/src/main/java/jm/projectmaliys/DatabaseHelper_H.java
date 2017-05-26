package jm.projectmaliys;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// DB 관리
public class DatabaseHelper_H extends SQLiteOpenHelper {

    private static final String DB_NAME = "diarys.db";
    private static final int DB_VERSION = 1;

    private static DatabaseHelper_H _instance;
    private static SQLiteDatabase _database;


    public static DatabaseHelper_H getInstance(Context context) {
        if (_instance == null) {
            _instance = new DatabaseHelper_H(context);
            _database = _instance.getWritableDatabase();
        }

        return _instance;
    }

    private DatabaseHelper_H(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * INSET, UPDATE, DELETE 문 실행
     * @param sql 실행할 쿼리문
     * @return 성공 시 1, 실패 시 0
     */
    public int executeDML(String sql) {
        int result = 0;
        try {
            _database.execSQL(sql);
            result++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * SELECT 문 실행
     * @param sql 실행할 쿼리문, where 절에 ? 사용 가능
     * @param selectionArgs 쿼리문에 사용된 ? 위치에 바인딩할 값들의 배열
     * @return 조회된 데이터의 집합
     * */
    public Cursor executeQuery(String sql, String[] selectionArgs) {
        Cursor result = null;
        try {
            result = _database.rawQuery(sql, selectionArgs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE IF NOT EXISTS ";

        String diaryTable = "diary(d_date TEXT UNIQUE PRIMARY KEY," +
                " d_weather TEXT NOT NULL," +
                " d_content TEXT NOT NULL)";

        String mapTable = "map(m_number INTEGER PRIMARY KEY AUTOINCREMENT," +
                " d_date TEXT NOT NULL," +
                " m_time TEXT," +
                " m_xPoint TEXT NOT NULL," +
                " m_yPoint TEXT NOT NULL," +
                " FOREIGN KEY(d_date) REFERENCES diary(d_date)" +
                ")";

        String imageTable = "image(i_number INTEGER PRIMARY KEY AUTOINCREMENT," +
                " d_date TEXT NOT NULL," +
                " i_path TEXT NOT NULL," +
                " FOREIGN KEY(d_date) REFERENCES diary(d_date)" +
                ")";

        db.execSQL(createQuery + diaryTable);
        db.execSQL(createQuery + mapTable);
        db.execSQL(createQuery + imageTable);

        //region 테스트용 하드코딩
        String sql = "INSERT INTO diary(d_date, d_weather, d_content) VALUES ('2017/05/25', '맑음', '힘들다..')";
        db.execSQL(sql);
        sql = "INSERT INTO diary(d_date, d_weather, d_content) VALUES ('2017/05/24', '맑음', '아무말이나 해봅시다 우장창창은 리쌍 와장창은 이말년 그리고 나는 쿠와아앙')";
        db.execSQL(sql);
        sql = "INSERT INTO diary(d_date, d_weather, d_content) VALUES ('2017/05/23', '비', '테스트')";
        db.execSQL(sql);
        //endregion
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("onUpgrade()", "Database is upgraded from " + oldVersion + " to " + newVersion);
        onCreate(db);
    }
}
