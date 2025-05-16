/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test3;

/**
 *
 * @author Xu
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MyQueue a = new MyQueue();
        
        a.enqueue(new Student("Nguyen A", 30));
        a.enqueue(new Student("Nguyen B", 50));
        a.enqueue(new Student("Nguyen C", 60));
        a.enqueue(new Student("Nguyen D", 80));
        
        a.display();
        a.dequeue();
        a.display();
        
        System.out.println("Front: "+a.front());
    }
    
}
