/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.listenersMenu;

import br.udesc.ceavi.progii.avicena.control.dao.ConsultaDAO;
import br.udesc.ceavi.progii.avicena.control.dao.DAO;
import br.udesc.ceavi.progii.avicena.control.listenersCRUD.ListenerCRUDDiagnostico;
import br.udesc.ceavi.progii.avicena.model.Consulta;
import br.udesc.ceavi.progii.avicena.model.DiagnosticoFinal;
import br.udesc.ceavi.progii.avicena.model.DiagnosticoPrimario;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCadastroDiagnostico;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Listener para o item de menu Cadastro de Diagnóstico
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */

public class MenuCadDiagnosticoListener extends MenuActionListener{
    
    public MenuCadDiagnosticoListener(FrameSistema tela){
        super(tela);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        frame = FrameCadastroDiagnostico.getInstance();
        DiagnosticoFinal diagnosticoFinal = new DiagnosticoFinal();
        DiagnosticoPrimario diagnosticoPrimario = new DiagnosticoPrimario();
        ListenerCRUDDiagnostico listenerCRUDDiagnosticoFinal = ListenerCRUDDiagnostico.getInstance(diagnosticoPrimario, diagnosticoFinal, frame);
       
        DAO dao = new ConsultaDAO();
        List<Consulta> consultas = dao.getList();
        FrameCadastroDiagnostico.getInstance().getCbConsulta().removeAllItems();
        
        for (int i = 0; i < consultas.size(); i++) {
            FrameCadastroDiagnostico.getInstance().getCbConsulta().addItem(consultas.get(i).getHora() +" - "+consultas.get(i).getPaciente().getNome());
        }
        
        if(frame.isVisible()){
            
        }else{
            tela.adicionarFrameInterno(frame);
            frame.setVisible(true);
        }
    }
    
}
