import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import java.util.*;
import java.io.*;

public class WordCount {
	public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {
		public void map(LongWritable key, Text value, Context context) throws InterruptedException,IOException{
			StringTokenizer tokens = new StringTokenizer(value.toString());
			while(tokens.hasMoreTokens()) {
				String word = tokens.nextToken();
				Text t = new Text(word);
				context.write(t, new IntWritable(1));
			}
		}
	}
	
	public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable>{
		
		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException  {
			int value = 0;
			for(IntWritable i : values) {
				value += i.get();
			}
			context.write(key, new IntWritable(value));
		}
	}

	public static void main(String args[]) throws IOException, InterruptedException, ClassNotFoundException{
		Configuration config = new Configuration();
		Job job = new Job(config, "wordcount");
		
		job.setMapperClass(WordCount.Map.class);
		job.setReducerClass(WordCount.Reduce.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		FileInputFormat.addInputPath(job, new Path("wordcount_input.txt"));
		FileOutputFormat.setOutputPath(job, new Path("wordcount_output"));
		
		job.waitForCompletion(true);
	}
}
