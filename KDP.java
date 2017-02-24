import java.util.*;
class KDP extends OptimizationAlgorithm
{
    //SwitchList<WBPair> pairlist;
    WBPair [] pairlist;
    long stepcount = 0;
    int paircount = 0;
    int keyindex = 0;
    int maxdepth = 0;
    long sizesum = 0;
    long valuesum = 0;
    long maxsize = 0;
    long bestpack = 0;
    boolean dfault = true;
    
    public KDP()
    {
        name = this.getClass().getName();
    }
    
    public KDP(Mylistclass<WBPair> ilist)
    {
        name = this.getClass().getName();
        setInstance(ilist);
    }
    
    public void setInstance(Object instance)
    {
        Mylistclass<WBPair> inlist = (Mylistclass<WBPair>) instance;
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
        maxdepth = paircount;
        //showInstance();
        //report();
        long[] B = new long[(int)maxsize+1];//New array to store best benefit per weight.
        for (int w = 0; w <= maxsize; w++ )
        {
            B[w] = 0;
            stepcount++;
            maxdepth++;
        }
        
        for (int k = 0; k <= paircount-1; k++)
        {
            for (int w = (int)maxsize; w >= pairlist[k].getWeight(); w-- )
            {
                if(B[(int)(w - pairlist[k].getWeight())] + pairlist[k].getBenefit() > B[w])
                {
                    B[w] =( B[(int)(w - pairlist[k].getWeight())] + pairlist[k].getBenefit()) ;
                    stepcount++;
                }
                
            }
            
        }
        bestpack = B[(int)maxsize];  
        
        
    }
    
    
    
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
