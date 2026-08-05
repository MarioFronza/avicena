/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.listenersMenu;

import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.AppointmentEntity;
import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import br.udesc.ceavi.progii.avicena.control.listenersCRUD.ListenerCRUDDiagnostico;
import br.udesc.ceavi.progii.avicena.model.DiagnosticoFinal;
import br.udesc.ceavi.progii.avicena.model.DiagnosticoPrimario;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCadastroDiagnostico;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import jakarta.persistence.EntityManager;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Listener para o item de menu Cadastro de Diagnóstico
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class MenuCadDiagnosticoListener extends MenuActionListener {

    public MenuCadDiagnosticoListener(FrameSistema tela) {
        super(tela);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame = FrameCadastroDiagnostico.getInstance();
        DiagnosticoFinal diagnosticoFinal = new DiagnosticoFinal();
        DiagnosticoPrimario diagnosticoPrimario = new DiagnosticoPrimario();
        ListenerCRUDDiagnostico listenerCRUDDiagnosticoFinal =
                ListenerCRUDDiagnostico.getInstance(diagnosticoPrimario, diagnosticoFinal, frame);

        EntityManager entityManager =
                PersistenceConfig.createEntityManagerFactory().createEntityManager();
        List<AppointmentEntity> consultas;
        try {
            consultas = entityManager
                    .createQuery("SELECT a FROM AppointmentEntity a", AppointmentEntity.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
        FrameCadastroDiagnostico.getInstance().getCbConsulta().removeAllItems();

        for (int i = 0; i < consultas.size(); i++) {
            FrameCadastroDiagnostico.getInstance()
                    .getCbConsulta()
                    .addItem(consultas.get(i).getTime() + " - "
                            + consultas.get(i).getPatient().getName());
        }

        if (frame.isVisible()) {

        } else {
            tela.adicionarFrameInterno(frame);
            frame.setVisible(true);
        }
    }
}
