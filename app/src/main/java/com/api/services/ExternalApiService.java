package com.api.services;

import java.util.List;

public interface ExternalApiService {
    List<Integer> fetchCardUsageCounts(long cardNum);
}
