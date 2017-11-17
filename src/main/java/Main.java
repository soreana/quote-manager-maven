import helpers.Facade;
import helpers.LambdaExceptionUtil;
import helpers.MissedKeyException;
import helpers.Tag;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.jar.JarFile;

public class Main {
    private final static Logger log = Logger.getLogger(Main.class);

    private static Properties prop = new Properties();
    private static ArrayList<Facade> something = new ArrayList<>();
    private static Scanner console = new Scanner(System.in);

    static {
        try (InputStream input = new FileInputStream("src/main/resources/conf.properties")) {

            // load a properties file
            prop.load(input);

            // load jar file
            File file = new File(prop.getProperty("jarFilePath"));

            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};

            JarFile jarFile = new JarFile(file);

            ClassLoader cl = new URLClassLoader(urls);

            jarFile.stream()
                    .filter(e -> e.getName().endsWith(".class"))
                    .map(e -> e.getName().substring(0, e.getName().length() - 6).replace('/', '.'))
                    .map(LambdaExceptionUtil.rethrowFunction(cl::loadClass))
                    .filter(e -> !e.isInterface())
                    .filter(Facade.class::isAssignableFrom)
                    .forEach(LambdaExceptionUtil.rethrowConsumer(e->{
                            Object obj = e.newInstance();
                            Method method = e.getMethod("getInstance");
                            Facade facade = (Facade) method.invoke(obj);
                            something.add(facade);
                            log.info("class " + e.getTypeName() + " was added as a facade.");
                        }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws MissedKeyException {
        boolean quit = false;
        boolean end;
        AtomicReference<Map<String, String>> userInput = new AtomicReference<>(new HashMap<>());
        AtomicReference<ArrayList<Tag<String>>> tags = new AtomicReference<>(new ArrayList<Tag<String>>());

        while (!quit) {
            System.out.println("I can store these information for you:");
            for (int i = 0; i < something.size(); i++) {
                System.out.println((i + 1) + ". " + something.get(i).getName());
            }
            System.out.println("q. quit");
            System.out.print("please enter a number or q to quit:");
            String input = console.next();

            if (input.equals("q")) {
                quit = true;
                continue;
            }

            Facade f = something.get(Integer.parseInt(input) - 1);
            Set<String> temp = f.getKeys();
            userInput.get().clear();

            for (String current : temp) {
                System.out.print("please enter " + current + " : ");
                input = console.next();
                userInput.get().put(current, input);
            }

            tags.get().clear();
            end = false;
            do {
                System.out.println("please enter tags comma separated, e.g: tag1, tag2, tag3");

                console.nextLine(); // to skip new lines

                for (String current : console.nextLine().replaceAll(" ", "").split(",")) {
                    if (!tags.get().contains(new Tag<>(current)))
                        tags.get().add(new Tag<>(current));
                }

                System.out.println("add more tag [y/n]: ");
                if (!"y".equals(console.next()))
                    end = true;
            } while (!end);

            f.addNewEntry(userInput.get(), tags.get());
        }
    }
}
