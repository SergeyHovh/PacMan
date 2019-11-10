package com.company.Search;

import com.company.Helpers.Cell;
import com.company.Models.Entity;

import java.util.TreeMap;
import java.util.Vector;

public interface State {
	TreeMap<Cell, Vector<Cell>> getApplicableActions();
	State getActionResult(Action action);
	boolean equals(Object that);
	int hashCode();
}
