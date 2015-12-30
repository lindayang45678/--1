package com.lakala.model.profit;

import java.io.Serializable;

public class TgoodsinfoWithBLOBs  extends Tgoodsinfo  implements Serializable{
    
	private static final long serialVersionUID = 3998984113696526929L;

	private String gooddisc;

    private String goodbigpic;

    private String goodsmallpic;

    private String distributionregion;
    
    private String[] goodbigpicArray={};

    public String[] getGoodbigpicArray() {
    	if(goodbigpic!=null){
    		goodbigpicArray=goodbigpic.split(";");
    	}
		return goodbigpicArray;
	}

	public void setGoodbigpicArray(String[] goodbigpicArray) {
		this.goodbigpicArray = goodbigpicArray;
	}

	public String getGooddisc() {
        return gooddisc;
    }

    public void setGooddisc(String gooddisc) {
        this.gooddisc = gooddisc == null ? null : gooddisc.trim();
    }

    public String getGoodbigpic() {
        return goodbigpic;
    }

    public void setGoodbigpic(String goodbigpic) {
        this.goodbigpic = goodbigpic == null ? null : goodbigpic.trim();
    }

    public String getGoodsmallpic() {
        return goodsmallpic;
    }

    public void setGoodsmallpic(String goodsmallpic) {
        this.goodsmallpic = goodsmallpic == null ? null : goodsmallpic.trim();
    }

    public String getDistributionregion() {
        return distributionregion;
    }

    public void setDistributionregion(String distributionregion) {
        this.distributionregion = distributionregion == null ? null : distributionregion.trim();
    }
}