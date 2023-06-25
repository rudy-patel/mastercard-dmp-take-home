package com.api.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MonitoringStatsTest {

    @Test
    public void testGetTransactionCount() {
        MonitoringStats monitoringStats = new MonitoringStats();
        monitoringStats.setTransactionCount(10);

        int result = monitoringStats.getTransactionCount();

        Assertions.assertEquals(10, result);
    }

    @Test
    public void testSetTransactionCount() {
        MonitoringStats monitoringStats = new MonitoringStats();

        monitoringStats.setTransactionCount(5);

        Assertions.assertEquals(5, monitoringStats.getTransactionCount());
    }

    @Test
    public void testGetTotalTransactionAmount() {
        MonitoringStats monitoringStats = new MonitoringStats();
        monitoringStats.setTotalTransactionAmount(1000.50);

        double result = monitoringStats.getTotalTransactionAmount();

        Assertions.assertEquals(1000.50, result);
    }

    @Test
    public void testSetTotalTransactionAmount() {
        MonitoringStats monitoringStats = new MonitoringStats();

        monitoringStats.setTotalTransactionAmount(500.75);

        Assertions.assertEquals(500.75, monitoringStats.getTotalTransactionAmount());
    }

    @Test
    public void testGetPercentageApproved() {
        MonitoringStats monitoringStats = new MonitoringStats();
        monitoringStats.setPercentageApproved(90.2);

        double result = monitoringStats.getPercentageApproved();

        Assertions.assertEquals(90.2, result);
    }

    @Test
    public void testSetPercentageApproved() {
        MonitoringStats monitoringStats = new MonitoringStats();

        monitoringStats.setPercentageApproved(90.2);

        Assertions.assertEquals(90.2, monitoringStats.getPercentageApproved());
    }
}
