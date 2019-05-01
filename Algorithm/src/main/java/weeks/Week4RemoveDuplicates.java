package weeks;



/**
 * @Author Alex
 * @Desc
 * <p>
 *
 *
 * 给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
 * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
 *     双指针法
 *     放置两个指针 i和 j，其中 i 是慢指针，而 j 是快指针
 * </p>
 * @Date 2019/4/27 14:01
 */
public class Week4RemoveDuplicates {

    /**
     * nums是有序数组
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        if( nums.length==0){
         return 0;
        }
        //慢指针,i指向最后一个不重复的数，固定地址
        //快指针,j用于向后试探
        int i=0;
        for (int j = 1; j < nums.length; j++) {
            if(nums[i]!=nums[j]){
                i++;
                nums[i]=nums[j];
            }
        }
        //将序号转成个数
        return i+1;
    }

}
