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
        System.out.println("externalApiService: entry");
        List<Integer> cardUsageCounts = new ArrayList<>();

        try {
            System.out.println("externalApiService: connecting to service");
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
                System.out.println("Error: " + responseCode);
            }
            connection.disconnect();
            System.out.println("externalApiService: disconnecting from service");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("externalApiService: returning card usage counts: " + cardUsageCounts);
        return cardUsageCounts;
    }
}
