package org.vaadin.artur.gamecard.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentEventNotifier;
import com.vaadin.flow.shared.Registration;

/**
 * Mixin interface to handle click events on components.
 *
 * @param <T>
 *            the type of the component
 */
public interface HasClickListenersWithTarget<T extends Component, U extends Component>
        extends ComponentEventNotifier {

    /**
     * Add a listener to click DOM events.
     *
     * @param listener
     *            The click listener.
     * @return A registration that can be used to unregister the listener.
     * @see <a href=
     *      "https://developer.mozilla.org/en-US/docs/Web/Events/click">click
     *      event at MDN</a>
     */
    default Registration addClickListener(
            ComponentEventListener<ClickEventWithTargetElement<T, U>> listener) {
        return addListener(ClickEventWithTargetElement.class,
                (ComponentEventListener) listener);
    }

    /**
     * Add a listener to double click DOM events.
     *
     * @param listener
     *            The click listener.
     * @return A registration that can be used to unregister the listener.
     * @see <a href=
     *      "https://developer.mozilla.org/en-US/docs/Web/Events/click">click
     *      event at MDN</a>
     */
    default Registration addDoubleClickListener(
            ComponentEventListener<DoubleClickEventWithTargetElement<T, U>> listener) {
        return addListener(DoubleClickEventWithTargetElement.class,
                (ComponentEventListener) listener);
    }

}
