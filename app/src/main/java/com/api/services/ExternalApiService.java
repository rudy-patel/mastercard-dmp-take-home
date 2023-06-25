package com.api.services;

import java.io.IOException;
import java.util.List;

import javax.naming.ServiceUnavailableException;

public interface ExternalApiService {
    List<Integer> fetchCardUsageCounts(long cardNum) throws IOException, ServiceUnavailableException;
}
