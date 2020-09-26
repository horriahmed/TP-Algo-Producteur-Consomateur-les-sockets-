
import java.io.*;
import java.net.*;
public class Consommateur {

    public static void main (String[] args){
        int port=4020;
        InetAddress hote=null;
        Socket s=null;
        BufferedReader in = null;
        PrintWriter out = null;
        String messageC=""; // message a consommer
        String demandeC="est ce que je peux Consommer ??";
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
            System.err.println("Impossible de creer la socket du Consommateur : " +e);
        }
        
         	// demande au serveur esq je peux consomme
        	
            
           out.println(demandeC);// envoi demande de production 
           out.flush();
           try {
				 rep=in.readLine(); // reponse 
			} catch (IOException e) {
				e.printStackTrace();
			}
           
           if(rep.equals("oui")) { // autorisation de consomation
	        	   try {
					messageC = in.readLine(); // lecture du message consomme
				} catch (IOException e) {
					e.printStackTrace();
				} 
	        	   System.out.println("le message consomme est : "+messageC );
	        	   
           }
           else
           {
        	   System.out.println(" y a pas de message a consomme");
	        	   try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
           }

       
        
        
	
    }// main
}