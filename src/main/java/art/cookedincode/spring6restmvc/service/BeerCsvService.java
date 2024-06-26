package art.cookedincode.spring6restmvc.service;

import art.cookedincode.spring6restmvc.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCsvService {

    List<BeerCSVRecord> convertCSV(File csvFile);
}
