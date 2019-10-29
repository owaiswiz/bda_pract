import java.io.IOException;

import org.apache.hadoop.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;


public class Kmeans {
	public static class Map extends Mapper<LongWritable, Text, IntWritable, Text> {
		float[][] centroids = new float[2][2];
		
		public void setup(Context context) {
			// Random 2 centroids
			centroids[0][0] = 2.3f;
			centroids[0][1] = 1.3f;
			centroids[1][0] = 8.3f;
			centroids[1][1] = 9.3f;
		}
		
		public void map(LongWritable key, Text value, Context context) throws InterruptedException, IOException{
			String line = value.toString();
			String[] coords = line.split(","); // x,y coords
			float[] point = new float[2]; //convert from string to float
			for(int i=0; i<2;i++) {
				point[i] = Float.parseFloat(coords[i]);
			}
			
			int closestCentroid = -1;
			float minDistance = Float.MAX_VALUE;
			
			//Get closest centroid
			for(int i=0; i<2;i++) {
				float distance = (float) (Math.pow(point[0]-centroids[i][0], 2) + Math.pow(point[1]-centroids[i][1], 2));
				if(distance < minDistance) {
					minDistance = distance;
					closestCentroid = i;
				}
			}
			System.out.println(line + " is closest to centroid: " + closestCentroid);
			context.write(new IntWritable(closestCentroid), value);
		}
	}
	
	public static class Reduce extends Reducer<IntWritable, Text, IntWritable, Text> {
		public void reduce(IntWritable centroid, Iterable<Text> values, Context context) throws IOException,InterruptedException{
			float centerX = 0.0f;
			float centerY = 0.0f;
			int no = 0;
			for(Text value: values) {
				String line = value.toString();
				String[] coords = line.split(","); // x,y coords
				float[] point = new float[2]; //convert from string to float
				for(int i=0; i<2;i++) {
					point[i] = Float.parseFloat(coords[i]);
				}
				centerX += point[0];
				centerY += point[1];
				no += 1;
			}
			
			centerX /= no;
			centerY /= no;
			
			String result = String.format("%f %f", centerX, centerY);
			
			context.write(centroid, new Text(result));
		}
	}
	
	public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration config = new Configuration();
		Job job = new Job(config, "kmeans");
		
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(Text.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		FileInputFormat.addInputPath(job, new Path("kmeans_input.txt"));
		FileOutputFormat.setOutputPath(job, new Path("kmeans_output"));
		
		job.waitForCompletion(true);
	}
}
