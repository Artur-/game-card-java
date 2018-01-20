package org.vaadin.artur.gamecard.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public class ClickEventWithTargetElement<T extends Component, U extends Component>
        extends ComponentEvent<T> {
    private U target;

    public ClickEventWithTargetElement(T source, boolean fromClient, U target) {
        super(source, fromClient);
        this.target = target;
    }

    public U getTarget() {
        return target;
    }

}
