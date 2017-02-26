package models;

import medianbased.CloneDetector;
import utility.BlockInfo;
import utility.Util;

public class Block implements Comparable<Block> {
    
    public int project_id;
    public int file_id;
    public int numTokens;
    public int minNumTokens;
    public int maxNumTokens;
    public float median;
    public float min_median;
    public float max_median;
    public float mean;
    public float min_mean;
    public float max_mean;
    public float variance;
    public float minVariance;
    public float maxVariance;
    //public long numChars;
    public String tokenHash;
    public long minNumChars;
    public long maxNumChars;
    public int uniqueTokens;
    public int minUniqueTokens;
    public int maxUniqueTokens;
    public float stdDev;
    public float minStdDev;
    public float maxStdDev;
    public float mad;
    public float minMad;
    public float maxMad;
    public float metric;
    public float minMetric;
    public float maxMetric;
    
    
    public Block(float median,float mean, int project_id, int file_id, int numTokens,float stdDev, float variance, String tokenHash, int uniqueTokens, float mad) {
        this.mean=mean;
        this.median = median;
        this.project_id = project_id;
        this.file_id = file_id;
        this.numTokens = numTokens;
        this.variance = variance;
        this.tokenHash = tokenHash;
        this.uniqueTokens=uniqueTokens;
        this.stdDev=stdDev;
        this.mad = mad;
        
        this.min_median = BlockInfo.getMinimumSimilarityThreshold(this.median, CloneDetector.th);
        this.max_median = BlockInfo.getMaximumSimilarityThreshold(this.median, CloneDetector.th);

        this.min_mean=BlockInfo.getMinimumSimilarityThreshold(this.mean,CloneDetector.th);
        this.max_mean=BlockInfo.getMaximumSimilarityThreshold(this.mean,CloneDetector.th);

        this.minNumTokens = BlockInfo.getMinimumSimilarityThreshold(this.numTokens, CloneDetector.th);
        this.maxNumTokens = BlockInfo.getMaximumSimilarityThreshold(this.numTokens, CloneDetector.th);
        
        this.minVariance = BlockInfo.getMinimumSimilarityThreshold(this.variance, CloneDetector.th);
        this.maxVariance = BlockInfo.getMaximumSimilarityThreshold(this.variance, CloneDetector.th);

        //change it later to reflect token hash
        //this.minNumChars = BlockInfo.getMinimumSimilarityThreshold(this.numChars, CloneDetector.th-(1.5f*Util.MUL_FACTOR));
        //this.maxNumChars = BlockInfo.getMaximumSimilarityThreshold(this.numChars, CloneDetector.th-(1.5f*Util.MUL_FACTOR));
        
        this.minUniqueTokens = BlockInfo.getMinimumSimilarityThreshold(this.uniqueTokens, CloneDetector.th);
        this.maxUniqueTokens = BlockInfo.getMaximumSimilarityThreshold(this.uniqueTokens, CloneDetector.th);
        
        this.minStdDev = BlockInfo.getMinimumSimilarityThreshold(this.stdDev, CloneDetector.th-(1.5f*Util.MUL_FACTOR));
        this.maxStdDev = BlockInfo.getMaximumSimilarityThreshold(this.stdDev, CloneDetector.th-(1.5f*Util.MUL_FACTOR));
        
        this.minMad = BlockInfo.getMinimumSimilarityThreshold(this.mad, CloneDetector.th-Util.MUL_FACTOR);
        this.maxMad = BlockInfo.getMaximumSimilarityThreshold(this.mad, CloneDetector.th-Util.MUL_FACTOR);
    }
    
    @Override
    public int compareTo(Block o) {
        if (this.metric<o.metric){
            return -1;
        }else if (this.metric==o.metric){
            return 0;
        }else{
            return 1;
        }
    }



    @Override
    public String toString() {
        return "Block [project_id=" + project_id + ", file_id=" + file_id
                + ", numTokens=" + numTokens + ", minNumTokens=" + minNumTokens
                + ", maxNumTokens=" + maxNumTokens + ", median=" + median
                + ", min_median=" + min_median + ", max_median=" + max_median
                + ", variance=" + variance + ", minVariance=" + minVariance
                + ", maxVariance=" + maxVariance + ", tokenHash=" + tokenHash
                + ", minNumChars=" + minNumChars + ", maxNumChars="
                + maxNumChars + ", uniqueTokens=" + uniqueTokens
                + ", minUniqueTokens=" + minUniqueTokens + ", maxUniqueTokens="
                + maxUniqueTokens + ", stdDev=" + stdDev + ", minStdDev="
                + minStdDev + ", maxStdDev=" + maxStdDev + ", mad=" + mad
                + ", minMad=" + minMad + ", maxMad=" + maxMad + ", metric="
                + metric + ", minMetric=" + minMetric + ", maxMetric="
                + maxMetric + "]";
    }

    public void setMetric(float statistic,int includeWidth) {
        this.metric = statistic;
        int width = -Util.MUL_FACTOR * includeWidth;
        this.minMetric = BlockInfo.getMinimumSimilarityThreshold(this.metric, CloneDetector.th+width);
        this.maxMetric = BlockInfo.getMaximumSimilarityThreshold(this.metric, CloneDetector.th+width);
    }

}