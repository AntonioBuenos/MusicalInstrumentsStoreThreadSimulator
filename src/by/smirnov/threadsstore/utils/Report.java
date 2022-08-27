package by.smirnov.threadsstore.utils;

import by.smirnov.threadsstore.entities.Cashier;
import by.smirnov.threadsstore.entities.Store;
import by.smirnov.threadsstore.repo.Wordings;

import java.util.StringJoiner;

import static java.lang.System.out;

public class Report {

    private Report() {
    }

    private static double getStoreRevenue(Store store) {
        double storeRevenue = 0.0;
        for (Cashier cashier : store.cashiers) {
            storeRevenue += cashier.revenue;
        }
        return storeRevenue;
    }

    public static void printReport(Store store){
        StringJoiner sj = new StringJoiner("");
        Cashier cashier = null;
        int cells = store.cashiers.size() + 2;
        int rows = cells / 5;
        if (cells % 5 != 0) rows = (cells / 5) + 1;
        sj.add(Wordings.TABLE_UPPER_FRAME);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 5; j++) {
                int index = (i*5)+j;
                if (index < store.cashiers.size()) cashier = store.cashiers.get(index);
                sj.add("║");
                if (index >= cells) sj.add(Wordings.TABLE_AD);
                else if (index == cells - 2) sj.add(String.format(Wordings.QUEUE_SIZE));
                else if (index == cells - 1) sj.add(String.format(Wordings.TOTAL_STORE_REVENUE));
                else sj.add(String.format(Wordings.REVENUE, cashier));
            }
            sj.add("║\n");
            for (int j = 0; j < 5; j++) {
                int index = (i*5)+j;
                if (index < store.cashiers.size()) cashier = store.cashiers.get(index);
                sj.add("║");
                if (index >= cells) sj.add(Wordings.TABLE_BLANK);
                else if (index == cells - 2) sj.add(String.format(" = %16d ", store.getStoreQueue().queue.size()));
                else if (index == cells - 1) sj.add(String.format(" = %,16.2f ", getStoreRevenue(store)));
                else sj.add(String.format(" = %,16.2f ", cashier.revenue));
            }
            sj.add("║\n");
            if (i + 1 < rows) sj.add(Wordings.TABLE_INTER_FRAME);
        }
        sj.add(Wordings.TABLE_LOWER_FRAME);
        out.println(sj);
    }
}

