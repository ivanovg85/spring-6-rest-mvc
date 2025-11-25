package art.cookedincode.spring6restmvc.services;

import art.cookedincode.spring6restmvc.model.BeerCSVRecord;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Georgi Ivanov
 */
@Service
public class BeerCsvServiceImpl implements BeerCsvService {
    @Override
    public List<BeerCSVRecord> convertCSV(InputStream csvInputStream) {
        return new CsvToBeanBuilder<BeerCSVRecord>(new InputStreamReader(csvInputStream))
                .withType(BeerCSVRecord.class)
                .build()
                .parse();
    }
}
