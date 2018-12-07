package smart_farm_api.sensor.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorDataDto {
	private Timestamp d;
	private double t;
	private double h;
	private int e;
	private double wt;
	private double wl;
	private double ec;
	private double ph;
}
