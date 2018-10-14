package com.altran.controllers;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.altran.configurations.Application;
import com.altran.domain.ajuntament.OrganizationVO;
import com.altran.infrastructure.ExternalQueryService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application_test.properties")
@AutoConfigureWebTestClient
public class AjuntamentControllerTest {

	private static final String BASE_URL = "http://localhost:9091/";
	private static final String SEARCH_BASE_URI = "/ajuntament/search?v=1";

	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	@Autowired
	private ApplicationContext context;

	private WebTestClient webTestClient;

	@Autowired
	@Qualifier("ajuntamentQueryService")
	private ExternalQueryService<OrganizationVO> externalQueryService;

	@Value("${query.ajuntament.url}")
	private String externalClientUrl;

	@Before
	public void setUp() {
		ReflectionTestUtils.setField(externalQueryService, "externalClientUrl", externalClientUrl);
		webTestClient = WebTestClient.bindToApplicationContext(context).configureClient().baseUrl(BASE_URL)
				.filter(documentationConfiguration(restDocumentation)).build();
	}

	@Test
	public void getOrganizationFromExternalResource() {
		final String queryParams = "query=SOLR_PARAM&filterQueries=SOLR_PARAM&sort=relevance asc, metadata_modified desc&rows=1000&"
				+ "start=0&facet=true&facetMinCount=0&facetLimit=100&facetFields=array&includeDrafts=true_false&"
				+ "includePrivate=false&useDefaultSchema=fase";
		webTestClient.get().uri("ajuntament/search?v=1&".concat(queryParams)).exchange().expectStatus().isOk()
		.expectHeader().valueEquals("Content-Type", "application/json;charset=UTF-8").expectBody()
		.jsonPath("$[0].description").isEqualTo("Society and Welfare").consumeWith(
				document("ajuntament", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
	};

	@Test
	public void getError5xxExternalUrlNotDeclared() {
		ReflectionTestUtils.setField(externalQueryService, "externalClientUrl", null);
		webTestClient.get().uri(SEARCH_BASE_URI).exchange().expectStatus().is5xxServerError().expectBody().consumeWith(
				document("ajuntament_server_error", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
	};

	@Test
	public void getError4xxFromExternalResource() {
		ReflectionTestUtils.setField(externalQueryService, "externalClientUrl",
				BASE_URL.concat(SEARCH_BASE_URI.concat("/blablabla")));
		webTestClient.get().uri(SEARCH_BASE_URI).exchange().expectStatus().is4xxClientError().expectBody().consumeWith(
				document("ajuntament_client_error", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
	};
}
