package org.vaadin.artur.gamecard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.vaadin.artur.gamecard.data.CardInfo;
import org.vaadin.artur.gamecard.event.ClickEventWithTargetElement;
import org.vaadin.artur.gamecard.event.DoubleClickEventWithTargetElement;
import org.vaadin.artur.gamecard.event.HasClickListenersWithTarget;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.shared.Registration;

/**
 * A card stack shows 0-N cards on top of each other but offset so that you can
 * see the rank and suite of all cards which are face up.
 */
public class CardStack extends Composite<Div>
        implements HasClickListenersWithTarget<CardStack, Card> {
    private int OFFSET = Card.HEIGHT / 6;
    private List<Card> cards = new ArrayList<>();
    private Map<Card, Registration> cardClickListeners = new HashMap<>();
    private Map<Card, Registration> cardDoubleClickListeners = new HashMap<>();

    public CardStack() {
        getElement().getStyle().set("width", Card.WIDTH + "px");
        getElement().getStyle().set("display", "inline-block");
        getElement().getStyle().set("position", "relative");
        getElement().addEventListener("click", e -> {
            if (e.getEventData().getBoolean("event.target == element")) {
                fireEvent(new ClickEventWithTargetElement<>(CardStack.this,
                        true, null));
            }
        }, "event.target == element");

        getElement().addEventListener("dblclick", e -> {
            if (e.getEventData().getBoolean("event.target == element")) {
                fireEvent(new DoubleClickEventWithTargetElement<>(
                        CardStack.this, true, null));
            }
        }, "event.target == element");

    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public Card getTopCard() {
        if (isEmpty()) {
            return null;
        }
        return cards.get(cards.size() - 1);
    }

    public int getNumberOfCards() {
        return cards.size();
    }

    public int getCardPosition(Card card) {
        return cards.indexOf(card);
    }

    public Card getCard(int i) {
        return cards.get(i);
    }

    public List<Card> getCardsOnTopOf(Card cardInfo) {
        List<Card> cardsAbove = new ArrayList<>();
        int cardIndex = getCardPosition(cardInfo);
        for (int aboveIndex = cardIndex
                + 1; aboveIndex < getNumberOfCards(); aboveIndex++) {
            cardsAbove.add(getCard(aboveIndex));
        }
        return cardsAbove;
    }

    public void addCard(Card card) {
        cards.add(card);
        cardClickListeners.put(card, card.addClickListener(e -> {
            fireEvent(new ClickEventWithTargetElement<>(CardStack.this,
                    e.isFromClient(), card));
        }));
        cardDoubleClickListeners.put(card, card.addDoubleClickListener(e -> {
            fireEvent(new DoubleClickEventWithTargetElement<>(CardStack.this,
                    e.isFromClient(), card));
        }));
        getContent().add(card);
        setCardTopOffset(cards.size() - 1);
        updateLayoutHeight();
    }

    private void updateLayoutHeight() {
        int h = Card.HEIGHT + (cards.size() - 1) * OFFSET;
        getElement().getStyle().set("height", h + "px");
    }

    public boolean removeCard(Card card) {
        int cardIndex = getCardPosition(card);
        if (cardIndex == -1) {
            return false;
        }

        cards.remove(card);
        cardClickListeners.remove(card).remove();
        cardDoubleClickListeners.remove(card).remove();
        getContent().remove(card);

        for (int i = cardIndex; i < getNumberOfCards(); i++) {
            setCardTopOffset(i);
        }

        updateLayoutHeight();
        return true;
    }

    private void setCardTopOffset(int cardIndex) {
        Card card = cards.get(cardIndex);
        int offset = cardIndex * OFFSET;
        card.getElement().getStyle().set("top", offset + "px");
        card.getElement().getStyle().set("position", "absolute");
    }

    public boolean removeCard(CardInfo card) {
        for (Card c : cards) {
            if (c.getCardInfo().equals(card)) {
                return removeCard(c);
            }
        }

        return false;
    }

    public void removeAllCards() {
        getContent().removeAll();
        cards.clear();
        cardClickListeners.forEach((card, reg) -> reg.remove());
        cardDoubleClickListeners.forEach((card, reg) -> reg.remove());
        cardClickListeners.clear();
        cardDoubleClickListeners.clear();
        updateLayoutHeight();
    }

    public void deselectAll() {
        for (Card c : cards) {
            c.setSelected(false);
        }
    }

    public Optional<Card> getFirstSelected() {
        List<Card> selected = getSelected();
        if (selected.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(selected.get(0));
        }

    }

    public List<Card> getSelected() {
        List<Card> selected = new ArrayList<>();

        for (Card c : cards) {
            if (c.isSelected()) {
                selected.add(c);
            }
        }

        return selected;
    }

    public boolean containsCard(CardInfo cardToFind) {
        for (Card card : cards) {
            if (cardToFind.equals(card.getCardInfo())) {
                return true;
            }
        }
        return false;
    }

}
