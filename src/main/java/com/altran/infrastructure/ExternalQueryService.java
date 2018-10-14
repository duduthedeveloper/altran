package com.altran.infrastructure;

import reactor.core.publisher.Flux;

public interface ExternalQueryService<T> {

	Flux<T> searchOnExternalAPI(final QueryBuilder queryBuilder);

}
