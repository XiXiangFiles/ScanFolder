package caseofListenFloder;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.net.ftp.FTPClient;

import caseofListenFloder.scanFolderUpload;


class folderScanUpload implements scanFolderUpload{

	String path;
	folderScanUpload(String path){
		this.path=path;
	}
	@Override
	public ArrayList<String> scan() {
		// TODO Auto-generated method stub
		
		ArrayList<String> filenames=new ArrayList();
		File filelist=new File(this.path);
		File[] files= filelist.listFiles();
		
		for( File f  :files) {
			filenames.add(f.getName());
		}
		return filenames;
		
	}
	public int uploadFTP(String username , String password, String server , String FOLDER ,String filename) {
		
		
		FTPClient ftp = new FTPClient();
		try {
			ftp.connect(server,21);
			ftp.login(username, password);
			
			
			File filelist=new File(this.path);
			File[] files= filelist.listFiles();
			for( File f  :files) {
				
				if(f.getName().equals(filename)) {
					System.out.println("Test"+filename);
		            InputStream inputStream = new FileInputStream(f);
		            ftp.changeWorkingDirectory(FOLDER);
		            ftp.setFileType(ftp.BINARY_FILE_TYPE);
		            boolean done = ftp.storeFile(filename, inputStream);
		            if (done) {
		                System.out.println("The first file is uploaded successfully.");
		            }
		            inputStream.close();
				}
			}
			
		}catch(Exception e) {
			System.out.println("Error ="+ e.toString());
		}
		
		return 0;
	}
	
}
public class main {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Timer timer = new Timer(false);
		
		TimerTask task = new TimerTask() {
			
			String path="C:\\\\Users\\\\perocm\\\\Desktop\\\\test_create_mp4\\\\output";
			folderScanUpload fs = new folderScanUpload(path);
			ArrayList<String> oldFilenames =fs.scan();	
			int cnt=0;

			@Override
			public void run() {
				
				folderScanUpload fs1 = new folderScanUpload(path);
				ArrayList<String> newFilenames =fs1.scan();
				
				newFilenames.forEach((element)->{
					
					if(oldFilenames.contains(element)==false) {
						
						System.out.println(element);
						fs1.uploadFTP("pi", "nccutest", "140.119.163.195","/home/pi/Desktop", element);
						
						
					}
					
				});
				
				folderScanUpload fs2 = new folderScanUpload(path);
				oldFilenames=fs2.scan();
				
			}
		};
		
		timer.schedule(task, 0, 1000);
	}

}
