package org.labcabrera.rolemaster.core.table.loader;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import org.labcabrera.rolemaster.core.model.Range;
import org.labcabrera.rolemaster.core.model.exception.DataConsistenceException;
import org.labcabrera.rolemaster.core.table.Table3DIntRange;
import org.springframework.stereotype.Component;

import com.mongodb.Function;

@Component
public class CsvTable3DIntRangeLoader {

	public <E> Table3DIntRange<E> load(InputStream in, String rangeSeparator, Function<String, E> converter) {
		Map<Range<Integer>, Map<Integer, E>> map = new LinkedHashMap<>();
		try (
			Scanner scanner = new Scanner(in);) {
			scanner.nextLine();
			while (scanner.hasNextLine()) {
				String nextLine = scanner.nextLine();
				String[] x = nextLine.split(",");
				Range<Integer> range = parseRange(x[0], rangeSeparator);
				Map<Integer, E> tmp = new LinkedHashMap<>();
				for (int i = 1; i < x.length; i++) {
					tmp.put(i, converter.apply(x[i]));
				}
				map.put(range, tmp);
			}
		}
		catch (Exception ex) {
			throw new DataConsistenceException("Error reading table.", ex);
		}
		return new Table3DIntRange<>(map);
	}

	private Range<Integer> parseRange(String value, String rangeSeparator) {
		if (value.startsWith("<")) {
			int i = Integer.parseInt(value.substring(1)) - 1;
			return Range.<Integer>builder().max(i).build();
		}
		else if (value.startsWith(">")) {
			int i = Integer.parseInt(value.substring(1)) + 1;
			return Range.<Integer>builder().min(i).build();
		}
		String[] tmp = value.split(rangeSeparator);
		int min = Integer.parseInt(tmp[0]);
		int max = Integer.parseInt(tmp[1]);
		return Range.<Integer>builder().min(min).max(max).build();
	}

}
