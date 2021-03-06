package hera.database.entities.mapped;

import hera.database.entities.persistence.RolePO;

public class Role implements IMappedEntity<RolePO> {

	public static final String NAME = "Role";

	private int id;

	private Long guild;

	private int parent;

	private String name;

	private String description;

	public Role() {
	}

	public Role(Long guild, int parent, String name, String description) {
		this.guild = guild;
		this.parent = parent;
		this.name = name;
		this.description = description;
	}

	public Role(int id, Long guild, int parent, String name, String description) {
		this.id = id;
		this.guild = guild;
		this.parent = parent;
		this.name = name;
		this.description = description;
	}

	public RolePO mapToPO() {
		return new RolePO(
				this.guild,
				this.parent,
				this.name,
				this.description
		);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getGuild() {
		return guild;
	}

	public void setGuild(Long guild) {
		this.guild = guild;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
