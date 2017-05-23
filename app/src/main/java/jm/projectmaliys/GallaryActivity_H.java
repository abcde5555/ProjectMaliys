package jm.projectmaliys;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class GallaryActivity_H extends AppCompatActivity {

    private static final String TAG = "GallActivity";
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_DB = 2;

    private RecyclerView recyclerView;

    private GallaryAdapter adapter;
    private ArrayList<Uri> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_gallary_h);

        initViewAndDataSet();

        initEventListener(); // 초기화된 뷰에 이벤트 리스너 등록
    }

    // 뷰와 데이터셋 초기화
    private void initViewAndDataSet() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_daily_gall);

        photos = new ArrayList<>(); // 갤러리에 연결될 데이터셋
        // (select from 문으로 얻어오는 메서드 작성해서 교체할 것)

        // 어댑터 생성 및 설정
        adapter = new GallaryAdapter(photos);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        // 이미지 추가 버튼
        Button addImageButton = (Button)findViewById(R.id.btn_add_image);
        addImageButton.setOnClickListener(new OnAddImageClickListener());

        // DB에서 가져온 이미지 Uri를 데이터셋에 추가

        adapter.notifyDataSetChanged(); // 어댑터에 데이터셋의 변화를 알려줌
    }

    // 이미지 추가 버튼 클릭 이벤트에 대한 리스너 클래스
    private class OnAddImageClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            startActivityForResult(intent, PICK_FROM_ALBUM);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        if (requestCode == PICK_FROM_ALBUM) {
            Uri imageUri = data.getData();
            photos.add(imageUri);
            adapter.notifyDataSetChanged();
        } else if (requestCode == PICK_FROM_DB) {
            // 구현해야 함
        }
    }

    // 개별 이미지 클릭에 대한 이벤트 리스너
    private void initEventListener() {
        // 모션이벤트를 탐지
        final GestureDetector detector
                = new GestureDetector(GallaryActivity_H.this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent event) {
                return true;
            }
        });

        // 각 이미지 터치 시
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                Log.d(TAG, "onInterceptTouchEvent");
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && detector.onTouchEvent(e)){
                    int index = rv.getChildViewHolder(child).getAdapterPosition();
                    Log.d(TAG, "coordinate => " + e.getX() + ", " + e.getY());
                    Log.d(TAG, "getChildViewHolder => " + index);

                    // 크게 보기
                    createCustomDialog(photos.get(index));
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

    // 이미지 크게 보기 다이얼로그 생성 및 띄우기
    private void createCustomDialog(Uri uri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(GallaryActivity_H.this);

        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.expanded_image_dialog_h, null);
        ImageView image = (ImageView)layout.findViewById(R.id.img_expanded);
        image.setImageURI(uri);

        builder.setView(layout);
        builder.setNegativeButton("닫기", new OnCloseButtonListener());
        builder.create().show();
    }

    // RecyclerView에 적용할 어댑터 클래스
    private class GallaryAdapter extends RecyclerView.Adapter<ViewHolder> {
        ArrayList<Uri> _photos;

        // 생성자
        public GallaryAdapter(ArrayList<Uri> photos) {
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
            Uri photo = _photos.get(position);
            holder.imageView.setImageURI(photo);
        }

        @Override
        public int getItemCount() {
            return _photos.size();
        }
    }

    // RecyclerView.ViewHolder 객체
    private class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_item);
        }
    }

    // 크게 보기 다이얼로그 창 끄기
    private class OnCloseButtonListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }
}
