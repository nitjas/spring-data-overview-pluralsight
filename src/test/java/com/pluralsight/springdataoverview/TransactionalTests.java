package com.pluralsight.springdataoverview;

import com.pluralsight.springdataoverview.entity.Flight;
import com.pluralsight.springdataoverview.repository.FlightRepository;
import com.pluralsight.springdataoverview.service.FlightsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest // we'll be creating some non-JPA related beans
public class TransactionalTests {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightsService flightsService;

    @Before
    public void setUp() {
        flightRepository.deleteAll();
    }

    @Test
    public void shouldNotRollBackWhenTheresNoTransaction() {
        try {
            flightsService.saveFlight(new Flight());
        } catch (Exception e) {
            // Do nothing
        } finally {
            assertThat(flightRepository.findAll()).isNotEmpty();
        }
    }

    @Test
    public void shouldRollBackWhenTheresTransaction() {
        try {
            flightsService.saveFlightTransactional(new Flight());
        } catch (Exception e) {
            // Do nothing
        } finally {
            assertThat(flightRepository.findAll()).isEmpty();
        }
    }

}
