package com.parkinglot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parkinglot.exception.RecordNotFoundException;
import com.parkinglot.model.LotDetail;
import com.parkinglot.repository.ParkingLotRepository;

@Service
public class ParkingLotserviceImpl implements ParkingLotservice {
	
	@Autowired
	ParkingLotRepository plotRepository;
	
	@Transactional
	@Override
	public List<LotDetail> searchavailablelot() throws RecordNotFoundException {
		List<LotDetail> availots=plotRepository.findavailotBystatus();
		if(availots.size()==0) {
			throw new RecordNotFoundException("Parking Full ! Lots Not available");
		}
		return availots;
	}
	
	@Transactional
	@Override
	public List<LotDetail> searchafilledlot() throws RecordNotFoundException {
		List<LotDetail> notavailots=plotRepository.findNotavailotBystatus();
		System.out.println("lotbytype: "+notavailots.size());
		if(notavailots.size()==0) {
			throw new RecordNotFoundException("Not yet parked vehicles! All Lots are available");
		}
		return notavailots;
	}

	@Override
	public List<LotDetail> findByLotType(long lottype) throws RecordNotFoundException{
		List<LotDetail> lotbytype=plotRepository.findLotByType(lottype);
		System.out.println("lotbytype: "+lotbytype.size());
		if(lotbytype.size()==0) {
			throw new RecordNotFoundException(lotbytype+" wheels Parking Full ! Lots Not available");
		}
		return lotbytype;
		
		
		
	}

	@Transactional
	@Override
	public LotDetail chackandpark(long lotid, long lottype) throws RecordNotFoundException {
		Optional<LotDetail> checklot=plotRepository.findById(lotid);
		if(checklot.isPresent()) {
			LotDetail lotinfo=checklot.get();
			if(lottype ==lotinfo.getLottype()) {
				if(lotinfo.getStatus().equalsIgnoreCase("y")) {
					lotinfo.setStatus("N");
					return plotRepository.save(lotinfo);
					
				}else {
					System.out.println("Parking lot "+lotid+" Not available");
					throw new RecordNotFoundException("Parking lot "+lotid+" Not available");
				}
			}else {
				System.out.println("This parking available for "+lotinfo.getLottype()+" wheel only");
				throw new RecordNotFoundException("This parking available for "+lotinfo.getLottype()+" wheel only");
			}
			
		}else {
			System.out.println("Parking lot Not available");
			throw new RecordNotFoundException("Parking lot Not available");
		}
	}

	@Override
	public LotDetail exitparkinglot(long lotid) throws RecordNotFoundException {
		Optional<LotDetail> checkltid=plotRepository.findById(lotid);
		if(checkltid.isPresent()) {
			LotDetail upforexit=checkltid.get();
			if(upforexit.getStatus().equalsIgnoreCase("N")) {
				upforexit.setStatus("y");
				return plotRepository.save(upforexit);
			}else {
				throw new RecordNotFoundException("Your already exit!");
			}
		}else {
			throw new RecordNotFoundException("Lot Id Not Found! Please check!");
		}
		
	}

}
