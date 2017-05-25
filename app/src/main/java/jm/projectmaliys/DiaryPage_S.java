package jm.projectmaliys;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
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

public class DiaryPage_S extends AppCompatActivity
{
    public static final String EXTRA_POSITION = "position";

    private String date;
    private static String weatherStr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_page__s);

        date = getIntent().getStringExtra("date");

        // 데이터 불러오기 수정 중 (뷰 초기화 메서드)
        DiaryModel_H detail = getDataFromDatabase();
        if (detail != null) {
            initView(detail);
        }

        int position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        Resources resources = getResources();
        String[] diaryDate = resources.getStringArray(R.array.diary_date);
        String[] diaryContents = resources.getStringArray(R.array.diary_contents);
        //TextView diaryContent = (TextView) findViewById(R.id.diary_content);
        String[] diaryAvator = resources.getStringArray(R.array.diary_avator);
        TypedArray diaryPictures = resources.obtainTypedArray(R.array.diary_picture);

        diaryPictures.recycle();

        // 대표 사진
        ImageView diaryPicture = (ImageView) findViewById(R.id.image_main);
        diaryPicture.setOnClickListener(new OnImageClickListener());

        // 날씨 선택 버튼(라디오버튼)
        RadioGroup weathers = (RadioGroup)findViewById(R.id.weathers);
        weathers.setOnCheckedChangeListener(new onWeatherCheckedChangeListener());
    }

    private void initView(DiaryModel_H detail) {
        // 대표 사진
        ((ImageView)findViewById(R.id.image_main)).setImageURI(detail.getImage());

        // 본문 내용
        EditText editContent = (EditText)findViewById(R.id.editText);
        editContent.setText(detail.getContent());

        // 날씨 선택
        RadioGroup weathers = (RadioGroup)findViewById(R.id.weathers);
        RadioButton sun = (RadioButton)findViewById(R.id.rb_sun);
        RadioButton cloud = (RadioButton)findViewById(R.id.rb_cloud);
        RadioButton rain = (RadioButton)findViewById(R.id.rb_rain);
        RadioButton snow = (RadioButton)findViewById(R.id.rb_snow);
        if (sun.getText().toString().equals(detail.getWeather())) {
            sun.setChecked(true);
        } else if (cloud.getText().toString().equals(detail.getWeather())) {
            cloud.setChecked(true);
        } else if (rain.getText().toString().equals(detail.getWeather())) {
            rain.setChecked(true);
        } else if (snow.getText().toString().equals(detail.getWeather())) {
            snow.setChecked(true);
        }
    }

    // DB에서 데이터 불러오기
    private DiaryModel_H getDataFromDatabase() {
        Context context = getApplicationContext();
        DBUtil_H databaseUtil = new DBUtil_H(context);

        DiaryModel_H diaryModel = new DiaryModel_H();

        // 다이어리 테이블 쿼리
        String selectSql = "SELECT d_weather, d_content FROM diary WHERE d_date = ?";
        String[] parameters = new String[] {date};
        Cursor diaryCursor = databaseUtil.executeQuery(selectSql, parameters);
        if (diaryCursor.moveToFirst()) {
            String weatherString = diaryCursor.getString(diaryCursor.getColumnIndex("d_weather"));
            String contentString = diaryCursor.getString(diaryCursor.getColumnIndex("d_content"));

            diaryModel.setWeather(weatherString);
            diaryModel.setContent(contentString);
        }

        // 대표 이미지 쿼리
        selectSql = "SELECT i_path FROM image WHERE d_date = ?";
        Cursor imageCursor = databaseUtil.executeQuery(selectSql, parameters);
        String imageString = imageCursor.getString(imageCursor.getColumnIndex("i_path"));
        Uri imageUri = imageCursor.moveToFirst() ?
                Uri.parse(imageString) : null;

        diaryModel.setImage(imageUri);

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
            switch (checkedId) {
                case R.id.rb_sun:
                    weatherStr = ((RadioButton)findViewById(R.id.rb_sun)).getText().toString();
                    break;
                case R.id.rb_cloud:
                    weatherStr = ((RadioButton)findViewById(R.id.rb_cloud)).getText().toString();
                    break;
                case R.id.rb_rain:
                    weatherStr = ((RadioButton)findViewById(R.id.rb_rain)).getText().toString();
                    break;
                case R.id.rb_snow:
                    weatherStr = ((RadioButton)findViewById(R.id.rb_snow)).getText().toString();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        //insertDiary();
        super.onBackPressed();
    }
}
