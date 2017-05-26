package jm.projectmaliys;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jm on 2017-05-26.
 */

public class AutoPositionSave_M extends Thread {

    SupportMapFragment mapFragment;
    GoogleMap map;

    MyPosition myPositon = new MyPosition();

    public void run(){
        try{
            while(true){
                // 현재시간을 msec으로 구한다.
                long now = System.currentTimeMillis();

                // 현재시간을 date 변수에 저장한다.
                Date date = new Date(now);

                // 시간을 나타내는 포맷을 정한다(yyyy/MM/dd HH:mm:ss)
                SimpleDateFormat simpleDate = new SimpleDateFormat("ss");

                // nowDate 변수에 값을 저장한다.
                String nowDate = simpleDate.format(date);

                //시간을 계산하기 위하여 정수로 변환.
                int intDate = Integer.parseInt(nowDate);

                if (intDate % 5 == 0) {
                    myPositon.requestMyLocation();
                }
                Thread.sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    class MyPosition extends AppCompatActivity {
        // 나의 위치 요청
        private void requestMyLocation() {
            LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            try {
                long minTime = 10000;
                float minDistance = 0;
                manager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        minTime,
                        minDistance,
                        new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                showCurrentLocation(location);
                            }

                            @Override
                            public void onStatusChanged(String provider, int status, Bundle extras) {

                            }

                            @Override
                            public void onProviderEnabled(String provider) {

                            }

                            @Override
                            public void onProviderDisabled(String provider) {

                            }
                        }
                );

                Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastLocation != null) {
                    showCurrentLocation(lastLocation);
                }

                manager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        minTime,
                        minDistance,
                        new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                showCurrentLocation(location);
                            }

                            @Override
                            public void onStatusChanged(String provider, int status, Bundle extras) {

                            }

                            @Override
                            public void onProviderEnabled(String provider) {

                            }

                            @Override
                            public void onProviderDisabled(String provider) {

                            }
                        }
                );
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

        // 현재 위치 탐색
        private void showCurrentLocation(Location location) {
            // 현재 위치를 이용해 LatLng 객체 생성
            LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());

            String Lat = Double.toString(location.getLatitude());
            String Lng = Double.toString(location.getLongitude());

            Log.d(" 현재 위치 ", Lat+"/"+Lng);

//        // 현재 위치를 지도의 중심으로 표시
//        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
        }
    }

}
