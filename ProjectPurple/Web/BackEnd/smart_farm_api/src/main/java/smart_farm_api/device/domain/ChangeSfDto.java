package smart_farm_api.device.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangeSfDto {
	private int after;
	private int before;
	private int stamp;
	private int apCode;
}
