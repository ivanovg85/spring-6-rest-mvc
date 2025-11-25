package art.cookedincode.spring6restmvc.services;

import art.cookedincode.spring6restmvc.model.BeerCSVRecord;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by Georgi Ivanov
 */
class BeerCsvServiceImplTest {

    BeerCsvService beerCsvService = new BeerCsvServiceImpl();

    @Test
    void convertCSV() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("csvdata/beers.csv");

        List<BeerCSVRecord> records = beerCsvService.convertCSV(inputStream);

        System.out.println(records.size());

        assertThat(records.size()).isGreaterThan(0);
    }
}