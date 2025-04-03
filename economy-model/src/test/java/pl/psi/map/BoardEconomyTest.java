package pl.psi.map;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import pl.psi.Point;
import pl.psi.hero.EconomyHero;

class BoardEconomyTest
{
    private EconomyHero hero1;
    private EconomyHero hero2;
    private BoardEconomy board;


    @BeforeEach
    void init()
    {
        hero1 = Mockito.mock( EconomyHero.class );
        hero2 = Mockito.mock( EconomyHero.class );
        board = new BoardEconomy( hero1, hero2 );
    }

    @Test
    void unitsMoveProperly()
    {
        Mockito.when(hero1.getMoveRange()).thenReturn(10);
        Mockito.when(hero2.getMoveRange()).thenReturn(20);
        board.move(hero1, new Point( 3, 3 ) );
        board.move(hero2, new Point( 10, 10 ) );

        assertThat( board.getHero( new Point( 3, 3 ) )
                .isPresent() ).isTrue();
        assertThat(board.getHero( new Point( 10, 10 ) ).isPresent() ).isTrue();
    }

    @Test
    void heroesCannotMove()
    {
        Mockito.when(hero1.getMoveRange()).thenReturn(1);
        board.move(hero1, new Point( 3, 3 ) );
        assertThat( board.getHero( new Point( 3, 3 ) )
                .isPresent() ).isFalse();
    }

    @Test
    void setterSetsGoldCorrectly(){

        Gold gold = new Gold(500);
        board.setObject(gold,new Point(5,5));
        assertThat(board.getObject(new Point(5,5))).isEqualTo(gold);
    }

    @Test
    void heroGainsGoldUponPickup()
    {
        EconomyHero hero1 = new EconomyHero(EconomyHero.Fraction.NECROPOLIS, 0);
        board.setObject(new Gold(500),new Point(5,5));
        board.move(hero1, new Point(5,5));
        assertThat(hero1.getGold()).isEqualTo(500);
    }

    @Test
    void goldDissapearsUponPickup()
    {
        Gold gold = new Gold(500);
        EconomyHero hero1 = new EconomyHero(EconomyHero.Fraction.NECROPOLIS, 0);
        board.setObject(gold, new Point(5,5));
        board.move(hero1, new Point(5,5));
        board.move(hero1, new Point(10,10));
        board.move(hero1, new Point(5,5));
        assertThat(board.getObject(new Point(5,5)).equals(gold)).isFalse();
        assertThat(board.getHero(new Point(10,10)).isPresent());

    }



}