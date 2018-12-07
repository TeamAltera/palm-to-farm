package smart_farm_api.plant.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantDto {
	private String plantName;
	private double optTemp;
	private double maxTemp;
	private double minTemp;
	private double minPh;
	private double maxPh;
	private double maxEc;
	private double minEc;
	private Timestamp farmingDate;
	private int plantCode;
}
