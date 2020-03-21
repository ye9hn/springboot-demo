package com.henu.mybat.entity;


import java.util.Date;
import java.util.List;


public class Message {

	private String id;    //id

    private List<Area> msg; //消息

    private Date sendTime;  //时间戳

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Area> getMsg() {
		return msg;
	}

	public void setMsg(List msg) {
		this.msg = msg;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	@Override
	public String toString() {
		return "Message{" +
				"id='" + id + '\'' +
				", msg=" + msg +
				", sendTime=" + sendTime +
				'}';
	}
}
