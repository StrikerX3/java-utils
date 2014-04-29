package com.ivan.utils.nativelibs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * Helper class to facilitate loading native libraries that are included in the .jar file.
 * Libraries are expected to be within a folder in the classpath and their naming should
 * be as follows:
 * <pre>
 * &lt;library-path&gt;/&lt;os&gt;-&lt;arch&gt;/&lt;lib-prefix&gt;&lt;lib-name&gt;.&ltext&gt;
 * </pre>
 * where:
 * <ul>
 * <li><code>library-path</code> is specified by the user on the {@link #load(String, String)} method</li>
 * <li><code>os</code> is one of:
 *   <ul>
 *   <li><code>win</code> on Windows platforms
 *   <li><code>linux</code> on Linux platforms
 *   <li><code>unix</code> on UNIX-like platforms
 *   <li><code>mac</code> on Mac OS platforms
 *   <li><code>other</code> on other platforms
 *   </ul>
 * </li>
 * <li><code>arch</code> is either <code>x86</code> or <code>x64</code></li>
 * <li><code>lib-prefix</code> is either <code>lib</code> or nothing, depending on the platform
 * (typically Linux and UNIX-like platforms adopt <code>lib</code>, whereas Windows platforms do not use a prefix)</li>
 * <li><code>lib-name</code> is the library name, specified by the user on the <code>load(String, String)</code> method</li>
 * <li><code>ext</code> is the library extension, according to the platform standards
 * (<code>dll</code> on Windows, <code>so</code> on Linux and UNIX-like platforms, and <code>dylib</code> on Mac OS)</li>
 * </ul>
 */
// TODO allow a custom string format
public class NativeLibraryHelper {
    /**
     * System architectures: x86 or x64.
     */
    private static enum SystemArch {
        x86, x64;
    }

    /**
     * Operating systems: Windows, Linux, UNIX-like, Mac OS or Other.
     */
    private static enum OS {
        Windows("win", "", ".dll"),
        Linux("linux", "lib", ".so"),
        UnixLike("unix", "lib", ".so"),
        MacOS("mac", "lib", ".dylib"),
        Other("other", "", "");

        /**
         * The short name of the operating system, such as <code>win</code>,
         * <code>linux</code> or <code>mac</code>.
         */
        private final String name;

        /**
         * The library prefix used by this operating system (typically either <code>lib</code>
         * for Linux and UNIX-like systems or nothing for others).
         */
        private final String libPrefix;

        /**
         * The library file extension used by this operating system, such as <code>dll</code>
         * on Windows or <code>so</code> on Linux.
         */
        private final String libExt;

        private OS(final String name, final String libPrefix, final String libExt) {
            this.name = name;
            this.libPrefix = libPrefix;
            this.libExt = libExt;
        }

        /**
         * Retrieves the short name of the operating system, such as <code>win</code>,
         * <code>linux</code> or <code>mac</code>.
         *
         * @return the short name of the operating system
         */
        public String getName() {
            return name;
        }

        /**
         * Converts a library name into the corresponding file name according to this
         * operating system's specifications.
         * The naming convention is:
         * <pre>
         * &lt;lib-prefix&gt;&lt;lib-name&gt;.&ltext&gt;
         * </pre>
         * where:
         * <ul>
         * <li><code>lib-prefix</code> is either <code>lib</code> or nothing, depending on the platform
         * (typically Linux and UNIX-like platforms adopt <code>lib</code>, whereas Windows platforms do not use a prefix)</li>
         * <li><code>lib-name</code> is the library name, specified by the user on the <code>load(String, String)</code> method</li>
         * <li><code>ext</code> is the library extension, according to the platform standards
         * (<code>dll</code> on Windows, <code>so</code> on Linux and UNIX-like platforms, and <code>dylib</code> on Mac OS)</li>
         * </ul>
         *
         * @param libName the library name
         * @return the library file name for this operating system
         */
        public String toFilename(final String libName) {
            return libPrefix + libName + libExt;
        }
    }

    /**
     * The current system architecture.
     */
    private static final SystemArch arch;

    /**
     * The current operating system.
     */
    private static final OS os;

    /**
     * Temporary directory to store the extracted libraries.
     */
    private static final File tempDir;

    static {
        arch = System.getProperty("os.arch").contains("64") ? SystemArch.x64 : SystemArch.x86;

        final String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("windows")) {
            os = OS.Windows;
        } else if (osName.contains("nux")) {
            os = OS.Linux;
        } else if (osName.contains("nix") || osName.contains("aix")) {
            os = OS.UnixLike;
        } else if (osName.contains("mac")) {
            os = OS.MacOS;
        } else {
            os = OS.Other;
        }

        tempDir = new File(System.getProperty("java.io.tmpdir"), "/lib/native/" + arch);
        tempDir.mkdirs();

        // add the tempDir to the java.library.path
        final String pathSep = System.getProperty("path.separator");
        System.setProperty("java.library.path",
                System.getProperty("java.library.path") + pathSep + tempDir.getAbsolutePath());

        // hack to force the class loader to reload the java.library.path paths
        Field sysPathsField;
        try {
            sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
            sysPathsField.setAccessible(true);
            sysPathsField.set(null, null);
        } catch (final NoSuchFieldException e) {
            e.printStackTrace();
        } catch (final SecurityException e) {
            e.printStackTrace();
        } catch (final IllegalArgumentException e) {
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private NativeLibraryHelper() {
    }

    /**
     * Loads a native library using the system class loader.
     *
     * @param path the base path for the library
     * @param libName the library name
     * @throws IOException if there is a problem reading or writing the library
     */
    public static void load(final String path, final String libName) throws IOException {
        load(path, libName, ClassLoader.getSystemClassLoader());
    }

    /**
     * Loads a native library using the given class loader.
     *
     * @param path the base path for the library
     * @param libName the library name
     * @param cl the ClassLoader to use to load the library
     * @throws IOException if there is a problem reading or writing the library
     */
    public static void load(final String path, final String libName, final ClassLoader cl) throws IOException {
        final String filename = os.toFilename(libName);
        final String resourcePath = path + "/" + os.getName() + "-" + arch + "/" + filename;
        final InputStream input = cl.getResourceAsStream(resourcePath);
        if (input == null) {
            throw new IOException("Library " + libName + " not found. Full path: " + resourcePath);
        }
        final File outputFile;
        try {
            outputFile = new File(tempDir, filename);
            if (!outputFile.exists()) {
                outputFile.createNewFile();

                final FileOutputStream fos = new FileOutputStream(outputFile);
                try {
                    final byte[] buf = new byte[65536];
                    int len;
                    while ((len = input.read(buf)) > -1) {
                        fos.write(buf, 0, len);
                    }
                } finally {
                    fos.close();
                }
            }
        } finally {
            input.close();
        }
        System.load(outputFile.getAbsolutePath());
    }
}
