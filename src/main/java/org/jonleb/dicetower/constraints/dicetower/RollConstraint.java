package org.jonleb.dicetower.constraints.dicetower;

import org.jonleb.dicetower.services.RollType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RollValidator.class)
public @interface RollConstraint {
    String message() default "{dicetower.wrongusage}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    RollType[] rollTypes();
}
