class MergeSort extends DecisionAlgorithm
{
    int [] intset;
    long stepcount = 0;
    int numcount = 0;
    long setsum = 0;
    
    private int[] aux;// create a auxiliary array
    
    public MergeSort()
    {
        name = "Merge Sort";
    }
    
    public void setInstance(Object instance)
    {
        int [] iset = (int []) instance;
        numcount = iset.length;
        intset = new int [numcount];
        setsum = 0;
        for (int pos=0; pos<numcount; pos++)
        {
            intset[pos] = iset[pos];
            setsum = setsum + intset[pos];
        }
    }
    
    public void setConstraint(int i, long value)
    {
        // not needed
    }
    
    public void setConstraint(int i)
    {
        // not needed
    }
    
    public long getStepCount()
    {
        return stepcount;
    }
    
    public long getSpace()
    {
        return numcount;
    }
    
    public boolean getBooleanResult()
    {
        return orderCheck();
    }
    
    public void run()
    {
        stepcount = 0;
        
        aux = new int[intset.length];// allocate space only one time;
        numcount = numcount + intset.length;
        
        sortSet(intset, 0, intset.length-1);
    }
    
    /**  
     * Mersort array. 
     * @param a is the array to be sorted. 
     * @param lo is the value of low bound. 
     * @param hi is the value of high bound. 
     * it will call merge() methods. 
     */   
    public void sortSet(int[] a, int lo, int hi )
    {
        if (hi <= lo)
        {
            return;
        }
        int mid = lo + (hi - lo)/2;
        
        sortSet(a, lo, mid);
        sortSet(a, mid+1, hi);
        merge (a, lo, mid, hi);
        
        
    }
    
    
    /**  
     * merge array. 
     * @param a is the array to be sorted. 
     * @param lo is the value of low bound. 
     * @param mid is the value of middle. 
     * @param hi is the value of high bound. 
     * it will use aux array. 
     */   
    private  void merge (int[] a, int lo, int mid, int hi)
    {
        int i = lo;
        int j = mid +1;
        
        
        for (int k = lo; k <= hi; k++) // copy to auxiliary array
        {
            aux[k] = a[k];//?????
            stepcount++;
        }
        
        for (int k = lo; k <= hi; k++)
        {
            stepcount++;
            if (i > mid)
            {   
                a[k] = aux[j++];
            }
            else if (j > hi)
            {
                a[k] = aux[i++];
            }
            else if (aux[j] < aux[i])
            {
                a[k] = aux[j++];
            }
            else
            {
                a[k] = aux[i++];
            }
            
        }
        
    }
    
    
    private boolean orderCheck()
    {
        long sortedsum = 0;
        boolean result = true;
        int n = intset.length;
        for (int i=0; i<n-1; i++)
        {
            sortedsum = sortedsum + intset[i];
            if (intset[i] > intset[i+1])
            {
                result = false;
                System.out.println("*** out of order");
                break;
            }
        }
        if (setsum != sortedsum + intset[n-1])
            result = false;
        if (result == false)
        {
            System.out.println("*** sort failure");
            showInstance();
        }
        return result;
    }
    
    public void showInstance()
    {
        for (int i=0; i<intset.length; i++)
            System.out.print(" "+intset[i]);
        System.out.println();
    }
    
    public static void main (String [] args)
    {
        InsertSort sort = new InsertSort();
        IntSetGenerator setgen = new IntSetGenerator();
        int [] set = setgen.makeIntSet(10, 100, 0);
        sort.setInstance(set);
        sort.showInstance();
        sort.run();
        sort.showInstance();
        System.out.println("Steps: "+sort.getStepCount()+" Space: "+sort.getSpace()+" Result: "+sort.getBooleanResult());
    }
}
