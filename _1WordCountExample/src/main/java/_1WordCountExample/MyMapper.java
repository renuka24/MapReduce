package _1WordCountExample;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

public class MyMapper extends Mapper<LongWritable,Text,Text,IntWritable>{
	//Logger
	Logger log = Logger.getLogger(MyMapper.class);
	//default constructor
	//Mapper instance is created for every block
	public MyMapper() {
		log.info("Calling Default Constructor");
		log.info("MyMapper()");
	}
	//MapReduce Lifecycle - Step 1 (run)
	public void run(Context context) throws IOException, InterruptedException{
		log.info("Mapper Run Method");
		super.run(context);
	}
	//MapReduce Lifecycle - Step 2 (setup)
	protected void setup(Context context){
		log.info("Mapper setup Method");
	}
	//MapReduce Lifecycle - Step 3 (map Method)
	//Mapper method is called for every line
	//Map Method
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		log.info("MyMapper.map()");
		log.info("Current Line" +value);
		//Key - 0
		//value - apple ball apple ball
		String currentline = value.toString();
		String words[] = currentline.split(" ");
		
		for(String word:words) {
			context.write(new Text(word),new IntWritable(1));
		}
	}
	//MapReduce Lifecycle - Step 4 (cleanup)
	protected void cleanup(Context context){
		log.info("Mapper cleanup Method");
	}
}
