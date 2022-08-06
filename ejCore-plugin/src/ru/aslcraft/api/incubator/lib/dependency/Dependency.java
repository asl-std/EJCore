package ru.aslcraft.api.incubator.lib.dependency;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Locale;

import lombok.Getter;

/**
 * <p>Dependency class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public enum Dependency {
	JAVASSIST("org.javassistm", "javassist", "3.28GA", "");

	@Getter private final String mavenRepoPath;

	private final String version;

	@Getter private final byte[] checksum;

	private static final String MAVEN_FORMAT = "%s/%s/%s/%s-%s.jar";

	Dependency(String groupId, String artifactId, String version, String checksum) {
		mavenRepoPath = String.format("%s/%s/%s/%s-%s.jar",
				rewriteEscaping(groupId).replace(".", "/"),
				rewriteEscaping(artifactId), version,
				rewriteEscaping(artifactId), version);
		this.version = version;
		this.checksum = Base64.getDecoder().decode(checksum);
	}

	private static String rewriteEscaping(String s) {
		return s.replace("$", ".");
	}

	/**
	 * <p>getFileName.</p>
	 *
	 * @param classifier a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	public String getFileName(String classifier) {
		final String name = name().toLowerCase(Locale.ROOT).replace('_', '-');
		final String extra = (classifier == null || classifier.isEmpty()) ? "" : ("-" + classifier);
		return name + "-" + version + extra + ".jar";
	}

	/**
	 * <p>checksumMatches.</p>
	 *
	 * @param hash an array of {@link byte} objects
	 * @return a boolean
	 */
	public boolean checksumMatches(byte[] hash) {
		return Arrays.equals(checksum, hash);
	}

	/**
	 * <p>createDigest.</p>
	 *
	 * @return a {@link java.security.MessageDigest} object
	 */
	public static MessageDigest createDigest() {
		try {
			return MessageDigest.getInstance("SHA-256");
		} catch (final NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

}
