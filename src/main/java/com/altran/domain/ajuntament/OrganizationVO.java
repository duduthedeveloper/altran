package com.altran.domain.ajuntament;

public class OrganizationVO {

	private final String description;

	public OrganizationVO(final String description) {
		super();
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "OrganizationVO [description=" + description + "]";
	}

}
