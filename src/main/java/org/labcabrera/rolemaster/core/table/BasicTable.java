package org.labcabrera.rolemaster.core.table;

public interface BasicTable<E, R> {

	R get(E e);

	Integer min();

	Integer max();

}
