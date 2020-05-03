package com.parkinglot.service;

import java.util.List;

import com.parkinglot.exception.RecordNotFoundException;
import com.parkinglot.model.LotDetail;

public interface ParkingLotservice {

	public List<LotDetail> searchavailablelot() throws RecordNotFoundException;

	public List<LotDetail> searchafilledlot() throws RecordNotFoundException;

	public List<LotDetail> findByLotType(long lottype) throws RecordNotFoundException;

	public LotDetail chackandpark(long lotid, long lottype) throws RecordNotFoundException;

	public LotDetail exitparkinglot(long lotid) throws RecordNotFoundException;

}
