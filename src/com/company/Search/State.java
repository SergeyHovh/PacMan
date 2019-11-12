package com.company.Search;

import com.company.Helpers.Cell;

import java.util.Map;
import java.util.Vector;

public interface State {
	Map<Cell, Vector<Cell>> getApplicableActions();

	State getActionResult(Action action);

	boolean equals(Object that);

	int hashCode();
}
