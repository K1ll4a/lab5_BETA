import Mclass.*;


import java.util.ArrayList;



public class Main {
    public static void main(String[] args)  {
        Filler filler = new Filler();
        Mclass Product = new Mclass();




        ArrayList<Mclass> arrayList= new ArrayList<Mclass>();

        filler.Fill(Product);
        arrayList.add(Product);
        System.out.println(arrayList);
        for(Mclass pr : arrayList){
            System.out.println(pr);
        }
    }
}


