package _1WordCountExample;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class MyIOUtils {
	private static String hdfsUrl = "hdfs://localhost:9000";
	private static String localInputPath = "src/main/resources/input_data/";
	private static Logger log = Logger.getLogger(MyIOUtils.class);
	//Upload Files into HDFS
	protected static void uploadInputFile(String hdfsInputPath) throws IOException{
		
		File filesList[] = new File(localInputPath).listFiles();
		
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(hdfsUrl), conf);
		Path input_path = new Path(hdfsInputPath);
		
		for(File file:filesList){
			log.info(file.getName().toString()+"File is Writing into HDFS");
			InputStream in = new BufferedInputStream(new FileInputStream(localInputPath+file.getName().toString()));
			FSDataOutputStream out = fs.create(new Path(hdfsInputPath+file.getName().toString()));
			IOUtils.copyBytes(in, out, 4096, true);
			log.info(file.getName().toString()+"File written into HDFS");
		}
		log.info("All Files successfully written into HDFS");
	}
	//Read output from HDFS
	protected static void readFilesFromHdfs(String hdfsOutputPath) throws IOException{
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(hdfsUrl), conf);
		Path output_path = new Path(hdfsOutputPath);
		FileStatus status[] = fs.listStatus(output_path);
		Path files[] = FileUtil.stat2Paths(status);
		
		for(Path file:files){
			log.info("***********"+file.getName().toString()+"*********");
			FSDataInputStream in = fs.open(file);
			IOUtils.copyBytes(in, System.out, 4096, false);
		}
		
	}
}
