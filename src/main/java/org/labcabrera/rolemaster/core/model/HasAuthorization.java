package org.labcabrera.rolemaster.core.model;

import java.util.List;

public interface HasAuthorization {

	String getOwner();

	void setOwner(String owner);

	List<String> getAuthorization();

	void setAuthorization(List<String> permissions);

}