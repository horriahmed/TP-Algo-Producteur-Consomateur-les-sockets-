
import java.io.*;
import java.net.*;
public class Producteur {
	
    public static void main (String[] args){
        int port=4020;
        InetAddress hote=null;
        Socket s=null;
        BufferedReader in = null;
        PrintWriter out = null;
        String message ="message";
        String demandeP="est ce que je peux produire ??";
    	String rep="";
      
        try{
                if (args.length>=2){
                    hote= InetAddress.getByName(args[0]);
                    port=Integer.parseInt(args[1]);
                }
                else{
                    hote = InetAddress.	getLocalHost();
                    }
                }
            catch(UnknownHostException e){
                    System.err.println("Machine inconnue :" +e);
        }
        
        
        try{
            s = new Socket(hote,port);
            in = new BufferedReader( 
                                 new InputStreamReader(s.getInputStream())
                                );
                            
            out = new PrintWriter(s.getOutputStream(),true);
            
        }
        catch(IOException e){
            System.err.println("Impossible de creer la socket du Producteur : " +e);
        }
               
         	// demande au serveur esq je peux produire 
        	
            
           out.println(demandeP);// envoi demande de production 
           out.flush();
           try {
				 rep=in.readLine(); // reponse 
             System.out.println("la reponse est : "+rep);
			} catch (IOException e) {
				e.printStackTrace();
			}
           
           if(rep.equals("oui")) { // autorisation de production
        	   // envoi de message 
        	   out.println(message);
               out.flush();
        	   
           }
           else
           {
        	   System.out.println("y a pas de place dans le tempon pour produire");
	        	   try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
           }


       
        
        
	
    }// main
}