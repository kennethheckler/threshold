package com.kennethheckler.solutions.threshold.user;

import com.kennethheckler.solutions.threshold.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Following the MVC pattern, this Service class provides the logic and services required by the UserController to
 * fulfill the Rest API contract exposed by the application. This service follows good Domain Driven Design practices
 * by only handling User-specific service requests.
 *
 * @author Kenneth Heckler
 */
@Service
public class UserService {

    // The injected User repository providing the available commands and queries to be run against User data sources.
    private final UserRepository userRepo;

    /**
     * Required args constructor.
     * @param userRepo The injected User repository providing the available commands and queries to be run against User data sources.
     */
    UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Get users that have an approval percentage greater than the provided threshold.
     * @param threshold The approval percentage filter. Must be an integer value between 0 and 99, inclusive.
     * @return A new ThresholdResponse object containing the filtered user data.
     */
    ThresholdResponse getUsersGreaterThanThreshold(@NotNull @Min(0) @Max(99) Integer threshold) {
        List<ThresholdResponse.User> users;

        try {
            users = queryAndSortUsersAboveThreshold(threshold);
        } catch (Exception e) {

            // TODO: Throw an exception here that can be handled by the controller
            users = Collections.emptyList();
        }

        return new ThresholdResponse(threshold, users);
    }

    /*
     * Query the User repository for users that have an approval percentage greater than the provided threshold.
     * Sort any results from lowest to highest approval percentage;
     */
    private List<ThresholdResponse.User> queryAndSortUsersAboveThreshold(Integer threshold) {
        return userRepo.findByApprovedPercentGreaterThanThreshold(threshold).stream()
                .flatMap(resp -> resp.getData().stream())
                .map(ThresholdResponse.User::of)
                .sorted(Comparator.comparingDouble(ThresholdResponse.User::getApprovalRatePercentage))
                .collect(Collectors.toList());
    }
}
