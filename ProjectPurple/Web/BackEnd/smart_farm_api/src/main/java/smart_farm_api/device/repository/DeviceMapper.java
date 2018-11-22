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
	
	Object getSmartPlant(@Param("apCode") int apCode,@Param("sfCode") int sfCode);
	
	int insertAP(APInfoDto dto);
	
	void deleteAP(int apCode);
	
	void updateSFCount(int apCode);
	
	void insertSmartFarmDeviceList(@Param("deviceList") List<Map<String, Object>> deviceList, @Param("apCode") int apCode);
	
	void insertSmartFarmDevice(@Param("sfCode") int sfCode, @Param("innerIp") String innerIp, @Param("apCode") int apCode);
	
	void deleteSmartFarmDevice(int sfCode);
	
	int deleteSmartFarmAPAllDevice(int apCode);
	
	void updatePort(@Param("apCode") int apCode, @Param("sfCode") int sfCode, @Param("sfPort") int sfPort, @Param("pumpSt") char pumpSt);
	
	void updateMode(@Param("mode") char mode,@Param("apCode") int apCode,@Param("sfCode") int sfCode);
	
	void updateCooler(@Param("cooler") char cooler,@Param("apCode") int apCode,@Param("sfCode") int sfCode);
	
	void updateLED(@Param("led") char led,@Param("apCode") int apCode,@Param("sfCode") int sfCode);
	
	String getApIp(int apCode);
	
	int getUserCodeOfAP(int apCode);
}
