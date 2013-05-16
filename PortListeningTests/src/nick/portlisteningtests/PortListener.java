package nick.portlisteningtests;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class PortListener implements Runnable{

	
	public void run(){
			
		try {
			ServerSocket serverSocket = new ServerSocket(1995);
			
			Socket clientSocket = null;
			DataOutputStream out = null;
			
			
			try {
				
				clientSocket = serverSocket.accept();
				
				System.out.println("The connection was accepted");
				
				String inputLine;
				String header = "";
				ArrayList<String> headers = new ArrayList<String>();
				
				InputStreamReader inputStream = new InputStreamReader(clientSocket.getInputStream());
				
				//BufferedReader bufferedReader = new BufferedReader(inputStream);
				
				
				/*while ((inputLine = bufferedReader.readLine()) != null){
					header = header + "\n" + inputLine;
					headers.add(inputLine);
						if (inputLine.startsWith("Content-Length: ")){
							break;
						}
				}*/	
				
				
				
				System.out.println(header);
				
				
				
				StringBuilder requestContent = new StringBuilder();
				
				/*String contentLengthString = headers.get(headers.size() - 1);
				int contentLength = Integer.parseInt(contentLengthString.substring("Content-Length: ".length()));
				
				for (int i = 0; i < contentLength; i++){
					
					requestContent.append((char) bufferedReader.read());
					
				}*/
				
				System.out.println("");
				
				boolean continueReading = true;
				int listenTimeMillis = 1000;		
				long startTime = System.currentTimeMillis();
				int testCount = 0;
				
				while(continueReading){
					
					testCount++;
					
					if (testCount == 10){
						break;
					}
					int listenDuration = (int) (System.currentTimeMillis() - startTime);
					
					//System.out.print(listenDuration + " ");
					
					if (listenDuration > listenTimeMillis){
						break;
					}
					
					char currentChar = (char) inputStream.read();
					System.out.print(currentChar);
					requestContent.append(currentChar);
						
				}
					
				System.out.println("Now trying to send response");
				System.out.print(requestContent);
				
				out = new DataOutputStream(clientSocket.getOutputStream());
				out.writeBytes("HTTP/1.1 200 OK\r\n");
				out.writeBytes("Content-Type: text/html\r\n\r\n");
				out.writeBytes("<html> <head> A Response from port sniffer </head> </html>");
				
				out.flush();
				//bufferedReader.close();
				clientSocket.close();
				serverSocket.close();
				
				
				
			}catch(IOException e){
				
				System.out.println("Accept failed: 1995");
				System.out.println(e.getMessage());
				System.exit(-1);
			}
			
		}catch(IOException e){
			
			System.out.print("Could not listen on port: 1995");
			System.exit(-1);
			
		}
		
	}
	

}
