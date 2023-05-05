/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.interfaces;

import java.util.List;

/**
 *
 * @author dhia
 */

    public interface equipeinterfaces <T>{
    public void insert(T o);
    public void delete(int i);
   public List<T> displayAll();
   public T displayById(int id);
    
  public boolean update(T os);
    
}
