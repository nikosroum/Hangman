/**
 *
 * @author Natalia Paratsikidou
 * @author Nikolaos Roumpoutsos
 * 
 */
package client;

import server.Client2Server;
import server.Server2Client;

public interface ClientInterface {
	public void setEnvironment(Server2Client a);
        public Server2Client sendclientanswer(Client2Server clientanswer);
}