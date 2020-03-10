package com.henu.demo.entity;

import java.util.Date;
import java.util.List;

public class Message {
	private Long id;    //id

    private List<Area> msg; //消息

    private Date sendTime;  //时间戳

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
				"id=" + id +
				", msg=" + msg +
				", sendTime=" + sendTime +
				'}';
	}
}
