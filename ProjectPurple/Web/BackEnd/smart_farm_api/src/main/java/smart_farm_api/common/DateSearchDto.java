package smart_farm_api.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateSearchDto {
	private String date; //조회하고자 하는 날짜(YYYY-MM-DD)
	private int stamp;//조회 하고자 하는 수경재배기 코드
	private int apCode;//조회 하고자 하는 공유기 코드
	private int userCode;//조회 하고자 하는 사용자 코드
}
