package jm.projectmaliys;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DiaryList_S extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //리사이클러 뷰
        RecyclerView recyclerView
                = (RecyclerView)inflater.inflate(R.layout.recycler_view_s, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //리스트 - 썸네일, 날짜, 간단한 내용
        public ImageView avator;
        public TextView name;
        public TextView briefcontent;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.activity_diary_list__s, parent, false));
            avator = (ImageView) itemView.findViewById(R.id.list_avatar);
            name = (TextView) itemView.findViewById(R.id.list_title);

            itemView.setOnClickListener(new OnItemViewClickListener());
        }

        private class OnItemViewClickListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DiaryPage_S.class);
                intent.putExtra(DiaryPage_S.EXTRA_POSITION, getAdapterPosition());
                context.startActivity(intent);
            }
        }
    }


    // Adapter to display recycler view.
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 18;

        private final String[] dDates;
        private final String[] dBrifcon;
        //private final Drawable[] dAvator;

        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            dDates = resources.getStringArray(R.array.diary_date);
            dBrifcon = resources.getStringArray(R.array.diary_briefcon);
            TypedArray a = resources.obtainTypedArray(R.array.diary_avator);

        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //holder.avator.setImageDrawable(mPlaceAvators[position % mPlaceAvators.length]);
            //holder.name.setText(mPlaces[position % mPlaces.length]);
            //holder.briefcontent.setText(mPlaceDesc[position % mPlaceDesc.length]);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }

}
