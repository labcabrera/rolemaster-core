package org.labcabrera.rolemaster.core.table;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import org.labcabrera.rolemaster.core.model.Range;
import org.labcabrera.rolemaster.core.model.exception.DataConsistenceException;
import org.springframework.stereotype.Component;

import com.mongodb.Function;

@Component
public class Table2DIntRangeLoader {

	public <E> Table2DIntRange<E> load(InputStream in, String separator, Function<String, E> converter) {
		Map<Range<Integer>, E> map = new LinkedHashMap<>();
		try (
			Scanner scanner = new Scanner(in);) {
			scanner.nextLine();
			while (scanner.hasNextLine()) {
				String nextLine = scanner.nextLine();
				String[] x = nextLine.split(",");
				Range<Integer> range = parseRange(x[0], "-");
				for (int i = 1; i < x.length; i++) {
					map.put(range, converter.apply(x[i]));
				}
			}
		}
		catch (Exception ex) {
			throw new DataConsistenceException("Error reading table.", ex);
		}
		return new Table2DIntRange<>(map);
	}

	protected Range<Integer> parseRange(String value, String separator) {
		if (value.startsWith("<")) {
			int i = Integer.parseInt(value.substring(1)) - 1;
			return Range.<Integer>builder().max(i).build();
		}
		else if (value.startsWith(">")) {
			int i = Integer.parseInt(value.substring(1)) + 1;
			return Range.<Integer>builder().min(i).build();
		}
		String[] tmp = value.split(separator);
		int min = Integer.parseInt(tmp[0]);
		int max = Integer.parseInt(tmp[1]);
		return Range.<Integer>builder().min(min).max(max).build();
	}

}
