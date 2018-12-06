package com.example.thinkpad.sousuokuang2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.thinkpad.suosoukuang2.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChangeActivity extends Activity {

    private EditText editText;
    private EditText dateEdit;
    // 定义显示时间控件
    private Calendar calendar; // 通过Calendar获取系统时间
    private int mYear;
    private int mMonth;
    private int mDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_list);
        // 锁定屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        editText = (EditText) findViewById(R.id.birthday);
        setTouchListener(this);//只需调用这个方法即可

        //从上个页面中获取数据
        Intent intent = getIntent();
        String phone_name1 = intent.getStringExtra("extra_name");
        String phone_number1 = intent.getStringExtra("extra_number");
//        String phone_location1 = intent.getStringExtra("extra_location");
//        String phone_time1 = intent.getStringExtra("extra_time");
        //将得到的数据显示在布局中
        EditText changePhone_name = (EditText) findViewById(R.id.contact_name);
        EditText changePhone_number = (EditText) findViewById(R.id.contact_number);
//        EditText changePhone_location = (EditText) findViewById(R.id.location);
//        EditText changePhone_time = (EditText) findViewById(R.id.birthday);
        changePhone_name.setText(phone_name1);
        changePhone_number.setText(phone_number1);
//        changePhone_location.setText(phone_location1);
//        changePhone_time.setText(phone_time1);
        // 获取对象
        //titleEdit = (EditText) findViewById(R.id.showtitle);
//        dateEdit = (EditText) findViewById(R.id.birthday);
//        calendar = Calendar.getInstance();
//        // 点击"日期"按钮布局 设置日期
//        dateEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(ChangeActivity.this,
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int month, int day) {
//                                // TODO Auto-generated method stub
//                                mYear = year;
//                                mMonth = month;
//                                mDay = day;
//                                // 更新EditText控件日期 小于10加0
//                                dateEdit.setText(new StringBuilder()
//                                        .append(mYear)
//                                        .append("-")
//                                        .append((mMonth + 1) < 10 ? "0"
//                                                + (mMonth + 1) : (mMonth + 1))
//                                        .append("-")
//                                        .append((mDay < 10) ? "0" + mDay : mDay));
//                            }
//                        }, calendar.get(Calendar.YEAR), calendar
//                        .get(Calendar.MONTH), calendar
//                        .get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
        //点击提交的响应事件
        Button btn = (Button) findViewById(R.id.submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String phone_name1 = intent.getStringExtra("extra_name");

                EditText editText1 = (EditText) findViewById(R.id.contact_name);
                String name = editText1.getText().toString();
                EditText editText2 = (EditText) findViewById(R.id.contact_number);
                String number = editText2.getText().toString();
//                EditText editText3 = (EditText) findViewById(R.id.location);
//                String location = editText3.getText().toString();
//                EditText editText4 = (EditText) findViewById(R.id.birthday);
//                String day = editText4.getText().toString();
//                Log.d("test", "click" + name);
//                Log.d("test", "click" + number);
                addContact(name, number);
                deleteContactPhoneNumber(phone_name1);
                // 执行 CQL 语句实现删除一个 Todo 对象




                intent.setClass(ChangeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    //返回的点击事件
    public void ImageButtonClick(View v) {
        switch (v.getId()) {
            case R.id.return5:
                Intent intent = new Intent();
                intent.setClass(ChangeActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
    //最后一行失去焦点时，自动隐藏键盘
    private void setTouchListener(final ChangeActivity context) {
        context.getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (context.getCurrentFocus() != null) {
                    imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
                } else {
                    imm.hideSoftInputFromWindow((context.findViewById(android.R.id.content)).getWindowToken(), 0);
                }
                return false;
            }
        });
    }
    // 一个添加联系人信息的例子

    public void addContact(String name, String phoneNumber) {

        // 创建一个空的ContentValues

        ContentValues values = new ContentValues();


        // 向RawContacts.CONTENT_URI空值插入，

        // 先获取Android系统返回的rawContactId

        // 后面要基于此id插入值

        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);

        long rawContactId = ContentUris.parseId(rawContactUri);

        values.clear();


        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);

        // 内容类型

        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);

        // 联系人名字

        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);

        // 向联系人URI添加联系人名字

        getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

        values.clear();


        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);

        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);

        // 联系人的电话号码

        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber);

        // 电话类型

        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);

        // 向联系人电话号码URI添加电话号码

        getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

        values.clear();


        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);

        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);

        // 联系人的Email地址

        values.put(ContactsContract.CommonDataKinds.Email.DATA, "zhangphil@xxx.com");

        // 电子邮件的类型

        values.put(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);

        // 向联系人Email URI添加Email数据

        getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);


        Toast.makeText(this, "联系人数据添加成功", Toast.LENGTH_SHORT).show();

    }
    private void deleteContactPhoneNumber(String contactName) {
        //根据姓名求id
        Uri uri = ContactsContract.RawContacts.CONTENT_URI;
        ContentResolver resolver = getContentResolver();
        String where = ContactsContract.PhoneLookup.DISPLAY_NAME;
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Data._ID}, where + "=?", new String[]{contactName}, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            //根据id删除data中的相应数据
            resolver.delete(uri, where + "=?", new String[]{contactName});
            uri = ContactsContract.Data.CONTENT_URI;
            resolver.delete(uri, ContactsContract.Data.RAW_CONTACT_ID + "=?", new String[]{id + ""});
        }
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }


}