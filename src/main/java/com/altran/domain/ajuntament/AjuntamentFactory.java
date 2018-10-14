package com.altran.domain.ajuntament;

import com.altran.infrastructure.QueryBuilder;

public class AjuntamentFactory {

	enum Community {
		CATALUNA, NULL;

		public static Community getCommunityByName(final String community) {
			for (final Community value : Community.values()) {
				if (value.toString().equals(community.toUpperCase())) {
					return value;
				}
			}
			return NULL;
		}

	}

	public static QueryBuilder createQueryBuilder(final String community, final AjuntamentFilterDecorator filter) {
		switch (Community.getCommunityByName(community.toUpperCase())) {
		case CATALUNA:
			return new CatalunaQueryBuilder(filter);
		default:
			throw new IllegalArgumentException("Community does not exist.");
		}
	}

}
