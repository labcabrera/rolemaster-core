package org.labcabrera.rolemaster.core.model;

import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.character.RankType;

public interface HasRanks {

	Map<RankType, Integer> getRanks();

	List<Integer> getDevelopmentCost();

}
