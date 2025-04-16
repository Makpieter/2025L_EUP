package pl.psi.map;

import pl.psi.Point;
import pl.psi.creatures.Creature;
import pl.psi.hero.EconomyHero;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Optional;

public class BoardEconomyEngine {

    public static final String HERO_MOVED = "HERO_MOVED";
    private final TurnQueueEconomy turnQueue;
    private final BoardEconomy board;
    private final PropertyChangeSupport observerSupport = new PropertyChangeSupport(this);

    public BoardEconomyEngine(final EconomyHero hero1, final EconomyHero hero2) {
        turnQueue = new TurnQueueEconomy(hero1, hero2);
        board = BoardEconomy.builder()
                .addHero(hero1, 0)
                .addHero(hero2,14)
                .build();
    }

    public boolean canMove(final Point aPoint) {
        return board.canMove(turnQueue.getCurrentHero(), aPoint);
    }

    public void move(final Point aPoint) {
        board.move(turnQueue.getCurrentHero(), aPoint);
        observerSupport.firePropertyChange(HERO_MOVED, null, aPoint);
    }

    public Optional<EconomyHero> getHero(final Point aPoint) {
        return board.getHero(aPoint);
    }

    public void pass() {
        turnQueue.next();
    }

    public void addObserver(final PropertyChangeListener aObserver) {
        observerSupport.addPropertyChangeListener(aObserver);
        turnQueue.addObserver(aObserver);
    }
//
//    public boolean canAttack(final Point point) {
//        double distance = board.getPosition(turnQueue.getCurrentCreature())
//                .distance(point);
//        return board.getCreature(point)
//                .isPresent()
//                && distance < 2 && distance > 0;
//    }

    public boolean isCurrentHero(Point aPoint) {
        return Optional.of(turnQueue.getCurrentHero()).equals(board.getHero(aPoint));
    }
}
