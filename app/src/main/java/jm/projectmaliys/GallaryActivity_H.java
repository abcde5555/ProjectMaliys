package jm.projectmaliys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

// 일별 갤러리 화면
public class GallaryActivity_H extends AppCompatActivity {

    private static final String TAG = "GallActivity";
    private RecyclerView _recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_gallary_h);

        _recyclerView = (RecyclerView) findViewById(R.id.recycler_daily_gall);

        ArrayList<Photo> photos = new ArrayList<>(); // 갤러리에 연결될 데이터셋 모델
        // (select from 문으로 얻어오는 메서드 작성해서 교체할 것)

        // 어댑터 생성 및 설정
        GallaryAdapter adapter = new GallaryAdapter(photos);
        _recyclerView.setAdapter(adapter);
        _recyclerView.setHasFixedSize(true);
        _recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));


        final GestureDetector detector
                = new GestureDetector(GallaryActivity_H.this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent event) {
                return true;
            }
        });

        // 각 이미지 터치 시 이벤트 핸들링
        _recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                Log.d(TAG, "onInterceptTouchEvent");
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && detector.onTouchEvent(e)){
                    Log.d(TAG, "coordinate => " + e.getX() + ", " + e.getY());
                    Log.d(TAG, "getChildViewHolder => " + rv.getChildViewHolder(child).itemView);


                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }

    // 갤러리 어댑터는 내부클래스로 구현
    private class GallaryAdapter extends RecyclerView.Adapter<ViewHolder> {
        ArrayList<Photo> _photos;

        // 생성자
        public GallaryAdapter(ArrayList<Photo> photos) {
            _photos = photos;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.gallary_item_h, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Photo photo = _photos.get(position);
            holder.imageView.setBackground(photo.getDrawableImage());
            holder.text.setText(photo.getShortenText());
        }

        @Override
        public int getItemCount() {
            return _photos.size();
        }
    }

    // RecyclerView의 재사용성을 위해 강제되는 ViewHolder 패턴 역시 내부 클래스로 구현
    private class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) findViewById(R.id.image_item);
            text = (TextView) findViewById(R.id.shorten_text);
        }
    }
}
