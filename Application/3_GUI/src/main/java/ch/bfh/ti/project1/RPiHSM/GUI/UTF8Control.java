package ch.bfh.ti.project1.RPiHSM.GUI;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * <h1>UTF8Control</h1>
 * An extension of {@link ResourceBundle.Control}. This class allows to convert a properties file to a UTF-8 file (keeps the accents)
 */
public class UTF8Control extends ResourceBundle.Control {
	
    /**
     * Instantiates a resource bundle for the given bundle name of the given format and locale, using the given class loader if necessary.
     * This method returns null if there is no resource bundle available for the given parameters.
     * If a resource bundle can't be instantiated due to an unexpected error,
     * the error must be reported by throwing an Error or Exception rather than simply returning null. 
     * If the reload flag is true, it indicates that this method is being called because the previously loaded resource bundle has expired
     *
     * @param baseName the base bundle name of the resource bundle, a fully qualified class name
     * @param locale the locale for which the resource bundle should be instantiated
     * @param format the resource bundle format to be loaded
     * @param loader the ClassLoader to use to load the bundle
     * @param reload the flag to indicate bundle reloading; true if reloading an expired resource bundle, false otherwise
     * @return the resource bundle instance, or null if none could be found
     * @throws IllegalAccessException if the class or its nullary constructor is not accessible
     * @throws InstantiationException if the instantiation of a class fails for some other reason
     * @throws IOException if an error occurred when reading resources using any I/O operations
     */
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
            throws IllegalAccessException, InstantiationException, IOException {
        // The below is a copy of the default implementation.
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, "properties");
        ResourceBundle bundle = null;
        InputStream stream = null;
        if (reload) {
            URL url = loader.getResource(resourceName);
            if (url != null) {
                URLConnection connection = url.openConnection();
                if (connection != null) {
                    connection.setUseCaches(false);
                    stream = connection.getInputStream();
                }
            }
        } else {
            stream = loader.getResourceAsStream(resourceName);
        }
        if (stream != null) {
            try {
                bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
            } finally {
                stream.close();
            }
        }
        return bundle;
    }
}