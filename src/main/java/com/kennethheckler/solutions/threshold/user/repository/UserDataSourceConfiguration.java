package com.kennethheckler.solutions.threshold.user.repository;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * Type-safe configuration properties bean for Data Sources. Spring will automatically bind any externalized properties
 * that either start with datasource prefix or provided under the datasource section of the application's YAML file
 * to the JavaBean properties in this file.
 *
 * @author Kenneth Heckler
 * @see org.springframework.boot.context.properties.ConfigurationProperties
 */
@ConfigurationProperties(prefix = "userdatasource")
@ConfigurationPropertiesScan
@Validated
public class UserDataSourceConfiguration {

    // The data source's URI scheme, such as http, https, file
    private final String scheme;

    // The data source's host name
    private final String host;

    // The data source's URI path to the user endpoint
    private final String path;

    // The data source's page query parameter name
    private final String pageParam;

    /**
     * All args constructor.
     * @param scheme The data source's URI scheme, such as http, https, file. Defaults to "https"
     * @param host The data source's host name - required
     * @param path TThe data source's URI path to the user endpoint - required
     * @param pageParam TThe data source's page query parameter name. Defaults to "page"
     */
    @ConstructorBinding
    public UserDataSourceConfiguration(@DefaultValue("https") String scheme, @NotEmpty String host, @NotEmpty String path, @DefaultValue("page") String pageParam) {
        this.scheme = scheme;
        this.host = host;
        this.path = path;
        this.pageParam = pageParam;
    }

    public String getHost() {
        return host;
    }

    public String getPath() {
        return path;
    }

    public String getPageParam() {
        return pageParam;
    }

    public String getScheme() {
        return scheme;
    }
}
