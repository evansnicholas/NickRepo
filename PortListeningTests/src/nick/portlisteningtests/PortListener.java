package nick.portlisteningtests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class PortListener {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		try {
			ServerSocket serverSocket = new ServerSocket(1995);
			
			Socket clientSocket = null;
			
			try {
				
				clientSocket = serverSocket.accept();
				
				String inputLine;
				String header = "";
				ArrayList<String> headers = new ArrayList<String>();
				
				InputStreamReader inputStream = new InputStreamReader(clientSocket.getInputStream());
				
				BufferedReader bufferedReader = new BufferedReader(inputStream);
				
				while ((inputLine = bufferedReader.readLine()) != null){
					header = header + "\n" + inputLine;
					headers.add(inputLine);
						if (inputLine.startsWith("Content-Length: ")){
							break;
						}
				}	
				
				System.out.println(header);
				
				
				
				StringBuilder requestContent = new StringBuilder();
				
				/*String contentLengthString = headers.get(headers.size() - 1);
				int contentLength = Integer.parseInt(contentLengthString.substring("Content-Length: ".length()));
				
				for (int i = 0; i < contentLength; i++){
					
					requestContent.append((char) bufferedReader.read());
					
				}*/
				
				System.out.println("");
				
				int expectedMessageLength = 500;
				int numberOfCharsProcessed = 0;
				boolean continueReading = true;
				
				while(continueReading){
					
					
					char currentChar = (char) bufferedReader.read();	
					System.out.print(currentChar);
					requestContent.append(currentChar);
					numberOfCharsProcessed++;
					
					if (numberOfCharsProcessed == expectedMessageLength){
						break;
					}
					
					
						
				}
					
				
				System.out.print(requestContent);
				
				serverSocket.close();
				clientSocket.close();
				bufferedReader.close();
				
			}catch(IOException e){
				
				System.out.println("Accept failed: 1997");
				System.exit(-1);
			}
			
		}catch(IOException e){
			
			System.out.print("Could not listen on port: 1995");
			System.exit(-1);
			
		}
		
		
	}

}
