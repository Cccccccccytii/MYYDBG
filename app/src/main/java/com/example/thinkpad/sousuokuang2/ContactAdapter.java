package com.example.thinkpad.sousuokuang2;


import android.content.Context;
import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.BaseAdapter;

import android.widget.LinearLayout;

import android.widget.SectionIndexer;

import android.widget.TextView;


import com.example.thinkpad.suosoukuang2.R;

import java.util.List;



/**

 * 联系人列表适配器。

 *

 * @author guolin

 */

public class ContactAdapter extends BaseAdapter {





    /**

     * 需要渲染的item布局文件

     */

    private int resource;

    private Context context;

    private List<PlayerInfo> players;

    private boolean flag = true;



    /**

     * 字母表分组工具

     */

    private SectionIndexer mIndexer;



    public ContactAdapter(Context context, int textViewResourceId, List<PlayerInfo> players) {



        resource = textViewResourceId;

        this.context = context;

        this.players = players;

    }





    @Override

    public int getCount() {

        return players.size();

    }



    @Override

    public PlayerInfo getItem(int position) {

        return players.get(position);

    }



    @Override

    public long getItemId(int position) {

        return position;

    }



    public void dataChanged(List<PlayerInfo> players) {

        this.players = players;

        notifyDataSetChanged();

        flag = false;

    }



    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        PlayerInfo contact = getItem(position);

        LinearLayout layout = null;

        if (convertView == null) {

            layout = (LinearLayout) LayoutInflater.from(context).inflate(resource, null);

        } else {

            layout = (LinearLayout) convertView;

        }

        TextView name = (TextView) layout.findViewById(R.id.name);

        LinearLayout sortKeyLayout = (LinearLayout) layout.findViewById(R.id.sort_key_layout);

        TextView sortKey = (TextView) layout.findViewById(R.id.sort_key);

        name.setText(contact.getPlayerName());

        if (flag) {

            int section = mIndexer.getSectionForPosition(position);

            if (position == mIndexer.getPositionForSection(section)) {

                sortKey.setText(contact.getSortKey());

                sortKeyLayout.setVisibility(View.VISIBLE);

            } else {

                sortKeyLayout.setVisibility(View.GONE);

            }

        } else {

            sortKeyLayout.setVisibility(View.GONE);

        }

        return layout;

    }



    /**

     * 给当前适配器传入一个分组工具。

     *

     * @param indexer

     */

    public void setIndexer(SectionIndexer indexer) {

        mIndexer = indexer;

        flag = true;

    }



}
