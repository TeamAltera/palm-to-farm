package smart_farm_api.device.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmartFarmInfoDto {
	private int stamp;
	private int sfCode;
	private int sfPortCnt;
	private int floorCnt;
	private int coolerCnt;
	private char ledCtrlMode;
	private char cooler1St;
	private char cooler2St;
	private char cooler3St;
	private char led21St;
	private char led22St;
	private char led31St;
	private char led32St;
	private char pumpSt;
	private int apCode;
	private String innerIp;
	private Timestamp sfRegDate;
}
