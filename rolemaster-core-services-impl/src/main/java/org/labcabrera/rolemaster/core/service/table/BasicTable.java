package org.labcabrera.rolemaster.core.service.table;

public interface BasicTable<E, R> {

	R get(E e);

	Integer min();

	Integer max();

}
