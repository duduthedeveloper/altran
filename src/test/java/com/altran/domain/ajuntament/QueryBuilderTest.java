package com.altran.domain.ajuntament;

import org.junit.Assert;
import org.junit.Test;

public class QueryBuilderTest {

	@Test
	public void buildDefaultCatalunaBuilderQuery() {
		Assert.assertEquals("sort=relevance asc, metadata_modified desc&facet=true&include_drafts=false&include_private=false&use_default_schema=false", AjuntamentFactory.createQueryBuilder("CATALUNA", new AjuntamentFilterDecorator()).buildQuery());
	}

}
