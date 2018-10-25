import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.StringUtils;

public class AverageGrade {

    public static class GradeMapper
        extends Mapper<Object, Text, Text, FloatWritable> {

        public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
            String[] fields = value.toString().split("\t");
            String course = fields[1];
            FloatWritable grade = new FloatWritable(Float.valueOf(fields[2]));
            context.write(new Text(course), grade);
        }
    }

    public static class AvgReducer
        extends Reducer<Text, FloatWritable, Text, FloatWritable> {
        private FloatWritable result = new FloatWritable();

        public void reduce(Text key, Iterable<FloatWritable> values, Context context)
            throws IOException, InterruptedException {
            float sum = 0;
            int count = 0;
            for(FloatWritable val: values){
                sum += val.get();
                count += 1;
            }
            float avg = sum / count;
            result.set(avg);
            context.write(key, result);
        }
    }
    public static void main(String[] args) throws Exception{
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "avg grader");
        job.setJarByClass(AverageGrade.class);
        job.setMapperClass(GradeMapper.class);
        job.setCombinerClass(AvgReducer.class);
        job.setReducerClass(AvgReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FloatWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0:1);
	}

}
