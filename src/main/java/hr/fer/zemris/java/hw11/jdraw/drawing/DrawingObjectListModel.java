package hr.fer.zemris.java.hw11.jdraw.drawing;

import hr.fer.zemris.java.hw11.jdraw.geoobj.GeometricalObject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**List model for that adapts {@link DrawingModel} so it can be used as a model for a {@link JList}.
 * @author Jan Tomljanović
 */
public class DrawingObjectListModel implements ListModel<GeometricalObject>, DrawingModelListener {

	private DrawingModel model;
	private List<ListDataListener> listeners = new LinkedList<>();


	/**Constructs a new instance with given drawing model that will provide all necessary information.
	 * @param model that will be adapted to a list model
	 * @param listeners
	 * @throws IllegalArgumentException
	 */
	public DrawingObjectListModel(DrawingModel model, ListDataListener... listeners) {
		if (model == null) {
			throw new IllegalArgumentException();
		}
		this.model = model;
		if (listeners != null) {
			this.listeners = new LinkedList<>(Arrays.asList(listeners));
		}
		model.addDrawingModelListener(this);
	}
	
	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}
	
	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		for (ListDataListener l : listeners) {
			l.intervalAdded(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index0, index1));
		}
	}
	
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		for (ListDataListener l : listeners) {
			l.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, index0, index1));
		}
	}
	
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		for (ListDataListener l : listeners) {
			l.intervalRemoved(new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index0, index1));
		}
	}
	
	@Override
	public void addListDataListener(ListDataListener l) {
		if (!listeners.contains(l)) {
			listeners = new LinkedList<>(listeners);
			listeners.add(l);
		}
	}
	
	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners = new LinkedList<>(listeners);
		listeners.remove(l);
	}


}
