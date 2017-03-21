package com.eclipsercp.swtjface.view.listeners;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class WeakPersonGroupChangeListener implements Listener {

	private WeakReference<Listener> listenerRef;
	Object src;

	public WeakPersonGroupChangeListener(Listener listener, Object src) {
		listenerRef = new WeakReference(listener);
		this.src = src;
	}

	@Override
	public void handleEvent(Event event) {
		Listener listener = (Listener) listenerRef.get();
		if (listener == null) {
			removeListener();
		} else
			listener.handleEvent(event);

	}

	private void removeListener() {
		try {
			Method method = src.getClass().getMethod("remove", new Class[] { Listener.class });
			method.invoke(src, new Object[] { this });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
