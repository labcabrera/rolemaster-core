package org.labcabrera.rolemaster.core.table.maneuver.m;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDificulty;
import org.labcabrera.rolemaster.core.table.BasicTable;
import org.labcabrera.rolemaster.core.table.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MovingManeuverTable implements Table<ManeuverDificulty, Integer, MovingManeuverEntry> {

	private final Map<ManeuverDificulty, BasicTable<Integer, MovingManeuverEntry>> map = new HashMap<>();

	@Autowired
	private ApplicationContext context;

	@PostConstruct
	public void loadTableData() {
		map.put(ManeuverDificulty.ROUTINE, context.getBean(MovingManeuverTableRoutine.class));
	}

	@Override
	public MovingManeuverEntry get(ManeuverDificulty column, Integer row) {
		return map.get(column).get(row);
	}

}
