package ma.netguard;

import ma.netguard.config.HibernateUtil;
import ma.netguard.view.MenuConsole;

/** Point d'entrée de l'application NetGuard. */
public class Main {
    public static void main(String[] args) {
        try { new MenuConsole().demarrer(); }
        finally { HibernateUtil.fermer(); }
    }
}
