package week1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author Alex
 * @Desc
 * <p>
 *
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 *
 * 示例:
 *
 * 给定 nums = [2, 7, 11, 15], target = 9
 *
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 *
 *
 * </p>
 * @Date 2019/4/5 10:47
 */


public class TwoSum {


    public static void main(String[] args) {
        int[]  nums = new int[]{2, 7, 11, 15};
        int target = 9;
        TwoSum tesTwoSum = new TwoSum();
        int[] ints = tesTwoSum.twoSum1(nums, target);
        int[] int2s = tesTwoSum.twoSum2(nums, target);
        int[] int3s = tesTwoSum.twoSum3(nums, target);
        System.out.println(ints[0]+""+ints[1]);
        System.out.println(int2s[0]+""+int2s[1]);
        System.out.println(int3s[0]+""+int3s[1]);
    }


    /**
     *
     * 时间复杂度：O(n)， 我们把包含有 n个元素的列表遍历两次。由于哈希表将查找时间缩短到 O(1) ，所以时间复杂度为 O(n)。
     *
     * 空间复杂度：O(n)， 所需的额外空间取决于哈希表中存储的元素数量，该表中存储了 n个元素。
     */
    public int[] twoSum2(int[] nums, int target) {
        //1.记录数值对应的下标 扩展空间
        //2.遍历数组，直到取到非本身的索引元素

        Map<Integer,Integer> valueIndexMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            valueIndexMap.put(nums[i],i);
        }

        for (int i = 0; i < nums.length; i++) {
            int temp = target - nums[i];
            if(valueIndexMap.containsKey(temp)&&(valueIndexMap.get(temp)!=i )){
                return new int[]{valueIndexMap.get(temp),i};
            }
        }


        return null;
    }

    public int[] twoSum3(int[] nums, int target) {
        //用一次遍历  边遍历 边扩展空间
        //记录经历失败的
        Map<Integer, Integer> valueIndexMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int value = target - nums[i];
            if (valueIndexMap.containsKey(value)) {
                return new int[]{i, valueIndexMap.get(value)};
            }
            //valueIndexMap 保存的没有获取成功的索引，能够与当前索引区分开
            valueIndexMap.put(nums[i], i);
        }
        return null;
    }




    /**
     * 利用排序去遍历
     * fail :不能通过测试  改变本身的数据结构 导致在数组中值相同的索引会被覆盖
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum1(int[] nums, int target) {
        //1.记录数值对应的下标
        //2.从小到大排序
        //3.定义首指针，与尾指针，遍历
        Map<Integer,Integer> indexMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            indexMap.put(nums[i],i);
        }


        Arrays.sort(nums);

        int start = 0 ;
        int end = nums.length - 1 ;


        while(start < end){
            int min = nums[start];
            int max = nums[end];
            if(min + max == target ){
                return new int[]{indexMap.get(min),indexMap.get(max)};
            }


            if(min + max < target){
                start++;
                continue;
            }
            if(min + max > target){
                end--;

            }
        }


        return null;

    }
}
