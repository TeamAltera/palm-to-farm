package smart_farm_oauth.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="plant_user")
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_code", nullable = false, updatable = false)
	private Long id;
	
	@Column(name = "email", nullable = false, unique = true)
	private String username;
	
	@Column(name = "pwd", nullable = false)
	private String password;
	
	//@Column(name = "enabled", nullable = false)
	//private boolean enabled;
}
