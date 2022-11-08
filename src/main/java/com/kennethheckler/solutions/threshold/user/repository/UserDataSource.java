package com.kennethheckler.solutions.threshold.user.repository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.net.URI;

/**
 * Provides the application the ability to read data from the configured user data source.
 *
 * @author Kenneth Heckler
 */
@Component
public class UserDataSource {

    // Used to provide context to DataSourceException messages
    private static final String DS_NAME = "User DS";

    // Injected, type-safe, externalized data source configurations
    private final UserDataSourceConfiguration configuration;

    // Synchronous client used to perform HTTP requests
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Required args constructor.
     * @param configuration Injected, type-safe, externalized data source configurations
     */
    UserDataSource(UserDataSourceConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Read user data, grouped by pages, from the configured User Rest API data source
     * @param page The page number to retrieve
     * @return A response data wrapper of user data returned from the data source
     */
    UserResponse read(@Min(1) @Max(Integer.MAX_VALUE) int page) throws DataSourceException {
        URI sourceURI = new DefaultUriBuilderFactory().builder()
                .scheme(configuration.getScheme())
                .host(configuration.getHost())
                .path(configuration.getPath())
                .queryParam(configuration.getPageParam(), page)
                .build();

        ResponseEntity<UserResponse> response = restTemplate.getForEntity(sourceURI, UserResponse.class);
        handleHttpError(response);

        return response.getBody();
    }

    /*
     * Provides some basic handling of HTTP errors. It converts an HTTP error response to a DataSourceException so that
     * it may be handled by the application.
     */
    private void handleHttpError(ResponseEntity<UserResponse> response) throws DataSourceException {
        if (response.getStatusCode().isError()) {
            throw new DataSourceException(DS_NAME, "Service returned an HTTP error code!",
                    new HttpServerErrorException(response.getStatusCode(), response.getStatusCode().getReasonPhrase()));
        }
    }
}
