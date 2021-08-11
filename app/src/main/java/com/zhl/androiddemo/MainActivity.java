package com.zhl.androiddemo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.zhl.androiddemo.kotlin.MainKotlinActivity;
import com.zhl.androiddemo.kotlin.MyService;
import com.zhl.androiddemo.kotlin.navigation.BasicMainActivity;
import com.zhl.androiddemo.mvvm.MvvmActivity;
import com.zhl.androiddemo.rxjava.RxJavaMainActivity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        createNotificationChannel();
        Button btnLocation = findViewById(R.id.btn_location);
        btnLocation.setOnClickListener(this);
        Button btnService = findViewById(R.id.btn_service);
        btnService.setOnClickListener(this);
        Button btnCamerax = findViewById(R.id.btn_camerax);
        btnCamerax.setOnClickListener(this);
        Button btnDayNight = findViewById(R.id.btn_daynight);
        btnDayNight.setOnClickListener(this);
        Button btnDrag = findViewById(R.id.btn_drag);
        btnDrag.setOnClickListener(this);
        Button btnMvvm = findViewById(R.id.btn_mvvm);
        btnMvvm.setOnClickListener(this);
        Button btnKotlin = findViewById(R.id.btn_kotlin);
        btnKotlin.setOnClickListener(this);
        Button btnLink = findViewById(R.id.btn_linklist_test);
        btnLink.setOnClickListener(this);
        Button btnRxJava = findViewById(R.id.btn_rxjava_test);
        btnRxJava.setOnClickListener(this);
        Button btnMotionLayout = findViewById(R.id.btn_motion_layout);
        btnMotionLayout.setOnClickListener(this);
        Button basciMain = findViewById(R.id.btn_navigation_ui);
        basciMain.setOnClickListener(this);
        Button reflect = findViewById(R.id.btn_navigation_reflect);
        reflect.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_location:
                Intent intent = new Intent(this,LocationActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_service:
                Intent intentService = new Intent(this,ServiceTestActivity.class);
                startActivity(intentService);
                break;
            case R.id.btn_camerax:
                Intent intent2 = new Intent(this,CameraXActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_daynight:
                Intent intent3 = new Intent(this,DayNightActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_drag:
                Intent intentDrag = new Intent(this,DragEventActivity.class);
                startActivity(intentDrag);
            case R.id.btn_mvvm:
                Intent intentMvvm = new Intent(this, MvvmActivity.class);
                startActivity(intentMvvm);
                break;
            case R.id.btn_kotlin:
                Intent intentKotlin = new Intent(this, MainKotlinActivity.class);
                startActivity(intentKotlin);
                break;
            case R.id.btn_linklist_test:
                Intent intentLink = new Intent(this, LinkListActivity.class);
                startActivity(intentLink);
                break;
            case R.id.btn_rxjava_test:
                Intent intentRxJava = new Intent(this, RxJavaMainActivity.class);
                startActivity(intentRxJava);
                break;
            case R.id.btn_motion_layout:
                Intent intentMotionLayout = new Intent(this, MotionLayoutTestActivity.class);
                startActivity(intentMotionLayout);
                break;
            case R.id.btn_navigation_ui:
                Intent navigationMain = new Intent(this, BasicMainActivity.class);
                startActivity(navigationMain);
                break;
            case R.id.btn_navigation_reflect:
                Intent flectAndProxy = new Intent(this, FlectAndProxyActivitiy.class);
                startActivity(flectAndProxy);
                break;
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "重要通知";
            String description = "接收用户必要通知";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel_imporpant", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        int currentNightMode = newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        Toast.makeText(this,"更改主题："+(currentNightMode==Configuration.UI_MODE_NIGHT_NO?"日间模式":"夜间模式"),Toast.LENGTH_SHORT).show();
        switch (currentNightMode){
            case Configuration.UI_MODE_NIGHT_NO:

                break;
            case Configuration.UI_MODE_NIGHT_YES:

                break;
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MyService.class));
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(1);
    }
}
