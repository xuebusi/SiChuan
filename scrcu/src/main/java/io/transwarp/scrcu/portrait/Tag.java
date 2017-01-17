package io.transwarp.scrcu.portrait;

import java.io.Serializable;

public class Tag implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6426891205282177741L;
	String code;
	String name;
	String type;
	Integer total;
	double rate;
	boolean isStatic;
	// topic,topic_type,topic_label,label_desc,TOTAL,insert_year,insert_week

	private String topic;
	private String topic_desc;
	private String topic_type;
	private String topic_label;
	private String label_desc;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTopic_desc() {
		return topic_desc;
	}

	public void setTopic_desc(String topic_desc) {
		this.topic_desc = topic_desc;
	}

	public String getTopic_type() {
		return topic_type;
	}

	public void setTopic_type(String topic_type) {
		this.topic_type = topic_type;
	}

	public String getTopic_label() {
		return topic_label;
	}

	public void setTopic_label(String topic_label) {
		this.topic_label = topic_label;
	}

	public String getLabel_desc() {
		return label_desc;
	}

	public void setLabel_desc(String label_desc) {
		this.label_desc = label_desc;
	}

}