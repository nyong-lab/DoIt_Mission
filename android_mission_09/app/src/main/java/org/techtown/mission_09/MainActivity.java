package org.techtown.mission_09;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    MainFragment mainFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container,mainFragment).commit();

    }


}

/* 프래그먼트로 고객 정보입력 화면 만들어 액티비티에 넣기
 * 이름/나이 입력받는 입력상자, 생년월일 표시하는 버튼, 저장 버튼 배치
 * 생년월일 버튼에 오늘날짜 표시, 버튼 누르면 [날짜 선택] 대화상자 띄우고 날짜 입력받아 표시
 * 이름 넣을 수 있는 입력상자에 문자열, 나이 입력상자에 숫자 입력 설정, 나이는 세 자리까지만 입력할 수 있게 만들기
 * [저장]버튼 누르면 토스트로 입력한 정보 표시
 */