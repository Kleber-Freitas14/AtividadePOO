package org.AvaliacaoP2.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.AvaliacaoP2.model.Paciente;
import org.AvaliacaoP2.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    // === CREATE ===
    public void salvarPaciente(Paciente paciente) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(paciente);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // === READ BY ID ===
    public Paciente buscarPacientePorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Paciente.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // === READ ALL ===
    public List<Paciente> buscarPacientes() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Paciente", Paciente.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // === SEARCH BY NAME OR CPF (case-insensitive for name) ===
    public List<Paciente> buscarPorNome(String filtro) {
        if (filtro == null) filtro = "";
        String filtroLike = "%" + filtro.toLowerCase() + "%";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Busca por nome (case-insensitive) ou por CPF (contendo)
            String hql = "FROM Paciente p WHERE lower(p.nome) LIKE :filtro OR p.cpf LIKE :filtroCpf";
            return session.createQuery(hql, Paciente.class)
                    .setParameter("filtro", filtroLike)
                    .setParameter("filtroCpf", "%" + filtro + "%")
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // === UPDATE ===
    public void atualizarPaciente(Paciente paciente) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(paciente);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // === DELETE by id ===
    public void deletarPaciente(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Paciente paciente = session.find(Paciente.class, id);
            if (paciente != null) {
                session.remove(paciente);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
