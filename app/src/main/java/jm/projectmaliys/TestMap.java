//package jm.projectmaliys;
//
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.RelativeLayout;
//import android.widget.Toast;
//
//import com.google.android.gms.maps.CameraUpdate;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapsInitializer;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//public class TestMap extends AppCompatActivity {
//
//    private static final String TAG = "GoogleMapPin";
//
//    SupportMapFragment mapFragment;
//    GoogleMap map;
//
//    MarkerOptions myLocationMarker;
//
//    private TestCompassView mCompassView;
//    private SensorManager mSensorManager;
//    private boolean mCompassEnabled;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test_map);
//
//
//        // XML 레이아웃에 정의한 지도 객체 참조
//        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(
//                new OnMapReadyCallback() {
//                    @Override
//                    public void onMapReady(GoogleMap googleMap) {
//                        Log.d(TAG, "GoogleMapPin is ready");
//
//                        map = googleMap;
//                        if (ActivityCompat.checkSelfPermission(TestMap.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(TestMap.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                            // TODO: Consider calling
//                            //    ActivityCompat#requestPermissions
//                            // here to request the missing permissions, and then overriding
//                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                            //                                          int[] grantResults)
//                            // to handle the case where the user grants the permission. See the documentation
//                            // for ActivityCompat#requestPermissions for more details.
//                            return;
//                        }
//                        map.setMyLocationEnabled(true);
//                    }
//                }
//        );
//
//        try {
//            MapsInitializer.initialize(this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Button button = (Button) findViewById(R.id.buttonFind);
//        button.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        requestMyLocation();
//
//                        // 맵 위치를 이동하기
//                        CameraUpdate update = CameraUpdateFactory.newLatLng(
//                                new LatLng(37.542212, 126.841319));
//
//                        // 해당 위도,경도로 카메라 이동
//                        map.moveCamera(update);
//
//                        // 카메라 zoom 하기
//                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
//                        map.animateCamera(zoom);
//
//                        // 마커 표시하기 : 위치지정, 풍선말 설정
//                        MarkerOptions markerOptions = new MarkerOptions()
//                            .position(new LatLng(37.542212, 126.841319))
//                            .title("Great DavidJ!")
//                            .snippet("Really Great!");
//
//                        // 화면에 마커 추가하고 말풍선 표시한 것을 보여주도록 호출
//                        map.addMarker(markerOptions).showInfoWindow();
//                    }
//
//                }
//        );
//
//        // 센서 관리자 객체 참조
//        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//
//        // 나침반을 표시할 뷰 생성
//        boolean sideBotton = true;
//        mCompassView = new TestCompassView(this);
//        mCompassView.setVisibility(View.VISIBLE);
//
//        RelativeLayout.LayoutParams params =
//                new RelativeLayout.LayoutParams(
//                        RelativeLayout.LayoutParams.WRAP_CONTENT,
//                        RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        params.addRule(sideBotton ?
//                RelativeLayout.ALIGN_PARENT_BOTTOM : RelativeLayout.ALIGN_PARENT_TOP);
//
//        ((ViewGroup) mapFragment.getView()).addView(mCompassView, params);
//
//        mCompassEnabled = true;
//    }
//
//    // 나의 위치 요청
//    private void requestMyLocation() {
//        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        try {
//            long minTime = 10000;
//            float minDistance = 0;
//            manager.requestLocationUpdates(
//                    LocationManager.GPS_PROVIDER,
//                    minTime,
//                    minDistance,
//                    new LocationListener() {
//                        @Override
//                        public void onLocationChanged(Location location) {
//                            showCurrentLocation(location);
//                        }
//
//                        @Override
//                        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//                        }
//
//                        @Override
//                        public void onProviderEnabled(String provider) {
//
//                        }
//
//                        @Override
//                        public void onProviderDisabled(String provider) {
//
//                        }
//                    }
//            );
//
//            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (lastLocation != null) {
//                showCurrentLocation(lastLocation);
//            }
//
//            manager.requestLocationUpdates(
//                    LocationManager.NETWORK_PROVIDER,
//                    minTime,
//                    minDistance,
//                    new LocationListener() {
//                        @Override
//                        public void onLocationChanged(Location location) {
//                            showCurrentLocation(location);
//                        }
//
//                        @Override
//                        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//                        }
//
//                        @Override
//                        public void onProviderEnabled(String provider) {
//
//                        }
//
//                        @Override
//                        public void onProviderDisabled(String provider) {
//
//                        }
//                    }
//            );
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // 현재 위치를 보여준다
//    private void showCurrentLocation(Location location) {
//        // 현재 위치를 이용해 LatLng 객체 생성
//        LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());
//
//        // 현재 위치를 지도의 중심으로 표시
//        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
//
//        // 현재 위치의 좌표를 토스트로 출력
//        Toast.makeText(
//                getApplicationContext(),
//                "현재 위치 : "+location.getLatitude()+" / "+location.getLongitude(),
//                Toast.LENGTH_LONG
//        ).show();
//
//        showMyLocationMarker(location);
//    }
//
//    // 위치에 마커를 사용해 표시
//    private void showMyLocationMarker(Location location) {
//        if (myLocationMarker == null) {
//            myLocationMarker = new MarkerOptions();
//            myLocationMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
//            myLocationMarker.title("● 내 위치\n");
//            myLocationMarker.snippet("● GPS로 확인한 위치");
//            myLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
//            map.addMarker(myLocationMarker);
//        } else {
//            myLocationMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        // 액티비티가 중지될 때 내 위치 표시 활성화
//        if (map != null) {
//            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            map.setMyLocationEnabled(false);
//        }
//
//        if (mCompassEnabled) {
//            mSensorManager.unregisterListener(mListener);
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        // 액티비티가 화면에 보일 때 내 위치 표시 활성화
//        if (map != null) {
//            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            map.setMyLocationEnabled(true);
//        }
//
//        if(mCompassEnabled) {
//            mSensorManager.registerListener(mListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
//        }
//    }
//
//    /**
//     * 센서의 정보를 받기 위한 리스너 객체 생성
//     */
//    private final SensorEventListener mListener = new SensorEventListener() {
//        private int iOrientation = -1;
//
//        public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//        }
//
//            // 센서의 값을 받을 수 있도록 호출되는 메소드
//        public void onSensorChanged(SensorEvent event) {
//            if (iOrientation < 0) {
//                iOrientation = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
//            }
//
//            mCompassView.setAzimuth(event.values[0] + 90 * iOrientation);
//            mCompassView.invalidate();
//
//        }
//
//    };
//}
