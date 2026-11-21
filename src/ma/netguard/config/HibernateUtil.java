package ma.netguard.config;

import ma.netguard.model.Equipement;
import ma.netguard.model.Incident;
import ma.netguard.model.Technicien;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory =
            construireSessionFactory();

    private static SessionFactory construireSessionFactory() {

        try {
            Configuration configuration = new Configuration();

            configuration.setProperty(
                    "hibernate.connection.driver_class",
                    "org.postgresql.Driver"
            );

            configuration.setProperty(
                    "hibernate.connection.url",
                    "jdbc:postgresql://localhost:5432/NetGuard"
            );

            configuration.setProperty(
                    "hibernate.connection.username",
                    "postgres"
            );

            // Définir NETGUARD_DB_PASSWORD dans les variables d'environnement.
            String motDePasse = System.getenv("NETGUARD_DB_PASSWORD");
            if (motDePasse == null || motDePasse.isBlank()) {
                throw new IllegalStateException(
                        "La variable d'environnement NETGUARD_DB_PASSWORD est obligatoire."
                );
            }
            configuration.setProperty("hibernate.connection.password", motDePasse);

            configuration.setProperty(
                    "hibernate.dialect",
                    "org.hibernate.dialect.PostgreSQLDialect"
            );

            configuration.setProperty(
                    "hibernate.show_sql",
                    "true"
            );

            configuration.setProperty(
                    "hibernate.hbm2ddl.auto",
                    "update"
            );
            configuration.addAnnotatedClass(Equipement.class);
            configuration.addAnnotatedClass(Technicien.class);
            configuration.addAnnotatedClass(Incident.class);
            return configuration.buildSessionFactory();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erreur de connexion Hibernate : " + e.getMessage(), e
            );
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void fermer() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
