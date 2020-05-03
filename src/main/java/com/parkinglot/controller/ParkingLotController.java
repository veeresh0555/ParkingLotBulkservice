package com.parkinglot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglot.exception.RecordNotFoundException;
import com.parkinglot.model.LotDetail;
import com.parkinglot.service.ParkingLotservice;

@RestController
@RequestMapping("/parkinglotbulk")
public class ParkingLotController {

	@Autowired
	Environment env;
	
	@Autowired
	ParkingLotservice parkingservice;
	
	@GetMapping("/lotinfo")
	public String lotinf() {
		String port=env.getProperty("local.server.port");
		return "Parking Lot Bulk Running on "+port;
	}
	
	@GetMapping
	public ResponseEntity<List<LotDetail>> searchavailablelot() throws RecordNotFoundException{
		List<LotDetail> availablelots=parkingservice.searchavailablelot();
		return new ResponseEntity<List<LotDetail>>(availablelots, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@GetMapping("/searchfilledlot")
	public ResponseEntity<List<LotDetail>> searchfilledlot() throws RecordNotFoundException{
		List<LotDetail> filledlots=parkingservice.searchafilledlot();
		return new ResponseEntity<List<LotDetail>>(filledlots, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/{lottype}")
	public ResponseEntity<List<LotDetail>> findByLotType(@PathVariable("lottype")long lottype) throws RecordNotFoundException{
		List<LotDetail> availotbytype=parkingservice.findByLotType(lottype);
		return new ResponseEntity<List<LotDetail>>(availotbytype, new HttpHeaders(), HttpStatus.OK);
	}
	
	@PostMapping("/{lotid}/{lottype}")
	public ResponseEntity<LotDetail> parkvehicle(@PathVariable("lotid")long lotid,@PathVariable("lottype")long lottype) throws RecordNotFoundException{
		LotDetail parkinglot=parkingservice.chackandpark(lotid,lottype);
		return new ResponseEntity<LotDetail>(parkinglot, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@PostMapping("/exitvehicle/{lotid}")
	public ResponseEntity<LotDetail> exitvehicle(@PathVariable("lotid")long lotid) throws RecordNotFoundException{
		
		LotDetail exitpark=parkingservice.exitparkinglot(lotid);
		
		return new ResponseEntity<LotDetail>(exitpark,new HttpHeaders(),HttpStatus.OK);
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
