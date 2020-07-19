package org.techtown.mission_09;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainFragment extends Fragment {
    ViewGroup rootView;
    Button birthButton, saveButton;
    EditText name,age;
    String c_year, c_month, c_day;
    String s_year, s_month, s_day;
    String setBirth, setName, setAge;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup)inflater.inflate(R.layout.main_fragment,container,false);

        setCurrentTime();

        birthButton = (Button)rootView.findViewById(R.id.birthButton);
        setBirth(c_year,c_month,c_day);
        birthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });

        name = (EditText)rootView.findViewById(R.id.name);
        age = (EditText)rootView.findViewById(R.id.age);
        saveButton = (Button)rootView.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setName = name.getText().toString();
                setAge = age.getText().toString();

                if( setName.length() != 0 && setAge.length() != 0) {
                    Toast.makeText(getContext(),"이름 : " + setName + ", 나이 : " + setAge + ", 생년월일 : " + setBirth , Toast.LENGTH_SHORT).show();
                } else { Toast.makeText(getContext(),"고객 정보를 입력해주세요." , Toast.LENGTH_SHORT).show(); }
            }
        });

        return rootView;
    }

    public void setCurrentTime(){

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());

        c_year = yearFormat.format(currentTime);
        c_month = monthFormat.format(currentTime);
        c_day = dayFormat.format(currentTime);

    }

    public void showDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                s_year = Integer.toString(year);
                s_month = Integer.toString(month+1);
                s_day = Integer.toString(dayOfMonth);

                setBirth(s_year,s_month,s_day);
                setBirth =  s_year + "년 " + s_month + "월 " + s_day + "일";
            }
        },Integer.parseInt(c_year), Integer.parseInt(c_month)-1, Integer.parseInt(c_day));

        datePickerDialog.show();
    }

    void setBirth(String year, String month, String day){
        birthButton.setText(year + "년 " + month + "월 " + day + "일");
    }
}

