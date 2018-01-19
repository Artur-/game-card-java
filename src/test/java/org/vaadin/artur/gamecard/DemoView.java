package org.vaadin.artur.gamecard;

import java.util.Random;

import org.vaadin.artur.gamecard.data.CardInfo;
import org.vaadin.artur.gamecard.data.Suite;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class DemoView extends Div {

    private Deck deck;
    private CardStack nextStack;
    private HorizontalLayout stackLayout;

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        setWidth("100%");

        add(new Html("<div>Click on the deck to deal a card<br>"
                + "Click on one of the hearts (1-13) to flip the card<br>"
                + "Click on a dealt card to select/deselect it</div>"));

        deck = new Deck();
        deck.shuffle();
        deck.addClickListener(event -> {
            if (!deck.isEmpty()) {
                Card card = new Card(deck.removeTopCard());
                card.addClickListener(e -> {
                    card.setSelected(!card.isSelected());
                });
                getNextStack().addCard(card);
            }
        });
        add(deck);
        // DragSourceExtension deckDrag = new DragSourceExtension<>(deck);
        // deckDrag.setEffectAllowed(EffectAllowed.MOVE);
        // deckDrag.addDragEndListener(e -> {
        // System.out.println(e.getDropEffect());
        // System.out.println("drag end for deck");
        // });
        // new DropTargetExtension<>(layout)
        // .addDropListener((DropEvent<CssLayout> e) -> {
        // System.out.println("drop for layout");
        // });

        CardPile pile = new CardPile();
        pile.addClickListener(e -> {
            pile.addCard(
                    new CardInfo(Suite.CLUBS, 1 + new Random().nextInt(13)));
        });
        add(pile);
        stackLayout = new HorizontalLayout();

        CardStack stack = new CardStack();
        for (int i = 13; i >= 1; i--) {
            Card card = new Card(Suite.HEARTS, i);
            card.addClickListener(e -> {
                card.setBacksideUp(!card.isBacksideUp());
            });
            stack.addCard(card);
        }
        stackLayout.add(stack);
        nextStack = new CardStack();
        stackLayout.add(nextStack);

        add(stackLayout);
    }

    private CardStack getNextStack() {
        if (nextStack.getNumberOfCards() == 13) {
            nextStack = new CardStack();
            stackLayout.add(nextStack);
        }
        return nextStack;
    }
}
