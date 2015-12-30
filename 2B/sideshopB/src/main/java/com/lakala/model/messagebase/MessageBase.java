package com.lakala.model.messagebase;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by HOT.LIU on 2015/3/10.
 */
public class MessageBase implements Serializable {
    private Integer id;

    private String psam;

    private String netpointCode;

    private Integer canalId;

    private Integer channelId;

    private Integer category;

    private String title;

    private Byte visible;

    private Integer userId;

    private String userName;

    private Date createDate;

    private Date updateDate;

    private String content;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPsam() {
        return psam;
    }

    public void setPsam(String psam) {
        this.psam = psam;
    }

    public String getNetpointCode() {
        return netpointCode;
    }

    public void setNetpointCode(String netpointCode) {
        this.netpointCode = netpointCode;
    }

    public Integer getCanalId() {
        return canalId;
    }

    public void setCanalId(Integer canalId) {
        this.canalId = canalId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Byte getVisible() {
        return visible;
    }

    public void setVisible(Byte visible) {
        this.visible = visible;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}