package jm.projectmaliys;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class DiaryPage_S extends AppCompatActivity
{
    public static final String EXTRA_POSITION = "position";

    static final String AUDIO_URL = "http://sites.google.com/site/ubiaccessmobile/sample_audio.amr";

    final private static String RECORDED_FILE = "/sdcard/recorded.mp4";

    private MediaPlayer mediaPlayer;
    private int playbackPosition = 0;

    MediaPlayer player;
    MediaRecorder recorder;

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

    public void onBtnRecordClicked(View v)
    {
        if (recorder != null){
            recorder.stop();
            recorder.release();
            recorder.release();
            recorder = null;
        }
        recorder = new MediaRecorder();

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        recorder.setOutputFile(RECORDED_FILE);

        try {
            recorder.prepare();
            recorder.start();
        } catch (Exception ex) {
            Log.e("SampleAudioRecorder", "Exception : ", ex);
        }
    }

    public void onBtnRecordStopClicked(View v) {
        if (recorder == null)
            return;

        recorder.stop();
        recorder.release();
        recorder = null;

        ContentValues values = new ContentValues(10);

        values.put(MediaStore.MediaColumns.TITLE, "Recorded");
        values.put(MediaStore.Audio.Media.ALBUM, "Audio Album");
        values.put(MediaStore.Audio.Media.ARTIST, "Mike");
        values.put(MediaStore.Audio.Media.DISPLAY_NAME, "Recorded Audio");
        values.put(MediaStore.Audio.Media.IS_RINGTONE, 1);
        values.put(MediaStore.Audio.Media.IS_MUSIC, 1);
        values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis()/1000);
        values.put(MediaStore.Audio.Media.DATA, RECORDED_FILE);

        Uri audioUri = getContentResolver().insert(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);
        if (audioUri == null) {
            Log.d("SampleAudioRecorder", "Audio insert failed.");
            return;
        }
    }

    public void onBtnPlayClicked(View v) {
        try {
            playAudio(AUDIO_URL);

            Toast.makeText(getApplicationContext(), "음악 파일 재생 시작됨.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBtnPlayStopClicked(View v) {
        if (mediaPlayer != null) {
            if (mediaPlayer != null) {
                playbackPosition = mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();
                Toast.makeText(getApplicationContext(), "음악 파일 재생 중지됨.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void playAudio(String url) throws Exception {
        killMediaPlayer();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(url);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    protected void onPause() {
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }

        super.onPause();
    }

    protected void onResume() {
        super.onResume();
        recorder = new MediaRecorder();
    }

    protected void onDestroy() {
        super.onDestroy();
        killMediaPlayer();
    }

    private void killMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
