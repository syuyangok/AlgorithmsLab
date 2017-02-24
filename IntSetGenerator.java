import java.util.*;

class IntSetGenerator implements InstanceGenerator
{
    public Object makeInstance(long seed, long [] params)
    {
        return makeIntSet(seed, params[0], params[1], params[2]);
    }
    
    /**  
     * Create random arrays. 
     * @param seed is the value of seed. 
     * @param setsize is the size of set. 
     * @param max is the max value . 
     * @param percentordered is the value of percent of ordered. 
     * @return arrays. 
     */       
    public int[] makeIntSet(long seed, long setsize, long max, long percentordered)
    {
        int count = 0;
        int arraysize = Long.valueOf(setsize).intValue();
        int maxvalue = Long.valueOf(max).intValue();
        ArrayList<Integer> isetList = new ArrayList<>();//Create a new arraylist for easy sorting.
        Random randgen = new Random(seed);
        
        while (count < setsize)
        {
            isetList.add(randgen.nextInt(maxvalue)+1); // create random number and add to the arraylist
            count++;
        }
        
        //Above generarted a random arraylist.
        Collections.sort(isetList);// sort the arraylist
        
        //Create a new list and Copy arraylist value to it.
        int [] iset = new int[arraysize];
         for (int i =0; i < arraysize; i++)
         {
             iset[i] = isetList.get(i); 
         }
         
         // randomize list by percentorder
         int nonorder = 100 - (Long.valueOf(percentordered).intValue());
         int n = (nonorder*arraysize)/100;
         for (int k = n-1; k>=1; k--)
         {
             int j = randgen.nextInt(k)+1;
             int temp = iset[k];
             iset[k] = iset[j];
             iset[j] = temp;
         }
         
         
        return iset;
    }
    /**  
     * Create random sets. 
     * @param setsize is the size of set. 
     * @param max is the max value . 
     * @param percentordered is the value of percent of ordered. 
     * @return arrays. 
     */       
    public int[] makeIntSet(long setsize, long max, long percentordered)
    {
        int count = 0;
        int arraysize = Long.valueOf(setsize).intValue();
        int maxvalue = Long.valueOf(max).intValue();
        ArrayList<Integer> isetList = new ArrayList<>();
        
        Random randgen = new Random();
        
        while (count < setsize)
        {
            isetList.add(randgen.nextInt(maxvalue)+1); // create random number and add to the arraylist
            count++;
        }
        
        //Above generarted a random arraylist.
        Collections.sort(isetList);// sort the arraylist
        
        //Create a new list and Copy arraylist value to it.
        int [] iset = new int[arraysize];
         for (int i =0; i < arraysize; i++)
         {
             iset[i] = isetList.get(i); 
         }
         
         // randomize list by percentorder
         int nonorder = 100 - (Long.valueOf(percentordered).intValue());
         int n = (nonorder*arraysize)/100;
         for (int k = n-1; k>=1; k--)
         {
             int j = randgen.nextInt(k)+1;
             int temp = iset[k];
             iset[k] = iset[j];
             iset[j] = temp;
         }
                  
         
        return iset;
    }
    
    public static void main(String [] args)
    {
        int [] intset;
        IntSetGenerator setmaker = new IntSetGenerator();
        if (args.length == 2) // set size and max value
            intset = setmaker.makeIntSet(Long.parseLong(args[0]), Long.parseLong(args[1]), 0);
        else // seed, set size, and max value
            intset = setmaker.makeIntSet(Long.parseLong(args[0]), Long.parseLong(args[1]), Long.parseLong(args[2]), 0);
        for (int i: intset)
            System.out.print(" "+i);
        System.out.println();
    }
}
