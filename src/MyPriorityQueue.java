import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rishabh
 * This is an extension of Java 7's inbuilt PriorityQueue with an added
 * advantage that the remove operation can be performed in logarithmic time.
 */
public class MyPriorityQueue<T extends Comparable<T>>
{
    Object[] elements;
    int maxSize;
    int currSize;
    Map<Object, Integer> indMap;

    public MyPriorityQueue (int maxSize)
    {
        PriorityQueue q;
        this.maxSize = maxSize;
        this.currSize = 0;
        this.indMap = new HashMap<>();
        elements = new Object[this.maxSize];
    }
    private int getParent (int ind)
    {
        return (int) ((ind + 1) / 2) - 1;
    }
    private int getFirstChild (int ind)
    {
        return (ind + 1) * 2 - 1;
    }
    public boolean isEmpty ()
    {
        return currSize == 0;
    }
    public T peek ()
    {
        if (currSize == 0) 
            throw new NoSuchElementException();
        return (T) elements[0];
    }
    public void poll ()
    {
        remove(0);
    }
    public void offer (T obj)
    {
        if (currSize == maxSize)
            throw new IndexOutOfBoundsException();
        Integer ind = indMap.get(obj);
        if (ind != null)
            throw new IllegalArgumentException("Element already exists in the heap.");
        elements[currSize] = obj;
        ind = currSize;
        currSize++;
        indMap.put(elements[ind], ind);
        int parentInd = getParent(ind);
        while (ind != 0 && ((T) elements[ind]).compareTo((T) elements[parentInd]) < 0)
        {
            Object tempEle = elements[parentInd];
            elements[parentInd] = elements[ind];
            elements[ind] = tempEle;
            indMap.put(elements[ind], ind);
            indMap.put(elements[parentInd], parentInd);
            ind = parentInd;
            parentInd = getParent(ind);
        }
    }
    public void remove (T obj)
    {
        Integer ind = indMap.get(obj);
        if (ind == null)
            throw new NoSuchElementException();
        remove(ind);
    }
    private void remove (int ind)
    {
        if (ind >= currSize)
            throw new NoSuchElementException();
        indMap.remove(elements[ind]);
        while (ind < currSize)
        {
            int childInd = getFirstChild(ind);
            boolean rightIsParent = childInd + 1 < currSize
                    && ((T) elements[childInd + 1]).compareTo((T) elements[childInd]) <= 0;
            elements[ind] = rightIsParent ? elements[childInd + 1] : (childInd < currSize ? elements[childInd] : null);
            if (elements[ind] != null)
            {
                indMap.put(elements[ind], ind);
            }
            ind = rightIsParent ? childInd + 1 : childInd;
        }
        ind = getParent(ind);
        if (ind == currSize - 1)
        {
            currSize--;
        }
        else
        {
            elements[ind] = elements[currSize - 1];
            indMap.put(elements[ind], ind);
            currSize--;
            int parentInd = getParent(ind);
            while (ind != 0 && ((T) elements[ind]).compareTo((T) elements[parentInd]) < 0)
            {
                Object tempEle = elements[parentInd];
                elements[parentInd] = elements[ind];
                elements[ind] = tempEle;
                indMap.put(elements[ind], ind);
                indMap.put(elements[parentInd], parentInd);
                ind = parentInd;
                parentInd = getParent(ind);
            }
        }
    }
//    public boolean validate ()
//    {
//        boolean valid = true;
//        for (int ind = 0; valid && ind < currSize; ind++)
//        {
//            int childInd = getFirstChild(ind);
//            if ((childInd >= currSize || ((T) elements[ind]).compareTo(((T) elements[childInd])) <= 0)
//                    && (childInd + 1 >= currSize || ((T) elements[ind]).compareTo(((T) elements[childInd + 1])) <= 0))
//            {
//
//            }
//            else
//            {
//                valid = false;
//            }
//        }
//        return valid;
//    }
}
