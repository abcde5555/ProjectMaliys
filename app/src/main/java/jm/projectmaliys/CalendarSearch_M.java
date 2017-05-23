package jm.projectmaliys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

//달력검색 클래스
public class CalendarSearch_M extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_calendar_search__m, null);

        //리스너 사용을 위해 캘린더뷰 타입으로
        final CalendarView calendarView = (CalendarView) view.findViewById(R.id.calendar);

        //리스너 등록
        calendarView.setOnDateChangeListener(
                new CalendarView.OnDateChangeListener(){

                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMenth){
                        Toast.makeText(
                                getActivity(),
                                year+"/"+(month+1)+"/"+dayOfMenth,
                                Toast.LENGTH_SHORT
                                ).show();
                    }
                });
        return view;
    }
}
