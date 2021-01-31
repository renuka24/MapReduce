package _1WordCountExample;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Iterator;

public class MyReducer extends Reducer<Text,IntWritable,Text,IntWritable>{
	//Reducer instance is created only once by default in local mode
	//It varies based on number of reducers configured for the particular application
	//Reducer method is called for each group
	//Define logger
	Logger log = Logger.getLogger(MyReducer.class);
	//Default constructor
	public MyReducer() {
		log.info("Default Constructor");
		log.info("MyReducer()");
	};
	//MapReduce Lifecycle - Step 1 (run)
	public void run(Context context) throws IOException, InterruptedException{
		log.info("Reducer Run Method");
		super.run(context);
	}
	//MapReduce Lifecycle - Step 2 (setup)
	protected void setup(Context context){
		log.info("Reducer setup Method");
	}
	//MapReduce Lifecycle - Step 3 (reduce method)
	//Override Default reduce method
	protected void reduce(Text word,Iterable<IntWritable> values,Context context) throws IOException, InterruptedException{
		log.info("MyReducer.reduce()");
		log.info("key"+word);
		log.info("values"+values);
		Iterator<IntWritable> it = values.iterator();
		int count = 0;
		while(it.hasNext()){
			count++;
			it.next();
		}
		context.write(word, new IntWritable(count));
	}
	//MapReduce Lifecycle - Step 2 (setup)
	protected void cleanup(Context context){
		log.info("Reducer cleanup Method");
	}
}
