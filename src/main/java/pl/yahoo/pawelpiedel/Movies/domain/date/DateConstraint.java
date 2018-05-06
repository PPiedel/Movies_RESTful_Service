package pl.yahoo.pawelpiedel.Movies.domain.date;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateConstraint {
    String message() default "Invalid date time";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
