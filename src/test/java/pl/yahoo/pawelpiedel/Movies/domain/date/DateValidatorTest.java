package pl.yahoo.pawelpiedel.Movies.domain.date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class DateValidatorTest {
    @Autowired
    private DateValidator localDateTimeValidator;

    @MockBean
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void isValid_ValidDatePassed_TrueReturned() {
        //given
        String date = "1999-02-22";

        //when
        boolean isValid = localDateTimeValidator.isValid(date,constraintValidatorContext);

        //then
        assertTrue(isValid);
    }

    @Test
    public void isValid_ValidDate2Passed_TrueReturned() {
        //given
        String date = "2008-10-29";

        //when
        boolean isValid = localDateTimeValidator.isValid(date,constraintValidatorContext);

        //then
        assertTrue(isValid);
    }

    @Test
    public void isValid_DateWithMinutesPassed_FalseReturned() {
        //given
        String date = "1999-02-22T19:22";

        //when
        boolean isValid = localDateTimeValidator.isValid(date,constraintValidatorContext);

        //then
        assertFalse(isValid);
    }

    @Test
    public void isValid_DateWithSeconds_FalseReturned() {
        //given
        String date = "1999-02-22T19:22:00";

        //when
        boolean isValid = localDateTimeValidator.isValid(date,constraintValidatorContext);

        //then
        assertFalse(isValid);
    }

    @TestConfiguration
    static class LocalDateTImeValidatorTest {
        @Bean
        DateValidator localDateTimeValidator() {
            return new DateValidator();
        }
    }

}