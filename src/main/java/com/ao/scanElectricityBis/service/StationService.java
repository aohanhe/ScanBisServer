package com.ao.scanElectricityBis.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.JpaQueryCreator;
import org.springframework.stereotype.Service;

import com.ao.scanElectricityBis.entity.QStationDevice;
import com.ao.scanElectricityBis.entity.QStationStationInfo;
import com.ao.scanElectricityBis.entity.StationDevice;
import com.ao.scanElectricityBis.entity.StationMongoEntry;
import com.ao.scanElectricityBis.entity.StationMongoEntry.Pos;
import com.ao.scanElectricityBis.entity.StationStationInfo;
import com.ao.scanElectricityBis.repository.StationEntryMongoRepository;
import com.ao.scanElectricityBis.repository.StationRepositiory;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ao.jpaQueryHelper.BaseJpaQueryBean;
import ao.jpaQueryHelper.JpaQueryHelper;
import ao.jpaQueryHelper.JpaQueryHelperException;
import reactor.core.publisher.Flux;

/**
 * վ��������
 * 
 * @author aohanhe
 *
 */
@Service
public class StationService extends BaseService<StationStationInfo, StationRepositiory> {
	@Autowired
	private EntityManager em;
	@Autowired
	private StationEntryMongoRepository mongoRep;
	
	/**
	 * querydsl��������
	 */
	private JPAQueryFactory factory;

	@PostConstruct
	public void init() {
		factory = new JPAQueryFactory(em);
	}


	
	/**
	 * ��д�������ݲ�������ͬ��mongodb����
	 */
	@Override
	protected StationStationInfo onSave(StationStationInfo item) {
		item=this.rep.save(item);
		StationMongoEntry mongItem=new StationMongoEntry();
		
		mongItem.setStationId(item.getId());		
		mongItem.setName(item.getName());
		mongItem.setAddress(item.getAddress());

		String[] strPos = item.getPoint().split(",");
		mongItem.setPos(new Pos(Double.parseDouble(strPos[0]),Double.parseDouble(strPos[1])));
		
		mongoRep.insert(mongItem);
		
		return item;
	}
	
	/**
	 * ��д������������ʵ����mongodb��ͬ��
	 */
	@Override
	protected StationStationInfo onSaveNew(StationStationInfo item) {
		item=this.rep.save(item);
		StationMongoEntry mongItem=new StationMongoEntry();
		
		mongItem.setStationId(item.getId());		
		mongItem.setName(item.getName());
		mongItem.setAddress(item.getAddress());

		String[] strPos = item.getPoint().split(",");
		mongItem.setPos(new Pos(Double.parseDouble(strPos[0]),Double.parseDouble(strPos[1])));
		
		mongoRep.save(mongItem);
		
		return item;
	}
	
	/**
	 * ����ָ����ĸ���������վ��
	 * @param pos
	 * @return
	 */
	public List<StationStationInfo>  listNearPosStations(double lon,double lat){
		var reList = this.mongoRep.findByPos( lon,lat);
		if(reList==null) return null;
		ArrayList<StationStationInfo> re=new ArrayList<>();
		
		return Flux.fromIterable(reList)
			.map(item->{
				var rItem=new StationStationInfo();
				rItem.setId(item.getStationId());
				rItem.setAddress(item.getAddress());
				rItem.setPoint(item.getPos().toString());
				rItem.setName(item.getName());
				return rItem;
			}).collectList().block()
		;
	}
	
	/**
	 * ȡ��վ���Ŀ��в�����
	 * @param id
	 * @return
	 */
	public int FreePlugerNumber(int id) {
		var device=QStationDevice.stationDevice;
		var station=QStationStationInfo.stationStationInfo;
		
		var item=factory.select(device.totalNumber.sum(),device.usingNumber.sum(),device.faultNumber.sum())
			   .from(device)
			   .leftJoin(station).on(device.stationId.eq(station.id))			   					   
			   .groupBy(station.id)
			   .where(station.id.eq(id))
			   .fetchOne();
		
		
		
		if(item==null) return 0;
	
		
		return item.get(device.totalNumber)-item.get(device.faultNumber)-item.get(device.usingNumber);
		
	}

}
