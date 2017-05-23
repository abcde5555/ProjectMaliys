package jm.projectmaliys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

public class TagChoice_M extends AppCompatActivity {

    int sun = 1;
    int cloud = 1;
    int rain = 1;
    int snow = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_choice__m);

        ImageButton imgbtnsun = (ImageButton) findViewById(R.id.btnsun);
        ImageButton imgbtncloud = (ImageButton) findViewById(R.id.btncloud);
        ImageButton imgbtnrain = (ImageButton) findViewById(R.id.btnrain);
        ImageButton imgbtnsnow = (ImageButton) findViewById(R.id.btnsnow);

//        imgbtnsun.setImageResource(R.drawable.sun2);

        imgbtnsun.setOnClickListener(v -> OnClickImgBtn(imgbtnsun));
        imgbtncloud.setOnClickListener(v -> OnClickImgBtn(imgbtncloud));
        imgbtnrain.setOnClickListener(v -> OnClickImgBtn(imgbtnrain));
        imgbtnsnow.setOnClickListener(v -> OnClickImgBtn(imgbtnsnow));
    }

    public void OnClickImgBtn(ImageButton imgbtn) {
        if(imgbtn.getTag().equals("sun")){
            if (sun % 2 == 1) {
                imgbtn.setImageResource(R.drawable.sun2);
                sun++;
            } else {
                imgbtn.setImageResource(R.drawable.sun1);
                sun--;
            }
        }else if(imgbtn.getTag().equals("cloud")){
            if (cloud % 2 == 1) {
                imgbtn.setImageResource(R.drawable.cloud2);
                cloud++;
            } else {
                imgbtn.setImageResource(R.drawable.cloud1);
                cloud--;
            }
        }else if(imgbtn.getTag().equals("rain")){
            if (rain % 2 == 1) {
                imgbtn.setImageResource(R.drawable.rain2);
                rain++;
            } else {
                imgbtn.setImageResource(R.drawable.rain1);
                rain--;
            }
        }else if(imgbtn.getTag().equals("snow")){
            if (snow % 2 == 1) {
                imgbtn.setImageResource(R.drawable.snow2);
                snow++;
            } else {
                imgbtn.setImageResource(R.drawable.snow1);
                snow--;
            }
        }
    }
}