/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.dao;

import java.util.List;

/**
 * Esta Interface define as funções básicas de inserção, consulta, alteração e exclusão de objetos
 * @author Mário, Adroan, Vini, Raphael
 * @version 1.0
 */
public interface DAO<X> {
    
    boolean inserir(X obj) throws Exception;
    boolean atualizar(X obj)throws Exception;
    boolean deletar(X obj) throws Exception;
    List<X> getList();
    X pesquisarPorId(Class tipo, long id) throws Exception;
    
}
