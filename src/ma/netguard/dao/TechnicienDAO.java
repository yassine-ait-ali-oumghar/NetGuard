
package ma.netguard.dao;

import ma.netguard.config.HibernateUtil;
import ma.netguard.model.Technicien;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TechnicienDAO {

    public int ajouter(Technicien technicien) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.persist(technicien);

            System.out.println("Technicien ajouté avec succès !");
            transaction.commit();
            return technicien.getId();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            return 0;
        }
    }

    public List<Technicien> afficherTous() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                    "FROM Technicien", Technicien.class
            ).list();

        } catch (Exception e) {

            e.printStackTrace();
            return List.of();
        }
    }

    public Technicien rechercherParId(int id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.get(Technicien.class, id);

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    public void modifier(Technicien technicien) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.merge(technicien);

            transaction.commit();

            System.out.println("Technicien modifié avec succès !");

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }

    public void supprimer(int id) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Technicien technicien = session.get(Technicien.class, id);

            if (technicien != null) {
                session.remove(technicien);
            }

            transaction.commit();

            System.out.println("Technicien supprimé avec succès !");

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }
}
