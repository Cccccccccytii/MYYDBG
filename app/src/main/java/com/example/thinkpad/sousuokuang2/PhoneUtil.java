package com.example.thinkpad.sousuokuang2;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class PhoneUtil {
    public final static String NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;

    public final static String NUM = ContactsContract.CommonDataKinds.Phone.NUMBER;
    private Context context;
    private Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    public PhoneUtil(Context context){

        this.context = context;

    }
    public List<PhoneDto> getPhone(){

        List<PhoneDto> phoneDtos = new ArrayList<>();

        ContentResolver cr = context.getContentResolver();

        Cursor cursor = cr.query(phoneUri,new String[]{NAME,NUM},null,null,null);

        while (cursor.moveToNext()){

            PhoneDto phoneDto = new PhoneDto(cursor.getString(cursor.getColumnIndex(NAME)),cursor.getString(cursor.getColumnIndex(NUM)));

            phoneDtos.add(phoneDto);

        }

        return phoneDtos;

    }

}
