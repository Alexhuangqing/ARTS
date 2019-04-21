package weeks;

import org.junit.Test;



/**
 * @Author Alex
 * @Desc  编写一个函数来查找字符串数组中的最长公共前缀。 例如 S = n*m (n个长度为m的字符串)
 * <p>
 * 1. 为自己实现的算法  思路与水平扫描法类似
 * 2. 水平扫描法  时间复杂度O（S） 优先根据字符串长度判定
 *  LCP(S1​…Sn​)=LCP(LCP(LCP(S1​,S2​),S3​),…Sn​)
 * 3. 水平扫描法  时间复杂度O（S）  优先根据各个字符串对位是否相同来判定
 * 4. 分治
 * LCP(S1​…Sn​)=LCP(LCP(S1​…Sk​),LCP(Sk+1​…Sn​)) ，其中 LCP(S_1 \ldots S_n)LCP(S1​…Sn​) 是字符串 [S_1 \ldots S_n][S1​…Sn​] 的最长公共前缀，1 < k < n1<k<n。
 *      （1）首先给出最末节点出口，分治的最后出口，一定是只剩下最后一个元素
 *      （2）中间层，分左右，继续分治，并且将左右分治的结果合二为一
 *
 *
 * 5.二分查找算法 O(S⋅log(n)) :答案在目标区间中总能找到，我要做的只是缩小区间
 *  （1）定位结果的区间：最长公共串的必定是所有串的子集，并且该串的长度最大值必定是比n个长度串的最小值
 *   (2) 用mid长度去探测commonPrefix的长度区间，根据探测结果缩小目标区间
 *
 *6.字典树查找最长公共前缀  //todo
 *
 * </p>
 * @Date 2019/4/20 7:16
 */
public class Week3LongestCommonPrefix {
    @Test
    public void test1(){
        String s = longestCommonPrefix1(new String[]{"aa", "a"});
        System.out.println(s);
        String s2 = longestCommonPrefix2(new String[]{"aa", "a"});
        System.out.println(s2);
        String s3 = longestCommonPrefix3(new String[]{"aa", "a"});
        System.out.println(s3);
        String s4 = longestCommonPrefix4(new String[]{"aa", "a"});
        System.out.println(s4);
        String s5 = longestCommonPrefix5(new String[]{"aa", "a"});
        System.out.println(s5);
    }



    public String longestCommonPrefix5(String[] strs){
        //先给出简单情况的答案
        if(strs==null||strs.length==0){
            return "";
        }
        //n个字符串中的长度的最小值
        int high = Integer.MAX_VALUE;
        for(String str :strs){
            high = Math.min(high,str.length());
        }
        //初始化目标长度的最小值与最大值
        int low = 1;
        while(low<=high){
            int mid = (low+high)/2;
            //用mid长度去探测commonPrefix的长度区间，根据探测结果缩小目标区间
            if(isCommonPrefix(strs,mid)){
                low = mid+1;
            }else {
                high = mid-1;
            }
        }
        return strs[0].substring(0,(low+high)/2);

    }

    private boolean isCommonPrefix(String[] strs, int mid) {
        String target = strs[0].substring(0, mid);
        for (int i = 1; i < strs.length; i++) {
            if(!strs[i].startsWith(target)){
                return false;
            }
        }
        return true;
    }


    public String longestCommonPrefix4(String[] strs){
        //一般会先有简单的判断
        if(strs==null||strs.length==0){
            return "";
        }
        return  longestCommonPrefix(strs,0,strs.length - 1);
    }

    /**
     * 分治思想
     *
     * @param strs
     * @param start
     * @param end
     * @return
     */
    private String longestCommonPrefix(String[] strs, int start, int end) {

        if(start==end){
            // 分治的最末节点 ,出口
            return strs[start];
        }else{
            // 分治的中间节点,
            // 将中间层分成左右，继续分治，
            // 将中间层分治结果合并
            int mid = (start+end)/2;
            String lCommonPrefix = longestCommonPrefix(strs,start,mid);
            String rCommonPrefix = longestCommonPrefix(strs,mid+1,end);
            return CommonPrefix(lCommonPrefix,rCommonPrefix);
        }
    }

    private String CommonPrefix(String lCommonPrefix, String rCommonPrefix) {
        int min = Math.min(lCommonPrefix.length(), rCommonPrefix.length());
        //两个字符串可以遍历的最远距离
        for (int i = 0; i < min; i++) {
            if(lCommonPrefix.charAt(i)!=rCommonPrefix.charAt(i)){
            return     lCommonPrefix.substring(0,i);
            }
        }
        return lCommonPrefix.substring(0,min);
    }


    /**
     * 水平扫描法  扫描n个串
     * @param strs
     * @return
     */
    public String longestCommonPrefix2(String[] strs){
        //一般会先有简单的判断
        if(strs==null||strs.length==0){
          return "";
        }
        int length = strs.length;
        String commonPrefix = strs[0]; //公共前缀一定是所有字符串的子集，先给定一个最大值

        //字符串挨个比较
        for (int i = 1; i < length ; i++) {
            while(strs[i].indexOf(commonPrefix)!=0){
                commonPrefix =  commonPrefix.substring(0,commonPrefix.length()-1);
                //当公共字符串没有了，则无需继续比较
                if(commonPrefix.length()==0){return "";}
            }
        }
        return commonPrefix;
    }
    public String longestCommonPrefix3(String[] strs){
        if(strs==null||strs.length==0){
            return "";
        }
        String commonPrefix = strs[0];

        for (int i = 0; i < commonPrefix.length() ; i++) {
            int c = commonPrefix.charAt(i);
            for (int j = 1; j < strs.length ; j++) {
                if(strs[j].length()== i||strs[j].charAt(i)!=c){
                    return commonPrefix.substring(0,i);
                }
            }
        }
        return commonPrefix;
    }








    public String longestCommonPrefix1(String[] strs){
        int length = strs.length;

        String returnStr ="";
        if(length<1){
            return returnStr;
        }else{
            returnStr = strs[0];
        }

        for (int i = 1; i < length&&returnStr.length()>0; i++) {
            returnStr =  commonPrefix(returnStr,strs[i]);
        }

        return returnStr;
    }

        public String commonPrefix(String a,String b){

            int alength = a.length();
            int blength = b.length();
            int i = 0;
            while((i<alength)&&(i<blength)){
                if(a.charAt(i)!=b.charAt(i)){
                    return a.substring(0,i);
                }
                i++;
            }
            if(i ==alength ){
                return a;
            }
            return b;
        }



}
