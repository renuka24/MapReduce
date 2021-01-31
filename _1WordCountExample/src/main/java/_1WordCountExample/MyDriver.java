/**
 * 
 */
package _1WordCountExample;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.Logger;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;

/**
 * @author hduser
 *
 */
public class MyDriver {

	//Input and Output Paths
    public static final String INPUT_DIR = "hdfs://localhost:9000/user/hduser/wordcount/input_data/";
	public static final String OUTPUT_DIR = "hdfs://localhost:9000/user/hduser/wordcount/output_data/";
	public static boolean job_success_flag;
	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		// TODO Auto-generated method stub
		//File Paths
		Path input_path = new Path(INPUT_DIR);
		Path output_path = new Path(OUTPUT_DIR);
		//call static method to upload files from localto HDFS
		MyIOUtils.uploadInputFile(INPUT_DIR);
		Logger log = Logger.getLogger(MyDriver.class);
		//Configuration - this object is used to read hdfs configuration
		Configuration conf = new Configuration();
		//create job to submit to hdfs
		Job job = Job.getInstance(conf,"MyWordCountJob");
		//set jar by class (main class)
		job.setJarByClass(MyDriver.class);
		//set mapper class
		job.setMapperClass(MyMapper.class);
		//set reducer class
		job.setReducerClass(MyReducer.class);
		
		//Set Mapper datatypes
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		//Set Reducer datatypes
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		//set input and output directory
		FileInputFormat.addInputPath(job, input_path);
		FileOutputFormat.setOutputPath(job, output_path);
		output_path.getFileSystem(conf).delete(output_path, true);
		//job success flag
		job_success_flag = job.waitForCompletion(true);
		if(job_success_flag) {
			log.info("Job is Successful");
			MyIOUtils.readFilesFromHdfs(OUTPUT_DIR);
		} else {
			log.info("Job is Failed ..... Please check logs");
		}
	}

}
