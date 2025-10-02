package art.cookedincode.spring6restmvc.services;

import art.cookedincode.spring6restmvc.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

/**
 * Created by Georgi Ivanov
 */
public interface BeerCsvService {
    List<BeerCSVRecord> convertCSV(File csvFile);
}
