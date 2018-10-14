package com.altran.domain.ajuntament;

import org.junit.Assert;
import org.junit.Test;

public class AjuntamentFactoryTest {

	@Test(expected = IllegalArgumentException.class)
	public void communityDoesNotExist() {
		try {
			AjuntamentFactory.createQueryBuilder("abc", new AjuntamentFilterDecorator());
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("Community does not exist.", e.getLocalizedMessage());
			throw e;
		}
	}

	@Test
	public void communityExists() {
		Assert.assertTrue(AjuntamentFactory.createQueryBuilder("CATALUNA",
				new AjuntamentFilterDecorator()) instanceof CatalunaQueryBuilder);
	}

}
