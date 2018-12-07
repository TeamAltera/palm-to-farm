package smart_farm_api.log.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceLogDto {
	private Timestamp usedDate;
	private int stamp;
	private int apCode;
	private String usedIp; //명령을 내린 사용자Ip주소
	private String actName;
	private char result;
}
