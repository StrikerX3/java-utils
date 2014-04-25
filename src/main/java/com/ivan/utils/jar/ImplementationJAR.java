package com.ivan.utils.jar;

import java.io.File;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * A single JAR file, including the classes that implement or extend T.
 *
 * @param <T> the base type
 */
public class ImplementationJAR<T> {
    /**
     * Path to the JAR file.
     */
    private final File jarFile;

    /**
     * All implementations of T found within the JAR file.
     */
    private final List<Class<? extends T>> implementations = new ArrayList<Class<? extends T>>();

    /**
     * The class loader used to load this JAR file.
     */
    private URLClassLoader cl;

    /**
     * Creates a new ImplementationJAR object for the given JAR file.
     *
     * @param jarFile the JAR file
     */
    public ImplementationJAR(final File jarFile) {
        this.jarFile = jarFile;
    }

    /**
     * Retrieves the JAR file.
     *
     * @return the JAR file
     */
    public File getJarFile() {
        return jarFile;
    }

    /**
     * Retrieves all implementations of T found in this JAR file.
     *
     * @return all implementations of T found in this JAR file
     */
    public Class<? extends T>[] getImplementations() {
        return implementations.toArray(new Class[implementations.size()]);
    }

    /**
     * Adds a class to the list of implementations.
     *
     * @param cls the class that extends or implements T
     */
    void addClass(final Class<? extends T> cls) {
        implementations.add(cls);
    }

    /**
     * Determines whether this JAR has any implementations of T.
     *
     * @return <code>true</code> if there is at least one class implementing or extending T
     */
    boolean hasImplementations() {
        return !implementations.isEmpty();
    }

    /**
     * Defines the class loader used to load this JAR file.
     *
     * @param cl the URLClassLoader that loaded the JAR file
     */
    void setClassLoader(final URLClassLoader cl) {
        this.cl = cl;
    }

    /**
     * Retrieves the class loader used to load this JAR file.
     *
     * @return the class loader used to load this JAR file
     */
    public URLClassLoader getClassLoader() {
        return cl;
    }
}
