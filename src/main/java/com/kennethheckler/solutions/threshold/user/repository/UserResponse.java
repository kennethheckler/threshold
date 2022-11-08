package com.kennethheckler.solutions.threshold.user.repository;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * DTO wrapper for the list of users from the user data source. This object will be deserialized from a JSON string.
 *
 * @author Kenneth Heckler
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
public class UserResponse implements Serializable {

    // The current page of the results
    private final Integer page;

    // The maximum number of users returned per page
    private final Integer perPage;

    // The total number of users on all pages of the result
    private final Integer total;

    // The total number of pages with results
    private final Integer totalPages;

    // An array of objects containing users returned on the request page
    private final List<Data> data;

    /**
     * All args constructor.
     * @param page The current page of the results
     * @param perPage The maximum number of users returned per page
     * @param total The total number of users on all pages of the result
     * @param totalPages The total number of pages with results
     * @param data An array of objects containing users returned on the request page
     */
    @JsonCreator
    public UserResponse(
            @JsonProperty("page") Integer page,
            @JsonProperty("per_page") Integer perPage,
            @JsonProperty("total") Integer total,
            @JsonProperty("total_pages") Integer totalPages,
            @JsonProperty("data") @NotNull List<Data> data) {
        this.page = page;
        this.perPage = perPage;
        this.total = total;
        this.totalPages = totalPages;
        this.data = data;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public List<Data> getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponse response = (UserResponse) o;
        return getPage().equals(response.getPage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPage());
    }

    /**
     * Inner class that represents a user, but called data by the user data source.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {

        // Unique ID of the user
        private final Integer id;

        // The username of the user
        private final String username;

        // The about information of the user
        private final String about;

        // Total number of articles submitted by the user
        private final Integer submitted;

        // The date and time of the last update to the record
        @JsonProperty("updated_at")
        private final Instant updatedAt;

        // The number of submitted articles that are approved
        @JsonProperty("submission_count")
        private final Integer submissionCount;

        // The total number of comments the user made
        @JsonProperty("comment_count")
        private final Integer commentCount;

        // The date and time when the record was created
        @JsonProperty("created_at")
        private final Long createdAt;

        /**
         * All args constructor.
         * @param id Unique ID of the user
         * @param username The username of the user
         * @param about The about information of the user
         * @param submitted Total number of articles submitted by the user
         * @param updatedAt The date and time of the last update to the record
         * @param submissionCount The number of submitted articles that are approved
         * @param commentCount The total number of comments the user made
         * @param createdAt he date and time when the record was created
         */
        @JsonCreator
        public Data(
                @JsonProperty("id") Integer id,
                @JsonProperty("username") String username,
                @JsonProperty("about") String about,
                @JsonProperty("submitted") Integer submitted,
                @JsonProperty("updated_at") Instant updatedAt,
                @JsonProperty("submission_count") Integer submissionCount,
                @JsonProperty("comment_count") Integer commentCount,
                @JsonProperty("created_at") Long createdAt) {
            this.id = id;
            this.username = username;
            this.about = about;
            this.submitted = submitted;
            this.updatedAt = updatedAt;
            this.submissionCount = submissionCount;
            this.commentCount = commentCount;
            this.createdAt = createdAt;
        }

        public Integer getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getAbout() {
            return about;
        }

        public Integer getSubmitted() {
            return submitted;
        }

        public Instant getUpdatedAt() {
            return updatedAt;
        }

        public Integer getSubmissionCount() {
            return submissionCount;
        }

        public Integer getCommentCount() {
            return commentCount;
        }

        public Long getCreatedAt() {
            return createdAt;
        }

        /**
         * Calculates this user's approval percentage.
         * @return The user's approval percentage
         */
        public double getApprovalPercentage() {
            double submit = (getSubmitted() == null || getSubmitted() < 0 ? 0 : getSubmitted());
            double count = (getSubmissionCount() == null || getSubmissionCount() < 0 ? 0 : getSubmissionCount());

            if ( (submit == 0) || (count > submit) ) {
                return Double.NaN;
            }

            return ((count / submit) * 100);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Data data = (Data) o;
            return getId().equals(data.getId());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId());
        }
    }
}
