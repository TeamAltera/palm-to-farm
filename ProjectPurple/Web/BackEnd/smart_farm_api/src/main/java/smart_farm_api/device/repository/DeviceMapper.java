package smart_farm_api.device.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import smart_farm_api.device.domain.APInfoDto;
import smart_farm_api.device.domain.SmartFarmInfoDto;

public interface DeviceMapper {
	List<APInfoDto> getAllAP(int userCode);
	
	APInfoDto getAP(String ip);
	
	List<SmartFarmInfoDto> getAllSmartPlant(int userCode);
	
	Object getSmartPlant(@Param("apCode") int apCode,@Param("stamp") int stamp);
	
	int insertAP(APInfoDto dto);
	
	void deleteAP(int apCode);
	
	void updateSFCount(int apCode);
	
	void insertSmartFarmDeviceList(@Param("deviceList") List<Map<String, Object>> deviceList, @Param("apCode") int apCode);
	
	void insertSmartFarmDevice(@Param("stamp") int stamp, @Param("sfCode") int sfCode, @Param("innerIp") String innerIp, @Param("apCode") int apCode);
	
	void deleteSmartFarmDevice(int stamp);
	
	int deleteSmartFarmAPAllDevice(int apCode);
	
	void updatePort(@Param("apCode") int apCode, @Param("stamp") int stamp, @Param("sfPort") int sfPort, @Param("pumpSt") char pumpSt);
	
	void updateMode(@Param("mode") char mode,@Param("apCode") int apCode,@Param("stamp") int stamp);
	
	void updateCooler(@Param("cooler1") char cooler1, @Param("cooler2") char cooler2, @Param("cooler3") char cooler3,
			@Param("apCode") int apCode,@Param("stamp") int stamp);
	
	void updateCoolerA(@Param("cooler1") char cooler1,@Param("apCode") int apCode,@Param("stamp") int stamp);
	
	void updateCoolerB(@Param("cooler2") char cooler2,@Param("apCode") int apCode,@Param("stamp") int stamp);
	
	void updateCoolerC(@Param("cooler3") char cooler3,@Param("apCode") int apCode,@Param("stamp") int stamp);
	
	void updateLED(@Param("led21") char led21, @Param("led22") char led22, @Param("led31") char led31, @Param("led32") char led32,
			@Param("apCode") int apCode,@Param("stamp") int stamp);
	
	void update2floorLED(@Param("led21") char led21, @Param("led22") char led22,
			@Param("apCode") int apCode,@Param("stamp") int stamp);
	
	void update3floorLED(@Param("led31") char led31, @Param("led32") char led32,
			@Param("apCode") int apCode,@Param("stamp") int stamp);
	
	void update2floorALED(@Param("led21") char led21, 
			@Param("apCode") int apCode,@Param("stamp") int stamp);
	
	void update2floorBLED(@Param("led22") char led22,
			@Param("apCode") int apCode,@Param("stamp") int stamp);
	
	void update3floorALED(@Param("led31") char led31,
			@Param("apCode") int apCode,@Param("stamp") int stamp);
	
	void update3floorBLED(@Param("led32") char led32,
			@Param("apCode") int apCode,@Param("stamp") int stamp);
	
	void changeInnerIp(@Param("before") int before, @Param("after") int after, @Param("afterIp") String afterIp, @Param("stamp") int stamp, @Param("apCode") int apCode);
	
	String getApIp(int apCode);
	
	int getUserCodeOfAP(int apCode);
}
