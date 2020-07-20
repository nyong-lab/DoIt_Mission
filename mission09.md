# [09] Mission

```java
//MainActivity.java

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

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
```

```java
//activity_main.xml

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity"
    android:id="@+id/container"/>
```

```java
//MainFragment.java

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


```

```java
//main_fragment.xml

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#ffffff">


    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="30dp"
        android:layout_marginTop="30dp"
        android:text="고객정보 입력"
        android:textSize="20sp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <EditText
        android:id="@+id/name"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="28dp"
        android:layout_marginTop="10dp"
        android:hint="이름"
        android:ems="10"
        android:inputType="text"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/textView" />

    <EditText
        android:id="@+id/age"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="28dp"
        android:layout_marginTop="10dp"
        android:hint="나이"
        android:ems="10"
        android:inputType="number"
        android:maxLength="3"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/name" />

    <TextView
        android:id="@+id/textView2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="15dp"
        android:layout_marginLeft="30dp"
        android:text="생년월일"
        android:textSize="15sp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/age" />

    <Button
        android:id="@+id/birthButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="28dp"
        android:text="1998.03.21"
        android:textSize="20sp"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/textView2" />

    <Button
        android:id="@+id/saveButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="28dp"
        android:layout_marginTop="20dp"
        android:text="저장"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/birthButton" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

