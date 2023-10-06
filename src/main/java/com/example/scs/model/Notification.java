package com.example.scs.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class Notification {
    private Integer notification_id;

//    private String role;

    private String type;

    private String url_id;

    private LocalDateTime time;

	public Integer getNotification_id() {
		return notification_id;
	}

	public void setNotification_id(Integer notification_id) {
		this.notification_id = notification_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl_id() {
		return url_id;
	}

	public void setUrl_id(String url_id) {
		this.url_id = url_id;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Notification{" +
				"notification_id=" + notification_id +
				", type='" + type + '\'' +
				", url_id='" + url_id + '\'' +
				", time=" + time +
				'}';
	}
}
