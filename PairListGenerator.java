import java.util.*;

class PairListGenerator implements InstanceGenerator
{
    public Object makeInstance(long seed, long [] params)
    {
        return makeIntSet(seed, params[0], params[1], params[2]);
    }
    
    
    /**  
     * Create random switchable sets. 
     * @param setsize is the size of set. 
     * @param wmax is the max weight value . 
     * @param wmax is the max weight value  
     * @return switchable sets. 
     */       
    public Mylistclass<WBPair> makeIntSet(long setsize, long wmax, long bmax)
    {
        
        int count = 0;
        int arraysize = Long.valueOf(setsize).intValue();
        int wmaxvalue = Long.valueOf(wmax).intValue();
        int bmaxvalue = Long.valueOf(bmax).intValue();
        
        Mylistclass<WBPair> iset = new Mylistclass<WBPair>();
        Random randgen = new Random();
        
        while (count < setsize)
        {
            WBPair temp = new WBPair(randgen.nextInt(wmaxvalue)+1, randgen.nextInt(bmaxvalue)+1);
            iset.add(temp);
            count++;
        }
        return iset;
    }
    
    
    public Mylistclass<WBPair> makeIntSet(long seed, long setsize,  long wmax, long bmax)
    {
        int count = 0;
        int arraysize = Long.valueOf(setsize).intValue();
        int wmaxvalue = Long.valueOf(wmax).intValue();
        int bmaxvalue = Long.valueOf(bmax).intValue();
        
        Mylistclass<WBPair> iset = new Mylistclass<WBPair>();
        Random randgen = new Random(seed);
        
        while (count < setsize)
        {
            WBPair temp = new WBPair(randgen.nextInt(wmaxvalue)+1, randgen.nextInt(bmaxvalue)+1);
            iset.add(temp);
            count++;
        }
        return iset;
    }
    
    
    
    public static void main(String [] args)
    {
        Mylistclass<WBPair> intset;
        PairListGenerator setmaker = new PairListGenerator();
        if (args.length == 2) // set size and max value
            intset = setmaker.makeIntSet(Long.parseLong(args[0]), Long.parseLong(args[1]), 0);
        else // seed, set size, and max value
            intset = setmaker.makeIntSet(Long.parseLong(args[0]), Long.parseLong(args[1]), Long.parseLong(args[2]), 0);
        //for (int i: intset)
        //System.out.print(" "+i);
        System.out.println();
    }
}
