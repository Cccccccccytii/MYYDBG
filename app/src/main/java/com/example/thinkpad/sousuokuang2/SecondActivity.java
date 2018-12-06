package com.example.thinkpad.sousuokuang2;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;

import com.example.thinkpad.suosoukuang2.R;


public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //加载自己的布局文件
        setContentView(R.layout.second);


        //从上个页面中获取数据
        Intent intent = getIntent();
        final String data_name = intent.getStringExtra("extra_name");
        final String data_number = intent.getStringExtra("extra_number");
////        //测试用例
//        Log.d("test","click"+data_number);
        //将得到的数据显示在布局中
        TextView newPhone_name = (TextView) findViewById(R.id.phone_name);
        TextView newPhone_number = (TextView) findViewById(R.id.phone_number);
        newPhone_name.setText(data_name);
        newPhone_number.setText(data_number);

        Button btn = (Button) findViewById(R.id.change);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("extra_name",data_name);
                intent.putExtra("extra_number",data_number);
                intent.setClass(SecondActivity.this, ChangeActivity.class);
                startActivity(intent);
            }
        });
        Button btn1 = (Button) findViewById(R.id.delete);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                final String data_name = intent.getStringExtra("extra_name");
                deleteContactPhoneNumber(data_name);

                //删除后跳转页面
                intent.setClass(SecondActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
    //返回的点击事件1
    public void ImageButtonClick1(View v) {
        switch (v.getId()) {
            case R.id.return3:
                Intent intent = new Intent();
                intent.setClass(SecondActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
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

