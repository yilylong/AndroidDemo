package com.zhl.androiddemo.mvvm;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.zhl.androiddemo.R;
import com.zhl.androiddemo.databinding.ActivityRoomActivitiyBinding;
import com.zhl.androiddemo.mvvm.bean.Address;
import com.zhl.androiddemo.mvvm.bean.Car;
import com.zhl.androiddemo.mvvm.bean.Language;
import com.zhl.androiddemo.mvvm.bean.Person;
import com.zhl.androiddemo.mvvm.db.DBInstance;

import java.util.List;
import java.util.Random;

public class RoomActivitiy extends AppCompatActivity implements View.OnClickListener {
    ActivityRoomActivitiyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_activitiy);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_room_activitiy);
        binding.setClicklistener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert:

                Person person = new Person("张三"+new Random().nextInt());
                person.id = 100+new Random().nextInt(1000);
                person.setSkin("黄");
                person.setGender("男");
                person.setLanguage(Language.CHINESE);
                person.setNation("china");
                person.setAge(new Random().nextInt(100));

                Address address = new Address();
                address.setCountry("中国");
                address.setProvince("广东");
                address.setCity("广州市");
                address.setStreet("锦业路");
                address.setStreet("恒大御景");
                person.setAddress(address);

                Car car = new Car();
                car.brand="北京现代";
                car.carNum = "粤A-75N27";
                car.userId = person.id;

                DBInstance.getInstance().getPersonDao().insert(person);
                DBInstance.getInstance().getCarDao().insert(car);
                break;
            case R.id.btn_query:
                List<Person> list = DBInstance.getInstance().getPersonDao().getAll();
                if(!list.isEmpty()){
                    Person person1 = list.get(list.size()-1);
                    Car car1 = DBInstance.getInstance().getCarDao().getCarByUid(person1.id);
                    binding.setDBQueryResult(person1.name+"-"+person1.getAddress().getCity()+"-age:"+person1.getAge()+"--车牌号："+(car1==null?"":car1.carNum));
                }
                break;
        }
    }
}
