package pl.psi.map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.Optional;

import pl.psi.Point;
import pl.psi.hero.EconomyHero;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class BoardEconomy {
    private static final int MAX_WITDH = 14;
    private final BiMap<Point, Object> map = HashBiMap.create();

    public BoardEconomy(final EconomyHero hero1, final EconomyHero hero2 ) {
        addHero(hero1, 0);
        addHero(hero2, MAX_WITDH);
    }

    private void addHero(final EconomyHero hero, final int aXPosition) {
            map.put(new Point(aXPosition, 1), hero);
    }

    Optional <Object> getHero(final Point aPoint) {
        return Optional.ofNullable(map.get(aPoint));
    }

    public void move(final EconomyHero hero, final Point aPoint) {
        if (canMove(hero, aPoint)) { //checks based on availability of movement
            Object obj = map.get(aPoint); //receiving an object from the target square
            if (obj instanceof InteractableIf interactable) { //if the received object is an implementation of InteractableIf
                System.out.println("Hero interacts with: " + interactable.getClass().getSimpleName()); //log check
                interactable.interact(hero, this, aPoint); //invoke the method of the object on the hero
            }

            map.inverse().remove(hero);
            map.put(aPoint, hero);
        }
    }


    public boolean canMove(final EconomyHero hero, final Point aPoint) {
        Object obj = map.get(aPoint);
        if(obj instanceof InteractableIf){
            return true;
        }
        final Point oldPosition = getPosition(hero);
        return aPoint.distance(oldPosition.getX(), oldPosition.getY()) < hero.getMoveRange();
    }

    Point getPosition(EconomyHero hero) {
        return map.inverse()
                .get(hero);
    }

    void setObject(final Object object, final Point point)
    {
        map.put(point, object);
    }

    Object getObject(final Point point)
    {
        return map.get(point);
    }

    void removeObject(Point point){
        map.remove(point);
    }
}
