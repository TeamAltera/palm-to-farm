package smart_farm_api.device.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandDto {
	private String middle; //공유기의 주소
	private String dest; //명령어가 도착해야 되는 목적지
	private int cmd; //명령어의 종류
	private int stamp;
	private int apCode;
	private String usedIp;
	private CommandDataDto optData;	
}
