package org.labcabrera.rolemaster.core.service.table;

public interface Table<C, R, E> {

	E get(C column, R row);

}
