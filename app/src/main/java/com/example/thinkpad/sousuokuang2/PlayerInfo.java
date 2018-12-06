package com.example.thinkpad.sousuokuang2;


import java.io.Serializable;





/*

 * 用户信息

 * auth:liyachao

 * date:2015/4/6

 */

public class PlayerInfo {



    private String playerName;

    private String playerPhone;

    /**

     * 排序字母

     */

    private String sortKey;



    public String getSortKey() {

        return sortKey;

    }



    public void setSortKey(String sortKey) {

        this.sortKey = sortKey;

    }





    public String getPlayerPhone() {

        return playerPhone;

    }



    public void setPlayerPhone(String playerPhone) {

        this.playerPhone = playerPhone;

    }





    public String getPlayerName() {

        return playerName;

    }



    public void setPlayerName(String playerName) {

        this.playerName = playerName;

    }





}
