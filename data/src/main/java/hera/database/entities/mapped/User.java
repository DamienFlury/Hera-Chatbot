package hera.database.entities.mapped;

import hera.database.entities.persistence.UserPO;

public class User implements IMappedEntity<UserPO> {

	public static final String NAME = "User";

	private Long snowflake;

	public User() {
	}

	public User(Long snowflake) {
		this.snowflake = snowflake;
	}

	public UserPO mapToPO() {
		return new UserPO(
				this.snowflake
		);
	}

	public Long getSnowflake() {
		return snowflake;
	}

	public void setSnowflake(Long snowflake) {
		this.snowflake = snowflake;
	}
}
