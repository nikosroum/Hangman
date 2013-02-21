/**
 *
 * @author Natalia Paratsikidou
 * @author Nikolaos Roumpoutsos
 * 
 */

package server;

import java.io.Serializable;

public interface Task extends Serializable {
	public void execute();
}