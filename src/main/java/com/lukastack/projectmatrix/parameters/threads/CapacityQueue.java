package com.lukastack.projectmatrix.parameters.threads;

import java.util.*;

public class CapacityQueue<E> implements Iterable<E>{

    private int size = 0;
    private Node<E> first;
    private Node<E> last;

    final int capacity;

    public CapacityQueue(int capacity) {

        this.capacity = capacity;
    }

    public void add(E e) {

        if (size == capacity) {
            pushLast(e);
        }
        else {
            linkLast(e);
        }
    }

    public E remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    public void clear() {

        for (Node<E> x = first; x != null; ) {
            Node<E> next = x.next;
            x.item = null;
            x.next = null;
            x.prev = null;
            x = next;
        }

        first = last = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    private void linkLast(E e) {

        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
    }

    private void pushLast(E e) {

        unlink(first);
        linkLast(e);
    }

    private E unlink(Node<E> x) {

        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if (prev == null) {
            first = next;
        }
        else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        }
        else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;

        return element;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {

        return "Index: "+index+", Size: "+size;
    }

    private boolean isElementIndex(int index) {

        return index >= 0 && index < size;
    }

    private Node<E> node(int index) {

        Node<E> x;

        if (index < (size >> 1)) {
            x = first;

            for (int i = 0; i < index; i++) {
                x = x.next;
            }
        }
        else {
            x = last;

            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
        }

        return x;
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {

            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        /**
         * Index of element to be returned by subsequent call to next.
         */
        int cursor = 0;

        /**
         * Index of element returned by most recent call to next or
         * previous.  Reset to -1 if this element is deleted by a call
         * to remove.
         */
        int lastRet = -1;

        public boolean hasNext() {
            return cursor != size();
        }

        public E next() {
            try {
                int i = cursor;
                E next = get(i);
                lastRet = i;
                cursor = i + 1;
                return next;
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
        }
    }
}
