package com.company.Search;

import com.company.Helpers.Cell;

import java.util.Map;
import java.util.Vector;

public interface State {
	Vector<Cell> getApplicableActions(Action agent);

	State getActionResult(Action action, Action agent);

	boolean equals(Object that);

	int hashCode();
}
