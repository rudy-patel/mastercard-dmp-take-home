package com.api.services;

import java.io.IOException;
import java.util.List;

import javax.naming.ServiceUnavailableException;

/**
 * Represents an external API service.
 */
public interface ExternalApiService {
    
    /**
     * Fetches the card usage counts for a given card number.
     *
     * @param cardNum The card number for which to fetch the card usage counts.
     * @return A list of card usage counts.
     * @throws IOException                  If an error occurs while fetching the card usage counts.
     * @throws ServiceUnavailableException  If the external API service is unavailable.
     */
    List<Integer> fetchCardUsageCounts(long cardNum) throws IOException, ServiceUnavailableException;
}
