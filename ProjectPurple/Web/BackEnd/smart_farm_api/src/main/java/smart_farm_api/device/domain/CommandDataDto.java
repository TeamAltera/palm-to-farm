package smart_farm_api.device.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import smart_farm_api.plant.domain.PortStatusDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandDataDto {
	private int sfPort;
	private int plant;
	private List<List<PortStatusDto>> farm;
}
