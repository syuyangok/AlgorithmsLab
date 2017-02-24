/** 
 *The Mylistclass. It's purppose is to  defines a SwitchalbleList.
 * It can be use for create switchable list, which has add, delete, sort, duplicate etc. functions. 
 * To encapsulate, excecpt the fileds and  methds inherits from abstract class, all other fields and methods are protectd or private.
  *@verison CS242 Program1 
 */ 

import java.util.NoSuchElementException;
import java.util.Iterator;

class Mylistclass<E extends Comparable<E>>
    extends SwitchableList<E> 
    
{
    //these fields are private or protected to encapsulate.
    private int N;         // The size or length of List. Int type.
    protected Node first;    // Head of  Node. Node type.
    protected Node last;     // Tail of  Node. Node type.
    protected int ordercode;  // Order Code. Int type. If the value is 0, meaning none sorted. 
    //If the value is 1, meaning ascending sorted. If the value is 2, meaning descending sorted. 
    protected boolean dupok;  // duplicate indicator. Boolean type.
    //If the value is ture,meaning duplicate allowed, otherwise, duplicate not allowed. 
    
    
    /** 
     * The Node Class Stores a E type element and references to next and previous node. 
     */    
    protected class Node  
    {   
        protected E value;   
        protected Node next;   
        protected Node prev;   
        
        /** 
         * Node Constructor.To initial a node with parameters.
         * @param val is the element to be store in the node. 
         * @param n is the next node of the current node.
         * @param p is the previous node of the current node.
         */     
        Node(E val, Node n, Node p)
        {
            value = val;
            next = n;
            prev = p;
            
        }    
        /** 
         * Node Constructor.To initial a node with parameters.
         * @param val is the element to be store in the node. 
         */     
        Node(E val)
        {
            this(val, null, null);
        }
    } //Inner Node class end.  
    
    /** 
     * set Default Constructor. .To initial a list without parameters
     */    
    public Mylistclass()                         
    { 
        first = null; 
        last = null; 
        N = 0; 
        ordercode = NONE;
        dupok = true;
    } 
    
    /** 
     * Identify if the list is empty 
     * @return true if the list is empty. 
     */      
    private  boolean isEmpty()
    {
        return first == null;
    }    
    
    
    
    
    /** 
     * Return an iterator for the list 
     * @return an interator. 
     * it will call DoubleListIterator(). 
     */   
    public Iterator<E>  iterator()        
    {
        return new DoubleListIterator(); 
    }
    
    /** 
     * A  DoubleListIterator. 
     * it implements iterator.
     * it has hasNext(), next(), remove() methods. 
     * it will throw NoSuchElementException, IllegalStateException.
     */       
    private class DoubleListIterator implements java.util.Iterator<E> 
    {  
        
        private Node current      = first;
        private Node lastAccessed = null; // the last node to be returned by prev() or next()
        // reset to null upon intervening remove() or add()
        private int index = 0;
        
        public boolean hasNext()      
        { 
            return index < N; 
        }
        
        public E next() 
        {
            if (!hasNext()) 
            {
                throw new NoSuchElementException();
            }
            lastAccessed = current;
            E item = current.value;
            current = current.next; 
            index++;
            return item;
        }
        
        // remove the element that was last accessed by next() 
        // condition: no calls to remove()  after last call to next() 
        public void remove() 
        { 
            if (lastAccessed == null) throw new IllegalStateException();
            Node x = lastAccessed.prev;
            Node y = lastAccessed.next;
            x.next = y;
            y.prev = x;
            N--;
            if (current == lastAccessed)
                current = y;
            else
                index--;
            lastAccessed = null;
        }
        
    }
    
    
    /** 
     * sort the list to increase order.
     * it will call comapreTo() method.
     */         
    private void sort()
    {
        E key;
        Node current = first.next;
        while(current != null)
        {
            key = current.value;
            Node pre = current;
            
            while(pre.prev != null && (pre.prev.value.compareTo(key)) > 0 )
            {
                pre.value = pre.prev.value;
                pre = pre.prev;
            }   
            pre.value = key;
            
            current = current.next;
        }
        
    }
    
    
    /** 
     * Reverse the list .
     * it will call comapreTo() method.
     */         
    public void reverse()
    {
        
        Node tempLast = last;
        Node current = last;
        
        while(current != null)
        {
            Node tempNext = current.next;
            Node tempPrev = current.prev;
            current.next = tempPrev;
            current.prev = tempNext; 
            
            if (current.next == null)
            {
                break;
            }
            current = current.next;  
            
        }
        last = current;
        first = tempLast;
        
    }
    
    /** 
     * Add element when list is set UP.
     * Didn't deal with empty list.
     * @param emelment is the value to add in the list.
     * it will call comapreTo(), allowsDuplicates() methods.
     */   
    private void addUP(E element)
    {
        Node current = first;
        if (current.value.compareTo(element) > 0)//deal with first
        {
            first = new Node(element, current, null);
            current.prev = first;                    
        }
        else 
        {
            while (current != null && current.value.compareTo(element) < 0)
            {
                current = current.next;
            }
            if (current == null) // add last position.
            {
                last.next = new Node(element, null, last);
                last = last.next;
                
            }
            
            
            else if (allowsDuplicates() || current.value.compareTo(element) > 0) // insert before current
            {
                Node temp = current.prev;
                temp.next = new Node(element, current, temp); 
                current.prev = temp.next;
            }
        }
    }
    
    /** 
     * Add element at end.
     * Didn't deal with empty list.
     * @param emelment is the value to add in the list.
     * it will call isEmpty() methods.
     */   
    protected void addAtEnd(E element)
    {
        if (isEmpty())//deal with empty list
        {
            last = new Node(element);
            first = last;
        }
        else
        {
            last.next = new Node(element, null, last);
            last = last.next;
        }
        N++;
    }
    
    
    /** 
     * Merge two ascending list
     * @param S1 is the list to be merged in.
     * @param S2 is the list to be merged from..
     * @return node is the merged first node. 
     * it will call addAtEnd(), compareTo() methods.
     */  
    private Node mergeUP(Mylistclass<E> S1, Mylistclass<E> S2)
    {
        // build a new length list
        Mylistclass<E> S = new  Mylistclass<E>();
        
        
        // Pick the smaller value
        
        Node current1 = S1.first;
        Node current2 = S2.first;
        
        
        
        while (current1 != null && current2 != null)
        {
            if (current1.value.compareTo(current2.value) <=0)
            {
                S.addAtEnd(current1.value);
                current1 = current1.next;                
            }
            else
            {
                S.addAtEnd(current2.value);
                current2 = current2.next;  
            }
        }
        
        
        if (current1 == null) // Run out of S1, add S2 rest.
        {
            while (current2 != null)
            {
                S.addAtEnd(current2.value);
                
                current2 = current2.next; 
                
                
            }
            
            
        }
        
        if (current2 == null) // Run out of S2, add S1 rest.
        {
            while (current1 != null)
            {
                S.addAtEnd(current1.value);
                
                current1 = current1.next;  
            }
            
        }
        
        S1.last = S.last;
        return S.first;        
        
    }
    
    
    /** 
     * Merge two descending list
     * @param S1 is the list to be merged in.
     * @param S2 is the list to be merged from.
     * @return node is the merged first node. 
     * it will call addAtEnd(), compareTo() method.
     */  
    private Node mergeDOWN(Mylistclass<E> S1, Mylistclass<E> S2)
    {
        // build a new length list
        Mylistclass<E> S = new  Mylistclass<E>();
        
        
        // Pick the smaller value
        
        Node current1 = S1.first;
        Node current2 = S2.first;
        
        
        
        while (current1 != null && current2 != null)
        {
            if (current1.value.compareTo(current2.value) >=0)
            {
                S.addAtEnd(current1.value);
                current1 = current1.next;                
            }
            else
            {
                S.addAtEnd(current2.value);
                current2 = current2.next;  
            }
        }
        
        
        if (current1 == null) // Run out of S1, add S2 rest.
        {
            while (current2 != null)
            {
                S.addAtEnd(current2.value);
                
                current2 = current2.next; 
                
                
            }
            
            
        }
        
        if (current2 == null) // Run out of S2, add S1 rest.
        {
            while (current1 != null)
            {
                S.addAtEnd(current1.value);
                
                current1 = current1.next;  
            }
            
        }
        
        S1.last = S.last;
        return S.first;        
        
    }
    
    
    
    /** 
     * Delete all from sorted list
     * two list are both ascending.
     * @param S1 is the list delet from.
     * @param S2 is the targetlist to be deleted.
     * It will call campareTo() methods.
     */ 
    private void delAllUP(Mylistclass<E> S1, Mylistclass<E> S2)
    {
        Node current1 = S1.first;
        Node current2 = S2.first;       
        
        
        while (current1 != null && current2 != null)
        {
            if (current1.value.compareTo(current2.value) <0)
            {
                current1 = current1.next;    
                
            }
            else if (current1.value.compareTo(current2.value) > 0)
            {
                current2 = current2.next;      
            }
            else
            {
                Node pred = current1.prev;
                Node succ = current1.next;
                
                if (pred == null) // if target is head.
                {
                    first = succ;   
                }
                else
                {
                    pred.next = succ;
                }
                
                if (succ == null) // if target is tail.
                {
                    last = pred;
                }
                else
                {
                    succ.prev = pred;
                }       
                current1 = succ; 
                N--;
                
            }
        }
    }
    
    
    
    /** 
     * Delete all from sorted list
     * two list are both Descending.
     * @param S1 is the list delet from.
     * @param S2 is the targetlist to be deleted.
     *  It will call campareTo() methods.
     */ 
    private void delAllDOWN(Mylistclass<E> S1, Mylistclass<E> S2)
    {
        Node current1 = S1.first;
        Node current2 = S2.first;       
        
        
        while (current1 != null && current2 != null)
        {
            if (current1.value.compareTo(current2.value) <0)
            {
                current2 = current2.next;    
                
            }
            else if (current1.value.compareTo(current2.value) > 0)
            {
                current1 = current1.next;      
            }
            else
            {
                Node pred = current1.prev;
                Node succ = current1.next;
                
                if (pred == null) // if target is head.
                {
                    first = succ;   
                }
                else
                {
                    pred.next = succ;
                }
                
                if (succ == null) // if target is tail.
                {
                    last = pred;
                }
                else
                {
                    succ.prev = pred;
                }       
                current1 = succ; 
                N--;
                
            }
        }
    }
    //////////////////////////////////////////////Following methods are inherit from Abstract.    
    
    /** 
     * Returns value of size 
     * @return the current value of N. 
     */       
    public int getSize()
    {
        return N;
    }
    
    /** 
     * get the element at index.
     * @param index to set the value of element to be returned.
     * @return the current E type value at given index. 
     * if index not in range, throw IndexOutOfBoundsException.
     * it will call getSize() method.
     */       
    public  E getElementAt(int index) // index start at 0
    {
        if (index < 0 || index >= getSize())
        {  
            throw new IndexOutOfBoundsException();  
        }  
        Node current = first;
        int cIndex = 0;
        while (cIndex != index)
        {
            current = current.next;
            cIndex ++;
        }
        return current.value;
        
    }
    
    /** 
     * add the new element.
     * @param element is the new element to be added.
     * @return true if added new element, otherwise return false.
     * if element is null, throw NullPointerException.
     * it will call isEmpty(), allowsDuplicates(), contains()method.
     */         
    public  boolean add(E element)
    {
        boolean added = false;
        if (element == null)
        {  
            throw new java.lang.NullPointerException();  
        }  
        
        if (isEmpty())//deal with empty list
        {
            last = new Node(element);
            first = last;
            N++;
            added = true;
        }
        else // not empty list
        {
            if (ordercode == NONE )// add element to the last position
            {
                if (allowsDuplicates() || !contains(element))
                {
                    last.next = new Node(element, null, last);
                    last = last.next;
                    N++;     
                    added = true;
                }
                
            }
            else if (ordercode == UP) // go over list and add element.
            {
                if (allowsDuplicates() || !contains(element))
                {
                    addUP(element);     
                    N++;     
                    added = true;
                }
            }
            
            else if (ordercode == DOWN) //DOWN condition
            {
                if (allowsDuplicates() || !contains(element))
                {
                    reverse();
                    addUP(element);
                    reverse();
                    N++;     
                    added = true;
                }
            }           
            
            
        }
        return added;
    }
    
    
    
    /** 
     * add another list to this list.
     * @param inOthers is the list to be add.
     * It has several situations: Both sorted; Only this sorted; This sorted with/out duplciate.
     * It will call isSorted(), isAscending(), reverse(), getSize() methods
     * It will call allowsDuplicates(), setDuplicatesAllowed(), mergeUP(),mergeDOWN() methods.
     */          
    public void addAll(SwitchableList<E> inOthers)
    {
        Mylistclass<E> others = (Mylistclass<E>)inOthers;
        
        //both sorted
        if (this.isSorted() && others.isSorted())
        {
            if (others.isAscending())
            {
                
                if (this.isAscending())
                {
                    this.first = mergeUP(this, others);
                }
                else               
                {
                    others.reverse();
                    
                    this.first = mergeDOWN(this, others);
                    
                    others.reverse();
                }
            }
            
            else //others isDscending()
            {
                if (this.isAscending())
                {
                    others.reverse();
                    this.first = mergeUP(this, others);
                    others.reverse();
                }
                else
                {
                    this.first = mergeDOWN(this, others);
                }
            }  
            
        }// Both sorted End.
        
        
        
        //this sorted and others not sorted
        if (this.isSorted() && !(others.isSorted())) 
        {
            if(this.isAscending())
            {
                for (int i = 0; i < others.getSize(); i++)
                {
                    this.addUP(others.getElementAt(i));
                }
            }
            
            if(this.isDescending())
            {
                this.reverse();
                for (int i = 0; i < others.getSize(); i++)
                {
                    this.addUP(others.getElementAt(i));
                }
                this.reverse();
            }
        }
        
        
        //if not sorted.
        if (!(this.isSorted()))
        {
            //just at to the end
            
            
            this.last.next = others.first;
            others.first.prev = this.last;
            this.last = others.last; 
            
            
        }
        
        if(!(this.allowsDuplicates())) //Not allow duplicate, remove duplicate.
        {
            this.setDuplicatesAllowed(false);
        }
        N = N + others.getSize();
    }
    
    
    /** 
     * delete the target element.
     * @param element is the target to be deleted.
     * @return true if made changes. 
     * if target element is null, throw NullPointerException.
     * it will call isEmpty() method.
     * it will call equals() method.
     */  
    
    public  boolean delete(E target)
    {
        if (target == null)
        {  
            throw new java.lang.NullPointerException();  
        }  
        if (isEmpty())// if the list is empty.
        {
            return false;
        }
        
        Node temp = first;
        while (temp != null && !target.equals(temp.value))
        {
            temp = temp.next;
        }
        if (temp == null)// if target is not in list.
        {  
            return false;  
        }
        
        Node pred = temp.prev;
        Node succ = temp.next;
        
        if (pred == null) // if target is head.
        {
            first = succ;   
        }
        else
        {
            pred.next = succ;
        }
        
        if (succ == null) // if target is tail.
        {
            last = pred;
        }
        else
        {
            succ.prev = pred;
        }        
        N--;
        return true;
    }
    
    
    /** 
     * delte another list from this list.
     * @param inOthers is the list to be deleted.
     * It has two situations: Both sorted; Or not.
     * It will call isSorted(), isAscending(), reverse() methods
     * It will call delAllUP(), delAllDOWN() methods.
     */         
    public   void deleteAll(SwitchableList<E> inOthers)
    {
        
        Mylistclass<E> others = (Mylistclass<E>)inOthers;
        
        
        
        if (isSorted() && others.isSorted()) //deal with sorted
        { 
            if(others.isAscending())//others is ascending
            {
                if (this.isAscending())
                { 
                    delAllUP(this, others);
                }
                else
                {
                    this.reverse();
                    delAllUP(this, others);
                    this.reverse();
                }
                
            }
            else //others is descending
            {
                if (this.isDescending())
                { 
                    delAllDOWN(this, others);
                }
                else
                {
                    this.reverse();
                    delAllDOWN(this, others);
                    this.reverse();
                }
                
            }
            
            
        }//Deal with sorted END
        
        else  //deal with unsorted
        { 
            Node current = others.first;
            while(current != null)
            {
                this.delete(current.value);
                current = current.next;
            }
            
        }
        
    }
    
    /** 
     * search the target element.
     * @param element is the target to be searched.
     * @return true if the list contains target elemment. 
     * if target element is null, throw NullPointerException.
     * it will call equals() method.
     * O(n).
     */      
    public   boolean contains(E target)
    {
        
        if (target == null)
        {  
            throw new java.lang.NullPointerException();  
        }  
        
        Node current = first;
        while (current != null)
        {
            if (target.equals(current.value))
            {
                return true;
            }
            current = current.next;   
        }
        return false;
    }
    
    /** 
     * reset the list to empty.
     * O(1).
     */ 
    public  void clear()
    {
        first = null; 
        last = null; 
        N = 0; 
        
    }
    
    /** 
     * Set the list order.
     * @param inOrdercode is the value to be as ordercode.
     * it will call equals(), isEmpty(), sort(), reverse() methods.
     * O(n).
     */        
    public   void setOrder(int inOrdercode)
    {
        if (isEmpty() || inOrdercode == 0)// if the list is empty.
        {
            ordercode = inOrdercode;
        }
        
        else if (ordercode != inOrdercode)
        {
            if (inOrdercode == UP)
            {
                this.sort();
            }
            else if (inOrdercode == DOWN)
            {
                this.sort();
                this.reverse();
            }
            ordercode = inOrdercode;
        }
    }
    
    /** 
     * identify if the list is ordered.
     * @return true if the order of the list is UP or DOWN, false otherwise. 
     * O(1).
     */      
    public   boolean isSorted()
    {
        return ordercode != NONE;
    }
    
    /** 
     * identify if the list is non-decreasing order.
     * @return true if the list is sorted in UP order,  false otherwise. 
     * O(1).
     */   
    public   boolean isAscending()
    {
        return ordercode == UP;
    }
    
    /** 
     * identify if the list is non-increasing order.
     * @return true if the list is sorted in DOWN order,  false otherwise. 
     * O(1).
     */   
    public   boolean isDescending()
    {
        return ordercode == DOWN;
    }
    
    /** 
     * sets dupok value to indicate duplicates are accepted or not.
     * Duplicates must be removed when switching from duplicates allowed to not allowed. 
     * @param inDupok is the  value to be the new value of dupok.
     * it will call equals() method.
     * O(n) for ordered lists, O(n2) for unordered lists.
     */  
    
    public   void setDuplicatesAllowed(boolean inDupok)
    {
        dupok = inDupok;
        
        if (dupok == false)//if change to false
        {
            if (!isSorted())// Remove dup from unsort list
            {
                Node current = first;
                
                while(current.next != null)
                {
                    
                    Node temp = current.next;
                    while(temp != null)
                    {
                        Node pred = temp.prev;
                        Node succ = temp.next;  
                        
                        if (temp.value.compareTo(current.value) ==0)//find duplicated and remove it
                        {
                            
                            pred.next = succ;                                  
                            if (succ == null) // if target is tail.
                            {
                                last = pred;
                            }
                            
                            else
                            {
                                succ.prev = pred;
                            }                                  
                            N--;
                            
                        }
                        
                        temp = succ;
                    }//inner loop end.
                    
                    
                    if (current.next != null)
                    {
                        current = current.next;
                    }
                }//outer loop end.
            }
            else // Remove dup from sorted list
            {
                Node temp = first;
                while(temp.next != null)
                {
                    
                    if (temp.value.compareTo(temp.next.value) ==0) //find duplicated and remove it
                    {
                        Node pred = temp.next.prev;
                        Node succ = temp.next.next;                                
                        
                        pred.next = succ;  
                        
                        if (succ == null) // if target is tail.
                        {
                            last = pred;
                        }
                        else
                        {
                            succ.prev = pred;
                        }        
                        N--;
                    }
                    
                    else //No duplicated and move pointer to next nod
                    {
                        temp = temp.next;
                    }
                }//while loop end.
                
            }
        }
    }
    
    
    
    /** 
     * identify if the list duplicate allowed.
     * @return trueif duplicates are currently allowed, false otherwise. 
     * O(1).
     */   
    public   boolean allowsDuplicates()
    {
        return dupok == true;
        
    }
    
    
    
}//class end.
