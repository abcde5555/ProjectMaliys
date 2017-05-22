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
        startActivity(intent);  //로딩페이지 시작
    }

    //여긴 메인 왠만하면 건들지 않기~~
}
