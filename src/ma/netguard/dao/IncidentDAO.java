
package ma.netguard.dao;

import ma.netguard.config.HibernateUtil;
import ma.netguard.model.Incident;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class IncidentDAO {

    // Ajouter un incident
    public int ajouter(Incident incident) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.persist(incident);

            System.out.println("Incident ajouté avec succès !");
            transaction.commit();
            return incident.getId();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            return 0;
        }
    }

    // Afficher tous les incidents
    public List<Incident> afficherTous() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                    "FROM Incident", Incident.class
            ).list();

        } catch (Exception e) {

            e.printStackTrace();
            return List.of();
        }
    }

    // Rechercher un incident par ID
    public Incident rechercherParId(int id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.get(Incident.class, id);

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    // Modifier un incident
    public void modifier(Incident incident) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.merge(incident);

            transaction.commit();

            System.out.println("Incident modifié avec succès !");

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }

    // Supprimer un incident
    public void supprimer(int id) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Incident incident = session.get(Incident.class, id);

            if (incident != null) {
                session.remove(incident);
            }

            transaction.commit();

            System.out.println("Incident supprimé avec succès !");

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }
}
