package jm.projectmaliys;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class DiaryPage_S extends AppCompatActivity {
    public static final String EXTRA_POSITION = "position";

    private String date;
    private static String weatherStr;
    private static String contentStr;

    private ImageView diaryPicture;
    private RadioGroup weathers;
    private EditText editContent;

    private DatabaseHelper_H databaseUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_page__s);

        int position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        date = getIntent().getStringExtra("date");
        databaseUtil = DatabaseHelper_H.getInstance(getApplicationContext());

        diaryPicture = (ImageView) findViewById(R.id.image_main);
        weathers = (RadioGroup) findViewById(R.id.weathers);
        editContent = (EditText)findViewById(R.id.editText);

        if (position != 0) {
            // DB에서 불러온 데이터 표시
            DiaryModel_H detail = getDataFromDatabase();
            initView(detail);
        }

        diaryPicture.setOnClickListener(new OnImageClickListener());
        weathers.setOnCheckedChangeListener(new onWeatherCheckedChangeListener());
        editContent.setOnFocusChangeListener(new OnEditorFocusChangeListener());
    }

    private void initView(DiaryModel_H detail) {
        // 대표 사진
        if (detail.getImage() != null)
            diaryPicture.setImageURI(detail.getImage());

        // 본문 내용
        editContent.setText(detail.getContent());

        // 날씨 선택
        RadioGroup weathers = (RadioGroup)findViewById(R.id.weathers);
        RadioButton sun = (RadioButton)findViewById(R.id.rb_sun);
        RadioButton cloud = (RadioButton)findViewById(R.id.rb_cloud);
        RadioButton rain = (RadioButton)findViewById(R.id.rb_rain);
        RadioButton snow = (RadioButton)findViewById(R.id.rb_snow);

        if (detail.getWeather().equals(sun.getText().toString()))
            weathers.check(sun.getId());

        else if (detail.getWeather().equals(cloud.getText().toString()))
            weathers.check(cloud.getId());

        else if (detail.getWeather().equals(rain.getText().toString()))
            weathers.check(rain.getId());

        else if (detail.getWeather().equals(snow.getText().toString()))
            weathers.check(snow.getId());
    }

    // DB에서 데이터 불러오기
    private DiaryModel_H getDataFromDatabase() {
        DiaryModel_H diaryModel = new DiaryModel_H();

        // 다이어리 테이블 쿼리
        String selectSql = "SELECT d_weather, d_content FROM diary WHERE d_date = ?";
        String[] parameters = new String[] { date };

        Cursor diaryCursor = databaseUtil.executeQuery(selectSql, parameters);
        if (diaryCursor.moveToFirst()) {
            weatherStr = diaryCursor.getString(diaryCursor.getColumnIndex("d_weather"));
            contentStr = diaryCursor.getString(diaryCursor.getColumnIndex("d_content"));

            diaryModel.setWeather(weatherStr);
            diaryModel.setContent(contentStr);
        }

        // 대표 이미지 쿼리
        selectSql = "SELECT i_path FROM image WHERE d_date = ?";
        Cursor imageCursor = databaseUtil.executeQuery(selectSql, parameters);
        if (imageCursor.moveToFirst()) {
            String imageString = imageCursor.getString(imageCursor.getColumnIndex("i_path"));
            Uri imageUri = imageCursor.moveToFirst() ?
                    Uri.parse(imageString) : null;

            diaryModel.setImage(imageUri);
        }
        return diaryModel;
    }

    // 지도 화면 켜기
    public void onBtnMapClicked(View v) {
        LinearLayout container = (LinearLayout) findViewById(R.id.action_container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_google_maps_api__s, container, true);

    }

    // 갤러리 화면으로 이동
    private class OnImageClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intentForGallary = new Intent(DiaryPage_S.this, GallaryActivity_H.class);
            intentForGallary.putExtra("date", date); // 해당 일자를 인텐트로 넘겨줌
            startActivity(intentForGallary);
        }
    }

    // 날씨 선택 버튼에 대한 리스너
    private class onWeatherCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            weatherStr = ((RadioButton)findViewById(checkedId)).getText().toString();
        }
    }

    // 본문
    private class OnEditorFocusChangeListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus)
                contentStr = ((EditText)v).getText().toString();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 일기의 추가와 갱신을 구분하기 위해
        // DB에 해당 날짜 일기가 있는지 확인
        String queryForCheck = "SELECT d_date FROM diary WHERE d_date = ?";
        String[] parametersForQuery = new String[] { date };
        Cursor todayCursor = databaseUtil.executeQuery(queryForCheck, parametersForQuery);

        if (todayCursor.moveToFirst()) {
            String checkToday = todayCursor.getString(todayCursor.getColumnIndex("d_date"));
            if (date.equals(checkToday)) {
                // 일기 수정
                String queryForUpdate = "UPDATE diary SET d_weather = '" + weatherStr +
                        "', d_content = '" + contentStr + "' WHERE d_date = '" + date + "'";
                if (databaseUtil.executeDML(queryForUpdate) != 1)
                    throw new RuntimeException();

                // 지도 좌표 수정
            /*queryForUpdate = "map";
            if (databaseUtil.executeDML(queryForUpdate) != 1)
                throw new RuntimeException();*/
            }
        } else {
            // 일기 추가
            String queryForInsert = "INSERT INTO diary(d_date, d_weather, d_content) " +
                    "VALUES ('" + date + "', '" + weatherStr + "', '" + contentStr + "')";
            if (databaseUtil.executeDML(queryForInsert) != 1)
                throw new RuntimeException();

            // 지도 좌표 추가
            /*queryForInsert = "map";
            if (databaseUtil.executeDML(queryForInsert) != 1)
                throw new RuntimeException();*/
        }
    }

}
