package jm.projectmaliys;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class DiaryPage_S extends AppCompatActivity
{
    public static final String EXTRA_POSITION = "position";

    private String weatherStr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_page__s);

        int position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        Resources resources = getResources();
        String[] diaryDate = resources.getStringArray(R.array.diary_date);
        String[] diaryContents = resources.getStringArray(R.array.diary_contents);
        //TextView diaryContent = (TextView) findViewById(R.id.diary_content);
        String[] diaryAvator = resources.getStringArray(R.array.diary_avator);
        TypedArray diaryPictures = resources.obtainTypedArray(R.array.diary_picture);
        ImageView diaryPicture = (ImageView) findViewById(R.id.image);
        diaryPictures.recycle();

        // 날씨 선택 버튼(라디오버튼)
        RadioGroup weathers = (RadioGroup)findViewById(R.id.weathers);
        weathers.setOnCheckedChangeListener(new onWeatherCheckedChangeListener());

    }

    public void onBtnMapClicked(View v)
    {
        LinearLayout container =
                (LinearLayout) findViewById(R.id.action_container);
        LayoutInflater inflater =
                (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_google_maps_api__s, container, true);

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
}
