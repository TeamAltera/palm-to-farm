package smart_farm_api.device.domain;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APInfoDto {
	private int apCode;
	private String apPublicIp;
	private String apSsid;
	private int userCode;
	private int apSfCnt;
	private Timestamp apRegDate;
	private List<SmartFarmInfoDto> plantDevices;
}
