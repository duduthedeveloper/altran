package com.altran.domain.ajuntament;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.altran.infrastructure.ExternalQueryService;
import com.altran.infrastructure.QueryBuilder;
import com.altran.infrastructure.exceptions.ExternalQueryException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Qualifier("ajuntamentQueryService")
public class AjuntamentQueryService implements ExternalQueryService<OrganizationVO> {

	@Value("${query.ajuntament.url}")
	private String externalClientUrl;

	private static Logger logger;

	@Autowired
	public AjuntamentQueryService(final Logger logger) {
		AjuntamentQueryService.logger = logger;
	}

	@Override
	public Flux<OrganizationVO> searchOnExternalAPI(final QueryBuilder queryBuilder) {
		if (externalClientUrl == null) {
			AjuntamentQueryService.logger.info("External URL cannot be null.");
			throw new IllegalArgumentException("External URL cannot be null.");
		}
		final String queryParams = queryBuilder.buildQuery();
		final String queryUrl = externalClientUrl.concat("?").concat(queryParams);
		AjuntamentQueryService.logger.info("Query from external URL {} service has been initialized.", queryUrl);
		final WebClient client = WebClient.create(queryUrl);
		final Mono<AjuntamentResponseMapper> ajuntamentVO = client.get().retrieve()
				.onStatus(HttpStatus::is4xxClientError,
						clientResponse -> Mono.error(new ExternalQueryException(clientResponse.statusCode())))
				.onStatus(HttpStatus::is5xxServerError,
						clientResponse -> Mono.error(new ExternalQueryException(clientResponse.statusCode())))
				.bodyToMono(AjuntamentResponseMapper.class);
		final Flux<OrganizationVO> organizations = ajuntamentVO.map(ajuntament -> ajuntament.getResult())
				.map(result -> result.getResults()).flux().flatMap(Flux::fromIterable)
				.map(results -> new OrganizationVO(results.getOrganization().getDescription()));
		AjuntamentQueryService.logger.info("Query has completed.");
		return organizations;
	}

}
