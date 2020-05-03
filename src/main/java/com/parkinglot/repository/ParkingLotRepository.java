package com.parkinglot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parkinglot.model.LotDetail;

@Repository
public interface ParkingLotRepository extends JpaRepository<LotDetail, Long> {

	
	@Query("select lot from LotDetail lot where status='Y'")
	public List<LotDetail> findavailotBystatus();
	@Query("select lot from LotDetail lot where status='N'")
	public List<LotDetail> findNotavailotBystatus();
	
	@Query("select lot from LotDetail lot where lottype=:lottype")
	public List<LotDetail> findLotByType(@Param("lottype") long lottype);

}
