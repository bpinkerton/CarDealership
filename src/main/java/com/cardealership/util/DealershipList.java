package com.cardealership.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class DealershipList <E>{
    private int size = 0;

    private  Node<E> first;

    private Node<E> last;

    public DealershipList(){}

    public DealershipList(E[] array){
        this();
        addAll(array);
    }

    //public methods
    public int size(){ return size; }

    public boolean add(E e){
        linkLast(e);                // add the new element to the end of the list
        return true;
    }

    public boolean addAll(E[] array){
        return addAll(size, array);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Node<E> current = first;
        while(true){
            assert current != null;
            result.append(current.data.toString());
            if (current.next == null) break;
            current = current.next;
            result.append("\n");
        }
        return result.toString();
    }

    // private methods
    private void linkLast(E e){
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l,e,null); // create new node with last as their prev
        last = newNode;             // set newNode to be the last element
        if (l == null)              // check to see if the list was empty
            first = newNode;        // if so, newNode is also the first node as well as the last
        else
            l.next = newNode;       // point the previously last node to the new last node
        size++;
    }

    private boolean addAll(int index, E[] array){
        int numNew = array.length;
        if (0 == numNew)            //Yoda programming. Return false if array is size 0 and has no elements
            return false;

        Node<E> pred, succ;         //predecessor and successor
        if (index == size){         // add to the end of the list
            succ = null;            // end of the list so there is no successor
            pred = last;            // end of the list so the last node is this nodes predecessor
        } else{                     // not at the end of the list
            succ = node(index);     // retrieve the node at index and insert the new node before that node
            pred = succ.prev;       // set current nodes predecessor to the predecessor of our next node
        }

        for (Object o : array){
            @SuppressWarnings("unchecked") E e = (E) o; // cast the object as a type of object we expected
            Node<E> newNode = new Node<>(pred, e, null); // call the constructor where next is null
            if (pred == null)       // if there is no predecessor, we are at the front of the list
                first = newNode;
            else
                pred.next = newNode;
            pred = newNode;
        }

        if (succ == null)           // we are at the end of the list
            last = pred;
        else{                       // we are not at the end of the list
            pred.next = succ;
            succ.prev = pred;
        }
        size += numNew;             // increment the size by the number of elements added
        return true;
    }

    private class Node<E>{
        E data;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next){
            this.data = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<E> node(int index){
        if (index < (size / 2)){    // if index is in the first half of the list, work from the front
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else{                     // if index is in the second half of the list, work from the back
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }
}
