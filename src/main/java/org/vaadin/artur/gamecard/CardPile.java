package org.vaadin.artur.gamecard;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.artur.gamecard.data.CardInfo;
import org.vaadin.artur.gamecard.event.HasDoubleClickListeners;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.HasClickListeners;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.dom.Style;

/**
 * A card pile is either empty or has 1-N cards on top of each other, with the
 * topmost card face up.
 */
@Tag("game-card-pile")
@HtmlImport("bower_components/game-card/game-card-pile.html")
public class CardPile extends Component implements HasClickListeners<CardPile>,
        HasDoubleClickListeners<CardPile> {
    private static final String TOP_CARD_RANK = "topCardRank";

    private static final String TOP_CARD_SYMBOL = "topCardSymbol";

    private static final String TOP_CARD_SELECTED = "topCardHighlighted";

    private List<CardInfo> cards = new ArrayList<>();

    public CardPile() {
        Style style = getElement().getStyle();
        style.set("width", Card.WIDTH + "px");
        style.set("height", Card.HEIGHT + "px");
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public CardInfo getTopCard() {
        if (isEmpty()) {
            return null;
        }
        return cards.get(cards.size() - 1);
    }

    public int getNumberOfCards() {
        return cards.size();
    }

    public void addCard(CardInfo cardInfo) {
        cards.add(cardInfo);
        updateTopCard();
    }

    public boolean removeTopCard() {
        if (isEmpty()) {
            return false;
        }

        int cardIndex = cards.size() - 1;
        cards.remove(cardIndex);
        updateTopCard();
        return true;
    }

    public void removeAllCards() {
        cards.clear();
        updateTopCard();
    }

    private void updateTopCard() {
        if (isEmpty()) {
            getElement().setProperty(TOP_CARD_SYMBOL, "");
            getElement().setProperty(TOP_CARD_RANK, "");
            getElement().removeProperty(TOP_CARD_SELECTED);
        } else {
            getElement().setProperty(TOP_CARD_SYMBOL, getTopCard().getSymbol());
            getElement().setProperty(TOP_CARD_RANK,
                    getTopCard().getRankSymbol());
        }

    }

    public List<CardInfo> getCards() {
        return cards;
    }

    public void setTopCardSelected(boolean topCardSelected) {
        if (topCardSelected) {
            getElement().setProperty(TOP_CARD_SELECTED, topCardSelected);
        } else {
            getElement().removeProperty(TOP_CARD_SELECTED);
        }
    }

    public boolean isTopCardSelected() {
        return getElement().hasProperty(TOP_CARD_SELECTED);
    }

    @Override
    public void fireEvent(ComponentEvent<?> componentEvent) {
        super.fireEvent(componentEvent);
    }

}
