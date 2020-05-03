package com.parkinglot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="lotdetail")
public class LotDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long lotid;
	private long floor;
	private long lottype;//wheels
	private long bno;
	private String bname;
	private String city;
	private String location;
	private String status;
	private Double amount;
	
	public long getBno() {
		return bno;
	}

	public void setBno(long bno) {
		this.bno = bno;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public long getLotid() {
		return lotid;
	}

	public void setLotid(long lotid) {
		this.lotid = lotid;
	}

	public long getFloor() {
		return floor;
	}

	public void setFloor(long floor) {
		this.floor = floor;
	}

	public long getLottype() {
		return lottype;
	}

	public void setLottype(long lottype) {
		this.lottype = lottype;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
	
}
