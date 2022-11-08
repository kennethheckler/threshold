package com.kennethheckler.solutions.threshold.user.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the UserRepository.
 *
 * @author Kenneth Heckler
 * @see UserRepository
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    // The injected User data source
    private final UserDataSource userDS;

    /**
     * Required args constructor.
     * @param userDS The injected User data source
     */
    public UserRepositoryImpl(UserDataSource userDS) {
        this.userDS = userDS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserResponse> findByApprovedPercentGreaterThanThreshold(int threshold) {
        int page = 1;
        int totalPages = 1;
        List<UserResponse> responseSet = new ArrayList<>();

        // Have to make several calls to the data source to get all the pages of users
        // Obviously, you would not want to do this for data sources that have 100s of pages
        // TODO: Split this into concurrent calls
        try {
            while (page <= totalPages) {
                UserResponse response = userDS.read(page++);
                totalPages = response.getTotalPages();

                response.getData().removeIf(data -> threshold >= data.getApprovalPercentage());
                responseSet.add(response);
            }

            return responseSet;
        } catch (DataSourceException e) {
            return Collections.emptyList();
        }
    }
}
