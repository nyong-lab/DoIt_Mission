# [12] Mission

```java
//mainActivity.xml

<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <EditText
        android:id="@+id/editText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:ems="10"
        android:inputType="textPersonName"
        android:layout_marginTop="30dp"
        android:layout_marginLeft="30dp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="보내기"
        android:layout_marginTop="10dp"
        android:layout_marginLeft="30dp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/editText" />

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="수신자에서 전달받은 인텐트"
        android:textSize="15sp"
        android:layout_marginLeft="30dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/button" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

```java
//AndroidManifest.xml

<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="org.techtown.mission_12">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">

        <service
            android:name=".MyService"
            android:enabled="true"
            android:exported="true" />

        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

```java
//MainActivity.java 

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Nyong";
    EditText editText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                Intent serviceIntent = new Intent(getApplicationContext(), MyService.class);
                serviceIntent.putExtra("message", message);
                startService(serviceIntent);
                Log.d(TAG,"액티비티에서 서비스로 데이터를 전송합니다");
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.broadcast.NYONG");
        registerReceiver(mBroadcastReceiver, filter);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"메인 액티비티에 있는 브로드캐스트 리시버가 데이터를 받았습니다");
            String BroadcastMessage = intent.getStringExtra("serviceMessage");
            textView.setText(BroadcastMessage);
        }
    };

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }
}
```

```java
//MyService.java

public class MyService extends Service {
    private static final String TAG = "Nyong";

    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent == null){
            return Service.START_STICKY;
        } else {
            Log.d(TAG,"서비스에서 액티비티로부터 온 데이터를 받았습니다");
            goBroadcast(intent);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void goBroadcast(Intent intent) {
        String message = intent.getStringExtra("message");

        Intent broadIntent = new Intent("com.example.broadcast.NYONG");
        broadIntent.putExtra("serviceMessage", message);
        sendBroadcast(broadIntent);
        Log.d(TAG,"서비스에서 브로드캐스팅으로 데이터를 전송합니다");
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
```

