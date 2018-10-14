package com.altran.domain.ajuntament;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AjuntamentResponseMapper {

	@JsonProperty("result")
	private Result result;

	public Result getResult() {
		return result;
	}

	public void setResult(final Result result) {
		this.result = result;
	}

	static class Result {
		@JsonProperty("results")
		private List<Results> results;

		public List<Results> getResults() {
			return results;
		}

		public void setResults(final List<Results> results) {
			this.results = results;
		}
	}

	static class Results {
		@JsonProperty("organization")
		private Organization organization;

		public Organization getOrganization() {
			return organization;
		}

		public void setOrganization(final Organization organization) {
			this.organization = organization;
		}

	}

	static class Organization {
		@JsonProperty("description")
		private String description;

		public String getDescription() {
			return description;
		}

		public void setDescription(final String description) {
			this.description = description;
		}

		@Override
		public String toString() {
			return "Organization [description=" + description + "]";
		}

	}

}
