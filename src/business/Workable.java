/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

/**
 *
 * @author thanh
 */
public interface Workable<T> {
    public void addNew(T t);
    public void update(T t);
    public T searchById(String id);
    public void showAll();
}
