/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.view.painels;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Esta classe define o painel para as funcionalidades CRUD 
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class CRUDActionPanel extends JPanel implements ActionListener{
    
    private JButton btCancelar;
    private JButton btNovo;
    private JButton btGravar;
    private JButton btExcluir;
    
    private LayoutManager layout;

    private Dimension dimensaoBotao;
    private Dimension dimensaoPainel;
    
    private Container parent;
    
    public CRUDActionPanel(Container parent) {
        this.parent = parent;
        
        initComponents();
        addComponents();

        super.setVisible(true);
        super.setSize(dimensaoPainel);
    }

    private void initComponents() {
        dimensaoBotao = new Dimension(100,20);
        dimensaoPainel = new Dimension(parent.getWidth(), 30);
        
        layout = new FlowLayout(FlowLayout.CENTER);

        btCancelar = new JButton("Cancelar");
        btCancelar.setSize(dimensaoBotao);

        btNovo = new JButton("Novo");
        btNovo.setSize(dimensaoBotao);

        btGravar = new JButton("Gravar");
        btGravar.setSize(dimensaoBotao);

        btExcluir = new JButton("Excluir");
        btExcluir.setSize(dimensaoBotao);
        
        btNovo.addActionListener(this);
        btCancelar.addActionListener(this);
        btGravar.addActionListener(this);
        btExcluir.addActionListener(this);
    }

    private void addComponents() {
        this.setLayout(layout);
        this.add(btCancelar);
        this.add(btNovo);
        this.add(btExcluir);
        this.add(btGravar);
    }
    
    public JButton getBtCancelar() {
        return btCancelar;
    }

    public JButton getBtNovo() {
        return btNovo;
    }

    public JButton getBtGravar() {
        return btGravar;
    }

    public JButton getBtExcluir() {
        return btExcluir;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      
        /*if(e.getSource() == btCancelar){
           int aux = JOptionPane.showConfirmDialog(null,"Deseja realmente sair?","Fechar Janela",JOptionPane.YES_NO_OPTION);
           if(aux == 0){
                parent.setVisible(false);
           }
          
       }
*/
    }

    
    
}
