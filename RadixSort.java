import java.util.*;

class RadixSort extends DecisionAlgorithm
{
    int [] intset;
    long stepcount = 0;
    int numcount = 0;
    long setsum = 0;
    
    public RadixSort()
    {
        name = "Radix Sort";
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
        numcount = numcount + intset.length;    
        sortSet();
    }
    
    /**  
     * It will sort array with Radix sort . 
     * it will also use bucket sort. 
     */    
    public void sortSet() 
    {
        final int D = 10;
        
        List<Integer>[] bucket = new ArrayList[D];
        for (int i = 0; i < bucket.length; i++) 
        {
            bucket[i] = new ArrayList<Integer>();
            stepcount++;//becase 10 is constant, so this numcount can be ignored.
            
        }        
        
        
        int temp = -1;
        int placement = 1;
        boolean len = false;
        
        while (!len) 
        {
            len = true;
            
            for (Integer i : intset) 
            {
                temp = i / placement;
                bucket[temp % D].add(i);
                stepcount++;
                if (len && temp > 0) 
                {
                    len = false;
                }
            }
            
            int a = 0;
            for (int b = 0; b < D; b++) // copy lists into array
            {
                for (Integer i : bucket[b]) 
                {
                    intset[a++] = i;
                    stepcount++;
                }
                bucket[b].clear();
            }
            
            placement *= D;
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
