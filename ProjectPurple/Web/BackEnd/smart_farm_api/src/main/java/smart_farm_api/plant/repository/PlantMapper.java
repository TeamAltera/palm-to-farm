package smart_farm_api.plant.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import smart_farm_api.plant.domain.PlantDto;

public interface PlantMapper {
	PlantDto getPlantInfo(@Param("apCode") int apCode, @Param("sfCode") int sfCode);
	
	void insertGrowthPlant(@Param("apCode") int apCode, @Param("sfCode") int sfCode, 
			@Param("plantCode") int plantCode);
	
	void deleteGrowthPlant(@Param("apCode") int apCode, @Param("sfCode") int sfCode);
	
	void deletePortInfo(@Param("apCode") int apCode,@Param("sfCode") int sfCode);
	
	void insertPortInfo(@Param("apCode") int apCode, @Param("sfCode") int sfCode, 
			@Param("portList") List<Map<String, Object>> portList);
	
	List<Integer> getPortInfo(@Param("apCode") int apCode,@Param("sfCode")  int sfCode);
}
