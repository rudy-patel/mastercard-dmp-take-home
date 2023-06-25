package com.api.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.naming.ServiceUnavailableException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExternalApiServiceImpl implements ExternalApiService {
    private static final Logger logger = LoggerFactory.getLogger(ExternalApiServiceImpl.class);

    private final String apiUrl;

    public ExternalApiServiceImpl() {
        this.apiUrl = "https://www.random.org/integers/?num=7&min=0&max=12&col=1&base=10&format=plain&rnd=new";
    }

    @Override
    public List<Integer> fetchCardUsageCounts(long cardNum) throws IOException, ServiceUnavailableException {
        List<Integer> cardUsageCounts = new ArrayList<>();

        try {
            logger.debug("Fetching card usage counts from external service");
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    int cardUsageCount = Integer.parseInt(line.trim());
                    cardUsageCounts.add(cardUsageCount);
                }
                reader.close();
            } else {
                String errorMessage = "Unable to make request to external service";
                ServiceUnavailableException e = new ServiceUnavailableException(errorMessage);
                logger.error(e.getMessage(), e);
                throw e;
            }
            connection.disconnect();
        } catch (IOException e) {
            logger.error("Error occurred while making a request to external service. URL: {}", apiUrl, e);
            throw e;
        }

        logger.debug("Finished fetching card usage counts: {}", cardUsageCounts);
        return cardUsageCounts;
    }
}
