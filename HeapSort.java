class HeapSort extends DecisionAlgorithm
{
   int [] intset;
   long stepcount = 0;
   int numcount = 0;
   long setsum = 0;

   public HeapSort()
   {
      name = "Heap Sort";
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
      sortSet();
   }

   /**  
    * sort an array with Heapsort. 
    * it will call exch() methods. 
    * it will call sink() methods. 
    */   
   public void sortSet()
   {
       int N = intset.length;
        for (int k = N/2; k >= 1; k--)
        {
            
            sink(intset, k, N);
        }
        while (N > 1) 
        {
            exch(intset, 1, N--);
            
            sink(intset, 1, N);
            
        }

   }
   
    /**  
     * sink element in to  array and repair heap. 
     * @param list is the array to be sorted. 
     * @param k is the value of index. 
     * @param n is the value of length. 
     * it will call exch()method. 
     */   
    private  void sink(int[] list, int k, int n) 
    {
        while (2*k <= n) 
        {
            int j = 2*k;
            if (j < n && list[j-1] < list[j]) 
            {
                j++;
            }
            if (list[k-1] >= list[j-1]) 
            {
                
                break;
            }
            stepcount++;
            exch(list, k, j);
            k = j;
        }
    }   

    /**  
     * exch element in  array. 
     * @param list is the array to be sorted. 
     * @param i is the value of index. 
     * @param j is the value of length. 
     * it will call exch()method. 
     */      
    private  void exch(int[] list, int i, int j) 
    {
        stepcount++;
        int temp = list[i-1];
        list[i-1] = list[j-1];
        list[j-1] = temp;
    }    
   private boolean orderCheck()
   {
      long sortedsum = 0;
      boolean result = true;
      for (int i=0; i<numcount-1; i++)
      {
         sortedsum = sortedsum + intset[i];
         if (intset[i] > intset[i+1])
         {
            result = false;
            System.out.println("*** out of order");
            break;
         }
      }
      if (setsum != sortedsum + intset[numcount-1])
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
