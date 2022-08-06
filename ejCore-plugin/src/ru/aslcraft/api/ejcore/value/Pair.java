package ru.aslcraft.api.ejcore.value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>Pair class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
@AllArgsConstructor
public class Pair<T1, T2> {

	@Getter @Setter private T1 first;

	@Getter @Setter private T2 second;

}
