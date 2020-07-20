# [10] Mission

정말 삽질도 많이하고 공부도 많이한 문제(..)  

viewPager2를 써보고싶어서 삽질하다가 리싸이클러뷰에 대한 지식이 하나도 없어서ㅠㅠ 결국 포기하고 공부했던 viewPager을 사용해서 구현했다.  
사실 문제에서 `1번탭` 을 누르면 `프래그먼트 안에 뷰페이저, 뷰페이저 안에 이미지 여러장` 으로 구현하라고 하긴 했는데,  
갑자기 이상한 오기(?)가 붙어서 `프래그먼트 안 뷰페이저, 뷰페이저 안 프래그먼트` `2-3번 탭도 각각 프래그먼트` 로 화면을 구성해보았다.  

프래그먼트 안에 뷰페이저를 넣는 것도 힘겹게... 겨우 했다..  
근데 해냄과 동시에 터진 문제!! 다른 탭(프래그먼트)으로 들어갔다가 첫번째 탭으로 돌아가면 자꾸 앱이 강제종료 되는 현상..!  

결국 어댑터가 FragmentPagerAdapter냐, FragmentStatePagerAdapter냐에 따라  
프래그먼트를 다루는 방식이 다르기 때문이라는걸 알아냈다.(사실 확실한건 아님)  
FragmentStatePagerAdapter를 사용하다가 FragmentPagerAdapter로 바꿔주니 뷰가 더이상 삭제되지 않고 잘 동작하더라는..  
근데 이 방법이 다뤄야 할 소스들이 늘어나게 되면 좋은 방법(?)은 아닌 것 같다.  
그치만 깊게 들어가기엔 어댑터가 사실 뭔지도 아직 잘 모르는 상태여서..ㅋㅋ  

마지막 이슈이자 해결하지 못한 과제는 pager.setCurrentItem(0); << 요 메서드가 어떻게 해도 동작이 안된다는 거였다 ㅠㅠ  
프래그먼트에 따로 메서드를 만들어 액티비티에서 참조해도, 프래그먼트에 만들어주어도 동작을 안해서 결국 포기...()  
후에 공부가 더 진행된 후 원인을 파악해보는 것도 좋을 것 같당  

야매로 겨우 완성시킨 코드지만 그래두 잘 동작하는 것에 뿌듯해하며 마쳤다!  

```java
//MainActivity.java

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;
    Toolbar toolbar;
    mainFragment mainFragment;
    Fragment4 fragment4;
    Fragment5 fragment5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = new mainFragment();
        fragment4 = new Fragment4();
        fragment5 = new Fragment5();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.menu1){
                    Toast.makeText(getApplicationContext(),"첫 번째 메뉴 선택됨.",Toast.LENGTH_SHORT).show();
                } else if(id == R.id.menu2){
                    Toast.makeText(getApplicationContext(),"두 번째 메뉴 선택됨.",Toast.LENGTH_SHORT).show();
                } else if(id == R.id.menu3) {
                    Toast.makeText(getApplicationContext(), "세 번째 메뉴 선택됨.", Toast.LENGTH_SHORT).show();
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.tab1 :
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,mainFragment).commit();
                        return true;

                    case R.id.tab2 :
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment4).commit();

                        return true;

                    case R.id.tab3 :
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment5).commit();

                        return true;
                }

                return false;
            }
        });

        getSupportFragmentManager().beginTransaction().add(R.id.container, mainFragment).commit();

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
```

