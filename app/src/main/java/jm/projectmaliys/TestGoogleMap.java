package jm.projectmaliys;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TestGoogleMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_google_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
//    GoogleMap mGoogleMap; // 구글 맵 객체
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test_google_map);
//
//        // 1. SDK manager 에서 Google play services 설치
//        // 2. import google-play-services_lib
//        // 3. 프로젝트에 library 등록 : 메뉴 properites Android-library
//        // 4. 구글 API Key 발급
//        // 5. Manifest.xml 설정
//
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.fragment);
//        mapFragment.getMapAsync(this);
//
//        // 맵 객체 생성
////        mGoogleMap = ((SupportMapFragment) getSupportFragmentManager()
////                .findFragmentById(R.id.fragment)).getMap();
//
//        // 맵 위치를 이동하기    // 센터 좌표(37.542212, 126.841319)
//        CameraUpdate update = CameraUpdateFactory.newLatLng(
//                new LatLng(37.478911, 127.012339));

//        // 위도,경도
//        mGoogleMap.moveCamera(update);
//
//        // 보기 좋게 확대 zoom 하기
//        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
//        mGoogleMap.animateCamera(zoom);
//
//        // 마커 표시하기 : 위치지정, 풍선말 설정
//        MarkerOptions markerOptions = new MarkerOptions()
//                .position(new LatLng(37.479097, 127.011784))
//                .title("Great DavidJ!")
//                .snippet("Really Great!");
//
//        // 마커를 추가하고 말풍선 표시한 것을 보여주도록 호출
//        mGoogleMap.addMarker(markerOptions).showInfoWindow();
//
//        // 마커 클릭했을 떄 처리 : 리스너 달기
//        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                Toast.makeText(TestGoogleMap.this, "Oh my god!!", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//    } // end onCreate()
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mGoogleMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mGoogleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }
} // end class
