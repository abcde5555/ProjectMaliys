package jm.projectmaliys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DiaryList_S extends Fragment {

    private static final int SAVED_DIARY = 1;
    private ArrayList<DiaryModel_H> listModels;
    private static String date;

    ContentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // DB에서 데이터 가져오기
        getDataFromDatabase();

        //리사이클러 뷰
        RecyclerView recyclerView
                = (RecyclerView)inflater.inflate(R.layout.recycler_view_s, container, false);
        adapter = new ContentAdapter(listModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.notifyItemChanged(0);
        return recyclerView;
    }

    private void getDataFromDatabase() {
        Context context = getActivity().getApplicationContext();
        DatabaseHelper_H databaseUtil = DatabaseHelper_H.getInstance(context);

        listModels = new ArrayList<>();
        listModels.add(0, null);

        // 다이어리 테이블 쿼리
        String selectionSql = "SELECT d_date, d_content FROM diary ORDER BY d_date DESC"; // 쿼리문 작성

        Cursor listCursor = databaseUtil.executeQuery(selectionSql, new String[]{});
        while (listCursor.moveToNext()) {
            date = listCursor.getString(listCursor.getColumnIndex("d_date"));
            String contentString = listCursor.getString(listCursor.getColumnIndex("d_content"));
            if(contentString.length() > 30) {
                contentString = contentString.substring(0, 27).concat("...");
            }

            // 이미지 테이블 쿼리
            String selectImage = "SELECT i_path FROM image WHERE d_date = ?"; // 쿼리문 작성
            String[] parameters = new String[] {date};// 쿼리문 where 절에 들어갈 인자 (?와 매치)

            Cursor repImageCursor = databaseUtil.executeQuery(selectImage, parameters);
            if (repImageCursor.moveToFirst()) {
                String imagePathString = repImageCursor.getString(repImageCursor.getColumnIndex("i_path"));
                Uri imageUri = repImageCursor.moveToFirst() ? // 커서를 첫 행에 두어 이미지 하나만 가져온다
                        Uri.parse(imagePathString) : null;

                DiaryModel_H listModel = new DiaryModel_H(date, contentString, imageUri);
                listModels.add(listModel);
            }
            DiaryModel_H listModel = new DiaryModel_H(date, contentString, null);
            listModels.add(listModel);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode == SAVED_DIARY) {
            getDataFromDatabase();
            adapter.notifyDataSetChanged();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        //리스트 - 썸네일, 날짜, 간단한 내용
        ImageView avator;
        TextView textDate;
        TextView textBriefcontent;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.activity_diary_list__s, parent, false));
            avator = (ImageView) itemView.findViewById(R.id.list_avatar);
            textDate = (TextView) itemView.findViewById(R.id.list_title);
            textBriefcontent = (TextView) itemView.findViewById(R.id.list_desc);

            itemView.setOnClickListener(new OnItemViewClickListener());
        }

        private class OnItemViewClickListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DiaryPage_S.class);
                intent.putExtra(DiaryPage_S.EXTRA_POSITION, getAdapterPosition());

                date = textDate.getText().toString();
                intent.putExtra("date", date);
                context.startActivity(intent);
            }
        }

    }

    // Adapter to display recycler view.
    private static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {

        //Set models of List in RecyclerView.
        private final ArrayList<DiaryModel_H> _listModels;

        public ContentAdapter(ArrayList<DiaryModel_H> listModels) {
            _listModels = listModels;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            DiaryModel_H listModel = _listModels.get(position);
            if (listModel != null) {
                holder.avator.setImageURI(listModel.getImage());
                holder.textDate.setText(listModel.getDate());
                holder.textBriefcontent.setText(listModel.getContent());
            }
        }

        @Override
        public int getItemCount() {
            return _listModels.size();
        }
    }

}
