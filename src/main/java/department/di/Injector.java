package department.di;

import department.config.MainConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Objects;

/**
 * Created by Максим on 1/31/2017.
 */
public final class Injector {

    private static Injector instance;

    private AnnotationConfigApplicationContext context;

    private Injector(Class configClass) {
        Objects.requireNonNull(configClass, "config class wasn't specified");
        //this.context = new ClassPathXmlApplicationContext(filePath);
        this.context = new AnnotationConfigApplicationContext(configClass);
    }

    public static Injector initialize(Class configClass) {
        Injector local = instance;

        if (local == null) {
            synchronized (Injector.class) {
                Injector.instance = local = new Injector(configClass);
            }
        }
        return local;
    }

    public static Injector getInstance() {
        return instance;
    }

    public ApplicationContext getContext() {
        Objects.requireNonNull(instance, "initialize first!");
        return instance.context;
    }

}
