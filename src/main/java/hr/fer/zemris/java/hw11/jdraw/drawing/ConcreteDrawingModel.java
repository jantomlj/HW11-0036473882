package hr.fer.zemris.java.hw11.jdraw.drawing;

import hr.fer.zemris.java.hw11.jdraw.geoobj.GeometricalObject;
import hr.fer.zemris.java.hw11.jdraw.geoobj.GeometricalObjectListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class that implements {@link DrawingModel} interface that is designed to be the source for
 * {@link JDrawingCanvas}. Implements {@link GeometricalObjectListener} as well because it
 * contains different objects and needs to be alarmed if one of them changes
 * 
 * @author Jan Tomljanović
 */
public class ConcreteDrawingModel implements DrawingModel, GeometricalObjectListener {

	private List<GeometricalObject> objects = new ArrayList<>();
	private List<DrawingModelListener> listeners = new LinkedList<>();

	private boolean saved;

	/**Creates a new instance
	 * @param listeners that will be added
	 */
	public ConcreteDrawingModel(DrawingModelListener... listeners) {
		if (listeners != null) {
			this.listeners = new LinkedList<>(Arrays.asList(listeners));
		}
		saved = false;
	}

	@Override
	public void add(GeometricalObject object) {
		int index = objects.size();
		objects.add(object);
		object.addGeometricalObjectListener(this);
		saved = false;
		fireAddedSignal(index, index);
	}

	@Override
	public GeometricalObject getObject(int index) {
		if (index < 0 || objects.size() <= index) {
			throw new IllegalArgumentException();
		}
		return objects.get(index);
	}

	@Override
	public int getSize() {
		return objects.size();
	}

	/**Removes the object on a given index from the list
	 * @param index of the object to be removed
	 * @throws IllegalArgumentException if index is invalid
	 */
	public void remove(int index) {
		if (index < 0 || objects.size() <= index) {
			throw new IllegalArgumentException();
		}
		objects.get(index).removeGeometricalObjectListener(this);
		objects.remove(index);
		saved = false;
		fireRemovedSignal(index, index);
	}


	/**Removes all occurences of the given object from the list.
	 * @param o
	 */
	public void remove(GeometricalObject o) {
		while (true) {
			int index = objects.indexOf(o);
			if (index == -1) {
				break;
			}
			o.removeGeometricalObjectListener(this);
			objects.remove(o);
			saved = false;
			fireRemovedSignal(index, index);
		}
	}

	/**Delets all objects from the list.
	 */
	public void clear() {
		if (!objects.isEmpty()) {
			int index = objects.size() - 1;
			objects.clear();
			saved = false;
			fireRemovedSignal(0, index);
		}
	}

	private void fireAddedSignal(int index0, int index1) {
		for (DrawingModelListener listener : listeners) {
			listener.objectsAdded(this, index0, index1);
		}
	}

	private void fireRemovedSignal(int index0, int index1) {
		for (DrawingModelListener listener : listeners) {
			listener.objectsRemoved(this, index0, index1);
		}
	}

	private void fireChangedSignal(int index0, int index1) {
		for (DrawingModelListener listener : listeners) {
			listener.objectsChanged(this, index0, index1);
		}
	}


	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		if (!listeners.contains(l)) {
			listeners = new LinkedList<>(listeners);
			listeners.add(l);
		}
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners = new LinkedList<>(listeners);
		listeners.remove(l);
	}

	@Override
	public void gemetricalObjectChanged(GeometricalObject source) {
		int index = objects.indexOf(source);
		if (index == -1) {
			return;
		}
		saved = false;
		fireChangedSignal(index, index);
	}

	/**Method that can set if the current state of the drawing model is saved
	 * @param saved
	 */
	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	/**Is this state saved
	 * @return wheather the state is saved
	 */
	public boolean isSaved() {
		return saved;
	}
}
