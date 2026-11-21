
package ma.netguard.dao;

import ma.netguard.config.HibernateUtil;
import ma.netguard.model.Equipement;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EquipementDAO {

    // Ajouter un équipement
    public int ajouter(Equipement equipement) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.persist(equipement);

            System.out.println("Equipement ajouté avec succès !");
            transaction.commit();
            return equipement.getId();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            return 0;
        }
    }

    // Récupérer tous les équipements
    public List<Equipement> afficherTous() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                    "FROM Equipement", Equipement.class
            ).list();

        } catch (Exception e) {

            e.printStackTrace();
            return List.of();
        }
    }

    // Rechercher un équipement par son identifiant
    public Equipement rechercherParId(int id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.get(Equipement.class, id);

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    // Modifier un équipement
    public void modifier(Equipement equipement) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.merge(equipement);

            transaction.commit();

            System.out.println("Equipement modifié avec succès !");

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }

    // Supprimer un équipement
    public void supprimer(int id) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Equipement equipement = session.get(Equipement.class, id);

            if (equipement != null) {
                session.remove(equipement);
            }

            transaction.commit();

            System.out.println("Equipement supprimé avec succès !");

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }
}
