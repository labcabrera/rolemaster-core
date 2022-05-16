package org.labcabrera.rolemaster.core.data;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.character.Language;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.repository.LanguageRepository;
import org.labcabrera.rolemaster.core.repository.RaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CheckRaceLanguagesTest {

	private static final Logger log = LoggerFactory.getLogger(CheckRaceLanguagesTest.class);

	@Autowired
	private RaceRepository raceRepository;

	@Autowired
	private LanguageRepository languageRepository;

	@Test
	void test() {
		List<Race> races = raceRepository.findAll().collectList().share().block();
		List<Language> languages = languageRepository.findAll().collectList().share().block();
		List<String> errors = new ArrayList<>();
		races.stream().filter(e -> e.getStartingLanguageSelection() != null).forEach(race -> {
			race.getStartingLanguageSelection().stream().forEach(language -> checkLanguage(race.getId(), language, languages, errors));
		});
		errors.stream().forEach(e -> log.error(e));
		assertTrue(errors.isEmpty());
	}

	private void checkLanguage(String raceId, String language, List<Language> languages, List<String> errors) {
		Pattern pattern = Pattern.compile("([\\w-]+):s(\\d+):w(\\d+)");
		Matcher matcher = pattern.matcher(language);
		if (!matcher.matches()) {
			errors.add(String.format("Invalid pattern in race %s: '%s'", raceId, language));
			return;
		}
		String name = matcher.group(1);
		if (languages.stream().filter(e -> e.getId().equals(name)).count() != 1) {
			errors.add(String.format("Invalid language in race %s: '%s'", raceId, language));
		}
	}

}
