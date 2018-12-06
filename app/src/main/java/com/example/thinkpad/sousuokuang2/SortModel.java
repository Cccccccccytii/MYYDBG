package com.example.thinkpad.sousuokuang2;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class SortModel {



	private String name;   //显示的数据
private  String num;
	private String sortLetters;  //显示数据拼音的首字母


	public String getName() {
		return name;
	}
	public String getNum() {
		return num;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setNum (String num) {
		this.num=num;
	}
	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	public SortModel() {

	}

	public SortModel(String name,String num){
		this.name = name;
		this.num=num;
	}


}







