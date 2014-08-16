package com.example.android.fragments;

public class PostItem {
	private String object_id;
	private String link;
	private String from_id;
	private String type;
	private String updated_time;
	private int shares_count;
	private String picture;
	private String message;
	private String status_type;
	private String created_time;
	private String comments;
    private String source;
	
	public String getObject_id() {
		return object_id;
	}
	public void setObject_id(String object_id) {
		this.object_id = object_id;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getFrom_id() {
		return from_id;
	}
	public void setFrom_id(String from_id) {
		this.from_id = from_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus_type() {
		return status_type;
	}
	public void setStatus_type(String status_type) {
		this.status_type = status_type;
	}


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
