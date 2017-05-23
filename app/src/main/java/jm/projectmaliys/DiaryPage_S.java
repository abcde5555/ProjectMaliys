package jm.projectmaliys;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class DiaryPage_S extends AppCompatActivity
{
    public static final String EXTRA_POSITION = "position";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
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

    }

    public void onBtnMapClicked(View v)
    {
        LinearLayout container =
                (LinearLayout) findViewById(R.id.action_container);
        LayoutInflater inflater =
                (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.google_maps_api__s, container, true);

    }
}
