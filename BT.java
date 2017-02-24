import java.util.*;
class BT extends OptimizationAlgorithm
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
    
    public BT()
    {
        name = this.getClass().getName();
    }
    
    public BT(Mylistclass<WBPair> ilist)
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
    
    public void run()
    {
        stepcount = 0;
        maxdepth = 0;
        //showInstance();
        //report();
        bestpack = findSub(0, maxsize, 0, sizesum, valuesum, 0);
    }
    
    public long findSub(int index, long maxsize, long packvalue, long sizesum, long valuesum, int depth)
    {
        //System.out.println("findSub "+index+" "+maxsize+" "+packvalue+" "+sizesum+" "+valuesum+" "+depth);
        WBPair curpair;
        long packwith, packsans;
        stepcount = stepcount + 1;
        depth = depth + 1;
        if (depth > maxdepth)
            maxdepth = depth;
        if (maxsize < 0)
            return 0;
        else if (index < 0 || index >= paircount)
            return packvalue;
        else if (maxsize >= sizesum)  // all the rest will fit
            return packvalue + valuesum;
        curpair = pairlist[index];
        //System.err.println("Processing " + curpair.toString());
        packwith = findSub(index+1, maxsize-curpair.getWeight(), packvalue+curpair.getBenefit(),
                           sizesum-curpair.getWeight(), valuesum-curpair.getBenefit(), depth);
        packsans = findSub(index+1, maxsize, packvalue, sizesum-curpair.getWeight(),
                           valuesum-curpair.getBenefit(), depth);
        return Math.max(packwith, packsans);
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