```java
//activity_main.xml

<?xml version="1.0" encoding="utf-8"?>
<androidx.drawerlayout.widget.DrawerLayout
  	xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/drawer_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    tools:openDrawer="start">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">

        <com.google.android.material.appbar.AppBarLayout
            android:id="@+id/appBarLayout"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:theme="@style/AppTheme.AppBarOverlay"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toTopOf="parent">

            <androidx.appcompat.widget.Toolbar
                android:id="@+id/toolbar"
                android:layout_width="match_parent"
                android:layout_height="?attr/actionBarSize"
                android:background="?attr/colorPrimary"
                app:popupTheme="@style/AppTheme.PopupOverlay">

            </androidx.appcompat.widget.Toolbar>

        </com.google.android.material.appbar.AppBarLayout>

        <FrameLayout
            android:id="@+id/container"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            app:layout_constraintBottom_toTopOf="@+id/bottom_navigation"
            app:layout_constraintTop_toBottomOf="@+id/appBarLayout">

        </FrameLayout>

        <com.google.android.material.bottomnavigation.BottomNavigationView
            android:id="@+id/bottom_navigation"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:itemBackground="@color/colorPrimary"
            app:itemIconTint="@drawable/item_color"
            app:itemTextColor="@drawable/item_color"
            app:menu="@menu/bottom_menu"/>

    </androidx.constraintlayout.widget.ConstraintLayout>

    <com.google.android.material.navigation.NavigationView
        android:id="@+id/nav_view"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:fitsSystemWindows="true"
        app:headerLayout="@layout/nav_header_main"
        app:menu="@menu/activity_main_drawer" />

</androidx.drawerlayout.widget.DrawerLayout>
```

```java
//mainFragment.java

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class mainFragment extends Fragment {
    ViewPager pager;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    MyPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);

        pager = view.findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);
        pager.setCurrentItem(0);
        adapter = new MyPagerAdapter(getChildFragmentManager());

        fragment1 = new Fragment1();
        adapter.addItem(fragment1);
        fragment2 = new Fragment2();
        adapter.addItem(fragment2);
        fragment3 = new Fragment3();
        adapter.addItem(fragment3);

        pager.setAdapter(adapter);

        return view;
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> items = new ArrayList<Fragment>();
        public MyPagerAdapter(FragmentManager fm){
            super(fm);
        }

        public void addItem(Fragment item){
            items.add(item);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return items.get(position);
        }

        @Override
        public int getCount() {
            return items.size();
        }
    }

}

```

```java
//fragment_main.xml

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <androidx.viewpager.widget.ViewPager
        android:id="@+id/pager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent">
    </androidx.viewpager.widget.ViewPager>
    
</androidx.constraintlayout.widget.ConstraintLayout>
```

```java
//fragment1.xml (fragment1~5까지 생성. 텍스트 내용과 배경색만 바뀐거라 fragment1.xml 제외하고 코드 생략함)

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#B5B9C8">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="( ღ'ᴗ'ღ )"
        android:textAllCaps="false"
        android:textColor="#ffffff"
        android:textSize="20sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

```java
//Fragment1.java (마찬가지로 나머지 생략)

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment1,container,false);
    }
}

```

```java
//activity_main_drawer.xml (menu폴더)

<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    tools:showIn="navigation_view">

    <group android:checkableBehavior="single">
        <item
            android:id="@+id/menu1"
            android:icon="@drawable/ic_menu_camera"
            android:title="@string/menu_home" />
        <item
            android:id="@+id/menu2"
            android:icon="@drawable/ic_menu_gallery"
            android:title="@string/menu_gallery" />
        <item
            android:id="@+id/menu3"
            android:icon="@drawable/ic_menu_slideshow"
            android:title="@string/menu_slideshow" />
    </group>
</menu>
```

```java
//bottom_menu.xml (menu폴더)

<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <item
        android:id="@+id/tab1"
        app:showAsAction="ifRoom"
        android:enabled="true"
        android:icon="@android:drawable/ic_dialog_email"
        android:title="이메일"/>

    <item
        android:id="@+id/tab2"
        app:showAsAction="ifRoom"
        android:enabled="true"
        android:icon="@android:drawable/ic_dialog_info"
        android:title="정보"/>

    <item
        android:id="@+id/tab3"
        app:showAsAction="ifRoom"
        android:enabled="true"
        android:icon="@android:drawable/ic_dialog_map"
        android:title="위치"/>

</menu>
```

