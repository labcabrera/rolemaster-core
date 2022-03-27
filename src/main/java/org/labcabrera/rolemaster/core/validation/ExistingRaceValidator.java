package org.labcabrera.rolemaster.core.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.labcabrera.rolemaster.core.repository.RaceRepository;
import org.labcabrera.rolemaster.core.validation.ExistingRaceValidator.ExistingRace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class ExistingRaceValidator implements ConstraintValidator<ExistingRace, String> {

	@Target({ ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@Constraint(validatedBy = ExistingRaceValidator.class)
	public static @interface ExistingRace {

		String message() default "{validation.race.not-found}";

		Class<?>[] groups() default {};

		Class<? extends Payload>[] payload() default {};
	}

	@Autowired
	private RaceRepository repository;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		Mono<Boolean> mono = repository.existsById(value);
		boolean exists = mono.share().block();
		if (!exists) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("{validation.race.not-found}")
				.addConstraintViolation();
			return false;
		}
		return true;
	}

}
