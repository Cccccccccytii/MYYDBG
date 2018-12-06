package com.example.thinkpad.sousuokuang2;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.Data;
import android.widget.Button;
import android.widget.Toast;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thinkpad.suosoukuang2.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;
	
	/**
	 *汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;


	private  List<PhoneDto> phoneDtos;






	
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		check();
        Button btn2 = (Button) findViewById(R.id.send);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, NewContact.class);
                startActivity(intent);
            }
        });
		initViews();
	}

    private void check() {

        //判断是否有权限

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)

                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_CONTACTS},201);

        }else{

            initViews();

        }

    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==201){

            initViews();

        }else{

            return;

        }

    }



    private void initViews() {

		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		
		pinyinComparator = new PinyinComparator();
		
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);
		
		//设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
			
			@Override
			public void onTouchingLetterChanged(String s) {
				//该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if(position != -1){
					sortListView.setSelection(position);
				}
				
			}
		});
		
		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


				Intent intent = new Intent();

				//获取点击当前页面的属性

				String Phone_name= ((SortModel)adapter.getItem(position)).getName();
				String Phone_number= ((SortModel)adapter.getItem(position)).getNum();
//                            //测试用例
				Log.d("test","click"+Phone_name);
				Log.d("test","click"+Phone_number);
//                //传递参数给下个页面
				intent.setClass(MainActivity.this, SecondActivity.class);
				intent.putExtra("extra_name",Phone_name);
				intent.putExtra("extra_number",Phone_number);
				startActivity(intent);

			}

		});

		PhoneUtil phoneUtil = new PhoneUtil(this);

		phoneDtos= phoneUtil.getPhone();
		int size=phoneDtos.size();
		String[][] date=new String[size][2];
		for(int i=0;i<phoneDtos.size();i++){
			date[i][0]= phoneDtos.get(i).getName();
            date[i][1]= phoneDtos.get(i).getTelPhone();

		}









		SourceDateList = filledData(date);



		
		// 根据a-z进行排序源数据
		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new SortAdapter(this, SourceDateList);
		sortListView.setAdapter(adapter);
		
		
		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
		
		//根据输入框输入值的改变来过滤搜索
		mClearEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}


	/**
	 * 为ListView填充数据
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String [][] date){
		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<date.length; i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i][0]);
            sortModel.setNum(date[i][1]);
			//汉字转换成拼音
			String pinyin = characterParser.getSelling(date[i][0]);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}
            else
            {
				sortModel.setSortLetters("#");
			}
			
			mSortList.add(sortModel);
		}
		return mSortList;
		
	}
	
	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * @param filterStr
	 */
	private void filterData(String filterStr){
		List<SortModel> filterDateList = new ArrayList<SortModel>();
		
		if(TextUtils.isEmpty(filterStr)){
			filterDateList = SourceDateList;
		}
        else if (filterStr.matches("^([0-9]|[/+])*$")) {//正则表达式 匹配号码
            for (SortModel sortModel : SourceDateList) {
                if (sortModel.getNum() != null && sortModel.getName() != null) {
                    if (sortModel.getNum().contains(filterStr) || sortModel.getName().contains(filterStr)) {
                        if (!filterDateList.contains(sortModel)) {
                            filterDateList.add(sortModel);
                        }
                    }
                }
            }
            }else{
			filterDateList.clear();
			for(SortModel sortModel : SourceDateList){
				String name = sortModel.getName();
				if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterDateList.add(sortModel);
				}
			}
		}
		
		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
	
}
