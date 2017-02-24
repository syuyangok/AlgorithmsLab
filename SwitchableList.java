abstract class  SwitchableList <ElementType extends Comparable<ElementType>>
     implements Iterable<ElementType>
{
    public static final int NONE = 0;
    public static final int UP = 1;
    public static final int DOWN = 2;
    public abstract  int getSize();
    public abstract  ElementType getElementAt(int index);
    public abstract  boolean add(ElementType element);
    public abstract void addAll(SwitchableList<ElementType> others);
    public abstract  boolean delete(ElementType target);
    public abstract  void deleteAll(SwitchableList<ElementType> others);
    public abstract  boolean contains(ElementType target);
    public abstract  void clear();
    
    public abstract  void setOrder(int ordercode);
    public abstract  void setDuplicatesAllowed(boolean dupok);
    public abstract  boolean allowsDuplicates();
    public abstract  boolean isSorted();
    public abstract  boolean isAscending();
    public abstract  boolean isDescending();
    
}
