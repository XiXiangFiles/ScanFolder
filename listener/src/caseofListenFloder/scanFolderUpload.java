package caseofListenFloder;

import java.io.File;
import java.util.ArrayList;


public interface scanFolderUpload {
	public ArrayList<String> scan();
	public int uploadFTP(String ID , String PWD, String URL , String FOLDER,String filename);
}
