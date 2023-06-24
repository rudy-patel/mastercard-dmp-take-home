package com.api.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ExternalApiServiceImpl implements ExternalApiService {
    private final String apiUrl;

    public ExternalApiServiceImpl() {
        this.apiUrl = "https://www.random.org/integers/?num=7&min=0&max=12&col=1&base=10&format=plain&rnd=new";
    }

    @Override
    public List<Integer> fetchCardUsageCounts(long cardNum) {
        System.out.println("fetchCardUsageCounts: entry");
        List<Integer> cardUsageCounts = new ArrayList<>();

        try {
            System.out.println("fetchCardUsageCounts: fetching random value from external service");
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
                // TODO: maybe not sout? Handle error response
                System.out.println("fetchCardUsageCounts: Error: " + responseCode);
            }
            connection.disconnect();
            System.out.println("fetchCardUsageCounts: disconnecting from service");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("fetchCardUsageCounts: returning card usage counts: " + cardUsageCounts);
        return cardUsageCounts;
    }
}
