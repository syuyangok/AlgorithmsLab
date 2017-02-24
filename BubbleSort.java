class BubbleSort extends DecisionAlgorithm
{
   int [] intset;
   long stepcount = 0;
   int numcount = 0;
   int maxdepth = 0;
   long setsum = 0;

   public BubbleSort()
   {
      name = "Bubble Sort";
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
     * sort an array with bubblesort. 
     * it will call merge() methods. 
     */   
   private void sortSet()
   {
      int lastPos, index, temp;
      
      for (lastPos = intset.length-1; lastPos >=0; lastPos--)
      {
          for (index =0; index <= lastPos-1; index++)
          {   
              stepcount++;
              if(intset[index] > intset[index+1])
              {
                  
                  temp = intset[index];
                  intset[index] = intset[index +1];
                  intset[index+1] = temp;
              }

          }
      }
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
      SelectSort sort = new SelectSort();
      IntSetGenerator setgen = new IntSetGenerator();
      int [] set = setgen.makeIntSet(10, 100, 0);
      sort.setInstance(set);
      sort.showInstance();
      sort.run();
      sort.showInstance();
      System.out.println("Steps: "+sort.getStepCount()+" Space: "+sort.getSpace()+" Result: "+sort.getBooleanResult());
   }
}
