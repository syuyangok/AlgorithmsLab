import java.util.*;
class KGreedyLessStep extends OptimizationAlgorithm
{
    //SwitchList<WBPair> pairlist;
    WBPair [] pairlist;
    long stepcount = 0;
    int paircount = 0;
    int keyindex = 0;
    int maxdepth = 0;
    long sizesum = 0;//Weight
    long valuesum = 0;//Benefit
    long maxsize = 0;
    long bestpack = 0;
    boolean dfault = true;
    
    public  KGreedyLessStep()
    {
        name = this.getClass().getName();
    }
    
    public  KGreedyLessStep(Mylistclass<WBPair> ilist)
    {
        name = this.getClass().getName();
        setInstance(ilist);
    }
    
    public void setInstance(Object instance)
    {
       
        
        Mylistclass<WBPair> inlist = (Mylistclass<WBPair>) instance;
         WBPair.setKey(2); // set key as ratio
         inlist.setOrder(1);//set list increase order, so largest value raito will be the last item.
        
        //inlist.showList();
        paircount = inlist.getSize();
        pairlist = new WBPair [paircount];
        int i=0;
        valuesum = sizesum = 0;
        for (WBPair pair: inlist)
        {
            pairlist[i++] = pair;
            sizesum += pair.getWeight();
            valuesum += pair.getBenefit();
        }
        setConstraint(0);
    }
    
    public void setConstraint(int i, long value)
    {
        if (i == 0)
            maxsize = value;
        else
        {
            System.out.println("Invalid constraint index");
            return;
        }
        dfault = false;
    }
    
    public void setConstraint(int i)
    {
        if (i != 0)
        {
            System.out.println("Invalid constraint index");
            return;
        }
        maxsize = (long) sizesum/2;
        dfault = true;
    }
    
    public long getStepCount()
    {
        return stepcount;
    }
    
    public long getSpace()
    {
        return maxdepth;
    }
    
    public long getLongResult()
    {
        return bestpack;
    }
    
    
    
    /**  
     * find the bestpack value in the list.
     */    
    public void run()
    {
        stepcount = 0;
        bestpack = 0;
        maxdepth = paircount;
        //showInstance();
        //report();

  
        
        long w =0;
        int i = pairlist.length-1;
        while (w < maxsize && (i >= 0) )
        {
            
            WBPair maxTarget = pairlist[i];//Found the max ratio target            
            
            long a = Math.min(maxTarget.getWeight(), (maxsize - w) );      
            
            if (a == maxTarget.getWeight())//the current item can put in bag.
            {
                bestpack = bestpack + maxTarget.getBenefit();
                w = w + a;
            }

            i--; //Move to next            
            stepcount++;            
                
        }  
             
    }//run end
 
    private void report()
    {
        System.out.println(getName()+" Pair count: " + paircount + " Size: " + sizesum + " Value: " + valuesum);
        System.out.println("    Target size: " + maxsize); 
    }
    
    public void showInstance()
    {
        System.err.println("List: ");
        for (WBPair pair: pairlist)
            System.err.print(pair.toString()+" ");
        System.err.println();
        System.err.println("Pair count: " + paircount + " Size: " + sizesum + " Value: " + valuesum);
        System.err.println("Target size: " + maxsize);
    }
}
