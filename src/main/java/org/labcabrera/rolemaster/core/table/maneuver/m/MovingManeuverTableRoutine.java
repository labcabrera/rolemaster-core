package org.labcabrera.rolemaster.core.table.maneuver.m;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.labcabrera.rolemaster.core.table.BasicTable;
import org.springframework.stereotype.Component;

@Component
public class MovingManeuverTableRoutine implements BasicTable<Integer, MovingManeuverEntry> {

	private Map<Integer, MovingManeuverEntry> map;

	private MovingManeuverTableRoutine() {
		map = new LinkedHashMap<>();
		loadTableData();
	}

	private void loadTableData() {
		IntStream.of(-201).forEach(e -> map.put(e, MovingManeuverEntry.builder()
			.text("Fall down. +2 hits. You are out 3 rounds.")
			.hp(-2)
			.unconscious(3)
			.build()));
		IntStream.of(-200, -151).forEach(e -> map.put(e, MovingManeuverEntry.builder()
			.text("Fail to act.")
			.build()));
		IntStream.of(-150, -51).forEach(e -> map.put(e, MovingManeuverEntry.builder().result(10).build()));
		IntStream.of(-50, -26).forEach(e -> map.put(e, MovingManeuverEntry.builder().result(30).build()));
		IntStream.of(-25, -0).forEach(e -> map.put(e, MovingManeuverEntry.builder().result(70).build()));
		IntStream.of(1, 20).forEach(e -> map.put(e, MovingManeuverEntry.builder().result(80).build()));
		IntStream.of(21, 40).forEach(e -> map.put(e, MovingManeuverEntry.builder().result(90).build()));
		IntStream.of(41, 95).forEach(e -> map.put(e, MovingManeuverEntry.builder().result(100).build()));
		IntStream.of(96, 115).forEach(e -> map.put(e, MovingManeuverEntry.builder().result(110).build()));
		IntStream.of(116, 135).forEach(e -> map.put(e, MovingManeuverEntry.builder().result(120).build()));
		IntStream.of(136, 155).forEach(e -> map.put(e, MovingManeuverEntry.builder().result(130).build()));
		IntStream.of(156, 185).forEach(e -> map.put(e, MovingManeuverEntry.builder().result(140).build()));
		IntStream.of(186, 275).forEach(e -> map.put(e, MovingManeuverEntry.builder().result(150).build()));
		IntStream.of(276).forEach(e -> map.put(e, MovingManeuverEntry.builder()
			.text("Incredible move. You feel great. Heal 3 hits")
			.hp(3)
			.build()));
	}

	@Override
	public MovingManeuverEntry get(Integer result) {
		int tmp = result;
		if (result < min()) {
			tmp = min();
		}
		else if (result > max()) {
			tmp = max();
		}
		return map.get(tmp);
	}

	@Override
	public Integer min() {
		return -201;
	}

	@Override
	public Integer max() {
		return 276;
	}

}
