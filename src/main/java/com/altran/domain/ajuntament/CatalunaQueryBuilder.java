package com.altran.domain.ajuntament;

import org.springframework.util.StringUtils;

import com.altran.domain.ajuntament.AjuntamentFilterDecorator.BinaryAssertion;
import com.altran.infrastructure.QueryBuilder;

public class CatalunaQueryBuilder implements QueryBuilder {

	private final AjuntamentFilterDecorator filter;

	public CatalunaQueryBuilder(final AjuntamentFilterDecorator filter) {
		this.filter = filter;
	}

	@Override
	public String buildQuery() {
		final StringBuilder query = new StringBuilder();
		if (!StringUtils.isEmpty(filter.getQuery())) {
			query.append("q=").append(filter.getQuery()).append("&");
		}
		if (!StringUtils.isEmpty(filter.getFilterQueries())) {
			query.append("fq=").append(filter.getFilterQueries()).append("&");
		}
		if (!StringUtils.isEmpty(filter.getSort())) {
			query.append("sort=").append(filter.getSort()).append("&");
		}
		if (filter.getRows() != null) {
			query.append("rows=").append(filter.getRows()).append("&");
		}
		if (filter.getStart() != null) {
			query.append("start=").append(filter.getStart()).append("&");
		}
		if (!StringUtils.isEmpty(filter.getFacet())) {
			query.append("facet=").append(BinaryAssertion.getFacet(filter.getFacet())).append("&");
		}
		if (!StringUtils.isEmpty(filter.getFacetMinCount())) {
			query.append("facet.mincount=").append(filter.getFacetMinCount()).append("&");
		}
		if (!StringUtils.isEmpty(filter.getFacetLimit())) {
			query.append("facet.limit=").append(filter.getFacetLimit()).append("&");
		}
		if (filter.getFacetFields() != null) {
			for (final String facetField : filter.getFacetFields()) {
				query.append("facet.field=").append(facetField).append("&");
			}
		}
		if (!StringUtils.isEmpty(filter.getIncludeDrafts())) {
			query.append("include_drafts=").append(BinaryAssertion.getFacet(filter.getIncludeDrafts())).append("&");
		}
		if (!StringUtils.isEmpty(filter.getIncludePrivate())) {
			query.append("include_private=").append(BinaryAssertion.getFacet(filter.getIncludePrivate())).append("&");
		}
		if (!StringUtils.isEmpty(filter.getUseDefaultSchema())) {
			query.append("use_default_schema=").append(BinaryAssertion.getFacet(filter.getUseDefaultSchema())).append("&");
		}
		return query.length() > 0 ? query.deleteCharAt(query.length() - 1).toString() : "";
	}



}
