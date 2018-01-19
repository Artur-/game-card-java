package org.vaadin.artur.gamecard;

import java.util.ArrayList;
import java.util.Collections;

import org.vaadin.artur.gamecard.data.CardInfo;
import org.vaadin.artur.gamecard.data.Suite;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasClickListeners;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.dom.Style;

/**
 * Representation of a deck of cards or an empty placeholder where the deck
 * would be.
 *
 * Optionally shows the top card and the number of cards in the deck.
 */
@Tag("game-card-deck")
@HtmlImport("bower_components/game-card/game-card-deck.html")
public class Deck extends Component implements HasClickListeners<Deck> {

    private ArrayList<CardInfo> cards = new ArrayList<>();

    public Deck() {
        Style style = getElement().getStyle();
        // Last card is offset 8 px in both directions
        style.set("height", Card.HEIGHT + 8 + "px");
        style.set("width", Card.WIDTH + 8 + "px");
        reset();
    }

    public int getNumberOfCards() {
        return cards.size();
    }

    public void shuffle() {
        for (int i = 0; i < 10; i++) {
            Collections.shuffle(cards);
        }
    }

    public CardInfo removeTopCard() {
        if (cards.size() == 0) {
            return null;
        }

        CardInfo c = cards.remove(0);
        updateNumberOfCards();
        return c;
    }

    public void addCard(CardInfo c) {
        cards.add(c);
        updateNumberOfCards();
    }

    public void reset() {
        removeAllCards();

        // Initialize deck with 52 cards, no jokers
        for (Suite suite : Suite.values()) {
            for (int i = 1; i <= 13; i++) {
                CardInfo c = new CardInfo(suite, i);
                cards.add(c);
            }
        }
        updateNumberOfCards();
    }

    private void updateNumberOfCards() {
        getElement().setProperty("cardCount", cards.size() + "");
    }

    private void removeAllCards() {
        cards.clear();
        updateNumberOfCards();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

}
