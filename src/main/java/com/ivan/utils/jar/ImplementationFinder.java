package com.ivan.utils.jar;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Utility class to search for implementations of a class or interface within JAR files.
 */
public final class ImplementationFinder {
    private ImplementationFinder() {
    }

    /**
     * Searches for implementations of the given baseType within the folder or JAR file specified by path.
     *
     * @param path a folder or a single JAR file
     * @param baseType the base class or interface to search for
     * @return a list of JARs containing all classes that implement the given interface or extend the given class
     * @throws IOException if reading the JAR files fails
     */
    public static <T> List<ImplementationJAR<T>> findImplementations(final File path, final Class<T> baseType) throws IOException {
        final File[] jars;
        if (path.isDirectory()) {
            jars = path.listFiles(new FileFilter() {
                @Override
                public boolean accept(final File pathname) {
                    return pathname.isFile() && pathname.getName().toLowerCase().endsWith(".jar");
                }
            });
        } else {
            jars = new File[] { path };
        }
        final List<ImplementationJAR<T>> implementations = new ArrayList<ImplementationJAR<T>>();
        for (final File file : jars) {
            @SuppressWarnings("resource")
            final URLClassLoader cl = new URLClassLoader(new URL[] { file.toURI().toURL() });
            final ImplementationJAR<T> implJar = new ImplementationJAR<T>(file);
            final JarFile jarFile = new JarFile(file);
            try {
                final Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    final JarEntry jarEntry = entries.nextElement();
                    if (jarEntry.getName().toLowerCase().endsWith(".class")) {
                        String className = jarEntry.getName().replace('/', '.');
                        className = className.substring(0, className.lastIndexOf(".class"));
                        try {
                            final Class<?> cls = cl.loadClass(className);
                            if (baseType.isAssignableFrom(cls)) {
                                implJar.addClass(cls.asSubclass(baseType));
                            }
                        } catch (final ClassNotFoundException e) {
                            // should never happen
                            throw new Error(e);
                        } catch (final NoClassDefFoundError e) {
                            // might happen if the classes depend on other classes that are not in the current classpath
                            //throw new Error(e);
                        }
                    }
                }
                if (implJar.hasImplementations()) {
                    implementations.add(implJar);
                    implJar.setClassLoader(cl);
                }
            } finally {
                jarFile.close();
            }
        }
        return implementations;
    }
}
