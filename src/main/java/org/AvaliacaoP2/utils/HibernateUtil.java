package org.AvaliacaoP2.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Cria a configuração usando o arquivo hibernate.properties automaticamente
            Configuration configuration = new Configuration();

            // Adiciona classe anotada
            configuration.addAnnotatedClass(org.AvaliacaoP2.model.Paciente.class);

            return configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar SessionFactory", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
