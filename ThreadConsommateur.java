
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ThreadConsommateur extends Thread{

	PrintStream out=null;
	BufferedReader in=null;
	public static int nbMessage;
	Socket sc;
	
	public ThreadConsommateur( Socket s,int nbmessage) {
		sc=s;
		nbMessage=nbmessage;
	}
	
	@Override
	public void run() {
		
       /* while(nbMessage<=0){
            try{
                this.wait();
            } catch (InterruptedException e) {}
        }*/

	try {
					out = new PrintStream(sc.getOutputStream());
					in = new BufferedReader(new InputStreamReader(sc.getInputStream()));
				
                	String message="";
					message=in.readLine();
				
				
				while(message !=null ) { // tanque y a une demande de Consomation
					// reponse a la demande de consommation
							
                                System.out.println("demande de cosommation :" +message);
									
								if(message.equals("est ce que je peux Consommer ??")) {
									if(nbMessage>0){
										out.println("oui");
                                        out.flush();
                                    }
									else {
										out.println("non");
                                        out.flush();
                                    }
								}
								
								try {
									message=SiteT.consommer();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								
								if(message!=null) {
									out.println(message); // envoyer le message consomme
                                    out.flush();
								}
								message=in.readLine(); // lire la demande de consommation
				}// fin while
				
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
        }
		/*finally {
			try {
				in.close();
				out.close();
				sc.close();
			} catch (IOException e) {}

		}*/ // fin finaly
	}//fin run

}

