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
public class MyQueue {
    Node head, tail;
    public MyQueue() {head = tail = null;}
    public void clear() {head = tail = null;}
    public boolean isEmpty() {return head == null;}
    
    //addLast
    public void enqueue(Student info) {
        Node p = new Node(info);
        if(isEmpty()) {
            head = tail = p;
        }
        else {
            tail.next = p; tail = p;}
    }
    
    public Student dequeue() {
        if(isEmpty()){
            return null;
        }
        Student info = head.info;
        head = head.next;
        return info;
    }
    
    public Student front()
    {if(isEmpty()){
        return null;
    }
     Student info = head.info;
     return info;
    }
    
    public void display() {
        Node current = head;
        System.out.print("Queue: \n");
        while (current != null) {
            System.out.print(current.info + "\n");
            current = current.next;
        }
        System.out.println();
    }
}

