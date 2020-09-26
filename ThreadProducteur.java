import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ThreadProducteur extends Thread {

	PrintStream out=null;
	BufferedReader in=null;
	public static int nbplacelibre;
    public static int i=0;
	Socket sp=null;
	
	public ThreadProducteur( Socket s,int nbPlaceLibre) {
		sp=s;
		nbplacelibre=nbPlaceLibre;
	}
	@Override
	public void run() {
		
        

		try {
			out = new PrintStream(sp.getOutputStream());
			in = new BufferedReader(new InputStreamReader(sp.getInputStream()));
		
		String message="";
		message=in.readLine();
		
		while(message !=null) { // tanque y a une demande de production 
			
			// reponse a la demande de production
			
            System.out.println("demande de production : "+message);
				
			if(message.equals("est ce que je peux produire ??")) {
				if(nbplacelibre>0){
					out.println("oui");
                    out.flush();
                }
				else {
					out.println("non");
                    out.flush();
                }
			}
			
			// production 
			message=in.readLine(); // le message a produire
           
			
			
			if( message!=null) {
				try {
                    i++;
                    message = message+i;
                    System.out.println("le message a produire est :"+message );
					SiteT.produire(message);
                    //this.notify();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			message=in.readLine(); // lire la demande de production
			
		}// fin while
		
		
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
        /*finally {
			try {
				in.close();
				out.close();
				sp.close();
			} catch (IOException e) {}
			
		} // fin finaly*/
	}//fin run

}
