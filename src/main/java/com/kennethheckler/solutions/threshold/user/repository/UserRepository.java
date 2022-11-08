package com.kennethheckler.solutions.threshold.user.repository;

import org.springframework.stereotype.Repository;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Encapsulates commands and queues against user data collections.
 *
 * @author Kenneth Heckler
 */
@Repository
public interface UserRepository {

    /**
     * Queries the user repository for users whose approval percentage is above the given threshold value.
     * @param threshold The approval percentage filter. Must be an integer value between 0 and 99, inclusive.
     * @return Collection of response data wrapper objects returned from the repository's data source.
     */
    List<UserResponse> findByApprovedPercentGreaterThanThreshold(@Min(0) @Max(99) int threshold);
}
