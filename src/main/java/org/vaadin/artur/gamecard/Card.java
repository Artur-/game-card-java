package org.vaadin.artur.gamecard;

import org.vaadin.artur.gamecard.data.CardInfo;
import org.vaadin.artur.gamecard.data.Suite;
import org.vaadin.artur.gamecard.event.HasDoubleClickListeners;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasClickListeners;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.dom.Style;

/**
 * A game card with a given suite and a given rank; either face up or down.
 */
@Tag("game-card")
@HtmlImport("bower_components/game-card/game-card.html")
public class Card extends Component
        implements HasClickListeners<Card>, HasDoubleClickListeners<Card> {

    private static final String RANK = "rank";

    private static final String SYMBOL = "symbol";

    private static final String HIGHLIGHTED = "highlighted";

    private static final String UNREVEALED = "unrevealed";

    public static int HEIGHT = 200;
    public static int WIDTH = (int) (0.7 * HEIGHT);
    private CardInfo cardInfo;

    private boolean backsideUp;

    public Card(Suite suite, int rank) {
        this(new CardInfo(suite, rank));
    }

    public Card(CardInfo cardInfo) {
        this.cardInfo = cardInfo;
        updateSymbolAndRank();

        Style style = getElement().getStyle();
        style.set("height", HEIGHT + "px");
        style.set("width", WIDTH + "px");
    }

    private void updateSymbolAndRank() {
        if (backsideUp) {
            // This could be improved to remove symbol/rank delayed to avoid
            // clearing the card before flippin
            getElement().setProperty(SYMBOL, "");
            getElement().setProperty(RANK, "");
            getElement().setProperty(UNREVEALED, true);
        } else {
            getElement().setProperty(SYMBOL, cardInfo.getSymbol());
            getElement().setProperty(RANK, cardInfo.getRankSymbol());
            getElement().removeProperty(UNREVEALED);
        }
    }

    public boolean isBacksideUp() {
        return getElement().hasProperty(UNREVEALED);
    }

    public void setBacksideUp(boolean backsideUp) {
        this.backsideUp = backsideUp;
        updateSymbolAndRank();
    }

    public boolean isSelected() {
        return getElement().getProperty(HIGHLIGHTED, false);
    }

    public void setSelected(boolean selected) {
        getElement().setProperty(HIGHLIGHTED, selected);
    }

    @Override
    public String toString() {
        return "[" + getClass().getSimpleName() + ": " + getCardInfo() + "]";
    }

    public CardInfo getCardInfo() {
        return cardInfo;
    }

    public int getRank() {
        return getCardInfo().getRank();
    }

    public Suite getSuite() {
        return getCardInfo().getSuite();
    }

    public void setSuite(Suite suite) {
        getCardInfo().setSuite(suite);
        updateSymbolAndRank();
    }

}
