package jm.projectmaliys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, LoadingPage_M.class);
//        Intent intent = new Intent(this, TestGoogleMap.class);
//        Intent intent = new Intent(this, DiaryPage_S.class);
        startActivity(intent);  //로딩페이지 시작
        this.finish();
    }

    //여긴 메인 왠만하면 건들지 않기~~
}
