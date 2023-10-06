package com.example.scs.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Unread {
    private String unread_id;

    private String user_id;

    private int notification_id;

	public String getUnread_id() {
		return unread_id;
	}

	public void setUnread_id(String unread_id) {
		this.unread_id = unread_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getNotification_id() {
		return notification_id;
	}

	public void setNotification_id(int notification_id) {
		this.notification_id = notification_id;
	}
    
}
