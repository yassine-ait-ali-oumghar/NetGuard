
package ma.netguard;

import ma.netguard.config.HibernateUtil;
import org.hibernate.SessionFactory;

public class TestHibernate {

    public static void main(String[] args) {

        try {
            SessionFactory sessionFactory =
                    HibernateUtil.getSessionFactory();

            System.out.println("Connexion Hibernate réussie !");

            sessionFactory.close();

        } catch (Exception e) {
            System.out.println("Erreur de connexion !");
            e.printStackTrace();
        }
    }
}
