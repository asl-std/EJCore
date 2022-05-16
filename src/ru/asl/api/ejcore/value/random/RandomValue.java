package ru.asl.api.ejcore.value.random;

/**
 * <p>RandomValue interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface RandomValue {

	/**
	 * <p>roll.</p>
	 *
	 * @param lvl a double
	 * @return a {@link ru.asl.api.ejcore.value.random.Value} object
	 */
	Value roll(double lvl);

}
