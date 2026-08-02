package br.udesc.ceavi.progii.avicena.control.listenersMenu;

import br.udesc.ceavi.progii.avicena.control.listenersCRUD.ListenerCRUDConsulta;
import br.udesc.ceavi.progii.avicena.control.dao.DAO;
import br.udesc.ceavi.progii.avicena.control.dao.EnfermeiroDAO;
import br.udesc.ceavi.progii.avicena.control.dao.MedicoDAO;
import br.udesc.ceavi.progii.avicena.model.Consulta;
import br.udesc.ceavi.progii.avicena.model.Enfermeiro;
import br.udesc.ceavi.progii.avicena.model.Medico;
import br.udesc.ceavi.progii.avicena.view.frames.FrameConsultaNova;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Listener para o item de menu Nova Consulta
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */

public class MenuConsultaNovaListener extends MenuActionListener{
    
    public MenuConsultaNovaListener(FrameSistema tela){
        super(tela);
    }    
    
    private static MenuConsultaNovaListener instancia;
    private List<Medico> medicos;
    private List<Enfermeiro> enfermeiros;

    public static MenuConsultaNovaListener getInstace(){
        return instancia;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        frame = FrameConsultaNova.getInstance();
        
        
        
        Consulta consulta = new Consulta();
        
        ListenerCRUDConsulta listenerConsulta = ListenerCRUDConsulta.getInstance(consulta, frame);
        
        DAO dao = new MedicoDAO();
        medicos = dao.getList();
        FrameConsultaNova.getInstance().getCbMedico().removeAllItems();
        for (int i = 0; i < medicos.size(); i++) {
            FrameConsultaNova.getInstance().getCbMedico().addItem(medicos.get(i).getNome()+" - "+medicos.get(i).getEspecializacao());    
        }

        
        DAO dao2 = new EnfermeiroDAO();
        enfermeiros = dao2.getList();
        FrameConsultaNova.getInstance().getCbEnfermeiro();
        for (int i = 0; i < enfermeiros.size(); i++) {
            FrameConsultaNova.getInstance().getCbEnfermeiro().addItem(enfermeiros.get(i).getNome()+" - "+ enfermeiros.get(i).getFormacao());
        }
        
        
        
        if(frame.isVisible()){
            
        }else{
            tela.adicionarFrameInterno(frame);
            frame.setVisible(true);
        }        
    }
    
    public List<Medico> getListMedicos(){
        return medicos;
    }
    
    public List<Enfermeiro> getListEnfermeiros(){
        return enfermeiros;
    }

}
