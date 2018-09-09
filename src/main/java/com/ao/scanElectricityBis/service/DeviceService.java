package com.ao.scanElectricityBis.service;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ao.scanElectricityBis.base.ScanElectricityException;
import com.ao.scanElectricityBis.entity.QStationDevice;
import com.ao.scanElectricityBis.entity.StationDevice;
import com.ao.scanElectricityBis.repository.DeviceRepository;

/**
 * 设备服务
 * 
 * @author aohanhe
 *
 */
@Service
public class DeviceService extends BaseService<StationDevice, DeviceRepository> {
	@Autowired
	private EntityManager em;

	public DeviceService() {
		super(QStationDevice.stationDevice);
	}
	
	public void Test3() {
		
	}

	/**
	 * 通过ID取得对象
	 * 
	 * @param code
	 * @return
	 * @throws ScanElectricityException
	 */
	public StationDevice findItemByCode(String code) throws ScanElectricityException {
		var device = QStationDevice.stationDevice;

		return this.findAllItems(v -> {
			return v.where(device.code.eq(code));
		}).blockFirst();
	}
}
