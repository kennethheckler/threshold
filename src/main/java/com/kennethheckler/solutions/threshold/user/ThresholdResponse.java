package com.kennethheckler.solutions.threshold.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kennethheckler.solutions.threshold.user.repository.UserResponse;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * DTO wrapper for the list of users that fit the threshold criteria. This object will be serialized to a JSON string
 * and returned in the body of the successful_user Rest endpoint HTTP response.
 *
 * @author Kenneth Heckler
 */
@Validated
public class ThresholdResponse implements Serializable {

    // The approval percentage threshold value passed in as the thresholdperc successful_user query parameter.
    private final Integer threshold;

    // The list of Users that exceed the approval percentage threshold value.
    private final List<User> users;

    /**
     * All args constructor.
     * @param threshold The approval percentage threshold value passed in as the thresholdperc successful_user query parameter.
     * @param users The list of Users that exceed the approval percentage threshold value.
     */
    @JsonCreator
    public ThresholdResponse(
            @JsonProperty("thresholdperc") Integer threshold,
            @JsonProperty("users") @NotNull List<User> users) {
        this.threshold = threshold;
        this.users = users;
    }

    public Integer getThreshold() {
        return threshold;
    }
    public List<User> getUsers() {
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThresholdResponse response = (ThresholdResponse) o;
        return getThreshold().equals(response.getThreshold()) && getUsers().equals(response.getUsers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getThreshold(), getUsers());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ThresholdResponse.class.getSimpleName() + "[", "]")
                .add("threshold=" + threshold)
                .add("users=" + users)
                .toString();
    }

    /**
     * Inner class that represents a user amd their submission record;
     */
    static class User {

        // The username of the user
        private final String username;

        // Total number of articles submitted by the user
        private final Integer submitted;

        // The number of submitted articles that are approved
        private final Integer submissionCount;

        // Calculated approval percentage
        private final Double approvalRatePercentage;

        /**
         * All args constructor.
         * @param username The username of the user
         * @param submitted Total number of articles submitted by the user
         * @param submissionCount The number of submitted articles that are approved
         * @param approvalRatePercentage Calculated approval percentage
         */
        @JsonCreator
        public User(
                @JsonProperty("username") String username,
                @JsonProperty("submitted") Integer submitted,
                @JsonProperty("submission_count") Integer submissionCount,
                @JsonProperty("approval_rate_perc") Double approvalRatePercentage) {
            this.username = username;
            this.submitted = submitted;
            this.submissionCount = submissionCount;
            this.approvalRatePercentage = approvalRatePercentage;
        }

        static User of(UserResponse.Data userData) {
            return new User(userData.getUsername(), userData.getSubmitted(),
                    userData.getSubmissionCount(), userData.getApprovalPercentage());
        }

        public String getUsername() {
            return (username == null || username.trim().isEmpty() ? "Name Not Provided" : username.trim());
        }

        public Integer getSubmitted() {
            return (submitted >= 0 ? submitted : 0);
        }

        public Integer getSubmissionCount() {
            return (submissionCount >= 0 ? submissionCount : 0);
        }

        public Double getApprovalRatePercentage() {
            String percentage = (approvalRatePercentage == null ? "0" : String.valueOf(approvalRatePercentage));
            return new BigDecimal(percentage)
                    .setScale(1, RoundingMode.HALF_UP)
                    .doubleValue();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return getUsername().equals(user.getUsername()) && getSubmitted().equals(user.getSubmitted()) && getSubmissionCount().equals(user.getSubmissionCount()) && getApprovalRatePercentage().equals(user.getApprovalRatePercentage());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getUsername(), getSubmitted(), getSubmissionCount(), getApprovalRatePercentage());
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                    .add("username='" + username + "'")
                    .add("submitted=" + submitted)
                    .add("submissionCount=" + submissionCount)
                    .add("approvalRatePercentage=" + approvalRatePercentage)
                    .toString();
        }
    }
}
