package model;

import java.io.Serializable;

/**
 * @author sopr098
 *	Enumeration, um die einzelnen Spielertypen zu unterscheiden
 *	Mögliche Typen sind Menschen, die Freecell-, Idiot-, Zankeasy- und Zankhard KI
 */
public enum PlayerType implements Serializable {

	HUMAN, ZANK_EASY, ZANK_HARD, FREECELL, IDIOT;

}
