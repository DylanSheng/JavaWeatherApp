package ca.dylansheng.weatherapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import ca.dylansheng.weatherapp.R;
import ca.dylansheng.weatherapp.cityInfo.cityInfo;
import ca.dylansheng.weatherapp.db.MyDatabaseHelper;

import static android.R.attr.onClick;

public class MainActivity extends Activity implements View.OnTouchListener, View.OnClickListener{
    private ImageButton imageButtonMainActivity_1;
    private TextView textViewMainActivity_1_cityName;
    private TextView textViewMainActivity_1_temperature;
    private GestureDetector gestureDetector;

    private Button buttonAddCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        gestureDetector = new GestureDetector(this, new SingleTapConfirm());
        imageButtonMainActivity_1 = (ImageButton) findViewById(R.id.imageButtonMainActivity_1) ;
        imageButtonMainActivity_1.setOnTouchListener(this);
        textViewMainActivity_1_cityName = (TextView) findViewById(R.id.textViewMainActivity_1_cityName);
        textViewMainActivity_1_temperature = (TextView) findViewById(R.id.textViewMainActivity_1_temperature);
        buttonAddCity = (Button) findViewById(R.id.buttonAddCity);
        buttonAddCity.setOnClickListener(this);
        //intent to getInfoActivity
        readFromDatabaseInit();
        //Intent intent = new Intent(MainActivity.this, getInfoActivity.class);
        //startActivity(intent);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.imageButtonMainActivity_1:
                if (gestureDetector.onTouchEvent(event)) {
                    Intent intent = new Intent(MainActivity.this, getInfoActivity.class);
                    startActivity(intent);
                } else {
                    // your code for move and drag
                }
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonAddCity:
                Intent intent = new Intent(MainActivity.this, changeCity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }

    private void readFromDatabaseInit(){
        cityInfo city = new cityInfo();
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(MainActivity.this,"weatherDB.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        city = dbHelper.readDatabaseValue(db, "edmonton");
        textViewMainActivity_1_cityName.setText(city.cityName);
        textViewMainActivity_1_temperature.setText(city.temperature.toString());

        Drawable image = new BitmapDrawable(getResources(),BitmapFactory.decodeByteArray(city.cityImage, 0, city.cityImage.length));

        imageButtonMainActivity_1.setBackground(image);
    }
}