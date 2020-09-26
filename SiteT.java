
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class SiteT {
	// les Attributs
    
	public static int N=5; // taille du tempon
	public static int nbPlaceslibre=N;
	public static int nbMessages=0;
	public static int IN=0,OUT=0;// in :indice d'insertion, out: indice d'extraction 
	public static String Tempon[]=new String[N];
    public static  Lock lock = new ReentrantLock();
	
	/*public SiteT() {
		Tempon = 

	}*/
	
	public static void afficherTempon() {
        System.out.println();
		System.out.println("*************************************");
		for(String s :Tempon) {
			System.out.print(s+" / ");
		}
        System.out.println();
		System.out.println("*************************************");
	}
	
	public synchronized static void produire(String m) throws InterruptedException{

            Tempon[IN]=m;
            IN=(IN+1)%N;
            nbMessages++;
            nbPlaceslibre--;
            afficherTempon();
            //Thread.sleep(5000);
      
        
	}
	
	public synchronized static String consommer() throws InterruptedException{

       
            String m="";
            m=Tempon[OUT];
            Tempon[OUT]=null;
            OUT=(OUT+1)%N;
            nbMessages--;
            nbPlaceslibre++;
            afficherTempon();
           // Thread.sleep(5000);
         return m;
        
	}
	
	public static void main(String[] args) throws IOException {
		int port=4020;
        ServerSocket se = null;
        Socket sp=null;
        Socket sc=null;
        int nombreProd =Integer.parseInt(args[0]);
        int nombreCons=Integer.parseInt(args[1]);
        
         
            try {
		      se = new ServerSocket(4020);
	           
           

		      for(int i=0; i< nombreProd;i++) {
                  synchronized(lock){
                   sp=se.accept();// accepte socket producteurs
		            System.out.println("nbplace libre :"+nbPlaceslibre);
		            ThreadProducteur prod = new ThreadProducteur(sp, nbPlaceslibre);
		            prod.start();
                    
                  }
	            
		      }

		      for(int j=0;j<nombreCons;j++) {
                  synchronized(lock){
		           sc=se.accept();// accepte socket consommateur
                    System.out.println("nb message :"+nbMessages);
		            ThreadConsommateur cons = new ThreadConsommateur(sc, nbMessages);
		            cons.start();
                  }
		      }
		      
		      
            
            } catch (IOException e) {
				e.printStackTrace();
			}
            
            
            
	}
	

}