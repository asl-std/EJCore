package org.aslstd.api.attributes;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Priority class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
@AllArgsConstructor
public enum Priority {
	AFTER_DAMAGE_CALCULATING(16),
	BEFORE_DAMAGE_CALCULATING(1);

	/**
	 * <p>before.</p>
	 *
	 * @param stat a {@link org.aslstd.api.attributes.BasicAttr} object
	 * @return a int
	 */
	@Getter private int priority;

	public static int before(BasicAttr stat) {
		return stat.getPriority()-1;
	}

	/**
	 * <p>after.</p>
	 *
	 * @param stat a {@link org.aslstd.api.attributes.BasicAttr} object
	 * @return a int
	 */
	public static int after(BasicAttr stat) {
		return stat.getPriority()+1;
	}

}
