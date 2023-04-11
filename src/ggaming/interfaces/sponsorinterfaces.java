/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming.interfaces;

import java.util.List;

/**
 *
 * @author dhia
 */
public interface sponsorinterfaces  <S> {
    
    
    public void inserts(S o);
    public void deletes(int i);
   public List<S> displayAll();
   public S displayById(int id);
    
  public boolean updates(S os);
    
}
