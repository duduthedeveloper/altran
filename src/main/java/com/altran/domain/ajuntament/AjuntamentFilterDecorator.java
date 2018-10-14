package com.altran.domain.ajuntament;

import java.util.List;

import com.altran.infrastructure.QueryBuilder;

public class AjuntamentFilterDecorator implements QueryBuilder {

	enum BinaryAssertion {
		TRUE("true"), FALSE("false"), NULL("null");

		private final String binary;

		BinaryAssertion(final String binary) {
			this.binary = binary;
		}

		public String getBinaryValue() {
			return binary;
		}

		public static String getFacet(final String binary) {
			for (final BinaryAssertion f : BinaryAssertion.values()) {
				if (f.name().toUpperCase().equals(binary.toUpperCase())) {
					return f.getBinaryValue();
				}
			}
			return NULL.getBinaryValue();
		}

	}

	private String query;
	private String filterQueries;
	private String sort = "relevance asc, metadata_modified desc";
	private Integer rows;
	private Integer start;
	private String facet = BinaryAssertion.TRUE.name();
	// facet.mincount
	private Integer facetMinCount;
	// facet.limit
	private Integer facetLimit;
	// facet.field
	private List<String> facetFields;
	// include_drafts
	private String includeDrafts = BinaryAssertion.FALSE.name();
	// include_private
	private String includePrivate = BinaryAssertion.FALSE.name();
	// use_default_schema
	private String useDefaultSchema = BinaryAssertion.FALSE.name();

	public String getQuery() {
		return query;
	}

	public void setQuery(final String query) {
		this.query = query;
	}

	public String getFilterQueries() {
		return filterQueries;
	}

	public void setFilterQueries(final String filterQueries) {
		this.filterQueries = filterQueries;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(final String sort) {
		this.sort = sort;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(final Integer rows) {
		this.rows = rows;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(final Integer start) {
		this.start = start;
	}

	public String getFacet() {
		return facet;
	}

	public void setFacet(final String facet) {
		this.facet = facet;
	}

	public Integer getFacetMinCount() {
		return facetMinCount;
	}

	public void setFacetMinCount(final Integer facetMinCount) {
		this.facetMinCount = facetMinCount;
	}

	public Integer getFacetLimit() {
		return facetLimit;
	}

	public void setFacetLimit(final Integer facetLimit) {
		this.facetLimit = facetLimit;
	}

	public List<String> getFacetFields() {
		return facetFields;
	}

	public void setFacetFields(final List<String> facetFields) {
		this.facetFields = facetFields;
	}

	public String getIncludeDrafts() {
		return includeDrafts;
	}

	public void setIncludeDrafts(final String includeDrafts) {
		this.includeDrafts = includeDrafts;
	}

	public String getIncludePrivate() {
		return includePrivate;
	}

	public void setIncludePrivate(final String includePrivate) {
		this.includePrivate = includePrivate;
	}

	public String getUseDefaultSchema() {
		return useDefaultSchema;
	}

	public void setUseDefaultSchema(final String useDefaultSchema) {
		this.useDefaultSchema = useDefaultSchema;
	}

	@Override
	public String toString() {
		return "AjuntamentFilter [query=" + query + ", filterQueries=" + filterQueries + ", sort=" + sort + ", rows="
				+ rows + ", start=" + start + ", facet=" + facet + ", facetMinCount=" + facetMinCount + ", facetLimit="
				+ facetLimit + ", facetFields=" + facetFields + ", includeDrafts=" + includeDrafts + ", includePrivate="
				+ includePrivate + ", useDefaultSchema=" + useDefaultSchema + "]";
	}

	@Override
	public String buildQuery() {
		return AjuntamentFactory.createQueryBuilder("CATALUNA", this).buildQuery();
	}

}
