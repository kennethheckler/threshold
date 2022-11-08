package com.kennethheckler.solutions.threshold.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Following the MVC pattern, this Controller class exposes the application's Rest API endpoints and handles basic HTTP
 * request and response tasks for those endpoints.
 *
 * @author Kenneth Heckler
 */
@RestController
public class UserController {

    // The injected service for handling all the logic for getting and filtering users.
    private UserService userService;

    /**
     * Required args constructor.
     * @param userService The injected service for handling all the logic for getting and filtering users
     */
    UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Exposes and handles HTTP GET requests to the /successful_user Rest endpoint. The endpoint requires a single
     * thresholdperc query parameter to filter out users who have an approval percentage less than or equal to the
     * provided thresholdperc value. Results are in JSON format and ordered from least to highest approval percentage.
     *
     * @param thresholdperc The approval percentage filter. Must be an integer value between 0 and 99, inclusive.
     * @return The list of users who have approval percentages greater than the provided threshold value.
     *
     * @see ThresholdResponse
     */
    @GetMapping("/successful_user")
    ThresholdResponse getSuccessfulUser(@RequestParam(value = "thresholdperc") @Min(0) @Max(99) Integer thresholdperc) {
        return userService.getUsersGreaterThanThreshold(thresholdperc);
    }
}
